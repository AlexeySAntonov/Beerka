package com.aleksejantonov.core.navigation.navigation

import androidx.fragment.app.Fragment

interface Navigation {
    val containerId: Int

    fun back(force: Boolean): Boolean
    fun currentScreen(): Fragment?
}