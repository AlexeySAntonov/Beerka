package com.aleksejantonov.feature.details.next.api.di

import com.aleksejantonov.feature.details.next.api.data.FeatureDetailsNextScreenProvider

interface FeatureDetailsNextApi {
    fun featureDetailsNextScreenProvider(): FeatureDetailsNextScreenProvider
}