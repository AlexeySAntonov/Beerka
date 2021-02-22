package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.api.BeersApi
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.mediator.api.FilterDataMediator
import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.model.FilterModel
import com.aleksejantonov.core.ui.base.PagingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import kotlin.Exception

@RootScope
class BeerListRepositoryImpl @Inject constructor(
  private val beersApi: BeersApi,
  private val databaseApi: CoreDatabaseApi,
  private val filterDataMediator: FilterDataMediator
) : BeerListRepository {

  private val beersChannelFlow = MutableSharedFlow<PagingState<BeerModel>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  private val busy = AtomicBoolean(false)
  private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
  private var job: Job? = null
  private var allLoadedEnd: Boolean = false

  init {
    scope.launch {
      filterDataMediator.filterDataFlow.collect {  }
    }
  }

  override suspend fun data(): Flow<PagingState<BeerModel>> {
    job = scope.launch { initialData().collect { beersChannelFlow.tryEmit(it) } }
    return beersChannelFlow
//    return combine(
//      beersChannelFlow,
//      filterDataMediator.filterDataFlow.onStart { emit(FilterModel.default()) }
//    ) { state, filter ->
//      state.copy(data = state.data.filter { beer ->
//        beer.abv in filter.abvPair.first..filter.abvPair.second
//            && beer.ibu in filter.ibuPair.first..filter.ibuPair.second
//            && beer.ebc in filter.ebcPair.first..filter.ebcPair.second
//      })
//    }
  }

  override suspend fun loadMore() {
    if (busy.compareAndSet(false, true) && !beersChannelFlow.replayCache[0].allLoadedEnd) {
      job?.cancel()
      job = scope.launch {
        databaseApi.beersStore().beersData(DEFAULT_LIMIT + beersChannelFlow.replayCache[0].itemCount(), 0)
          .map { PagingState(data = it, allLoadedEnd = allLoadedEnd) }
          .onStart {
            try {
              val beers = beersApi.getBeers(page = beersChannelFlow.replayCache[0].itemCount() / DEFAULT_LIMIT + 1, limit = DEFAULT_LIMIT)
              allLoadedEnd = beers.size < DEFAULT_LIMIT
              databaseApi.beersStore().insertBeers(beers)
            } catch (e: Exception) {
            }
          }
          .collect { beersChannelFlow.tryEmit(it) }
      }
      busy.set(false)
    }
  }

  private fun initialData(): Flow<PagingState<BeerModel>> {
    return databaseApi.beersStore().beersData(DEFAULT_LIMIT, 0)
      .map { PagingState(data = it, allLoadedEnd = allLoadedEnd) }
      .onStart {
        try {
          val beers = beersApi.getBeers(page = 1, limit = DEFAULT_LIMIT)
          allLoadedEnd = beers.size < DEFAULT_LIMIT
          databaseApi.beersStore().insertBeers(beers)
        } catch (e: Exception) {
        }
      }
  }

  private companion object {
    const val DEFAULT_LIMIT = 20
  }
}