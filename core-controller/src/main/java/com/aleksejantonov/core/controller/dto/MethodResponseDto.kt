package com.aleksejantonov.core.controller.dto

import com.google.gson.annotations.SerializedName

data class MethodResponseDto(
  @SerializedName("mash_temp") val mashTemps: List<MashTempResponseDto>,
  @SerializedName("fermentation") val fermentation: FermentationResponseDto
)