package com.aleksejantonov.core.ui.base

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class BeerkaGlide : AppGlideModule() {

  override fun applyOptions(context: Context, builder: GlideBuilder) {
    val calculator = MemorySizeCalculator.Builder(context)
        .setMemoryCacheScreens(4f)
        .setBitmapPoolScreens(4f)
        .build()
    builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
    builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))

    val diskCacheSizeBytes = 1024 * 1024 * 100 // 100 MB
    builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes.toLong()))
  }

}