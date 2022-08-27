package com.fakhrirasyids.beritain.data.remote.retrofit

import com.fakhrirasyids.beritain.BuildConfig
import com.fakhrirasyids.beritain.data.remote.response.NewsResponse
import com.fakhrirasyids.beritain.data.remote.retrofit.ApiService.Companion.API_TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    @Headers("Authorization: token $API_TOKEN")
    fun getLatestNews(
        @Query("country") country: String
    ): Call<NewsResponse>

    @GET("top-headlines")
    @Headers("Authorization: token $API_TOKEN")
    fun getLatestCategoryNews(
        @Query("country") country: String,
        @Query("category") category: String
    ): Call<NewsResponse>

    @GET("everything")
    @Headers("Authorization: token $API_TOKEN")
    fun getNewsBySearch(
        @Query("q") news: String
    ): Call<NewsResponse>

    companion object {
        private const val API_TOKEN = BuildConfig.KEY
    }
}