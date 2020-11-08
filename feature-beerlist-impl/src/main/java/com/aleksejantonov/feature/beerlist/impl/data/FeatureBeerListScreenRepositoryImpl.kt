package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.api.BeersApi
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.model.BeerModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@RootScope
class FeatureBeerListScreenRepositoryImpl @Inject constructor(
    private val beersApi: BeersApi,
    private val databaseApi: CoreDatabaseApi
) : FeatureBeerListScreenRepository {

    private val beersChannel = ConflatedBroadcastChannel<List<BeerModel>>()

    override suspend fun data(): Flow<List<BeerModel>> {
        val beers = beersApi.getBeers()
        return beersChannel.asFlow().onStart { emit(beers) }
    }
}