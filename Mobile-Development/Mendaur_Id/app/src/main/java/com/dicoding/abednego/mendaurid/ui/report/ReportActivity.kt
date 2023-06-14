package com.dicoding.abednego.mendaurid.ui.report

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityReportBinding
import com.dicoding.abednego.mendaurid.ui.daftarartikel.DaftarArtikelActivity
import com.dicoding.abednego.mendaurid.utils.Result
import com.dicoding.abednego.mendaurid.utils.reduceFileImage
import com.dicoding.abednego.mendaurid.utils.uriToFile
import com.dicoding.abednego.mendaurid.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding
    private lateinit var auth: FirebaseAuth
    private var getFile: File? = null
    private lateinit var progressDialog: ProgressDialog
    private val reportViewModel: ReportViewModel by viewModels {
        ViewModelFactory()
    }
    private var jenisReport: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_report)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(getString(R.string.memuat))
        progressDialog.setMessage(getString(R.string.mohon_menunggu))
        progressDialog.setCancelable(false)

        auth = FirebaseAuth.getInstance()

        var uid: String? = null

        val sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        val accountId = sharedPreferences.getString(ACCOUNT_ID, null)
        if (accountId != null) {
            uid = accountId
        }

        val currentUser = auth.currentUser
        val photoUrl = currentUser?.photoUrl

        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivProfile)

        binding.ivReport.setOnClickListener {
            startGallery()
        }

        val jenisReportAdapter = ArrayAdapter(this, R.layout.item_list_jenis_report, dropdownItems)
        (binding.etJenisReport.editText as? AutoCompleteTextView)?.apply {
            setAdapter(jenisReportAdapter)
            doAfterTextChanged { text ->
                jenisReport = text.toString()
            }
        }

        binding.btnSend.setOnClickListener{
            val id = uid!!.toRequestBody("text/plain".toMediaTypeOrNull())
            val title = jenisReport
            if(title.isEmpty()) {
                Toast.makeText(this, getString(R.string.empty_title_report), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val content = binding.etDeskripsiReport.text.toString()
            if(content.isEmpty()) {
                Toast.makeText(this, getString(R.string.empty_content_report), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val file = getFile?.takeIf { it.exists() } ?: run {
                Toast.makeText(this, getString(R.string.empty_file), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressDialog.show()

            val reducedFile = reduceFileImage(file)
            val requestImageFile = reducedFile.asRequestBody(getString(R.string.media_type).toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                FILE,
                file.name,
                requestImageFile
            )

            reportViewModel.postReports(
                id,
                title.toRequestBody("text/plain".toMediaTypeOrNull()),
                content.toRequestBody("text/plain".toMediaTypeOrNull()),
                imageMultipart
            ).observe(this) { result ->
                when (result) {
                    is Result.Success -> {
                        progressDialog.dismiss()
                        val data = result.data
                        val message = data.message
                        Toast.makeText(this,
                            message,
                            Toast.LENGTH_LONG).show()
                        finish()
                    }
                    is Result.Error -> {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this,
                            getString(R.string.post_report_tidak_berhasil),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
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
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            binding.ivReport.setImageURI(selectedImg)
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

    companion object {
        const val MY_PREF = "MyPrefs"
        const val ACCOUNT_ID = "account_id"
        const val FILE = "file"
        val dropdownItems = arrayOf("Kesalahan Prediksi", "Bug Dalam Aplikasi", "Lainnya")
    }
}