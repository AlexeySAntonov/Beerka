package com.aleksejantonov.feature.favorites.api.di

import com.aleksejantonov.feature.favorites.api.data.FeatureFavoritesScreenProvider

interface FeatureFavoritesApi {
    fun featureFavoritesScreenProvider(): FeatureFavoritesScreenProvider
}