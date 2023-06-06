package com.dicoding.abednego.mendaurid.ui.daftardaurulang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.adapter.DaurUlangAdapter
import com.dicoding.abednego.mendaurid.databinding.ActivityDaurUlangBinding
import com.dicoding.abednego.mendaurid.viewmodel.ViewModelFactory
import com.dicoding.abednego.mendaurid.utils.Result

class DaurUlangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaurUlangBinding
    private lateinit var adapter: DaurUlangAdapter
    private val daurUlangViewModel: DaurUlangViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaurUlangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_daur_ulang)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val jenis = intent.getStringExtra("jenis").toString()

        adapter = DaurUlangAdapter(emptyList(), this)
        binding.rvDaurUlang.layoutManager = LinearLayoutManager(this)

        daurUlangViewModel.getListRecycle(jenis).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    val data = result.data
                    if (data.listRecycle.isNotEmpty()) {
                        binding.tvJenisSampah.text = jenis
                        adapter = DaurUlangAdapter(data.listRecycle[0].metode,this)
                        binding.rvDaurUlang.adapter = adapter
                    } else {
                        // Handle when data is empty
                    }
                }
                is Result.Error -> {
                    // Handle error
                }
                else ->{

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
}