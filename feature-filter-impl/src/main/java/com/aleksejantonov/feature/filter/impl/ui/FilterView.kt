package com.aleksejantonov.feature.filter.impl.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintLayout
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.ui.base.LayoutHelper
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.core.ui.base.mvvm.dpToPx
import com.aleksejantonov.feature.filter.impl.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.properties.Delegates

class FilterView(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

  private var componentKey: Long by Delegates.notNull()
  private val component: ViewModelFactoryProvider by lazy {
    requireNotNull(ComponentsManager.get(componentKey) as? ViewModelFactoryProvider)
  }
  private val viewModel: FilterViewModel by lazy { component.viewModelFactory().create(FilterViewModel::class.java) }
  private var scope: CoroutineScope? = null

  init {
    layoutParams = LayoutHelper.getFrameParams(
      context = context,
      width = LayoutHelper.MATCH_PARENT,
      height = 500,
      gravity = Gravity.BOTTOM
    )
    setBackgroundResource(R.color.appBlueLight)
    translationY = dpToPx(400f)

    scope = CoroutineScope(Dispatchers.Main)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    animate().translationY(0f).setDuration(400L).start()
    scope?.launch {
      viewModel.data.collect {  }
    }
  }

  override fun onDetachedFromWindow() {
    scope?.cancel()
    scope = null
    ComponentsManager.release(componentKey)
    super.onDetachedFromWindow()
  }

  companion object {
    fun create(context: Context, componentKey: Long): FilterView = FilterView(context).apply {
      this.componentKey = componentKey
    }
  }
}