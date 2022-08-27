package com.fakhrirasyids.beritain.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.fakhrirasyids.beritain.R

class Constants {
    companion object {
        const val SPLASH_TIME_OUT: Long = 1000
        const val TECHNOLOGY_CATEGORY: String = "technology"
        const val SPORTS_CATEGORY: String = "sports"
        const val SCIENCE_CATEGORY: String = "science"
        const val HEALTH_CATEGORY: String = "health"
        const val BUSINESS_CATEGORY: String = "business"
        const val ENTERTAINMENT_CATEGORY: String = "entertainment"
        val TAB_TITLES = intArrayOf(
            R.string.technology,
            R.string.sports,
            R.string.science,
            R.string.health,
            R.string.business,
            R.string.entertainment
        )
        const val LIST_NEWS = "list_news"
        const val SERIAZABLE_LIST_NEWS = "seriazable_list_news"
        const val NEWS_URL = "news_url"
        val THEME_KEY = booleanPreferencesKey("theme_setting")
    }
}