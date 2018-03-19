package com.domain.rentalproperty.modelbean

data class Image(
        val small: Small? = null,
        val large: Large? = null,
        val medium: Medium? = null,
        val url: String? = null
)
