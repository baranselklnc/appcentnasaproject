package com.example.appcentnasaproject.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import com.example.appcentnasaproject.databinding.ActivityMainBinding
import com.example.appcentnasaproject.fragment.FilterFragment
import com.example.appcentnasaproject.fragment.Rover1Fragment
import com.example.appcentnasaproject.fragment.Rover2Fragment
import com.example.appcentnasaproject.fragment.Rover3Fragment
import com.example.appcentnasaproject.viewmodel.CuruosityViewModel
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    var pos = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val tabAdapter = FragmentPagerItemAdapter(
            supportFragmentManager,
            FragmentPagerItems.with(baseContext)
                .add("Curiosity", Rover1Fragment::class.java)
                .add("Opportunity", Rover2Fragment::class.java)
                .add("Spirit", Rover3Fragment::class.java)
                .create()
        )

        binding.viewPager.adapter = tabAdapter
        binding.viewPagerTab.setViewPager(binding.viewPager)

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                pos = position

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })


        binding.filter.setOnClickListener {

            val filterBottom = FilterFragment()
            val bundle = Bundle()
            bundle.putInt("rover", pos)
            filterBottom.arguments = bundle
            filterBottom.show(supportFragmentManager, "FILTER_TAG")

        }

    }




}