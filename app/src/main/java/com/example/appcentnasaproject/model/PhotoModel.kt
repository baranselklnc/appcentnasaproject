package com.example.appcentnasaproject.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    @SerializedName("photos")
    val photos: List<NasaModel>
    )