package com.dicoding.abednego.mendaurid.ui.detaildaurulang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.MetodeItem
import com.dicoding.abednego.mendaurid.databinding.ActivityDetailDaurUlangBinding

class DetailDaurUlangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDaurUlangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDaurUlangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_detail_daur_ulang)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val metodeItem = intent.getParcelableExtra<MetodeItem>("metode_item")
        val langkah = metodeItem?.langkah ?: listOf()
        val alatDanBahan = metodeItem?.alatDanBahan?: listOf()

        val langkahStringBuilder = createFormattedStringBuilder(langkah)
        val alatDanBahanStringBuilder = createFormattedStringBuilder(alatDanBahan)

        val langkahText = langkahStringBuilder.toString()
        val alatDanBahanText = alatDanBahanStringBuilder.toString()

        if (metodeItem != null) {
            binding.tvJudulDaurUlang.text = metodeItem.judul
            Glide.with(this).load(metodeItem.urlGambar).into(binding.ivDaurUlang)
            binding.tvDaftarAlatDanBahan.text = alatDanBahanText
            binding.tvDaftarCaraMembuat.text = langkahText
        }
    }

    private fun createFormattedStringBuilder(items: List<String?>): StringBuilder {
        val stringBuilder = StringBuilder()
        val lastIndex = items.lastIndex
        for ((index, item) in items.withIndex()) {
            stringBuilder.append("${index + 1}. $item")
            if (index != lastIndex) {
                stringBuilder.append("\n")
            }
        }
        return stringBuilder
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
}