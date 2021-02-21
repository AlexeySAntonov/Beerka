package com.aleksejantonov.feature.filter.impl.data

import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.mediator.api.FilterDataMediator
import com.aleksejantonov.core.ui.model.FilterItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@FeatureScope
class FilterInteractorImpl @Inject constructor(
  private val filterDataMediator: FilterDataMediator
) : FilterInteractor {

  override suspend fun data(): Flow<FilterItem> {
    return filterDataMediator.filterDataFlow.map { FilterItem.from(it) }
  }

}