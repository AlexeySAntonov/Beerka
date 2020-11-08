package com.aleksejantonov.core.model

data class BeerModel(
  val id: Long,
  val name: String,
  val description: String,
  val image: String?
)