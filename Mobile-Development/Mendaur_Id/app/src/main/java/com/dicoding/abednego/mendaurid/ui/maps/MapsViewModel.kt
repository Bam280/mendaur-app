package com.dicoding.abednego.mendaurid.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abednego.mendaurid.data.api.response.maps.MapsResponse
import com.dicoding.abednego.mendaurid.repository.MendaurRepository
import com.dicoding.abednego.mendaurid.utils.Result

class MapsViewModel (private val repository: MendaurRepository) : ViewModel() {
    fun getNearbyBankSampah(url: String):LiveData<Result<MapsResponse>> = repository.getPlaces(url)
}