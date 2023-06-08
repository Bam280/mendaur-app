package com.dicoding.abednego.mendaurid.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.ArticlesItem
import com.dicoding.abednego.mendaurid.databinding.ItemListArtikelBinding
import com.dicoding.abednego.mendaurid.ui.detailartikel.DetailArtikelActivity
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter (private val data: MutableList<ArticlesItem>, private val activity: Activity) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = data[position]
                    val intent = Intent(activity, DetailArtikelActivity::class.java).apply {
                        putExtra(EXTRA_ARTICLE_ITEM, item)
                    }
                    activity.startActivity(intent)
                }
            }
        }
    }

    class ViewHolder(private val binding: ItemListArtikelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(data.createdAt.toString())
            val formattedDate = date?.let { outputFormat.format(it) }

            Glide.with(binding.ivArtikel)
                .load(data.url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivArtikel)
            binding.tvJudulArtikel.text = data.title
            binding.tvDescription.text = data.content
            Glide.with(binding.ivProfile)
                .load(data.profilePicture)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivProfile)
            binding.tvNamaUser.text = data.name
            binding.tvWaktuPost.text = formattedDate

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