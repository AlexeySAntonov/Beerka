package com.aleksejantonov.feature.favorites.impl.data

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.feature.favorites.api.data.FeatureFavoritesScreenProvider
import com.aleksejantonov.feature.favorites.impl.ui.FavoritesFragment
import javax.inject.Inject

@RootScope
class FeatureFavoritesScreenProviderImpl @Inject constructor(
  private val databaseApi: CoreDatabaseApi
): FeatureFavoritesScreenProvider {

  override fun screen(): Fragment {
    return FavoritesFragment.create(2)
  }
}