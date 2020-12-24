package com.aleksejantonov.feature.details.impl.data

import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.model.BeerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@FeatureScope
class DetailsInteractorImpl @Inject constructor(
  private val repository: DetailsRepository
) : DetailsInteractor {

  override suspend fun data(): Flow<BeerItem> {
    return repository.data().map { model -> BeerItem.from(model) }
  }

  override fun toggleFavorite() {
    repository.toggleFavorite()
  }

}