package com.dicoding.abednego.mendaurid.ui.daftarartikel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.abednego.mendaurid.databinding.ActivityDaftarArtikelBinding
import com.dicoding.abednego.mendaurid.ui.postartikel.PostArtikelActivity

class DaftarArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatBtnAdd.setOnClickListener{
            val intent = Intent(this, PostArtikelActivity::class.java)
            startActivity(intent)
        }
    }
}