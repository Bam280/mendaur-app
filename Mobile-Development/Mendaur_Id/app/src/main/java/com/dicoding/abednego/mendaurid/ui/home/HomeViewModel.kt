package com.dicoding.abednego.mendaurid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.ArticleResponse
import com.dicoding.abednego.mendaurid.repository.MendaurRepository
import com.dicoding.abednego.mendaurid.utils.Result

class HomeViewModel (private val repository: MendaurRepository) : ViewModel()  {
    fun getArticles(
    ): LiveData<Result<ArticleResponse>> = repository.getArticles()
}