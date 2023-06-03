package com.dicoding.abednego.mendaurid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.abednego.mendaurid.di.Injection
import com.dicoding.abednego.mendaurid.ui.maps.MapsViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapsViewModel(Injection.mapsProvideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}