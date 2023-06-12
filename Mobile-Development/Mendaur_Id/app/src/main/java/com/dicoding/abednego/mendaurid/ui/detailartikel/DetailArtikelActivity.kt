package com.dicoding.abednego.mendaurid.ui.detailartikel

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.ArticlesItem
import com.dicoding.abednego.mendaurid.databinding.ActivityDetailArtikelBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_detail_artikel)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val articleItem = intent.getParcelableExtra<ArticlesItem>(EXTRA_ARTICLE_ITEM)

        if (articleItem != null) {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(articleItem.createdAt.toString())
            val formattedDate = date?.let { outputFormat.format(it) }

            binding.tvNamaUser.text = articleItem.name
            binding.tvWaktuPost.text = formattedDate
            Glide.with(this).load(articleItem.profilePicture).into(binding.ivProfile)
            Glide.with(this).load(articleItem.url).into(binding.ivArtikel)
            binding.tvJudulArtikel.text = articleItem.title
            binding.tvDescIsiArtkel.text = articleItem.content
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_ARTICLE_ITEM = "article_item"
    }
}