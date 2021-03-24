package com.aleksejantonov.feature.details.impl.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.GlideApp
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.core.ui.base.mvvm.dpToPx
import com.aleksejantonov.core.ui.base.mvvm.setBackgroundTint
import com.aleksejantonov.core.ui.base.mvvm.setMargins
import com.aleksejantonov.core.ui.model.BeerItem
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenData
import com.aleksejantonov.feature.details.impl.R
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponentsHolder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

  private val viewModel by viewModels<DetailsViewModel> {
    (FeatureDetailsComponentsHolder.get(requireNotNull(arguments?.getString(COMPONENT_KEY))) as ViewModelFactoryProvider).viewModelFactory()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Link screen data and viewModel
    viewModel.passScreenData(requireNotNull(arguments?.getSerializable(SCREEN_DATA) as FeatureDetailsScreenData))
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    uiStateJob = lifecycleScope.launch { viewModel.data.collect { setData(it) } }
    backButton.setOnClickListener { viewModel.onBack() }
    favoriteButton.setOnClickListener { viewModel.toggleFavorite() }
  }

  override fun onStatusBarHeight(statusBarHeight: Int) {
    toolbarBackground.setMargins(top = statusBarHeight)
  }

  override fun onNavigationBarHeight(navBarHeight: Int) {
    favoriteButton.setMargins(bottom = navBarHeight + (context?.dpToPx(16f) ?: 0))
  }

  private fun setData(item: BeerItem) {
    GlideApp.with(requireContext())
      .load(item.image)
      .transition(DrawableTransitionOptions.withCrossFade(200))
      .fitCenter()
      .error(R.drawable.ic_beer_stub)
      .into(image)

    name.text = item.name
    description.text = item.description
    if (item.isFavorite) {
      favoriteButton.text = resources.getString(R.string.remove_from_favorite)
      favoriteButton.setIconResource(R.drawable.ic_close_24)
      favoriteButton.setBackgroundTint(R.color.appRed)
    } else {
      favoriteButton.text = resources.getString(R.string.add_to_favorite)
      favoriteButton.setIconResource(R.drawable.ic_star_empty_24)
      favoriteButton.setBackgroundTint(R.color.appYellow)
    }
  }

  companion object {
    fun create(componentKey: String, screenData: FeatureDetailsScreenData) = DetailsFragment().apply {
      arguments = Bundle().apply {
        putString(COMPONENT_KEY, componentKey)
        putSerializable(SCREEN_DATA, screenData)
      }
    }
  }
}