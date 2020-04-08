package com.transway.caresteouvert

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
class SummaryFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_summary, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val sharedPreferences = requireActivity().getSharedPreferences(
      getString(R.string.preference_file_key),
      Context.MODE_PRIVATE
    )

    val buttonStart = view.findViewById<Button>(R.id.button_start);
    buttonStart.setOnClickListener {
      val intent = Intent(activity, MainActivity::class.java);
      sharedPreferences.edit().putBoolean(PREF_FIRST_TIME_KEY, false).apply()
      startActivity(intent)
      requireActivity().finish()
    }
  }

  companion object {
    private const val PREF_FIRST_TIME_KEY = "com.transway.caresteouvert.PREF_FIRST_TIME_KEY"

    @JvmStatic()
    fun getInstance() = SummaryFragment();
  }
}
