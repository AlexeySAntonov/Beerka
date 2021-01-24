package com.aleksejantonov.feature.details.impl.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.EntityIdProvider
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import com.aleksejantonov.feature.details.impl.ui.DetailsFragment
import javax.inject.Inject

@FeatureScope
class FeatureDetailsScreenProviderImpl @Inject constructor(
    private val entityIdProvider: EntityIdProvider
) : FeatureDetailsScreenProvider {

    override fun screen(componentKey: String, screenData: Any): Fragment {
        screenData as ScreenData
        val entityId = (screenData.args[0] as? Long) ?: throw IllegalArgumentException("Entity id must be provided")
        entityIdProvider.safeSetId(entityId)
        return DetailsFragment.create(componentKey, screenData)
    }
}