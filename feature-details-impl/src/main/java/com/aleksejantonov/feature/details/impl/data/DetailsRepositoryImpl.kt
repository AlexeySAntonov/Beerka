package com.aleksejantonov.feature.details.impl.data

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenData
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponentsHolder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@FeatureScope
class DetailsRepositoryImpl @Inject constructor(
  @ComponentKey private val componentKey: String,
  private val databaseApi: CoreDatabaseApi
) : DetailsRepository {

  private val screenData: FeatureDetailsScreenData by lazy { FeatureDetailsComponentsHolder.getScreenData(componentKey) }

  override fun data(): Flow<BeerModel> {
    return databaseApi.beersStore().beerData(screenData.beerId)
  }

  override fun toggleFavorite() {
    databaseApi.beersStore().toggleFavorite(screenData.beerId)
  }

}