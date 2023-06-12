package com.dicoding.abednego.mendaurid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.abednego.mendaurid.data.api.response.maps.MapsResponse
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.*
import com.dicoding.abednego.mendaurid.data.api.retrofit.MapsApiService
import com.dicoding.abednego.mendaurid.data.api.retrofit.MendaurApiService
import com.dicoding.abednego.mendaurid.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MendaurRepository(
    private val MapsApiService: MapsApiService,
    private val MendaurApiService : MendaurApiService
) {
    fun getPlaces(url: String): LiveData<Result<MapsResponse>> = liveData {
        try {
            val response = MapsApiService.getNearByPlaces(url)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

    fun getListRecycle(jenis: String): LiveData<Result<RecycleResponse>> = liveData {
        try {
            val response = MendaurApiService.getListRecycle(jenis)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }
    fun postScanResult(image: MultipartBody.Part): LiveData<Result<ScanResponse>> = liveData {
        try {
            val response = MendaurApiService.postScanResult(image)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

    fun getArticles():LiveData<Result<ArticleResponse>> = liveData {
        try {
            val response = MendaurApiService.getArticles()
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

    fun postArticle(
        uid: RequestBody,
        title: RequestBody,
        content: RequestBody,
        image: MultipartBody.Part
    ):LiveData<Result<PostResponse>> = liveData{
        try {
            val response = MendaurApiService.postArticle(uid,title,content,image)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun postReports(
        uid: RequestBody,
        title: RequestBody,
        content: RequestBody,
        image: MultipartBody.Part
    ):LiveData<Result<ReportsResponse>> = liveData{
        try {
            val response = MendaurApiService.postReports(uid,title,content,image)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }
}