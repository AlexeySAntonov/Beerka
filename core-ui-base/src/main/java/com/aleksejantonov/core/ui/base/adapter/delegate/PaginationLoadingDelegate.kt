package com.aleksejantonov.core.ui.base.adapter.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.base.R
import com.aleksejantonov.core.ui.model.ListItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class PaginationLoadingDelegate : AbsListItemAdapterDelegate<PaginationLoadingItem, ListItem, PaginationLoadingDelegate.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false))
  }

  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean = item is PaginationLoadingItem
  override fun onBindViewHolder(item: PaginationLoadingItem, holder: ViewHolder, payloads: MutableList<Any>) = Unit

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}