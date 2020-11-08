package com.aleksejantonov.core.controller.dto

import com.google.gson.annotations.SerializedName

data class MeasureResponseDto(
  @SerializedName("value") val value: Float,
  @SerializedName("unit") val unit: String
)