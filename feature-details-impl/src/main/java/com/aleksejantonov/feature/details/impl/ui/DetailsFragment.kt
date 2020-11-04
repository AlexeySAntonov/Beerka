package com.aleksejantonov.feature.details.impl.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.feature.details.impl.R
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponent

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

  private val viewModel by viewModels<DetailsViewModel> {
    ComponentsManager.get<FeatureDetailsComponent>(requireNotNull(arguments?.getLong(COMPONENT_KEY))).viewModelFactory()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  companion object {
    fun create(componentKey: Long, screenData: ScreenData) = DetailsFragment().apply {
      arguments = Bundle().apply { putLong(COMPONENT_KEY, componentKey) }
    }
  }
}