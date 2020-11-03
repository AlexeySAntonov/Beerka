package com.aleksejantonov.feature.beerlist.impl.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.feature.beerlist.api.data.FeatureBeerListScreenProvider
import com.aleksejantonov.feature.beerlist.impl.ui.BeerListFragment
import javax.inject.Inject

@RootScope
class FeatureBeerListScreenProviderImpl @Inject constructor(
    private val databaseApi: CoreDatabaseApi
) : FeatureBeerListScreenProvider {

    override fun screen(): Fragment {
        return BeerListFragment.create(0)
    }
}