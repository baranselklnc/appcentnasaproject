package com.example.appcentnasaproject.model

import com.google.gson.annotations.SerializedName

data class NasaModel(

    @SerializedName("id")
    var id: String,
    @SerializedName("sol")
    var sol: String,
    @SerializedName("camera")
    var camera: CameraModel,
    @SerializedName("img_src")
    var img_src: String,
    @SerializedName("earth_date")
    var earth_date: String,
    @SerializedName("rover")
    var rover: RoverModel

)


