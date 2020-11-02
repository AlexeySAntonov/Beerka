package com.aleksejantonov.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beers")
data class BeerEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,
    val name: String = ""
)