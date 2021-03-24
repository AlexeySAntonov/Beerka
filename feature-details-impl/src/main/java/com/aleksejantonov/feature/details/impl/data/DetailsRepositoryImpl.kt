package com.aleksejantonov.feature.details.impl.data

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.model.BeerModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@FeatureScope
class DetailsRepositoryImpl @Inject constructor(
  private val databaseApi: CoreDatabaseApi
) : DetailsRepository {

  override fun data(beerId: Long): Flow<BeerModel> {
    return databaseApi.beersStore().beerData(beerId)
  }

  override fun toggleFavorite(beerId: Long) {
    databaseApi.beersStore().toggleFavorite(beerId)
  }

}