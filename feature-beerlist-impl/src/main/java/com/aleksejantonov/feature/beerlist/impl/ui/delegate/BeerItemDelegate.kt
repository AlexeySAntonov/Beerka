package com.aleksejantonov.feature.beerlist.impl.ui.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.base.GlideApp
import com.aleksejantonov.core.ui.base.releaseGlide
import com.aleksejantonov.core.ui.model.BeerItem
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.feature.beerlist.impl.R
import com.aleksejantonov.feature.beerlist.impl.ui.BeersAdapter.Companion.PAYLOAD_FAVORITE
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_beer.view.*

class BeerItemDelegate(
  private val onBeerClick: (BeerItem) -> Unit,
  private val onFavoriteClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<BeerItem, ListItem, BeerItemDelegate.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false))
  }

  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean = item is BeerItem
  override fun onBindViewHolder(item: BeerItem, holder: ViewHolder, payloads: MutableList<Any>) {
    if (payloads.any { it == PAYLOAD_FAVORITE }) {
      holder.updateFavorite(item)
      return
    }
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
        favoriteIcon.setImageResource(if (item.isFavorite) R.drawable.ic_star_24 else R.drawable.ic_star_empty_24)
        favoriteOverlay.setOnClickListener { onFavoriteClick.invoke(item.id) }

        GlideApp.with(context)
          .load(item.image)
          .transition(withCrossFade(200))
          .fitCenter()
          .error(R.drawable.ic_beer_stub)
          .into(image)
      }
    }

    fun updateFavorite(item: BeerItem) {
      itemView.favoriteIcon.setImageResource(if (item.isFavorite) R.drawable.ic_star_24 else R.drawable.ic_star_empty_24)
    }

    fun release() {
      releaseGlide(itemView.image)
    }
  }
}