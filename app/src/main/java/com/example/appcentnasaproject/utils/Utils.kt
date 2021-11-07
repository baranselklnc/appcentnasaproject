package com.example.appcentnasaproject.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appcentnasaproject.R
import com.example.appcentnasaproject.model.NasaModel

@BindingAdapter("android:downloadImage")
fun ImageView.setImage(url: String) {

    Glide.with(context).setDefaultRequestOptions(createCircleProgress(context)).load(url).into(this)

}

fun createCircleProgress(context: Context): RequestOptions {

    val options = RequestOptions().placeholder(createPlaceHolder(context)).error(R.drawable.error)

    return options
}


fun createPlaceHolder(context: Context): CircularProgressDrawable {


    return CircularProgressDrawable(context).apply {
        strokeWidth = 7f
        centerRadius = 40f
        setColorSchemeColors(Color.BLACK, Color.WHITE)
        start()

    }
}

@SuppressLint("ResourceAsColor")
fun Context.openPopup(model: NasaModel) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
    dialog.window?.attributes?.windowAnimations = R.style.DialogSlide
    dialog.setContentView(R.layout.dialog_popup)

    val roverName = dialog.findViewById<TextView>(R.id.roverName)
    val cameraName = dialog.findViewById<TextView>(R.id.cameraName)
    val status = dialog.findViewById<TextView>(R.id.status)
    val earthDate = dialog.findViewById<TextView>(R.id.earthDate)
    val landingDate = dialog.findViewById<TextView>(R.id.landingDate)
    val launchDate = dialog.findViewById<TextView>(R.id.launchDate)
    val roverImage = dialog.findViewById<ImageView>(R.id.roverImage)


    roverImage.setImage(model.img_src)
    cameraName.setText(model.camera.name)
    earthDate.setText(model.earth_date)
    landingDate.setText(model.rover.landing_date)
    launchDate.setText(model.rover.launch_date)
    roverName.setText(model.rover.name)
    status.setText(model.rover.status)
    status.setBackgroundColor(
        if (model.rover.status.lowercase().trim()
                .equals("active")
        ) Color.parseColor("#03D895")
        else
            Color.parseColor("#AAAAAA")
    )

    dialog.show()

}


@SuppressLint("ResourceAsColor")
fun createProgressBar(context: Context): Dialog {

    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
    dialog.window?.attributes?.windowAnimations = R.style.upToDownSlide
    dialog.setContentView(R.layout.progress_bar)

    return dialog

}

fun checkList(size: Int, view: LottieAnimationView) {
    if (size > 0)
        view.visibility = View.GONE
    else
        view.visibility = View.VISIBLE
}