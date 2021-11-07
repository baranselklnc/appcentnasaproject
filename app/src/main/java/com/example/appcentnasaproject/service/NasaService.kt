package com.example.appcentnasaproject.service

import androidx.lifecycle.LiveData
import com.example.appcentnasaproject.model.NasaModel
import com.example.appcentnasaproject.model.PhotoModel
import com.example.appcentnasaproject.retrofit.NasaAPI
import com.example.appcentnasaproject.utils.Constans.Companion.BASE_URL
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NasaService {


    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NasaAPI::class.java)

    fun getNasaRovers(END_URL: String): Single<PhotoModel> {
        return api.getNasaRover(END_URL)
    }

}