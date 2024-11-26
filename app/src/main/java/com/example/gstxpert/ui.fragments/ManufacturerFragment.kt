package com.example.gstxpert.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gstxpert.adapters.CardAdapter
import com.example.gstxpert.databinding.FragmentManufacturerBinding
import com.example.gstxpert.viewmodels.ManufacturerViewModel

class ManufacturerFragment : Fragment() {

    private lateinit var binding: FragmentManufacturerBinding
    private lateinit var manufacturerViewModel: ManufacturerViewModel
    private var mGstRate = 0.0
    private var mProfitRate = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manufacturerViewModel = ViewModelProvider(requireActivity()).get(ManufacturerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentManufacturerBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        subscribeToObservers()

        binding.seekBarGstRateManufacturer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val gst: Double = p1.toDouble()  // Adjust logic if needed
                binding.tvGstRateManufacturer.text = gst.toString()
                mGstRate = gst
                Log.i("debug", "$gst")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.i("debug", "start")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.i("debug", "end")
            }
        })

        binding.seekBarProfitRateManufacturer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val profitRate: Double = p1.toDouble()  // Adjust logic if needed
                binding.tvProfitRateManufacturer.text = profitRate.toString()
                mProfitRate = profitRate
                Log.i("debug", "$profitRate")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.i("debug", "start")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.i("debug", "end")
            }
        })

        binding.btnCalculateManufacturer.setOnClickListener {
            var price = 0
            if (!TextUtils.isEmpty(binding.costProduction.text.toString())) {
                price = binding.costProduction.text.toString().toInt()
            }
            manufacturerViewModel.calculateGstForManufacturer(price.toDouble(), mProfitRate, mGstRate)
        }

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.manufacturerRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
    }

    private fun subscribeToObservers() {
        manufacturerViewModel.result.observe(viewLifecycleOwner) { cards ->
            // Log each card title
            cards.forEach { card ->
                Log.i("debug", card.title.toString())
            }
            val adapter = CardAdapter(requireActivity(), ArrayList(cards))
            binding.manufacturerRecyclerView.adapter = adapter
        }
    }
}
