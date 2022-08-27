package com.fakhrirasyids.beritain.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fakhrirasyids.beritain.ui.main.ui.home.HomeFragment
import com.fakhrirasyids.beritain.ui.main.ui.home.categories.*

class SectionPagerAdapter(activity: HomeFragment) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = TechnologyFragment()
            1 -> fragment = SportsFragment()
            2 -> fragment = ScienceFragment()
            3 -> fragment = HealthFragment()
            4 -> fragment = BusinessFragment()
            5 -> fragment = EntertainmentFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 6
    }
}