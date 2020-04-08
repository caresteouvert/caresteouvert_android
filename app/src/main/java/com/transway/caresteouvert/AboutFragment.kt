package com.transway.caresteouvert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {
  private lateinit var callback: OnMoreButtonClickListener

  fun setOnMoreButtonClickListener(callback: OnMoreButtonClickListener) {
    this.callback = callback
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_about, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val buttonMore = view.findViewById<Button>(R.id.button_more);
    buttonMore.setOnClickListener {
      callback.onMoreButtonClicked();
    }
  }

  interface OnMoreButtonClickListener {
    fun onMoreButtonClicked()
  }

  companion object {
    @JvmStatic()
    fun getInstance() = AboutFragment();
  }
}