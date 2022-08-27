package com.fakhrirasyids.beritain.ui.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem
import com.fakhrirasyids.beritain.ui.adapter.HeadlineNewsAdapter
import com.fakhrirasyids.beritain.databinding.FragmentHomeBinding
import com.fakhrirasyids.beritain.ui.adapter.OnClickItemCallback
import com.fakhrirasyids.beritain.ui.adapter.SectionPagerAdapter
import com.fakhrirasyids.beritain.ui.main.ui.webview.WebviewActivity
import com.fakhrirasyids.beritain.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mRunnable: Runnable

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = obtainViewModel(requireActivity())

        setUpHeadlineNews()
        observeLoading()
        setOnCheckedChangeListener()
        setSwipeRefreshLayout()

        return root
    }

    private fun obtainViewModel(activity: FragmentActivity): HomeViewModel {
        return ViewModelProvider(activity)[HomeViewModel::class.java]
    }

    private fun observeLoading() {
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            homeViewModel.isError.observe(viewLifecycleOwner) { isError ->
                showContent(isLoading, isError)
            }
        }
    }

    private fun showContent(isLoading: Boolean, isError: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.containerHome.visibility = View.GONE
            binding.tvError.visibility = View.GONE
        } else if (!isLoading && isError) {
            binding.progressBar.visibility = View.GONE
            binding.containerHome.visibility = View.GONE
            binding.tvError.visibility = View.VISIBLE
        } else if (!isLoading && !isError) {
            binding.progressBar.visibility = View.GONE
            binding.containerHome.visibility = View.VISIBLE
            binding.tvError.visibility = View.GONE
        }
    }

    private fun setUpHeadlineNews() {
        homeViewModel.headlineNews.observe(viewLifecycleOwner) { newsArticle ->
            val adapter = HeadlineNewsAdapter(newsArticle, context)
            val horizontalLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvHeadlineLatestNews.layoutManager = horizontalLayoutManager
            binding.rvHeadlineLatestNews.adapter = adapter
            adapter.setOnItemClickCallback(object : OnClickItemCallback {
                override fun onNewsClicked(news: ArticlesItem) {
                    val iWebview = Intent(context, WebviewActivity::class.java)
                    iWebview.putExtra(Constants.NEWS_URL, news.url)
                    startActivity(iWebview)
                }
            })
        }
    }

    private fun setOnCheckedChangeListener() {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(Constants.TAB_TITLES[position])
        }.attach()
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            mRunnable = Runnable {
                homeViewModel.getHeadlineNews()
                homeViewModel.headlineNews.observe(viewLifecycleOwner) {
                    if (!it.isNullOrEmpty()) {
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
            mRunnable.run()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}