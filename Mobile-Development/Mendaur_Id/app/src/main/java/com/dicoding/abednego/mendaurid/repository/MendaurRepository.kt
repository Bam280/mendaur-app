package com.dicoding.abednego.mendaurid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.abednego.mendaurid.data.api.response.maps.MapsResponse
import com.dicoding.abednego.mendaurid.data.api.retrofit.MapsApiService
import com.dicoding.abednego.mendaurid.data.api.retrofit.MendaurApiService
import com.dicoding.abednego.mendaurid.data.item.FakeDataRecycle
import com.dicoding.abednego.mendaurid.data.item.FakeDataScan
import com.dicoding.abednego.mendaurid.utils.Result
import okhttp3.MultipartBody

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

    fun getListRecycle(jenis: String): LiveData<Result<FakeDataRecycle>> = liveData {
        try {
            val response = FakeDataRecycle
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }
    fun getScanResult(image: MultipartBody.Part): LiveData<Result<FakeDataScan>> = liveData {
        try {
            val response = FakeDataScan
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }
}