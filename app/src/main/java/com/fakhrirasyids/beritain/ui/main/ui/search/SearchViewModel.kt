package com.fakhrirasyids.beritain.ui.main.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem
import com.fakhrirasyids.beritain.data.remote.response.NewsResponse
import com.fakhrirasyids.beritain.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _searchedNews = MutableLiveData<ArrayList<ArticlesItem>>()
    val searchedNews: LiveData<ArrayList<ArticlesItem>> = _searchedNews

    fun getNewsSearch(news: String) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getNewsBySearch(news)
        client.enqueue(object : retrofit2.Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                _isLoading.value = false
                _isError.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.articles != null) {
                        _searchedNews.postValue(responseBody.articles)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "Search View Model"
    }
}