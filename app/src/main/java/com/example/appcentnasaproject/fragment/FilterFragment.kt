package com.example.appcentnasaproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.appcentnasaproject.databinding.FragmentFilterBinding
import com.example.appcentnasaproject.utils.Constans
import com.example.appcentnasaproject.viewmodel.CuruosityViewModel
import com.example.appcentnasaproject.viewmodel.OpportunityViewModel
import com.example.appcentnasaproject.viewmodel.SpiritViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBinding

    var select = "all"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(layoutInflater)


        val position = arguments?.getInt("rover")!!
        radioGroup(position)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            select(checkedId)
        }

        binding.okey.setOnClickListener {


            when (position) {

                0 -> {
                    val END_URL = if (select.equals("all"))
                        "mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=${Constans.api_key}"
                    else "mars-photos/api/v1/rovers/curiosity/photos?sol=1000&camera=$select&api_key=${Constans.api_key}"
                    val model =
                        ViewModelProvider(requireActivity()).get(CuruosityViewModel::class.java)
                    model.getApiData(END_URL)
                    model.newUrl.value = END_URL
                }
                1 -> {
                    val END_URL = if (select.equals("all"))
                        "mars-photos/api/v1/rovers/opportunity/photos?sol=1&api_key=${Constans.api_key}"
                    else
                        "mars-photos/api/v1/rovers/opportunity/photos?sol=1&camera=$select&api_key=${Constans.api_key}"
                    val model =
                        ViewModelProvider(requireActivity()).get(OpportunityViewModel::class.java)
                    model.getApiData(END_URL)
                    model.newUrl.value = END_URL
                }
                else -> {
                    val END_URL = if (select.equals("all"))
                        "mars-photos/api/v1/rovers/spirit/photos?sol=2&api_key=${Constans.api_key}"
                    else
                        "mars-photos/api/v1/rovers/spirit/photos?sol=2&camera=$select&api_key=${Constans.api_key}"
                    val model =
                        ViewModelProvider(requireActivity()).get(SpiritViewModel::class.java)
                    model.getApiData(END_URL)
                    model.newUrl.value = END_URL
                }

            }

            dismiss()

        }


        return binding.root
    }


    private fun radioGroup(position: Int) {

        when (position) {

            0 -> {
                binding.radioButton8.isEnabled = false
                binding.radioButton9.isEnabled = false

            }

            else -> {
                binding.radioButton3.isEnabled = false
                binding.radioButton4.isEnabled = false
                binding.radioButton5.isEnabled = false
                binding.radioButton6.isEnabled = false
            }


        }


    }


    private fun select(id: Int) {
        when (id) {
            2131231234 -> select = "all"
            2131231068 -> select = "fhaz"
            2131231069 -> select = "rhaz"
            2131231070 -> select = "mast"
            2131231071 -> select = "chemcam"
            2131231072 -> select = "mahli"
            2131231073 -> select = "mardi"
            2131231074 -> select = "navcam"
            2131231075 -> select = "pancam"
            2131231076 -> select = "minites"
            2131231235 -> select = "entry"

        }


    }

}

