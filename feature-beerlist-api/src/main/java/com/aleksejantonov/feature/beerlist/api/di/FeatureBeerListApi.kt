package com.aleksejantonov.feature.beerlist.api.di

import com.aleksejantonov.feature.beerlist.api.data.FeatureBeerListScreenProvider
import com.aleksejantonov.module.injector.BaseApi

interface FeatureBeerListApi : BaseApi {
    fun featureBeerListScreenProvider(): FeatureBeerListScreenProvider
}