package com.aleksejantonov.feature.favorites.impl.ui

import android.os.Bundle
import android.view.View
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.feature.favorites.impl.R

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  companion object {

    fun create(componentKey: Long) = FavoritesFragment().apply {

    }
  }
}