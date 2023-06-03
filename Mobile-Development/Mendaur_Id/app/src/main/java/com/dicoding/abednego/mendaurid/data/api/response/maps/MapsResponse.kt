package com.dicoding.abednego.mendaurid.data.api.response.maps

import com.google.gson.annotations.SerializedName

data class MapsResponse(

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<Any?>? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ResultsItem(

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,
)

data class Location(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class Geometry(
	@field:SerializedName("location")
	val location: Location? = null
)