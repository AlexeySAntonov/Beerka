package com.aleksejantonov.feature.favorites.api.di

import com.aleksejantonov.feature.favorites.api.data.FeatureFavoritesScreenProvider
import com.aleksejantonov.module.injector.BaseApi

interface FeatureFavoritesApi : BaseApi {
    fun featureFavoritesScreenProvider(): FeatureFavoritesScreenProvider
}