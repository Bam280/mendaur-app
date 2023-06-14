package com.dicoding.abednego.mendaurid.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.MetodeItem
import com.dicoding.abednego.mendaurid.databinding.ItemListDaurUlangBinding
import com.dicoding.abednego.mendaurid.ui.detaildaurulang.DetailDaurUlangActivity

class DaurUlangAdapter(private val data: List<MetodeItem>, private val activity: Activity) : RecyclerView.Adapter<DaurUlangAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListDaurUlangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = data[position]
                    val intent = Intent(activity, DetailDaurUlangActivity::class.java).apply {
                        putExtra(EXTRA_METODE_ITEM, item)
                    }
                    activity.startActivity(intent)
                }
            }
        }
    }

    class ViewHolder(private val binding: ItemListDaurUlangBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MetodeItem) {
            binding.tvJudulDaurUlang.text = data.judul
            Glide.with(binding.ivDaurUlang)
                .load(data.urlGambar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivDaurUlang)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    companion object {
        const val EXTRA_METODE_ITEM = "metode_item"
    }
}
