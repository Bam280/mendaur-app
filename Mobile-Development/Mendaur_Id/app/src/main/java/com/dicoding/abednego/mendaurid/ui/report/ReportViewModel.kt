package com.dicoding.abednego.mendaurid.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.PostResponse
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.ReportsResponse
import com.dicoding.abednego.mendaurid.repository.MendaurRepository
import com.dicoding.abednego.mendaurid.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ReportViewModel (private val repository: MendaurRepository) : ViewModel()  {
    fun postReports(
        uid: RequestBody,
        title: RequestBody,
        content: RequestBody,
        image: MultipartBody.Part
    ): LiveData<Result<ReportsResponse>> = repository.postReports(uid, title, content, image)
}