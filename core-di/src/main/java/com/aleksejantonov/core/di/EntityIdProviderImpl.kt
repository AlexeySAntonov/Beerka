package com.aleksejantonov.core.di

import javax.inject.Inject

@FeatureScope
class EntityIdProviderImpl @Inject constructor() : EntityIdProvider {

    private var entityId = 0L

    override fun safeSetId(id: Long) {
        if (entityId == 0L) {
            entityId = id
        }
    }

    override fun getId(): Long = entityId
}