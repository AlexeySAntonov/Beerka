package com.aleksejantonov.feature.favorites.impl.ui

import android.os.Bundle
import android.view.View
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.mvvm.trueViewModels
import com.aleksejantonov.feature.favorites.impl.R
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

  private val viewModel by trueViewModels<FavoritesViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    details.setOnClickListener { viewModel.navigateToDetails(ScreenData(1, "Baltica", null)) }
  }

  companion object {

    fun create(componentKey: Long) = FavoritesFragment().apply {
      arguments = Bundle().apply { putLong(COMPONENT_KEY, componentKey) }
    }
  }
}