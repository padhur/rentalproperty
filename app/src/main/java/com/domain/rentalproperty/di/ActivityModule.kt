package com.domain.rentalproperty.di

import com.domain.rentalproperty.view.RentalPropertyActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by padhu.r on 19/03/18.
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [PropertyListActivityModule::class, PropertyListActivityChildFragmentsModule::class, ViewModelModule::class])
    abstract fun bindPropertyListActivity() : RentalPropertyActivity
}