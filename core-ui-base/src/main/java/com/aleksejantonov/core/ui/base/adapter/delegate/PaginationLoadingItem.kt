package com.aleksejantonov.core.ui.base.adapter.delegate

import com.aleksejantonov.core.ui.base.adapter.ListItem
import com.aleksejantonov.core.ui.base.adapter.ListItem.Companion.PAGINATION_LOADING_ITEM_ID

object PaginationLoadingItem : ListItem {
    override fun itemId(): Long = PAGINATION_LOADING_ITEM_ID
}