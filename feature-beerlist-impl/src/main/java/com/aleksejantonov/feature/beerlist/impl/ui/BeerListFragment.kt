package com.aleksejantonov.feature.beerlist.impl.ui

import android.os.Bundle
import android.view.View
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.feature.beerlist.impl.R

class BeerListFragment : BaseFragment(R.layout.fragment_beer_list) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  companion object {

    fun create(componentKey: Long) = BeerListFragment().apply {

    }
  }
}