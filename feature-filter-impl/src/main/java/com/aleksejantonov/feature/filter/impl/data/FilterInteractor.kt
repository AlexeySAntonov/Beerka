package com.aleksejantonov.feature.filter.impl.data

import com.aleksejantonov.core.ui.model.FilterItem
import kotlinx.coroutines.flow.Flow

interface FilterInteractor {
  suspend fun initialData(): Flow<FilterItem>
  suspend fun applyFilter(item: FilterItem)
}