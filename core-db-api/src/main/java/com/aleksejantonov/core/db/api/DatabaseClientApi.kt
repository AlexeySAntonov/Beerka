package com.aleksejantonov.core.db.api

import com.aleksejantonov.core.db.api.dao.BeerDao

interface DatabaseClientApi {
  fun inTransaction(block: () -> Unit)
  fun clearAll()

  fun beerDao(): BeerDao
}