package com.aleksejantonov.core.db.api.dao

import androidx.room.*
import com.aleksejantonov.core.db.entity.BeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertBeer(beer: BeerEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertBeers(beers: List<BeerEntity>)

  @Query("SELECT * FROM beers WHERE id = :id")
  fun beerData(id: Long): Flow<BeerEntity>

  @Query("SELECT * FROM beers LIMIT :limit OFFSET :offset")
  fun beersData(limit: Int, offset: Int): Flow<List<BeerEntity>>

  @Query("SELECT COUNT(*) FROM beers")
  fun beersCount(): Long

  @Query("UPDATE beers SET isFavorite = 1 WHERE id = :id")
  fun setFavorite(id: Long)

  @Query("UPDATE beers SET isFavorite = ((isFavorite | 1) - (isFavorite & 1)) WHERE id = :id")
  fun toggleFavorite(id: Long)
}