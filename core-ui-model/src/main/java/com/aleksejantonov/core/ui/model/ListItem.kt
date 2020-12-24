package com.aleksejantonov.core.ui.model

import java.io.Serializable

interface ListItem : Serializable {
  fun itemId(): Long

  companion object {
    const val PAGINATION_LOADING_ITEM_ID = -1L
  }
}