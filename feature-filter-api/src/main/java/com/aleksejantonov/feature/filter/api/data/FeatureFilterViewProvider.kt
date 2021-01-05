package com.aleksejantonov.feature.filter.api.data

import android.content.Context
import android.view.View

interface FeatureFilterViewProvider {
    fun view(componentKey: Long, context: Context): View
}