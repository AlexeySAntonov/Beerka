package com.aleksejantonov.feature.favorites.api.data

import androidx.fragment.app.Fragment

interface FeatureFavoritesScreenProvider {
    fun screen(componentKey: Long): Fragment
}