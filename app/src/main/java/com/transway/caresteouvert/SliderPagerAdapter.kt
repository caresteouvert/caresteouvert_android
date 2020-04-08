package com.transway.caresteouvert

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SliderPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(
  manager,
  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

  private val fragments = mutableListOf<Fragment>()

  override fun getItem(position: Int): Fragment = fragments[position]

  override fun getCount() = fragments.size

  fun addFragment(fragment: Fragment) {
    this.fragments.add(fragment)
  }
}