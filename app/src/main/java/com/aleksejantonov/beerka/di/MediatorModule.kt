package com.aleksejantonov.beerka.di

import com.aleksejantonov.core.mediator.api.FilterDataMediator
import com.aleksejantonov.core.mediator.impl.FilterDataMediatorImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class MediatorModule {

  @Binds
  @Singleton
  abstract fun bindsFilterDataMediator(mediator: FilterDataMediatorImpl): FilterDataMediator
}