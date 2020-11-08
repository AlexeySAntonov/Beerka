package com.aleksejantonov.core.controller.dto

import com.google.gson.annotations.SerializedName

data class BeerResponseDto(
  @SerializedName("id") val id: Long,
  @SerializedName("name") val name: String,
  @SerializedName("tagline") val tagline: String,
  @SerializedName("first_brewed") val firstBrewed: String,
  @SerializedName("description") val description: String,
  @SerializedName("image_url") val imageUrl: String,
  @SerializedName("abv") val abv: Float,
  @SerializedName("ibu") val ibu: Float,
  @SerializedName("target_fg") val targetFg: Float,
  @SerializedName("target_og") val targetOg: Float,
  @SerializedName("ebc") val ebc: Float,
  @SerializedName("srm") val srm: Float,
  @SerializedName("ph") val ph: Float,
  @SerializedName("attenuation_level") val attenuationLevel: Float,
  @SerializedName("volume") val volume: MeasureResponseDto,
  @SerializedName("boil_volume") val boilVolume: MeasureResponseDto,
  @SerializedName("method") val method: MethodResponseDto,
  @SerializedName("ingredients") val ingredients: IngredientsResponseDto,
  @SerializedName("food_pairing") val foodPairings: List<String>,
  @SerializedName("brewers_tips") val brewersTips: String,
  @SerializedName("contributed_by") val contributor: String
)