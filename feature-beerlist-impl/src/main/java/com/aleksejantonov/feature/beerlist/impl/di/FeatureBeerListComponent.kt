package com.aleksejantonov.feature.beerlist.impl.di

import com.aleksejantonov.core.api.BeersApi
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.mediator.api.FilterDataMediator
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import dagger.BindsInstance
import dagger.Component
import java.util.*

@Component(modules = [FeatureBeerListModule::class])
@RootScope
interface FeatureBeerListComponent : FeatureBeerListApi, ViewModelFactoryProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun componentKey(@ComponentKey componentKey: String): Builder

        @BindsInstance
        fun beersApi(beersApi: BeersApi): Builder

        @BindsInstance
        fun coreDatabaseApi(coreDatabaseApi: CoreDatabaseApi): Builder

        @BindsInstance
        fun filterDataMediator(filterDataMediator: FilterDataMediator): Builder

        @BindsInstance
        fun router(router: GlobalRouter): Builder

        fun build(): FeatureBeerListComponent
    }

    companion object {

        fun init(dependencies: FeatureBeerListComponentDependencies): Pair<FeatureBeerListApi, String> {
            val componentKey = UUID.randomUUID().toString()
            return DaggerFeatureBeerListComponent.builder()
                .componentKey(componentKey)
                .beersApi(dependencies.beersApi())
                .coreDatabaseApi(dependencies.coreDatabaseApi())
                .filterDataMediator(dependencies.filterDataMediator())
                .router(dependencies.router())
                .build()
                .also { ComponentsManager.save(componentKey, it) } to componentKey
        }
    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureBeerListComponentDependencies {
    fun beersApi(): BeersApi
    fun coreDatabaseApi(): CoreDatabaseApi
    fun filterDataMediator(): FilterDataMediator
    fun router(): GlobalRouter
}