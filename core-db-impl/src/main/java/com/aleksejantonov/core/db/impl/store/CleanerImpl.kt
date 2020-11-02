package com.aleksejantonov.core.db.impl.store

import com.aleksejantonov.core.db.api.DatabaseClientApi
import com.aleksejantonov.core.db.api.store.Cleaner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CleanerImpl @Inject constructor(private val db: DatabaseClientApi) : Cleaner {
  override fun clearAll() {
    db.clearAll()
  }
}