package com.fakhrirasyids.beritain.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem
import com.fakhrirasyids.beritain.databinding.ItemHeadlineLatestNewsBinding
import com.fakhrirasyids.beritain.databinding.LoadMoreLayoutBinding
import com.fakhrirasyids.beritain.ui.main.ui.home.headlines.HeadlineActivity
import com.fakhrirasyids.beritain.utils.Constants.Companion.LIST_NEWS
import com.fakhrirasyids.beritain.utils.Constants.Companion.SERIAZABLE_LIST_NEWS
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class HeadlineNewsAdapter(
    private var listNews: List<ArticlesItem>,
    private var context: Context?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var headlineViewHolder: HeadlineViewHolder
    private lateinit var buttonViewHolder: ButtonViewHolder
    private lateinit var onClickItemCallback: OnClickItemCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        headlineViewHolder = HeadlineViewHolder(
            ItemHeadlineLatestNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        buttonViewHolder = ButtonViewHolder(
            LoadMoreLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context!!
        )

        return if (viewType == NEWS) {
            headlineViewHolder
        } else {
            buttonViewHolder
        }
    }

    override fun getItemCount() = 4

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == NEWS) {
            headlineViewHolder.bind(listNews[position])
            headlineViewHolder.itemView.setOnClickListener {
                onClickItemCallback.onNewsClicked(listNews[position])
            }
        } else {
            buttonViewHolder.showButton(listNews)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 3) {
            LOAD_MORE
        } else {
            NEWS
        }
    }

    class HeadlineViewHolder(var binding: ItemHeadlineLatestNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(news: ArticlesItem) {
            binding.tvNewsTitle.text = news.title
            Glide.with(binding.root)
                .load(news.urlToImage)
                .into(binding.ivImgNews)
            val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val outputDate = SimpleDateFormat("h:mm a, d MMM")
            val d: Date? = inputDate.parse(news.publishedAt!!)
            val formatted: String = outputDate.format(d!!)
            binding.tvTime.text = formatted
        }
    }

    class ButtonViewHolder(
        private var binding: LoadMoreLayoutBinding,
        private var context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun showButton(listNews: List<ArticlesItem>) {
            binding.btnLoadMoreHeadline.visibility = View.VISIBLE
            binding.btnLoadMoreHeadline.setOnClickListener {
                val iHeadline = Intent(context, HeadlineActivity::class.java)
                val args = Bundle()
                args.putSerializable(SERIAZABLE_LIST_NEWS, listNews as Serializable)
                iHeadline.putExtra(LIST_NEWS, args)
                context.startActivity(iHeadline)
            }
        }
    }

    fun setOnItemClickCallback(onClickItemCallback: OnClickItemCallback) {
        this.onClickItemCallback = onClickItemCallback
    }

    companion object {
        const val NEWS = 0
        const val LOAD_MORE = 1
    }
}