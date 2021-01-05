package com.aleksejantonov.feature.filter.impl.data

import android.content.Context
import android.view.View
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.filter.api.data.FeatureFilterViewProvider
import com.aleksejantonov.feature.filter.impl.ui.FilterView
import javax.inject.Inject

@FeatureScope
class FeatureFilterViewProviderImpl @Inject constructor() : FeatureFilterViewProvider {

    override fun view(componentKey: Long, context: Context): View {
        return FilterView.create(context, componentKey)
    }

}