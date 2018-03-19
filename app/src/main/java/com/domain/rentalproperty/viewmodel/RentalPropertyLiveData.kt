package com.domain.rentalproperty.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.domain.rentalproperty.modelbean.RentalProperty
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by padhu.r on 19/03/18.
 */
data class MappedPropertyList(
        val allPropertyList: RentalProperty,
        val premiumPropertyList: RentalProperty
)

class PropertyListLiveData : LiveData<MappedPropertyList>() {
     fun loadList(getPropertyListApi: () -> Single<RentalProperty>,
                  loadingStatus: MutableLiveData<Boolean>,
                  errorStatus: MutableLiveData<Throwable>): Disposable {
        return getPropertyListApi()
                .doOnSubscribe { loadingStatus.postValue(true) }
                .doAfterTerminate { loadingStatus.postValue(false) }
                .observeOn(Schedulers.computation())
                .map {
                    MappedPropertyList(it, RentalProperty(it.data?.filter { it?.isPremium ?: false }))
                }
                .subscribe(
                        { postValue(it) },
                        { errorStatus.postValue(it) }
                )
    }
}