package com.example.gstxpert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gstxpert.R
import com.example.gstxpert.adapters.CardAdapter
import com.example.gstxpert.viewmodels.BuyerViewModel
import com.example.gstxpert.databinding.FragmentBuyerBinding
import androidx.fragment.app.viewModels

class BuyerFragment : Fragment() {

    private val viewModel: BuyerViewModel by viewModels() // Use viewModels delegate to get ViewModel scoped to the fragment
    private lateinit var binding: FragmentBuyerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuyerBinding.inflate(inflater, container, false)

        // Setting up RecyclerView
        binding.buyersRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)

        // Observe the result from the ViewModel and update RecyclerView
        viewModel.result.observe(viewLifecycleOwner) { cards ->
            val adapter = CardAdapter(requireContext(), cards)
            binding.buyersRecyclerView.adapter = adapter
        }

        // Setup SeekBar for GST rate
        binding.seekBarGstRate.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvGstRate.text = "GST Rate: $progress%"
                // Assuming the amount is dynamic or fetched from some source, replace with actual value
                val gstAmount = 1000.0 // Replace with dynamic value
                viewModel.calculateGst(gstAmount, progress.toDouble())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return binding.root
    }
}
