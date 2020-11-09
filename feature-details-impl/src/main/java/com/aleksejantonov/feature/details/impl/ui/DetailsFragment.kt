package com.aleksejantonov.feature.details.impl.ui

import android.os.Bundle
import android.view.View
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.GlideApp
import com.aleksejantonov.core.ui.base.mvvm.setMargins
import com.aleksejantonov.core.ui.base.mvvm.trueViewModels
import com.aleksejantonov.feature.details.impl.R
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

  private val viewModel by trueViewModels<DetailsViewModel>()
  private val screenData by lazy { arguments?.getSerializable(SCREEN_DATA) as? ScreenData }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    applyInitialState()
  }

  override fun onStatusBarHeight(statusBarHeight: Int) {
    image.setMargins(top = statusBarHeight)
  }

  private fun applyInitialState() {
    screenData?.let {
      runCatching {
        GlideApp.with(requireContext())
          .load(it.args[3])
          .transition(DrawableTransitionOptions.withCrossFade(200))
          .fitCenter()
          .into(image)

        name.text = it.args[1] as String
        description.text = it.args[2] as String
      }
    }
  }

  companion object {
    /**
     * ScreenData: [0] - id: Long, [1] - name: String, [2] - description: String, [3] - image: String?
     */
    fun create(componentKey: Long, screenData: ScreenData) = DetailsFragment().apply {
      arguments = Bundle().apply {
        putLong(COMPONENT_KEY, componentKey)
        putSerializable(SCREEN_DATA, screenData)
      }
    }
  }
}