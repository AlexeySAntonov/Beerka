package com.aleksejantonov.feature.details.api.di

import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import com.aleksejantonov.module.injector.BaseApi

interface FeatureDetailsApi : BaseApi {
    fun featureDetailsScreenProvider(): FeatureDetailsScreenProvider
}