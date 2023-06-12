package com.dicoding.abednego.mendaurid.data.api.retrofit

import com.dicoding.abednego.mendaurid.data.api.response.mendaur.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface MendaurApiService {
    @GET("get-recycle")
    suspend fun getListRecycle(@Query("jenis") jenis: String): RecycleResponse

    @Multipart
    @POST("upload")
    suspend fun postScanResult(
        @Part file: MultipartBody.Part
    ): ScanResponse

    @GET("get-articles")
    suspend fun getArticles(): ArticleResponse

    @Multipart
    @POST("articles")
    suspend fun postArticle(
        @Part("uid") uid : RequestBody,
        @Part("title") title  : RequestBody,
        @Part("content") content : RequestBody,
        @Part file: MultipartBody.Part
    ): PostResponse

    @Multipart
    @POST("reports")
    suspend fun postReports(
        @Part("uid") uid : RequestBody,
        @Part("title") title  : RequestBody,
        @Part("content") content : RequestBody,
        @Part file: MultipartBody.Part
    ): ReportsResponse

}