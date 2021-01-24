package com.aleksejantonov.feature.details.api.data

import androidx.fragment.app.Fragment

interface FeatureDetailsScreenProvider {
    fun screen(componentKey: String, screenData: Any): Fragment
}