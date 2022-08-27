package com.fakhrirasyids.beritain.ui.adapter

import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem

interface OnClickItemCallback {
    fun onNewsClicked(news: ArticlesItem)
}