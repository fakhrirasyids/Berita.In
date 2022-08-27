package com.fakhrirasyids.beritain.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem
import com.fakhrirasyids.beritain.databinding.ItemNewsContainerBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(
    private var listNews: ArrayList<ArticlesItem>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private lateinit var onClickItemCallback: OnClickItemCallback

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemNewsContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNews[position])
        holder.itemView.setOnClickListener {
            onClickItemCallback.onNewsClicked(listNews[position])
        }
    }

    override fun getItemCount() = listNews.size

    class ViewHolder(var binding: ItemNewsContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(news: ArticlesItem) {
            Glide.with(binding.root)
                .load(news.urlToImage)
                .into(binding.ivImgNews)
            binding.tvNewsTitle.text = news.title
            val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val outputDate = SimpleDateFormat("h:mm a, d MMM")
            val d: Date? = inputDate.parse(news.publishedAt!!)
            val formatted: String = outputDate.format(d!!)
            binding.tvTime.text = formatted
        }
    }

    fun setOnItemClickCallback(onClickItemCallback: OnClickItemCallback) {
        this.onClickItemCallback = onClickItemCallback
    }
}