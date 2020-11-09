package com.aleksejantonov.core.api

import com.aleksejantonov.core.controller.dto.BeerResponseDto
import com.aleksejantonov.core.model.BeerModel

fun BeerResponseDto.model() = BeerModel(
  id = id,
  name = name,
  description = description,
  image = imageUrl,
  isFavorite = false
)