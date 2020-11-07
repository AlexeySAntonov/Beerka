package com.aleksejantonov.feature.details.next.impl.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.feature.details.next.api.data.FeatureDetailsNextScreenProvider
import com.aleksejantonov.feature.details.next.impl.ui.DetailsNextFragment
import javax.inject.Inject

@FeatureScope
class FeatureDetailsNextScreenProviderImpl @Inject constructor(
    private val databaseApi: CoreDatabaseApi
) : FeatureDetailsNextScreenProvider {

    override fun screen(componentKey: Long, screenData: Any): Fragment {
        screenData as ScreenData
        return DetailsNextFragment.create(componentKey, screenData)
    }
}