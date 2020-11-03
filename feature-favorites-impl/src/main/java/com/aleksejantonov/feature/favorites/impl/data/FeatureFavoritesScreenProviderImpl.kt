package com.aleksejantonov.feature.favorites.impl.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.favorites.api.data.FeatureFavoritesScreenProvider
import com.aleksejantonov.feature.favorites.impl.ui.FavoritesFragment
import javax.inject.Inject

@FeatureScope
class FeatureFavoritesScreenProviderImpl @Inject constructor(

): FeatureFavoritesScreenProvider {

  override fun screen(): Fragment {
    return FavoritesFragment.create(-1)
  }
}