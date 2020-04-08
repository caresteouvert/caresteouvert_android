package com.transway.caresteouvert

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
  companion object {
    private const val PREF_FIRST_TIME_KEY = "com.transway.caresteouvert.PREF_FIRST_TIME_KEY"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash);

    val sharedPreferences = getSharedPreferences(
      getString(R.string.preference_file_key),
      Context.MODE_PRIVATE
    )

    val isFirstTime = sharedPreferences.getBoolean(PREF_FIRST_TIME_KEY, true);
    if (isFirstTime) {
      launchOnBoarding()
    } else {
      launchHomeScreen()
    }
  }

  private fun launchHomeScreen() {
    val intent = Intent(this, MainActivity::class.java);
    startActivity(intent);
    finish()
  }

  private fun launchOnBoarding() {
    val intent = Intent(this, OnBoardingActivity::class.java);
    startActivity(intent);
    finish()
  }
}
