package com.aleksejantonov.feature.details.api.di

import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider

interface FeatureDetailsApi {
    fun featureDetailsScreenProvider(): FeatureDetailsScreenProvider
}