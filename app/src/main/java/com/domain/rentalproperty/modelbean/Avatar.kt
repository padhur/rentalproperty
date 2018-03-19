package com.domain.rentalproperty.modelbean

data class Avatar(
        val small: Small? = null,
        val large: Large? = null,
        val profile: Profile? = null,
        val medium: Medium? = null,
        val url: String? = null
)
