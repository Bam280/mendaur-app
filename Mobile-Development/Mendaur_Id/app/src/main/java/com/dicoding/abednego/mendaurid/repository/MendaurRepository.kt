package com.dicoding.abednego.mendaurid.repository

import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.abednego.mendaurid.data.api.response.maps.MapsResponse
import com.dicoding.abednego.mendaurid.data.api.retrofit.MapsApiService
import com.dicoding.abednego.mendaurid.utils.Result

class MendaurRepository(
    private val MapsApiService: MapsApiService

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
}