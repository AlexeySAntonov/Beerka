package com.aleksejantonov.core.controller.dto

import com.google.gson.annotations.SerializedName

data class MashTempResponseDto(
  @SerializedName("temp") val temp: MeasureResponseDto,
  @SerializedName("duration") val duration: Float
)