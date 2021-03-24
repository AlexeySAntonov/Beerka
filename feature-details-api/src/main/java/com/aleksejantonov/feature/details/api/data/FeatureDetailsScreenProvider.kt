package com.aleksejantonov.feature.details.api.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.module.injector.ScreenCustomDependencies

interface FeatureDetailsScreenProvider {
    fun screen(componentKey: String, customDependencies: ScreenCustomDependencies): Fragment
}