package com.aleksejantonov.core.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NavHostFragment : Fragment() {

  private var applyThemeBackground = false

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_nav_host, container, false).also {
      if (applyThemeBackground) it.setBackgroundResource(android.R.color.white)
    }
  }

  fun applyThemeBackground() {
    applyThemeBackground = true
  }
}