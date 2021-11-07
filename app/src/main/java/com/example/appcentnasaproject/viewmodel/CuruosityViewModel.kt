package com.example.appcentnasaproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcentnasaproject.fragment.Rover1Fragment
import com.example.appcentnasaproject.model.CameraModel
import com.example.appcentnasaproject.model.NasaModel
import com.example.appcentnasaproject.model.PhotoModel
import com.example.appcentnasaproject.model.RoverModel
import com.example.appcentnasaproject.retrofit.NasaAPI
import com.example.appcentnasaproject.service.NasaService
import com.example.appcentnasaproject.utils.Constans
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CuruosityViewModel : ViewModel() {


    private val disposable: CompositeDisposable
    private val apiService: NasaService

    val rover1List: MutableLiveData<MutableList<NasaModel>> by lazy {
        MutableLiveData<MutableList<NasaModel>>()
    }
    var hataMesaji= MutableLiveData<String>()
    var yukle = MutableLiveData<Boolean>()

    var END_URL = "mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=${Constans.api_key}"


    val newUrl: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            this.value =
                END_URL
        }
    }


    init {
        disposable = CompositeDisposable()
        apiService = NasaService()
        getApiData(END_URL)
    }


    fun getApiData(END_URL: String) {
        println("Apiye ulaştım")
        yukle.value = true
        disposable.add(
            apiService.getNasaRovers(END_URL)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PhotoModel>() {
                    override fun onError(e: Throwable) {
                        Log.d("error", e.message.toString())
                        hataMesaji.value = e.message.toString()
                        yukle.value = false
                    }

                    override fun onSuccess(t: PhotoModel) {
                        rover1List.value = t.photos as MutableList<NasaModel>
                        yukle.value = false

                    }

                })
        )

    }

    fun getList(): LiveData<MutableList<NasaModel>> {
        return rover1List
    }


}


