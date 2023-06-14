package com.dicoding.abednego.mendaurid.ui.postartikel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.PostResponse
import com.dicoding.abednego.mendaurid.repository.MendaurRepository
import com.dicoding.abednego.mendaurid.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostArtikelViewModel (private val repository: MendaurRepository) : ViewModel()  {
    fun postArticle(
        uid: RequestBody,
        title: RequestBody,
        content: RequestBody,
        image: MultipartBody.Part
    ): LiveData<Result<PostResponse>> = repository.postArticle(uid, title, content, image)
}