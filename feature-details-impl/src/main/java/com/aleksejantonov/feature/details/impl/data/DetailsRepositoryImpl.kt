package com.aleksejantonov.feature.details.impl.data

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.EntityIdProvider
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.model.BeerModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@FeatureScope
class DetailsRepositoryImpl @Inject constructor(
  private val entityIdProvider: EntityIdProvider,
  private val databaseApi: CoreDatabaseApi
) : DetailsRepository {

  override fun data(): Flow<BeerModel> {
    return databaseApi.beersStore().beerData(entityIdProvider.getId())
  }

  override fun toggleFavorite() {
    databaseApi.beersStore().toggleFavorite(entityIdProvider.getId())
  }

}