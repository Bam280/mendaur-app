package com.dicoding.abednego.mendaurid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.abednego.mendaurid.data.item.ItemJenisSampah
import com.dicoding.abednego.mendaurid.databinding.ItemListJenisBinding
import com.dicoding.abednego.mendaurid.ui.daftardaurulang.DaurUlangActivity

class JenisSampahAdapter(private val items: List<ItemJenisSampah>, private val context: Context) : RecyclerView.Adapter<JenisSampahAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListJenisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = items[position]
                    val intent = Intent(context, DaurUlangActivity::class.java).apply {
                        putExtra(EXTRA_JENIS, item.jenis)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    class ViewHolder(private val binding: ItemListJenisBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemJenisSampah) {
            binding.ivProfile.setImageResource(item.iconResId)
            binding.tvJenis.text = item.jenis
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    companion object {
        const val EXTRA_JENIS = "jenis"
    }
}