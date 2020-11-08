package com.aleksejantonov.feature.beerlist.impl.ui.delegate.item

import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.ui.base.adapter.ListItem

data class BeerItem(
  val id: Long,
  val name: String,
  val description: String,
  val image: String?
) : ListItem {

  override fun itemId(): Long = id

  companion object {

    fun from(model: BeerModel) = with(model) {
      BeerItem(
        id = id,
        name = name,
        description = description,
        image = image
      )
    }
  }
}