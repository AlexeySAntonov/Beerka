package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.ui.base.adapter.ListItem
import kotlinx.coroutines.flow.Flow

interface FeatureBeerListScreenInteractor {
  suspend fun data(): Flow<List<ListItem>>
}