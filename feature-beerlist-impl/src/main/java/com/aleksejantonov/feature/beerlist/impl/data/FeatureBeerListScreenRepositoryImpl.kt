package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.api.BeersApi
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.ui.base.PagingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import kotlin.Exception

@RootScope
class FeatureBeerListScreenRepositoryImpl @Inject constructor(
  private val beersApi: BeersApi,
  private val databaseApi: CoreDatabaseApi
) : FeatureBeerListScreenRepository {

  private val beersChannel = ConflatedBroadcastChannel<PagingState<BeerModel>>()
  private val busy = AtomicBoolean(false)
  private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
  private var job: Job? = null
  private var allLoadedEnd: Boolean = false

  override suspend fun data(): Flow<PagingState<BeerModel>> {
    job = scope.launch { initialData().collect { beersChannel.send(it) } }
    return beersChannel.asFlow()
  }

  override suspend fun loadMore() {
    if (busy.compareAndSet(false, true) && !beersChannel.value.allLoadedEnd) {
      job?.cancel()
      job = scope.launch {
        databaseApi.beersStore().beersData(DEFAULT_LIMIT + beersChannel.value.itemCount(), 0)
          .map { PagingState(data = it) }
          .onStart {
            try {
              val beers = beersApi.getBeers(page = beersChannel.value.itemCount() / DEFAULT_LIMIT + 1, limit = DEFAULT_LIMIT)
              allLoadedEnd = beers.size < DEFAULT_LIMIT
              databaseApi.beersStore().insertBeers(beers)
            } catch (e: Exception) {
            }
          }
          .collect { beersChannel.send(it) }
      }
      busy.set(false)
    }
  }

  private fun initialData(): Flow<PagingState<BeerModel>> {
    return databaseApi.beersStore().beersData(DEFAULT_LIMIT, 0)
      .map { PagingState(data = it) }
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