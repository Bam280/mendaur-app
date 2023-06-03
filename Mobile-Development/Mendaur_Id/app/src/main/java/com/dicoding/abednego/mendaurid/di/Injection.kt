package com.dicoding.abednego.mendaurid.di

import com.dicoding.abednego.mendaurid.data.api.retrofit.MapsApiConfig
import com.dicoding.abednego.mendaurid.repository.MendaurRepository

object Injection {
    fun mapsProvideRepository(): MendaurRepository {
        val apiService = MapsApiConfig.getMapsApiService()
        return MendaurRepository(apiService)
    }
}