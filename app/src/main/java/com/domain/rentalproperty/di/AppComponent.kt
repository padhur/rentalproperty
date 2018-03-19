package com.domain.rentalproperty.di

import com.domain.rentalproperty.app.HouseRentalApp
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by padhu.r on 19/03/18.
 */
@Singleton
@Component(modules = [(AppModule::class),
    (NetworkModule::class),
    (ActivityModule::class)])
interface AppComponent : AndroidInjector<HouseRentalApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<HouseRentalApp>()
}