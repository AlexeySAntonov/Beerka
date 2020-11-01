package com.aleksejantonov.core.ui.base

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.requireValue() = value!!