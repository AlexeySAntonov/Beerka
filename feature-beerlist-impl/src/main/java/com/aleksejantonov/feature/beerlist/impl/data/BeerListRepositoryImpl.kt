package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.api.BeersApi
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.model.BeerModel
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
  private val databaseApi: CoreDatabaseApi
) : BeerListRepository {

  private val beersChannelFlow = MutableSharedFlow<PagingState<BeerModel>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  private val busy = AtomicBoolean(false)
  private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
  private var job: Job? = null
  private var allLoadedEnd: Boolean = false

  override suspend fun data(): Flow<PagingState<BeerModel>> {
    job = scope.launch { initialData().collect { beersChannelFlow.tryEmit(it) } }
    return beersChannelFlow
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