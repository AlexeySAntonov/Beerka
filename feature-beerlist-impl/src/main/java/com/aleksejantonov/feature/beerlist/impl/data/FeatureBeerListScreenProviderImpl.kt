package com.aleksejantonov.feature.beerlist.impl.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.beerlist.api.data.FeatureBeerListScreenProvider
import com.aleksejantonov.feature.beerlist.impl.ui.BeerListFragment
import javax.inject.Inject

@FeatureScope
class FeatureBeerListScreenProviderImpl @Inject constructor(

) : FeatureBeerListScreenProvider {

    override fun screen(): Fragment {
        return BeerListFragment.create(-1)
    }
}