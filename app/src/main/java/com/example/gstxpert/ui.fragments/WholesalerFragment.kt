package com.example.gstxpert.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView // Add this import to resolve 'text' issue
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gstxpert.adapters.CardAdapter
import com.example.gstxpert.databinding.FragmentWholesalerBinding
import com.example.gstxpert.viewmodels.WholesalerViewModel

class WholesalerFragment : Fragment() {

    private lateinit var binding: FragmentWholesalerBinding
    private lateinit var wholesalerViewModel: WholesalerViewModel
    private var mGstRate = 0.0
    private var mProfitRate = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wholesalerViewModel = ViewModelProvider(requireActivity()).get(WholesalerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentWholesalerBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        subscribeToObservers()

        // Setup SeekBar listeners
        setSeekBarListener(binding.seekBarGstRateWholesaler, binding.tvGstRateWholesaler) { progress ->
            mGstRate = progress.toDouble()
        }

        setSeekBarListener(binding.seekBarProfitRateWholesaler, binding.tvProfitRateWholesaler) { progress ->
            mProfitRate = progress.toDouble()
        }

        // Calculate GST when button is clicked
        binding.btnCalculateWholesaler.setOnClickListener {
            var price = 0
            if (!TextUtils.isEmpty(binding.costGoods.text.toString())) {
                price = binding.costGoods.text.toString().toInt()
            }
            wholesalerViewModel.calculateGstForWholesaler(price.toDouble(), mProfitRate, mGstRate)
        }

        return binding.root
    }

    // Helper function to set SeekBar listener
    private fun setSeekBarListener(seekBar: SeekBar, textView: TextView, updateRate: (Int) -> Unit) {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val rate: Double = p1.toDouble() // Adjust logic if needed
                textView.text = rate.toString() // Now we can set the text of TextView
                updateRate(p1)
                Log.i("debug", "$rate")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.i("debug", "start")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.i("debug", "end")
            }
        })
    }

    private fun setUpRecyclerView() {
        binding.wholesalerRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
    }

    private fun subscribeToObservers() {
        wholesalerViewModel.result.observe(viewLifecycleOwner) { cards ->
            // Log each card title
            cards.forEach { card ->
                Log.i("debug", card.title.toString())
            }
            val adapter = CardAdapter(requireActivity(), ArrayList(cards))
            binding.wholesalerRecyclerView.adapter = adapter
        }
    }
}
