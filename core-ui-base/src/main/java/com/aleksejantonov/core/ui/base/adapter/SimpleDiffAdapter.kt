package com.aleksejantonov.core.ui.base.adapter

import androidx.recyclerview.widget.DiffUtil
import com.aleksejantonov.core.ui.model.ListItem
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

open class SimpleDiffAdapter : AsyncListDifferDelegationAdapter<ListItem>(DIFF_CALLBACK) {
  companion object {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
      override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.itemId() == newItem.itemId()
      }

      override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.equals(newItem)
      }
    }
  }
}