package com.teguh.setiawan.newsmission.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teguh.setiawan.core.databinding.ItemNewsBinding
import com.teguh.setiawan.core.domain.model.News
import com.teguh.setiawan.core.utils.DateUtils
import com.teguh.setiawan.newsmission.ui.utils.Common.loadImage


class HomeAdapter (
    private var onItemClickListener: ((News) -> Unit)? = null
) : ListAdapter<News, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.newsId == newItem.newsId
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class HomeViewHolder(private var binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            binding.apply {
                ivNewsThumbnail.loadImage(data.urlToImage)
                tvNewsTitle.text = data.title
                tvNewsDetail.text = data.description
                tvNewsTime.text = data.publishedAt?.let { date -> DateUtils.formatDate(date) }
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(getItem(adapterPosition))
            }
        }
    }

}