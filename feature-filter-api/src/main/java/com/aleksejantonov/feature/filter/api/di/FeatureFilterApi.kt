package com.aleksejantonov.feature.filter.api.di

import com.aleksejantonov.feature.filter.api.data.FeatureFilterViewProvider

interface FeatureFilterApi {
    fun featureFilterViewProvider(): FeatureFilterViewProvider
}