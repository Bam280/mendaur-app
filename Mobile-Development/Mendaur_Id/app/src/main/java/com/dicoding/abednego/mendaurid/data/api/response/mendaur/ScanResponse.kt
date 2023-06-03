package com.dicoding.abednego.mendaurid.data.api.response.mendaur

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Result(

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("akurasi")
	val akurasi: String? = null,

	@field:SerializedName("tipe")
	val tipe: String? = null
)
