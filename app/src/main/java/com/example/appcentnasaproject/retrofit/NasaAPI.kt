package com.example.appcentnasaproject.retrofit

import androidx.lifecycle.LiveData
import com.example.appcentnasaproject.model.NasaModel
import com.example.appcentnasaproject.model.PhotoModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.Flow


interface NasaAPI {

    @GET
    fun getNasaRover(@Url url: String): Single<PhotoModel>

}