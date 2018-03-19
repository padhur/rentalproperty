package com.domain.rentalproperty.app

import android.app.Activity
import android.app.Application
import com.domain.rentalproperty.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

/**
 * Created by padhu.r on 19/03/18.
 */
class HouseRentalApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
                .builder()
                .create(this)
                .inject(this)
    }
}