package com.aleksejantonov.core.ui.base.adapter

interface ListItem {
  fun itemId(): Long

  companion object {
    const val PAGINATION_LOADING_ITEM_ID = -1L
  }
}