package com.aleksejantonov.core.controller.dto

import com.google.gson.annotations.SerializedName

data class IngredientResponseDto(
  @SerializedName("name") val name: String,
  @SerializedName("amount") val amount: MeasureResponseDto,
  @SerializedName("add") val add: String,
  @SerializedName("attribute") val attribute: String,
)