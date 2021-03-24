package com.aleksejantonov.feature.details.impl.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenData
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import com.aleksejantonov.feature.details.impl.ui.DetailsFragment
import com.aleksejantonov.module.injector.ScreenCustomDependencies
import javax.inject.Inject

@FeatureScope
class FeatureDetailsScreenProviderImpl @Inject constructor() : FeatureDetailsScreenProvider {

    override fun screen(componentKey: String, customDependencies: ScreenCustomDependencies): Fragment {
        return DetailsFragment.create(componentKey, FeatureDetailsScreenData.from(customDependencies))
    }
}