package com.domain.rentalproperty.modelbean

data class Location(
		val country: String? = null,
		val address_1: String? = null,
		val address_2: String? = null,
		val latitude: Double? = null,
		val postcode: String? = null,
		val suburb: String? = null,
		val id: Int? = null,
		val state: String? = null,
		val fullAddress: String? = null,
		val longitude: Double? = null
)
