package com.aleksejantonov.feature.beerlist.impl.ui.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.base.GlideApp
import com.aleksejantonov.core.ui.base.adapter.ListItem
import com.aleksejantonov.core.ui.base.releaseGlide
import com.aleksejantonov.feature.beerlist.impl.R
import com.aleksejantonov.feature.beerlist.impl.ui.delegate.item.BeerItem
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_beer.view.*

class BeerItemDelegate(
  private val onBeerClick: (BeerItem) -> Unit
) : AbsListItemAdapterDelegate<BeerItem, ListItem, BeerItemDelegate.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false))
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

        GlideApp.with(context)
          .load(item.image)
          .transition(withCrossFade(200))
          .fitCenter()
          .into(image)
      }
    }

    fun release() {
      releaseGlide(itemView.image)
    }
  }
}