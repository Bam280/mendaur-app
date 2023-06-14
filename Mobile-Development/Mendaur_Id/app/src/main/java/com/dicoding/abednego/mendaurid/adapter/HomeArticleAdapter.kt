package com.dicoding.abednego.mendaurid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.ArticlesItem
import com.dicoding.abednego.mendaurid.databinding.ItemHomeListArtikelBinding
import com.dicoding.abednego.mendaurid.ui.detailartikel.DetailArtikelActivity

class HomeArticleAdapter (private val data: MutableList<ArticlesItem>, private val context: Context) : RecyclerView.Adapter<HomeArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeListArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = data[position]
                    val intent = Intent(context, DetailArtikelActivity::class.java).apply {
                        putExtra(EXTRA_ARTICLE_ITEM, item)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    class ViewHolder(private val binding: ItemHomeListArtikelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {
            binding.tvJudulArtikel.text = data.title
            binding.tvDescription.text = data.content
            Glide.with(binding.ivProfile)
                .load(data.profilePicture)
                .into(binding.ivProfile)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(articles: List<ArticlesItem>) {
        val previousSize = data.size
        data.clear()
        data.addAll(articles)
        data.sortByDescending { it.createdAt }
        val newSize = data.size

        if (previousSize == newSize) {
            notifyItemRangeChanged(0, newSize)
        } else {
            if (previousSize > newSize) {
                notifyItemRangeChanged(0, newSize)
                notifyItemRangeRemoved(newSize, previousSize - newSize)
            } else {
                notifyItemRangeChanged(0, previousSize)
                notifyItemRangeInserted(previousSize, newSize - previousSize)
            }
        }
    }

    companion object {
        const val EXTRA_ARTICLE_ITEM = "article_item"
    }
}