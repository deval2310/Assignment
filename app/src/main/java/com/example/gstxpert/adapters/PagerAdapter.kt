package com.example.gstxpert.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(
    private val bundle: Bundle?,  // We expect a Bundle here, not FragmentManager
    private val fragments: ArrayList<Fragment>,
    private val titles: ArrayList<String>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val fragment = fragments[position]

        // If bundle is not null, pass it to the fragment's arguments
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}
