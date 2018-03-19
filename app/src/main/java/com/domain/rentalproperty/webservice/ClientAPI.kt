package com.domain.rentalproperty.webservice

import com.domain.rentalproperty.modelbean.RentalProperty
import com.domain.rentalproperty.network.WebService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by padhu.r on 19/03/18.
 */
class ClientAPI(private val apiService : WebService) {
    fun getRentalPropertyList() : Single<RentalProperty> {
        return apiService.getProperties()
                .subscribeOn(Schedulers.io())
    }
}