package com.aleksejantonov.feature.details.next.api.data

import androidx.fragment.app.Fragment

interface FeatureDetailsNextScreenProvider {
    fun screen(componentKey: Long, screenData: Any): Fragment
}