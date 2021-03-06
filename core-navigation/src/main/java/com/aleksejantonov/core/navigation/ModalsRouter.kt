package com.aleksejantonov.core.navigation

import android.content.Context
import android.view.View
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.ui.base.BottomSheetable
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModalsRouter @Inject constructor(
  private val globalFeatureProvider: GlobalFeatureProvider
) {

  private var modalViewRef: WeakReference<View>? = null

  fun openFilterModal(context: Context) {
    openModalView(globalFeatureProvider.provideFeatureFilter(context))
  }

  private fun openModalView(view: View) {
    removeCurrentModal(view.context)
    modalViewRef = WeakReference(view)
    modalViewRef?.get()?.let { (view.context as? ModalsHost)?.addModalView(view) }
    (modalViewRef?.get() as? BottomSheetable)?.animateShow()
  }

  fun removeCurrentModal(parentContext: Context) {
    modalViewRef?.get()?.let { (parentContext as? ModalsHost)?.removeModalView() }
    modalViewRef = null
  }

  fun handleBack(): Boolean {
    (modalViewRef?.get() as? BottomSheetable)?.animateHide() ?: return false
    return true
  }

}