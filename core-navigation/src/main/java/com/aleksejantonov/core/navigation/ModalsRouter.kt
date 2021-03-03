package com.aleksejantonov.core.navigation

import android.app.Activity
import android.content.Context
import android.view.View
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.ui.base.BottomSheetable
import com.aleksejantonov.core.ui.base.mvvm.addViewOnTheVeryTop
import com.aleksejantonov.core.ui.base.mvvm.removeViewFromTheVeryTop
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModalsRouter @Inject constructor(
  private val globalFeatureProvider: GlobalFeatureProvider
) {

  private var modalViewRef: WeakReference<View>? = null

  fun openFilterModal(context: Context) {
    openModalView(globalFeatureProvider.provideFeatureFilter(context), context)
  }

  private fun openModalView(view: View, parentContext: Context) {
    removeCurrentModal(parentContext)
    modalViewRef = WeakReference(view)
    modalViewRef?.get()?.let { (parentContext as? Activity).addViewOnTheVeryTop(view) }
    (modalViewRef?.get() as? BottomSheetable)?.animateShow()
  }

  fun removeCurrentModal(parentContext: Context) {
    modalViewRef?.get()?.let { (parentContext as? Activity).removeViewFromTheVeryTop(it) }
    modalViewRef = null
  }

  fun handleBack(): Boolean {
    (modalViewRef?.get() as? BottomSheetable)?.animateHide() ?: return false
    return true
  }

}