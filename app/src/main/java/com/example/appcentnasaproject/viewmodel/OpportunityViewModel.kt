package com.example.appcentnasaproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appcentnasaproject.model.NasaModel
import com.example.appcentnasaproject.model.PhotoModel
import com.example.appcentnasaproject.retrofit.NasaAPI
import com.example.appcentnasaproject.service.NasaService
import com.example.appcentnasaproject.utils.Constans
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class OpportunityViewModel(application: Application) : AndroidViewModel(application) {


    private val disposable: CompositeDisposable
    private val apiService: NasaService

     val rover2List: MutableLiveData<MutableList<NasaModel>> by lazy {
        MutableLiveData<MutableList<NasaModel>>()
    }
    var hata = MutableLiveData<String>()

    var loading = MutableLiveData<Boolean>()

    var END_URL = "mars-photos/api/v1/rovers/opportunity/photos?sol=1&api_key=${Constans.api_key}"

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
        loading.value=true
        disposable.add(
            apiService.getNasaRovers(END_URL)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PhotoModel>() {
                    override fun onSuccess(t: PhotoModel) {
                        rover2List.value = t.photos as MutableList<NasaModel>
                        loading.value=false
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error", e.message.toString())
                        hata.value = e.message.toString()
                        loading.value=false
                    }

                })
        )
    }

    fun getList(): LiveData<MutableList<NasaModel>> {
        return rover2List
    }

}