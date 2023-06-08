package com.dicoding.abednego.mendaurid.ui.scan

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityScanBinding
import com.dicoding.abednego.mendaurid.ui.hasil.HasilActivity
import com.dicoding.abednego.mendaurid.utils.Result
import com.dicoding.abednego.mendaurid.utils.reduceFileImage
import com.dicoding.abednego.mendaurid.utils.reduceFileImageAndroidX
import com.dicoding.abednego.mendaurid.utils.uriToFile
import com.dicoding.abednego.mendaurid.viewmodel.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private var getFile: File? = null
    private val scanViewModel: ScanViewModel by viewModels {
        ViewModelFactory()
    }
    private lateinit var progressDialog: ProgressDialog

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.no_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_scan_sampah)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCamerax.setOnClickListener { startCameraX() }
        binding.btnGallery.setOnClickListener { startGallery() }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(getString(R.string.memuat))
        progressDialog.setMessage(getString(R.string.mohon_menunggu))
        progressDialog.setCancelable(false)

        binding.btnScan.setOnClickListener{

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

            scanViewModel.postScanResult(imageMultipart).observe(this){result ->
                when (result) {
                    is Result.Success -> {
                        progressDialog.dismiss()
                        val data = result.data
                        if (data.result != null) {
                            val intent = Intent(this, HasilActivity::class.java)
                            intent.putExtra(RESULT, data.result)
                            startActivity(intent)
                            finish()
                        }
                        Toast.makeText(this,
                            getString(R.string.scan_berhasil),
                            Toast.LENGTH_LONG).show()
                    }
                    is Result.Error ->{
                        progressDialog.dismiss()
                        Toast.makeText(
                            this,
                            getString(R.string.scan_tidak_berhasil),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = getString(R.string.intent_type)
        val chooser = Intent.createChooser(intent, getString(R.string.choose_image))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra(getString(R.string.picture)) as File
            val isBackCamera = it.data?.getBooleanExtra(getString(R.string.is_back_camera), true) as Boolean

            val resultFile = reduceFileImageAndroidX(myFile, isBackCamera)
            binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(resultFile.path))
            getFile = resultFile
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ScanActivity)
            getFile = myFile
            binding.previewImageView.setImageURI(selectedImg)
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
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val FILE = "file"
        const val RESULT = "result"
    }
}