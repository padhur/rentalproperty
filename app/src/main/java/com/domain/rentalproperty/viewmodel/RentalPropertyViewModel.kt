package com.domain.rentalproperty.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.domain.rentalproperty.webservice.ClientAPI
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

/**
 * Created by padhu.r on 19/03/18.
 */
class RentalPropertyViewModel(application: Application, private val apiClient: ClientAPI) : AndroidViewModel(application) {
    private var disposeBag = CompositeDisposable()
    val propertyListLiveData = PropertyListLiveData()
    val loadingStatus = MutableLiveData<Boolean>()
    val errorStatus = MutableLiveData<Throwable>()
    var isPremium = false

    init{
        loadPropertyList()
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }

    fun loadPropertyList() = propertyListLiveData
            .loadList(apiClient::getRentalPropertyList, loadingStatus, errorStatus)
            .addTo(disposeBag)
}

class RentalPropertyViewModelFactory(val application: Application, val apiClient: ClientAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RentalPropertyViewModel::class.java)) {
            return RentalPropertyViewModel(application, apiClient) as T
        } else {
            throw IllegalArgumentException("Must be of RentalPropertyViewModel")
        }

    }

}