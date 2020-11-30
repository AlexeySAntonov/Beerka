package com.aleksejantonov.core.db.entity

import com.aleksejantonov.core.model.BeerModel

fun BeerEntity.model() = BeerModel(
  id = id,
  name = name,
  description = description,
  image = imageUrl,
  isFavorite = isFavorite
)

fun BeerModel.entity() = BeerEntity(
  id = id,
  name = name,
  description = description,
  imageUrl = image,
  isFavorite = isFavorite
)