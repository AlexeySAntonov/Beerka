package com.aleksejantonov.core.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class RootScope // Root tabs, no need to release components