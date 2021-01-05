package com.aleksejantonov.feature.filter.impl.data

import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.model.ListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@FeatureScope
class FilterInteractorImpl @Inject constructor(

) : FilterInteractor {

  override suspend fun data(): Flow<List<ListItem>> {
    return emptyFlow()
  }

}