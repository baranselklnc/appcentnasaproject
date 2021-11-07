package com.example.appcentnasaproject.model

import com.google.gson.annotations.SerializedName

data class CameraModel(
    @SerializedName("id")
    val camera_id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rover_id")
    val rover_id: String,
    @SerializedName("full_name")
    val full_name: String,

)