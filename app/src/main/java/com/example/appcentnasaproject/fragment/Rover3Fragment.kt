package com.example.appcentnasaproject.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentnasaproject.R
import com.example.appcentnasaproject.adapter.NasaAdapter
import com.example.appcentnasaproject.databinding.FragmentRover3Binding
import com.example.appcentnasaproject.model.NasaModel
import com.example.appcentnasaproject.utils.Constans
import com.example.appcentnasaproject.utils.checkList
import com.example.appcentnasaproject.utils.createProgressBar
import com.example.appcentnasaproject.viewmodel.OpportunityViewModel
import com.example.appcentnasaproject.viewmodel.SpiritViewModel


class Rover3Fragment : Fragment() {

    private lateinit var binding: FragmentRover3Binding
    private lateinit var viewModel: SpiritViewModel
    private lateinit var layoutManager: GridLayoutManager
    private var list = mutableListOf<NasaModel>()
    private var realList = mutableListOf<NasaModel>()
    private lateinit var adapter: NasaAdapter
    private var isLoading = false
    private lateinit var progress: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRover3Binding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity()).get(SpiritViewModel::class.java)
        layoutManager = GridLayoutManager(requireActivity(), 2)
        progress = createProgressBar(requireContext())
        vmSetup()


        return binding.root
    }

    private fun vmSetup() {

        viewModel.yukle.observe(viewLifecycleOwner, {
            if (it) {
                binding.swipeRefresh.isRefreshing = true
                progress.show()
            } else {
                binding.swipeRefresh.isRefreshing = false
                progress.dismiss()
            }
        })

        viewModel.getList().observe(viewLifecycleOwner, {
            checkList(it.size, binding.notFound)
            list = it
            println("rover3 " + it.size)
            realList.clear()
            listOperation()
            onScroll()
            adapter = NasaAdapter(requireContext(), realList)
            binding.recyclerView.adapter = adapter

        })

        viewModel.isError.observe(viewLifecycleOwner, {
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
                val lItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val count = adapter.itemCount


                if (dy > 0 && !isLoading && vItem + lItem >= count && list.size > 0) {
                    listOperation()
                }

            }
        })

    }

    private fun listOperation() {

        isLoading = true
        binding.visibleLoading.visibility = View.VISIBLE
        val size = list.size
        for (i in 0..Constans.threshold) {
            if (i < size) {
                realList.add(list[list.size - 1])
                list.removeAt(list.size - 1)
            } else
                break
        }


        Handler().postDelayed({
            binding.visibleLoading.visibility = View.GONE
            isLoading = false
            adapter.notifyDataSetChanged()
        }, 2000)

    }



}