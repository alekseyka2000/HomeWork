package com.example.nine_mvm.view.city_chooser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nine_mvm.model.CityRepo
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CityViewModel(private val cityRepo: CityRepo) : ViewModel() {

    private val subscriber = CompositeDisposable()
    private val cityMutableLiveData = MutableLiveData<List<String>>()
    private val messageMutableLiveData = MutableLiveData<String>()
    val cityLiveData: LiveData<List<String>> = cityMutableLiveData
    val messageLiveData: LiveData<String> = messageMutableLiveData

    init {
        getCityList()
    }

    private fun getCityList() {
        subscriber.add(cityRepo.getCityData()
            .map { cityList -> cityList.map { city -> city.cityName } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cityMutableLiveData.value = it },
                { messageMutableLiveData.value = it.message })
        )
    }

    fun addCity(name: String) {
        subscriber.add(Completable.fromRunnable { cityRepo.addCity(name) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ messageMutableLiveData.value = "$name was add to list successfully" },
                { messageMutableLiveData.value = it.message })
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriber.clear()
    }
}