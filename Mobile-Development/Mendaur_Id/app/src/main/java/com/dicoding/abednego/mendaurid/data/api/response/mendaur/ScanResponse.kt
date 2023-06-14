package com.dicoding.abednego.mendaurid.data.api.response.mendaur

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ScanResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class Result(
	@field:SerializedName("jenis")
	val jenis: String,

	@field:SerializedName("akurasi")
	val akurasi: String,

	@field:SerializedName("tipe")
	val tipe: String
) : Parcelable
