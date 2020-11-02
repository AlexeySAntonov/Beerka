package com.aleksejantonov.core.db.impl.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aleksejantonov.core.db.api.DatabaseClientApi
import com.aleksejantonov.core.db.entity.BeerEntity

@Database(
    entities = [
      BeerEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TrDatabaseImpl : RoomDatabase(), DatabaseClientApi {

  override fun inTransaction(block: () -> Unit) {
    runInTransaction { block.invoke() }
  }

  override fun clearAll() {
    clearAllTables()
  }
}