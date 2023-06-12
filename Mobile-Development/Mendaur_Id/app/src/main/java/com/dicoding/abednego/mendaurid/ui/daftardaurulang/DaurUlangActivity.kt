package com.dicoding.abednego.mendaurid.ui.daftardaurulang

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.adapter.DaurUlangAdapter
import com.dicoding.abednego.mendaurid.databinding.ActivityDaurUlangBinding
import com.dicoding.abednego.mendaurid.utils.Result
import com.dicoding.abednego.mendaurid.viewmodel.ViewModelFactory

class DaurUlangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaurUlangBinding
    private lateinit var adapter: DaurUlangAdapter
    private val daurUlangViewModel: DaurUlangViewModel by viewModels {
        ViewModelFactory()
    }
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaurUlangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_daur_ulang)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = binding.progressBar

        val jenis = intent.getStringExtra(EXTRA_JENIS).toString()
        if (jenis == SAMPAH){
            val textColor = ContextCompat.getColor(this, R.color.red)
            binding.tvJenisSampah.setTextColor(textColor)
        }

        binding.tvJenisSampah.text = jenis

        adapter = DaurUlangAdapter(emptyList(), this)
        binding.rvDaurUlang.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE
        daurUlangViewModel.getListRecycle(jenis).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    val data = result.data
                    if (data.listRecycle != null) {
                        adapter = DaurUlangAdapter(data.listRecycle.metode,this)
                        binding.rvDaurUlang.adapter = adapter
                    } else {
                        binding.tvTidakDitemukan.visibility = View.VISIBLE
                    }
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    val error = result.error
                    if (error.contains(TIME_OUT)) {
                        Toast.makeText(
                            this,
                            getString(R.string.error_timeout),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.tvTidakDitemukan.visibility = View.VISIBLE
                    }
                }
            }
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

    companion object {
        const val EXTRA_JENIS = "jenis"
        const val SAMPAH = "sampah"
        const val TIME_OUT = "timeout"
    }
}