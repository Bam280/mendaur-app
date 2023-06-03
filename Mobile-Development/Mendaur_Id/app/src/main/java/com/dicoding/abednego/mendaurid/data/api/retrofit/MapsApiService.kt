package com.dicoding.abednego.mendaurid.data.api.retrofit

import com.dicoding.abednego.mendaurid.data.api.response.maps.MapsResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface MapsApiService {
    @GET
    suspend fun getNearByPlaces(@Url url: String): MapsResponse
}