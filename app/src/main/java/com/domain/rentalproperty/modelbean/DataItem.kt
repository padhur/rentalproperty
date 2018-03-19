package com.domain.rentalproperty.modelbean

data class DataItem(
        val bedrooms: Int? = null,
        val owner: Owner? = null,
        val isPremium: Boolean? = null,
        val price: Double? = null,
        val description: String? = null,
        val photo: Photo? = null,
        val location: Location? = null,
        val id: String? = null,
        val state: String? = null,
        val title: String? = null,
        val bathrooms: Int? = null,
        val carspots: Int? = null
)
