package com.example.appcentnasaproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentnasaproject.R
import com.example.appcentnasaproject.databinding.RoverPhotoDesignBinding
import com.example.appcentnasaproject.model.NasaModel
import com.example.appcentnasaproject.utils.openPopup

class NasaAdapter(context: Context, val list: List<NasaModel>) :
    RecyclerView.Adapter<NasaAdapter.ViewHolder>() {

    private val _context: Context

    init {
        _context = context
    }

    class ViewHolder(val view: RoverPhotoDesignBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rover_photo_design,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.view.nasamodel = list.get(position)
        holder.view.clickCard.setOnClickListener {
            _context.openPopup(list[position])
        }

    }

    override fun getItemCount() = list.size

}