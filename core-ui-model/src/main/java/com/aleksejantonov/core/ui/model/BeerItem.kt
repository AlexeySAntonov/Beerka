package com.aleksejantonov.core.ui.model

import com.aleksejantonov.core.model.BeerModel

data class BeerItem(
  val id: Long,
  val name: String,
  val description: String,
  val image: String?,
  val isFavorite: Boolean
) : ListItem {

  override fun itemId(): Long = id

  companion object {

    fun from(model: BeerModel) = with(model) {
      BeerItem(
        id = id,
        name = name,
        description = description,
        image = image,
        isFavorite = isFavorite
      )
    }

    fun fromArgs(vararg args: Any?) = BeerItem(
      id = args[0] as Long,
      name = args[1] as String,
      description = args[2] as String,
      image = args[3] as String?,
      isFavorite = false
    )
  }
}