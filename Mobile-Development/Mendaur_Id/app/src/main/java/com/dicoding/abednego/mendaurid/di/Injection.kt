package com.dicoding.abednego.mendaurid.di

import com.dicoding.abednego.mendaurid.data.api.retrofit.MapsApiConfig
import com.dicoding.abednego.mendaurid.data.api.retrofit.MendaurApiConfig
import com.dicoding.abednego.mendaurid.repository.MendaurRepository

object Injection {
    fun provideRepository(): MendaurRepository {
        val mapsApiService = MapsApiConfig.getMapsApiService()
        val mendaurApiService = MendaurApiConfig.getMendaurApiService()
        return MendaurRepository(mapsApiService,mendaurApiService )
    }
}