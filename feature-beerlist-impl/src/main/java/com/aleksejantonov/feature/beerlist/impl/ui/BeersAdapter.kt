package com.aleksejantonov.feature.beerlist.impl.ui

import androidx.recyclerview.widget.DiffUtil
import com.aleksejantonov.core.ui.base.adapter.SimpleDiffAdapter
import com.aleksejantonov.core.ui.base.adapter.delegate.PaginationLoadingDelegate
import com.aleksejantonov.core.ui.model.BeerItem
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.feature.beerlist.impl.ui.delegate.BeerItemDelegate

class BeersAdapter(
  onBeerClick: (BeerItem) -> Unit,
  onFavoriteClick: (id: Long) -> Unit
) : SimpleDiffAdapter(DIFF_CALLBACK) {
  init {
    delegatesManager
      .addDelegate(PaginationLoadingDelegate())
      .addDelegate(BeerItemDelegate(onBeerClick, onFavoriteClick))
  }

  companion object {
    const val PAYLOAD_FAVORITE = "PAYLOAD_FAVORITE"

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
      override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.itemId() == newItem.itemId()
      }

      override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.equals(newItem)
      }

      override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Any? {
        if (oldItem is BeerItem && newItem is BeerItem) {
          if (oldItem.isFavorite != newItem.isFavorite) {
            return PAYLOAD_FAVORITE
          }
        }
        return super.getChangePayload(oldItem, newItem)
      }
    }
  }
}