package com.fakhrirasyids.beritain.ui.main.ui.home.categories

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem
import com.fakhrirasyids.beritain.databinding.FragmentBusinessBinding
import com.fakhrirasyids.beritain.ui.adapter.NewsAdapter
import com.fakhrirasyids.beritain.ui.adapter.OnClickItemCallback
import com.fakhrirasyids.beritain.ui.main.ui.home.HomeViewModel
import com.fakhrirasyids.beritain.ui.main.ui.webview.WebviewActivity
import com.fakhrirasyids.beritain.utils.Constants

class BusinessFragment : Fragment() {
    private var _binding: FragmentBusinessBinding? = null
    private lateinit var homeViewModel: HomeViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = obtainViewModel(requireActivity())

        observeLoading()
        setUpBusinessNews()
    }

    private fun obtainViewModel(activity: FragmentActivity): HomeViewModel {
        return ViewModelProvider(activity)[HomeViewModel::class.java]
    }

    private fun observeLoading() {
        homeViewModel.isLoadingBusiness.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUpBusinessNews() {
        homeViewModel.getLatestCategoryNews(Constants.BUSINESS_CATEGORY)
        homeViewModel.businesssNews.observe(viewLifecycleOwner) { newsArticle ->
            val adapter = NewsAdapter(newsArticle)
            val horizontalLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvCategoryNews.layoutManager = horizontalLayoutManager
            binding.rvCategoryNews.adapter = adapter
            adapter.setOnItemClickCallback(object : OnClickItemCallback {
                override fun onNewsClicked(news: ArticlesItem) {
                    val iWebview = Intent(context, WebviewActivity::class.java)
                    iWebview.putExtra(Constants.NEWS_URL, news.url)
                    startActivity(iWebview)
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvCategoryNews.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvCategoryNews.visibility = View.VISIBLE
        }
    }
}