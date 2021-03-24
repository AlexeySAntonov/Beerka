package com.aleksejantonov.feature.filter.api.di

import com.aleksejantonov.feature.filter.api.data.FeatureFilterViewProvider
import com.aleksejantonov.module.injector.BaseApi

interface FeatureFilterApi : BaseApi {
    fun featureFilterViewProvider(): FeatureFilterViewProvider
}