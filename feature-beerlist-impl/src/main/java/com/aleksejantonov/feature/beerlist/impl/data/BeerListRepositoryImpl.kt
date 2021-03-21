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
import timber.log.Timber
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
      filterDataMediator.filterDataFlow
        .onStart { emit(FilterModel.default()) }
        .collect {
          job?.cancel()
          job = scope.launch {
            allLoadedEnd = false
            initialData(it).collect {
              beersChannelFlow.tryEmit(it)
            }
          }
        }
    }
  }

  override suspend fun data(): Flow<PagingState<BeerModel>> {
    return beersChannelFlow
  }

  override suspend fun loadMore() {
    if (busy.compareAndSet(false, true) && !beersChannelFlow.replayCache[0].allLoadedEnd) {
      job?.cancel()
      job = scope.launch {
        val filterRequest = filterDataMediator.filterDataFlow.replayCache.getOrNull(0) ?: FilterModel.default()
        databaseApi.beersStore().beersData(DEFAULT_LIMIT + beersChannelFlow.replayCache[0].itemCount(), 0, filterRequest)
          .map { PagingState(data = it, allLoadedEnd = allLoadedEnd) }
          .onStart {
            try {
              val beers = beersApi.getBeers(
                page = beersChannelFlow.replayCache[0].itemCount() / DEFAULT_LIMIT + 1,
                limit = DEFAULT_LIMIT,
                filterRequest = filterRequest
              )
              allLoadedEnd = beers.size < DEFAULT_LIMIT
              databaseApi.beersStore().insertBeers(beers)
            } catch (e: Exception) {
              Timber.e(e)
            }
          }
          .collect { beersChannelFlow.tryEmit(it) }
      }
      busy.set(false)
    }
  }

  override suspend fun toggleFavorite(id: Long) {
    databaseApi.beersStore().toggleFavorite(id)
  }

  private fun initialData(filterRequest: FilterModel): Flow<PagingState<BeerModel>> {
    busy.set(false)
    return databaseApi.beersStore().beersData(DEFAULT_LIMIT, 0, filterRequest)
      .map { PagingState(data = it, allLoadedEnd = allLoadedEnd) }
      .onStart {
        try {
          val beers = beersApi.getBeers(page = 1, limit = DEFAULT_LIMIT, filterRequest = filterRequest)
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