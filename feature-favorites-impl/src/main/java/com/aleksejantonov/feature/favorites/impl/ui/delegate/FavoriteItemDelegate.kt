package com.aleksejantonov.feature.favorites.impl.ui.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.base.GlideApp
import com.aleksejantonov.core.ui.base.releaseGlide
import com.aleksejantonov.core.ui.model.BeerItem
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.feature.favorites.impl.R
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteItemDelegate(
  private val onBeerClick: (BeerItem) -> Unit,
  private val onRemoveClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<BeerItem, ListItem, FavoriteItemDelegate.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false))
  }

  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean = item is BeerItem
  override fun onBindViewHolder(item: BeerItem, holder: ViewHolder, payloads: MutableList<Any>) {
    holder.bind(item)
  }

  override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
    (holder as ViewHolder).release()
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: BeerItem) {
      with(itemView) {
        name.text = item.name
        description.text = item.description
        setOnClickListener { onBeerClick.invoke(item) }
        removeOverlay.setOnClickListener { onRemoveClick.invoke(item.id) }

        GlideApp.with(context)
          .load(item.image)
          .transition(withCrossFade(200))
          .fitCenter()
          .error(R.drawable.ic_beer_stub)
          .into(image)
      }
    }

    fun release() {
      releaseGlide(itemView.image)
    }
  }
}