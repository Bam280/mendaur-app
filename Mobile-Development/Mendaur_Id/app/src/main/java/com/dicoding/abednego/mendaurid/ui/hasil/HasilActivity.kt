package com.dicoding.abednego.mendaurid.ui.hasil

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityHasilBinding
import com.dicoding.abednego.mendaurid.ui.daftardaurulang.DaurUlangActivity
import com.dicoding.abednego.mendaurid.ui.maps.MapsActivity
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.Result
import com.dicoding.abednego.mendaurid.ui.report.ReportActivity

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

            if (result.jenis == SAMPAH) {
                val textColor = ContextCompat.getColor(this, R.color.red)
                binding.tvJenisSampah.setTextColor(textColor)
                binding.tvTipeSampah.setTextColor(textColor)
                binding.tvAkurasi.setTextColor(textColor)
            }
        }

        binding.btnTempat.setOnClickListener {
            checkLocation()
        }
        binding.btnCaraDaurUlang.setOnClickListener {
            val intent = Intent(this, DaurUlangActivity::class.java)
            intent.putExtra(EXTRA_JENIS, result?.jenis)
            startActivity(intent)
        }
    }

    private fun checkLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_PERMISSIONS
            )
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.hasil_menu, menu)
        val addMenuItem = menu.findItem(R.id.report)
        addMenuItem.setOnMenuItemClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
            true
        }
        return true
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val EXTRA_JENIS = "jenis"
        const val SAMPAH = "sampah"
    }
}