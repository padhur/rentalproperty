package com.domain.rentalproperty.network

import com.domain.rentalproperty.modelbean.RentalProperty
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by padhu.r on 19/03/18.
 */
interface WebService {
    @GET("properties")
    fun getProperties() : Single<RentalProperty>
}