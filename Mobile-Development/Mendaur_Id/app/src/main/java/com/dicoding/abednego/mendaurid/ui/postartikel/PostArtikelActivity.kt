package com.dicoding.abednego.mendaurid.ui.postartikel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityPostArtikelBinding
import com.dicoding.abednego.mendaurid.ui.scan.uriToFile
import com.google.firebase.auth.FirebaseAuth
import java.io.File

class PostArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostArtikelBinding
    private lateinit var auth: FirebaseAuth
    private var getFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val photoUrl = currentUser?.photoUrl
        val displayName = currentUser?.displayName

        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivProfile)

        binding.ivArtikel.setOnClickListener {
            startGallery()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = getString(R.string.intent_type)
        val chooser = Intent.createChooser(intent, getString(R.string.choose_image))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@PostArtikelActivity)
            getFile = myFile
            binding.ivArtikel.setImageURI(selectedImg)
        }
    }
}