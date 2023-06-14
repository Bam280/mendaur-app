package com.dicoding.abednego.mendaurid.ui.daftardaurulang

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.RecycleResponse
import com.dicoding.abednego.mendaurid.repository.MendaurRepository
import com.dicoding.abednego.mendaurid.utils.Result

class DaurUlangViewModel (private val repository: MendaurRepository) : ViewModel()  {
    fun getListRecycle(
        jenis: String
    ): LiveData<Result<RecycleResponse>> = repository.getListRecycle(jenis)
}