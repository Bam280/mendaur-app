package com.dicoding.abednego.mendaurid.data.api.response.mendaur


import com.google.gson.annotations.SerializedName

data class PostResponse(
	@field:SerializedName("public url")
	val publicUrl: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
