package com.example.appcentnasaproject.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.example.appcentnasaproject.R
import com.example.appcentnasaproject.adapter.NasaAdapter
import com.example.appcentnasaproject.databinding.FragmentRover1Binding
import com.example.appcentnasaproject.model.NasaModel
import com.example.appcentnasaproject.utils.Constans
import com.example.appcentnasaproject.utils.Constans.Companion.threshold
import com.example.appcentnasaproject.utils.checkList
import com.example.appcentnasaproject.utils.createProgressBar
import com.example.appcentnasaproject.viewmodel.CuruosityViewModel


class Rover1Fragment : Fragment() {

    private lateinit var binding: FragmentRover1Binding
    lateinit var viewModel: CuruosityViewModel

    private lateinit var layoutManager: GridLayoutManager
    private var list = mutableListOf<NasaModel>()
    private var realList = mutableListOf<NasaModel>()

    private lateinit var adapter: NasaAdapter
    private var yukle = false
    private lateinit var progress: Dialog

    var st = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRover1Binding.inflate(layoutInflater)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            viewModel = ViewModelProvider(requireActivity()).get(CuruosityViewModel::class.java)
            layoutManager = GridLayoutManager(requireActivity(), 2)
            progress = createProgressBar(requireContext())
            vmSetup()
            st=true


    }


    fun vmSetup() {

        viewModel.yukle.observe(viewLifecycleOwner, {
            if (it) {
                binding.swipeRefresh.isRefreshing = true
                progress.show()
            } else {
                binding.swipeRefresh.isRefreshing = false
                progress.dismiss()
            }
        })

        viewModel.getList().observe(requireActivity(), {

            checkList(it.size, binding.notFound)

            list = it

            println("rover1 " + it.size)

            realList.clear()
            listOperation()
            onScroll()
            adapter = NasaAdapter(requireContext(), realList)
            binding.recyclerView.adapter = adapter
        })
/*

 */
        viewModel.hataMesaji.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })


        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getApiData(viewModel.newUrl.value.toString())
        }

    }


    private fun onScroll() {

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val vItem = layoutManager.childCount
                val count = adapter.itemCount
                val lItem = layoutManager.findFirstCompletelyVisibleItemPosition()


                if (dy > 0 && !yukle && vItem + lItem >= count && list.size > 0) {
                    listOperation()
                }

            }
        })

    }

    private fun listOperation() {
        yukle = true
        binding.visibleLoading.visibility = View.VISIBLE
        val size = list.size
        for (i in 0..threshold) {
            if (i < size) {
                realList.add(list[list.size - 1])
                list.removeAt(list.size - 1)
            } else
                break
        }


        Handler().postDelayed({
            binding.visibleLoading.visibility = View.GONE
            yukle = false
            adapter.notifyDataSetChanged()
        }, 2000)

    }


}

