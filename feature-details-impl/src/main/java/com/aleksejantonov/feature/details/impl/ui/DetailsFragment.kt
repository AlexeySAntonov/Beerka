package com.aleksejantonov.feature.details.impl.ui

import android.os.Bundle
import android.view.View
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.feature.details.impl.R

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  companion object {
    fun create(componentKey: Long) = DetailsFragment().apply {

    }
  }
}