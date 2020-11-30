package com.aleksejantonov.core.ui.base.mvvm

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.ui.base.BaseFragment

fun <T> LiveData<T>.requireValue() = value!!

@MainThread
inline fun <reified VM : ViewModel> Fragment.trueViewModels(
  noinline ownerProducer: () -> ViewModelStoreOwner = { this }
) = createViewModelLazy(
  viewModelClass = VM::class,
  storeProducer = { ownerProducer().viewModelStore },
  factoryProducer = {
    arguments?.getLong(BaseFragment.COMPONENT_KEY)?.let {
      (ComponentsManager.get(it) as? ViewModelFactoryProvider)?.viewModelFactory()
    } ?: defaultViewModelProviderFactory
  }
)