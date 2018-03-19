package com.domain.rentalproperty.di

import android.arch.lifecycle.ViewModelProviders
import com.domain.rentalproperty.view.RentalPropertyActivity
import com.domain.rentalproperty.view.RentalPropertyFragment
import com.domain.rentalproperty.viewmodel.RentalPropertyViewModel
import com.domain.rentalproperty.viewmodel.RentalPropertyViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * Created by padhu.r on 19/03/18.
 */

@Module
abstract class PropertyListActivityChildFragmentsModule {
    @ContributesAndroidInjector
    abstract fun bindPropertyListFragment(): RentalPropertyFragment
}

@Module
class PropertyListActivityModule {
    @Provides
    fun PropertyListActivityModule(activity: RentalPropertyActivity, propertyListViewModelFactory: RentalPropertyViewModelFactory) =
            ViewModelProviders.of(activity, propertyListViewModelFactory).get(RentalPropertyViewModel::class.java)
}