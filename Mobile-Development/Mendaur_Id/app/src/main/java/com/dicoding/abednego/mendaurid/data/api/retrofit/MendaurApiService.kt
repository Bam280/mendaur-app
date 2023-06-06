package com.dicoding.abednego.mendaurid.data.api.retrofit

import com.dicoding.abednego.mendaurid.data.api.response.maps.MapsResponse
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.RecycleResponse
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.ScanResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface MendaurApiService {
    @GET
    suspend fun getListRecycle(@Query("jenis") jenis: String): RecycleResponse

    @Multipart
    @POST
    suspend fun getScanResult(
        @Part file: MultipartBody.Part
    ): ScanResponse
}