package com.dicoding.abednego.mendaurid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.abednego.mendaurid.data.item.ItemAbout
import com.dicoding.abednego.mendaurid.databinding.ItemListAboutBinding

class AboutAdapter (private val items: List<ItemAbout>) : RecyclerView.Adapter<AboutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListAboutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ItemListAboutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemAbout) {
            binding.ivProfile.setImageResource(item.photo)
            binding.tvNamaAnggota.text = item.nama
            binding.tvIdAnggota.text = item.id
        }
    }
}