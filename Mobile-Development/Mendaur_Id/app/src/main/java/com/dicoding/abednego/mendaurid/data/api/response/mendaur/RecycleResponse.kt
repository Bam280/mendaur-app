package com.dicoding.abednego.mendaurid.data.api.response.mendaur

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RecycleResponse(

	@field:SerializedName("list_recycle")
	val listRecycle: ListRecycle? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ListRecycle(

	@field:SerializedName("metode")
	val metode: List<MetodeItem>,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("total_methods")
	val totalMethods: Int? = null
)

@Parcelize
data class MetodeItem(

	@field:SerializedName("langkah")
	val langkah: List<String?>? = null,

	@field:SerializedName("url_gambar")
	val urlGambar: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("alat_dan_bahan")
	val alatDanBahan: List<String?>? = null
) : Parcelable

