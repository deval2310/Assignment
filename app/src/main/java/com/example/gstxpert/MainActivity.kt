package com.example.gstxpert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.example.gstxpert.adapters.PagerAdapter
import com.example.gstxpert.fragments.BuyerFragment
import com.example.gstxpert.ui.fragments.ManufacturerFragment
import com.example.gstxpert.ui.fragments.WholesalerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fragments list
        val fragments = ArrayList<Fragment>()
        fragments.add(BuyerFragment())
        fragments.add(ManufacturerFragment())
        fragments.add(WholesalerFragment())

        // Tab titles
        val titles = ArrayList<String>()
        titles.add("Buyer")
        titles.add("Manufacturer")
        titles.add("Wholesaler")

        // Creating a bundle to pass data to the fragments
        val bundle = Bundle().apply {
            putString("Test", "String")  // Passing a simple test value to the fragments
        }

        // Creating the adapter and passing the required data
        val adapter = PagerAdapter(bundle, fragments, titles, supportFragmentManager)

        // ViewPager setup
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        // TabLayout setup
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }
}
