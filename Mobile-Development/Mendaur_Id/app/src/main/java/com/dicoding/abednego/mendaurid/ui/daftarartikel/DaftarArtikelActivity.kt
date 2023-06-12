package com.dicoding.abednego.mendaurid.ui.daftarartikel

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.adapter.ArticleAdapter
import com.dicoding.abednego.mendaurid.databinding.ActivityDaftarArtikelBinding
import com.dicoding.abednego.mendaurid.ui.main.MainActivity
import com.dicoding.abednego.mendaurid.ui.postartikel.PostArtikelActivity
import com.dicoding.abednego.mendaurid.utils.Result
import com.dicoding.abednego.mendaurid.viewmodel.ViewModelFactory

class DaftarArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarArtikelBinding
    private val daftarArtikelViewModel: DaftarArtikelViewModel by viewModels {
        ViewModelFactory()
    }
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_daftar_artikel)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.floatBtnAdd.setOnClickListener{
            val intent = Intent(this, PostArtikelActivity::class.java)
            startActivity(intent)
        }

        progressBar = binding.progressBar

        binding.rvArticle.layoutManager = LinearLayoutManager(this)

        getArticle()
    }

    private fun getArticle() {
        progressBar.visibility = View.VISIBLE
        daftarArtikelViewModel.getArticles().observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    val data = result.data
                    if (data.articles?.isNotEmpty() == true) {
                        val adapter = ArticleAdapter(mutableListOf(), this)
                        adapter.setData(data.articles)
                        binding.rvArticle.adapter = adapter
                    } else {
                        binding.tvArtikelTidakDitemukan.visibility = View.VISIBLE
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
                        binding.tvArtikelTidakDitemukan.visibility = View.VISIBLE
                    }
                }
            }
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
        startActivity(intent)
    }

    companion object {
        const val TIME_OUT = "timeout"
    }
}