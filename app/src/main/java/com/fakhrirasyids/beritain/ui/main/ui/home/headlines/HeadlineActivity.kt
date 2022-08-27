package com.fakhrirasyids.beritain.ui.main.ui.home.headlines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem
import com.fakhrirasyids.beritain.databinding.ActivityHeadlineBinding
import com.fakhrirasyids.beritain.ui.adapter.NewsAdapter
import com.fakhrirasyids.beritain.ui.adapter.OnClickItemCallback
import com.fakhrirasyids.beritain.ui.main.ui.home.HomeViewModel
import com.fakhrirasyids.beritain.ui.main.ui.webview.WebviewActivity
import com.fakhrirasyids.beritain.utils.Constants.Companion.LIST_NEWS
import com.fakhrirasyids.beritain.utils.Constants.Companion.NEWS_URL
import com.fakhrirasyids.beritain.utils.Constants.Companion.SERIAZABLE_LIST_NEWS

class HeadlineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeadlineBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var listNews: ArrayList<ArticlesItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel = obtainViewModel(this)

        setUpHeadlineAdapter()
        setBtnBackListener()
    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeViewModel {
        return ViewModelProvider(activity)[HomeViewModel::class.java]
    }

    private fun setUpHeadlineAdapter() {
        val args: Bundle = intent.getBundleExtra(LIST_NEWS)!!
        listNews = args.getSerializable(SERIAZABLE_LIST_NEWS) as ArrayList<ArticlesItem>
        val adapter = NewsAdapter(listNews)
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvHeadlineLatestNews.layoutManager = layoutManager
        binding.rvHeadlineLatestNews.adapter = adapter
        adapter.setOnItemClickCallback(object : OnClickItemCallback {
            override fun onNewsClicked(news: ArticlesItem) {
                val iWebview = Intent(this@HeadlineActivity, WebviewActivity::class.java)
                iWebview.putExtra(NEWS_URL, news.url)
                startActivity(iWebview)
            }
        })
    }

    private fun setBtnBackListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}