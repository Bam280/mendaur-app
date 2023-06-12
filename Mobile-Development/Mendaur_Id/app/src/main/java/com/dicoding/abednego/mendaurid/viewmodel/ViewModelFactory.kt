package com.dicoding.abednego.mendaurid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.abednego.mendaurid.di.Injection
import com.dicoding.abednego.mendaurid.ui.daftarartikel.DaftarArtikelViewModel
import com.dicoding.abednego.mendaurid.ui.daftardaurulang.DaurUlangViewModel
import com.dicoding.abednego.mendaurid.ui.home.HomeViewModel
import com.dicoding.abednego.mendaurid.ui.maps.MapsViewModel
import com.dicoding.abednego.mendaurid.ui.postartikel.PostArtikelViewModel
import com.dicoding.abednego.mendaurid.ui.report.ReportViewModel
import com.dicoding.abednego.mendaurid.ui.scan.ScanViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapsViewModel(Injection.provideRepository()) as T
        } else if (modelClass.isAssignableFrom(DaurUlangViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DaurUlangViewModel(Injection.provideRepository()) as T
        } else if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScanViewModel(Injection.provideRepository()) as T
        } else if (modelClass.isAssignableFrom(DaftarArtikelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DaftarArtikelViewModel(Injection.provideRepository()) as T
        } else if (modelClass.isAssignableFrom(PostArtikelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostArtikelViewModel(Injection.provideRepository()) as T
        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(Injection.provideRepository()) as T
        } else if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModel(Injection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}