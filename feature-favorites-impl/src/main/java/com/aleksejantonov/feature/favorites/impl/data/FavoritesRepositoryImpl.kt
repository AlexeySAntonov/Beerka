package com.aleksejantonov.feature.favorites.impl.data

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

@RootScope
class FavoritesRepositoryImpl @Inject constructor(
  private val databaseApi: CoreDatabaseApi
) : FavoritesRepository {

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
        databaseApi.beersStore().favoriteBeersData(DEFAULT_LIMIT + beersChannel.value.itemCount(), 0)
          .map { PagingState(data = it) }
          .collect { beersChannel.send(it) }
      }
      busy.set(false)
    }
  }

  private fun initialData(): Flow<PagingState<BeerModel>> {
    return databaseApi.beersStore().favoriteBeersData(DEFAULT_LIMIT, 0)
      .map { PagingState(data = it) }
  }

  private companion object {
    const val DEFAULT_LIMIT = 20
  }
}