package com.fakhrirasyids.beritain.ui.main.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fakhrirasyids.beritain.data.remote.retrofit.ApiConfig
import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem
import com.fakhrirasyids.beritain.data.remote.response.NewsResponse
import com.fakhrirasyids.beritain.utils.Constants
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isLoadingTechnology = MutableLiveData<Boolean>()
    val isLoadingTechnology: LiveData<Boolean> = _isLoadingTechnology

    private val _isLoadingSports = MutableLiveData<Boolean>()
    val isLoadingSports: LiveData<Boolean> = _isLoadingSports

    private val _isLoadingScience = MutableLiveData<Boolean>()
    val isLoadingScience: LiveData<Boolean> = _isLoadingScience

    private val _isLoadingHealth = MutableLiveData<Boolean>()
    val isLoadingHealth: LiveData<Boolean> = _isLoadingHealth

    private val _isLoadingBusiness = MutableLiveData<Boolean>()
    val isLoadingBusiness: LiveData<Boolean> = _isLoadingBusiness

    private val _isLoadingEntertainment = MutableLiveData<Boolean>()
    val isLoadingEntertainment: LiveData<Boolean> = _isLoadingEntertainment

    private val _headlineNews = MutableLiveData<ArrayList<ArticlesItem>>()
    val headlineNews: LiveData<ArrayList<ArticlesItem>> = _headlineNews

    private val _technologyNews = MutableLiveData<ArrayList<ArticlesItem>>()
    val technologyNews: LiveData<ArrayList<ArticlesItem>> = _technologyNews

    private val _sportsNews = MutableLiveData<ArrayList<ArticlesItem>>()
    val sportsNews: LiveData<ArrayList<ArticlesItem>> = _sportsNews

    private val _scienceNews = MutableLiveData<ArrayList<ArticlesItem>>()
    val scienceNews: LiveData<ArrayList<ArticlesItem>> = _scienceNews

    private val _healthNews = MutableLiveData<ArrayList<ArticlesItem>>()
    val healthNews: LiveData<ArrayList<ArticlesItem>> = _healthNews

    private val _businessNews = MutableLiveData<ArrayList<ArticlesItem>>()
    val businesssNews: LiveData<ArrayList<ArticlesItem>> = _businessNews

    private val _entertainmentNews = MutableLiveData<ArrayList<ArticlesItem>>()
    val entertainmentNews: LiveData<ArrayList<ArticlesItem>> = _entertainmentNews

    init {
        getHeadlineNews()
    }

    fun getHeadlineNews() {
        _isLoading.value = _isLoading.value == null
        _isError.value = false
        val client = ApiConfig.getApiService().getLatestNews("id")
        client.enqueue(object : retrofit2.Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                _isLoading.value = false
                _isError.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.articles != null) {
                        _headlineNews.postValue(responseBody.articles)
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

    fun getLatestCategoryNews(category: String) {
        when (category) {
            Constants.TECHNOLOGY_CATEGORY -> {
                _isLoadingTechnology.value = _isLoadingTechnology.value == null
            }
            Constants.SPORTS_CATEGORY -> {
                _isLoadingSports.value = _isLoadingSports.value == null
            }
            Constants.SCIENCE_CATEGORY -> {
                _isLoadingScience.value = _isLoadingScience.value == null
            }
            Constants.HEALTH_CATEGORY -> {
                _isLoadingHealth.value = _isLoadingHealth.value == null
            }
            Constants.BUSINESS_CATEGORY -> {
                _isLoadingBusiness.value = _isLoadingBusiness.value == null
            }
            Constants.ENTERTAINMENT_CATEGORY -> {
                _isLoadingEntertainment.value = _isLoadingEntertainment.value == null
            }
        }
        val client = ApiConfig.getApiService().getLatestCategoryNews("id", category)
        client.enqueue(object : retrofit2.Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                when (category) {
                    Constants.TECHNOLOGY_CATEGORY -> {
                        _isLoadingTechnology.value = false
                    }
                    Constants.SPORTS_CATEGORY -> {
                        _isLoadingSports.value = false
                    }
                    Constants.SCIENCE_CATEGORY -> {
                        _isLoadingScience.value = false
                    }
                    Constants.HEALTH_CATEGORY -> {
                        _isLoadingHealth.value = false
                    }
                    Constants.BUSINESS_CATEGORY -> {
                        _isLoadingBusiness.value = false
                    }
                    Constants.ENTERTAINMENT_CATEGORY -> {
                        _isLoadingEntertainment.value = false
                    }
                }
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.articles != null) {
                        when (category) {
                            Constants.TECHNOLOGY_CATEGORY -> {
                                _technologyNews.postValue(responseBody.articles)
                            }
                            Constants.SPORTS_CATEGORY -> {
                                _sportsNews.postValue(responseBody.articles)
                            }
                            Constants.SCIENCE_CATEGORY -> {
                                _scienceNews.postValue(responseBody.articles)
                            }
                            Constants.HEALTH_CATEGORY -> {
                                _healthNews.postValue(responseBody.articles)
                            }
                            Constants.BUSINESS_CATEGORY -> {
                                _businessNews.postValue(responseBody.articles)
                            }
                            Constants.ENTERTAINMENT_CATEGORY -> {
                                _entertainmentNews.postValue(responseBody.articles)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                when (category) {
                    Constants.TECHNOLOGY_CATEGORY -> {
                        _isLoadingTechnology.value = false
                    }
                    Constants.SPORTS_CATEGORY -> {
                        _isLoadingSports.value = false
                    }
                    Constants.SCIENCE_CATEGORY -> {
                        _isLoadingScience.value = false
                    }
                    Constants.HEALTH_CATEGORY -> {
                        _isLoadingHealth.value = false
                    }
                    Constants.BUSINESS_CATEGORY -> {
                        _isLoadingBusiness.value = false
                    }
                    Constants.ENTERTAINMENT_CATEGORY -> {
                        _isLoadingEntertainment.value = false
                    }
                }
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "Home View Model"
    }
}