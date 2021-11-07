package com.example.appcentnasaproject.model

import com.google.gson.annotations.SerializedName

data class RoverModel(
    @SerializedName("id")
    val rover_id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("landing_date")
    val landing_date: String,
    @SerializedName("launch_date")
    val launch_date: String,
    @SerializedName("status")
    val status: String,

)