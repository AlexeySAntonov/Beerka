package com.aleksejantonov.core.navigation

import android.view.View
import android.view.ViewGroup

interface ModalsHost {
  val modalsContainer: ViewGroup
  fun addModalView(view: View)
  fun removeModalView()
}