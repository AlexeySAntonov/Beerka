package com.aleksejantonov.feature.details.impl.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import com.aleksejantonov.feature.details.impl.ui.DetailsFragment
import javax.inject.Inject

@FeatureScope
class FeatureDetailsScreenProviderImpl @Inject constructor(
    private val databaseApi: CoreDatabaseApi
) : FeatureDetailsScreenProvider {

    override fun screen(): Fragment {
        return DetailsFragment.create(1)
    }
}