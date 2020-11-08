package com.aleksejantonov.core.controller.dto

import com.google.gson.annotations.SerializedName

data class IngredientsResponseDto(
  @SerializedName("malt") val malts: List<IngredientResponseDto>,
  @SerializedName("hops") val hops: List<IngredientResponseDto>,
  @SerializedName("yeast") val yeast: String,
)