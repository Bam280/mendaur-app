package com.dicoding.abednego.mendaurid.ui.hasil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityHasilBinding

class HasilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_hasil_scan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}