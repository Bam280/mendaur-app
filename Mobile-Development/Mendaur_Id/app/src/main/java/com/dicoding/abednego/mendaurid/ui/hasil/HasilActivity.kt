package com.dicoding.abednego.mendaurid.ui.hasil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityHasilBinding
import com.dicoding.abednego.mendaurid.ui.daftardaurulang.DaurUlangActivity
import com.dicoding.abednego.mendaurid.ui.maps.MapsActivity
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.Result

class HasilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_hasil_scan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val result = intent.getParcelableExtra<Result>("result")

        if (result != null) {
            binding.tvJenisSampah.text = result.jenis
            binding.tvTipeSampah.text = result.tipe
            binding.tvAkurasi.text = getString(R.string.tv_akurasi, result.akurasi)
        }

        binding.btnTempat.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        binding.btnCaraDaurUlang.setOnClickListener {
            val intent = Intent(this, DaurUlangActivity::class.java)
            intent.putExtra("jenis", result?.jenis)
            startActivity(intent)
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
}