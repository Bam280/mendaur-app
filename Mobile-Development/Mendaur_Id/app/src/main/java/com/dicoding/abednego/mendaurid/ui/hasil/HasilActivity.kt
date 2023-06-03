package com.dicoding.abednego.mendaurid.ui.hasil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityHasilBinding
import com.dicoding.abednego.mendaurid.ui.maps.MapsActivity

class HasilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_hasil_scan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnTempat.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    @Suppress("DEPRECATION")
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