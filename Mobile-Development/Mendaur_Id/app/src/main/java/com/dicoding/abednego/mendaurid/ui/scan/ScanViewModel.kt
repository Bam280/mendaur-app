package com.dicoding.abednego.mendaurid.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.ScanResponse
import com.dicoding.abednego.mendaurid.repository.MendaurRepository
import com.dicoding.abednego.mendaurid.utils.Result
import okhttp3.MultipartBody

class ScanViewModel (private val repository: MendaurRepository) : ViewModel()  {
    fun postScanResult(
        image: MultipartBody.Part
    ): LiveData<Result<ScanResponse>> = repository.postScanResult(image)
}