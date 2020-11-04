package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import javax.inject.Inject

@RootScope
class FeatureBeerListScreenInteractorImpl @Inject constructor(
    private val databaseApi: CoreDatabaseApi
) : FeatureBeerListScreenInteractor {

}