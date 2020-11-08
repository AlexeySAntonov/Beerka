package com.aleksejantonov.core.controller.dto

import com.google.gson.annotations.SerializedName

data class FermentationResponseDto(
  @SerializedName("temp") val temp: MeasureResponseDto
)