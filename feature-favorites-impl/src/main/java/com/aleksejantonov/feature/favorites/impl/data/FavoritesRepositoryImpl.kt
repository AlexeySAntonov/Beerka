package com.aleksejantonov.feature.favorites.impl.data

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.ui.base.PagingState
import com.aleksejantonov.core.util.value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@RootScope
class FavoritesRepositoryImpl @Inject constructor(
  private val databaseApi: CoreDatabaseApi
) : FavoritesRepository {

  private val beersChannelFlow = MutableSharedFlow<PagingState<BeerModel>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  private val busy = AtomicBoolean(false)
  private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
  private var job: Job? = null

  override suspend fun data(): Flow<PagingState<BeerModel>> {
    job = scope.launch { initialData().collect { beersChannelFlow.tryEmit(it) } }
    return combine(
      beersChannelFlow,
      databaseApi.beersStore().favoriteBeersCountData()
    ) { state, actualCount ->
      state.copy(allLoadedEnd = actualCount.toInt() == state.itemCount())
    }
  }

  override suspend fun loadMore() {
    if (busy.compareAndSet(false, true)) {
      job?.cancel()
      job = scope.launch {
        databaseApi.beersStore().favoriteBeersData(DEFAULT_LIMIT + (beersChannelFlow.value(0)?.itemCount() ?: 0), 0)
          .map { PagingState(data = it) }
          .collect { beersChannelFlow.tryEmit(it) }
      }
      busy.set(false)
    }
  }

  override suspend fun removeFromFavorites(id: Long) {
    databaseApi.beersStore().setFavorite(id, favorite = false)
  }

  private fun initialData(): Flow<PagingState<BeerModel>> {
    return databaseApi.beersStore().favoriteBeersData(DEFAULT_LIMIT, 0)
      .map { PagingState(data = it) }
  }

  private companion object {
    const val DEFAULT_LIMIT = 8
  }
}