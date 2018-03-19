package com.domain.rentalproperty.di

import com.domain.rentalproperty.webservice.ClientAPI
import com.domain.rentalproperty.network.WebService
import dagger.Module
import dagger.Provides


/**
 * Created by padhu.r on 19/03/18.
 */
@Module
class AppModule {
    @Provides
    fun provideApiClient(apiService: WebService): ClientAPI {
        return ClientAPI(apiService)
    }
}