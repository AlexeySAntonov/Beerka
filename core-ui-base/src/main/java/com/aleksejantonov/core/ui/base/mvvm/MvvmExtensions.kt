package com.aleksejantonov.core.ui.base.mvvm

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.requireValue() = value!!