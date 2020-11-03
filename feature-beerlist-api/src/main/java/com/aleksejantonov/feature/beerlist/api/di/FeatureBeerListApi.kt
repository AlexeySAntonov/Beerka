package com.aleksejantonov.feature.beerlist.api.di

import com.aleksejantonov.feature.beerlist.api.data.FeatureBeerListScreenProvider

interface FeatureBeerListApi {
    fun featureBeerListScreenProvider(): FeatureBeerListScreenProvider
}