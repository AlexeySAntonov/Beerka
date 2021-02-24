package com.aleksejantonov.feature.favorites.impl.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.di.ComponentsManager
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import dagger.BindsInstance
import dagger.Component
import java.util.*

@Component(modules = [FeatureFavoritesModule::class])
@RootScope
interface FeatureFavoritesComponent : FeatureFavoritesApi, ViewModelFactoryProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun componentKey(@ComponentKey componentKey: String): Builder

        @BindsInstance
        fun coreDatabaseApi(coreDatabaseApi: CoreDatabaseApi): Builder

        @BindsInstance
        fun router(router: GlobalRouter): Builder

        fun build(): FeatureFavoritesComponent
    }

    companion object {

        fun init(dependencies: FeatureFavoritesComponentDependencies): Pair<FeatureFavoritesApi, String> {
            val componentKey = UUID.randomUUID().toString()
            return DaggerFeatureFavoritesComponent.builder()
                .componentKey(componentKey)
                .coreDatabaseApi(dependencies.coreDatabaseApi())
                .router(dependencies.router())
                .build()
                .also { ComponentsManager.save(componentKey, it) } to componentKey

        }
    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureFavoritesComponentDependencies {
    fun coreDatabaseApi(): CoreDatabaseApi
    fun router(): GlobalRouter
}