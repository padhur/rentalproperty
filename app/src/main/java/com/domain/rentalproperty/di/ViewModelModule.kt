package com.domain.rentalproperty.di

import com.domain.rentalproperty.app.HouseRentalApp
import com.domain.rentalproperty.webservice.ClientAPI
import com.domain.rentalproperty.viewmodel.RentalPropertyViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by padhu.r on 19/03/18.
 */
@Module
class ViewModelModule {
    @Provides
    fun providePropertyListViewModelFactory(app : HouseRentalApp, apiClient: ClientAPI) = RentalPropertyViewModelFactory(app, apiClient)
}