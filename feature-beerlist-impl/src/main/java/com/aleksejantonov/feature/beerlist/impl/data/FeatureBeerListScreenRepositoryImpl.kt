package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.api.BeersApi
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.ui.base.PagingState
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@RootScope
class FeatureBeerListScreenRepositoryImpl @Inject constructor(
  private val beersApi: BeersApi,
  private val databaseApi: CoreDatabaseApi
) : FeatureBeerListScreenRepository {

  private val beersChannel = ConflatedBroadcastChannel<PagingState<BeerModel>>()
  private val busy = AtomicBoolean(false)

  override suspend fun data(): Flow<PagingState<BeerModel>> {
    val beers = beersApi.getBeers(page = 1, limit = DEFAULT_LIMIT)
    beersChannel.send(PagingState(data = beers))
    return beersChannel.asFlow()
  }

  override suspend fun loadMore() {
    if (busy.compareAndSet(false, true) && !beersChannel.value.allLoadedEnd) {
      val beers = beersApi.getBeers(page = beersChannel.value.itemCount() / DEFAULT_LIMIT + 1, limit = DEFAULT_LIMIT)

      beersChannel.send(
        PagingState(
          data = beersChannel.value.data.toMutableList().apply { addAll(beers) },
          allLoadedEnd = beers.size < DEFAULT_LIMIT
        )
      )

      busy.set(false)
    }
  }

  private companion object {
    const val DEFAULT_LIMIT = 20
  }
}