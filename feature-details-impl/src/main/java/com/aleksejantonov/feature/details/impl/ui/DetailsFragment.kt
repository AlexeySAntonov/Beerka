package com.aleksejantonov.feature.details.impl.ui

import android.os.Bundle
import android.view.View
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.mvvm.trueViewModels
import com.aleksejantonov.feature.details.impl.R

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

  private val viewModel by trueViewModels<DetailsViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  companion object {
    fun create(componentKey: Long, screenData: ScreenData) = DetailsFragment().apply {
      arguments = Bundle().apply { putLong(COMPONENT_KEY, componentKey) }
    }
  }
}