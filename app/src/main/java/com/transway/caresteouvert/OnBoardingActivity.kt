package com.transway.caresteouvert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

class OnBoardingActivity : AppCompatActivity(), AboutFragment.OnMoreButtonClickListener {
  private lateinit var pager: ViewPager
  private lateinit var aboutFragment: AboutFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_onboarding);

    // Instantiate a ViewPager and a PagerAdapter.
    pager = findViewById(R.id.view_pager)
    val pagerAdapter = SliderPagerAdapter(supportFragmentManager)
    aboutFragment = AboutFragment.getInstance()
    pagerAdapter.addFragment(aboutFragment);
    pagerAdapter.addFragment(SummaryFragment.getInstance())
    pager.adapter = pagerAdapter;
  }

  override fun onAttachFragment(fragment: Fragment) {
    super.onAttachFragment(fragment)
    if (fragment is AboutFragment) {
      fragment.setOnMoreButtonClickListener(this)
    }
  }

  override fun onMoreButtonClicked() {
    pager.setCurrentItem(1, true)
  }
}
