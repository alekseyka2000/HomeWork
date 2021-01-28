package com.example.nine_mvp.view.city_chooser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nine_mvp.model.CityRepo
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CityPresenterImpl(private val cityRepo: CityRepo) : CityPresenter {

    private val subscriber = CompositeDisposable()
    private val cityMutableLiveData = MutableLiveData<List<String>>()
    private val messageMutableLiveData = MutableLiveData<String>()
    private val cityLiveData: LiveData<List<String>> = cityMutableLiveData
    private val messageLiveData: LiveData<String> = messageMutableLiveData

    init {
        getCityList()
    }

    override fun getLiveData() = Pair(cityLiveData, messageLiveData)

    override fun getCityList() {
        subscriber.add(cityRepo.getCityData()
            .map { cityList -> cityList.map { city -> city.cityName } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cityMutableLiveData.value = it },
                { messageMutableLiveData.value = it.message })
        )
    }

    override fun addCity(name: String) {
        subscriber.add(Completable.fromRunnable { cityRepo.addCity(name) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ messageMutableLiveData.value = "$name was add to list successfully" },
                { messageMutableLiveData.value = it.message })
        )
    }

    override fun onCleared() {
        subscriber.clear()
    }
}