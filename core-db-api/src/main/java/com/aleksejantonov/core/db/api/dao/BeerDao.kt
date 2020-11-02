package com.aleksejantonov.core.db.api.dao

import androidx.room.*
import com.aleksejantonov.core.db.entity.BeerEntity

@Dao
interface BeerDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertBeer(user: BeerEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertBeers(users: List<BeerEntity>)
}