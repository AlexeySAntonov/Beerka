package com.aleksejantonov.core.db.api.di

import com.aleksejantonov.core.db.api.store.BeersStore
import com.aleksejantonov.core.db.api.store.Cleaner

interface CoreDatabaseApi {
  fun cleaner(): Cleaner
  fun beersStore(): BeersStore
}