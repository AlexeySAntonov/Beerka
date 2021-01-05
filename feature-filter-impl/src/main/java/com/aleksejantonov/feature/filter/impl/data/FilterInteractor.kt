package com.aleksejantonov.feature.filter.impl.data

import com.aleksejantonov.core.ui.model.ListItem
import kotlinx.coroutines.flow.Flow

interface FilterInteractor {
  suspend fun data(): Flow<List<ListItem>>
}