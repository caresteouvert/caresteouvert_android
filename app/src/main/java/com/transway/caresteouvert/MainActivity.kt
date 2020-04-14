package com.transway.caresteouvert

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

private const val REFERER_KEY = "Referral"
private const val REFERER_VALUE = "appli-transway"
private const val MOBILE_APP_QUERY_KEY = "fromapp"
private const val MOBILE_APP_QUERY_VALUE = "t"
private val headers = hashMapOf(Pair(REFERER_KEY, REFERER_VALUE))

class MainActivity : AppCompatActivity() {
  companion object {
    private const val WEB_URL = "https://www.caresteouvert.fr"
    private const val REQUEST_PERMISSIONS_REQUEST_CODE = 38
  }

  private lateinit var webView: WebView
  private var mOrigin: String? = null
  private var mCallback: GeolocationPermissions.Callback? = null

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (!checkPermissions()) {
      requestPermissions()
    }

    // Setup web view
    webView = findViewById(R.id.web_view)
    webView.settings.apply {
      javaScriptEnabled = true
      allowUniversalAccessFromFileURLs = true
      allowContentAccess = true
      allowFileAccess = true
      useWideViewPort = true
      domStorageEnabled = true
      builtInZoomControls = true
      userAgentString
      setGeolocationEnabled(true)
      javaScriptCanOpenWindowsAutomatically = true
    }
    webView.webViewClient = CustomWebViewClient()
    webView.webChromeClient = customWebChromeClient
    val uri = Uri.parse(WEB_URL).buildUpon()
      .appendQueryParameter(MOBILE_APP_QUERY_KEY, MOBILE_APP_QUERY_VALUE)
      .build()
    webView.loadUrl(uri.toString(), headers)
  }

  private val customWebChromeClient = object : WebChromeClient() {
    override fun onGeolocationPermissionsShowPrompt(
      origin: String,
      callback: GeolocationPermissions.Callback
    ) {
      mOrigin = origin
      mCallback = callback
      if (!checkPermissions()) {
        requestPermissions()
      } else {
        callback.invoke(origin, true, false)
      }
    }
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
      webView.goBack()
      return true
    }
    return super.onKeyDown(keyCode, event)
  }

  /**
   * Shows an [AlertDialog].
   *
   * @param mainTextStringId The id for the string resource for the AlertDialog text.
   * @param actionStringId   The text of the action item.
   * @param listener         The listener associated with the AlertDialog action.
   */
  private fun showAlertDialog(
    mainTextStringId: Int,
    actionStringId: Int,
    listener: DialogInterface.OnClickListener
  ) {
    val builder = AlertDialog.Builder(this)
    builder.apply {
      this.setMessage(mainTextStringId)
      setPositiveButton(actionStringId, listener)
    }
    // Create the AlertDialog
    builder.create().show()
  }

  /**
   * Return the current state of the permissions needed.
   */
  private fun checkPermissions(): Boolean {
    val permissionState = ActivityCompat.checkSelfPermission(
      this,
      Manifest.permission.ACCESS_FINE_LOCATION
    )
    return permissionState == PackageManager.PERMISSION_GRANTED
  }

  private fun requestPermissions() {
    val shouldProvideRationale =
      ActivityCompat.shouldShowRequestPermissionRationale(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
      )
    // Provide an additional rationale to the user. This would happen if the user denied the
    // request previously, but didn't check the "Don't ask again" checkbox.
    if (shouldProvideRationale) {
      showAlertDialog(R.string.permission_rationale,
        android.R.string.ok, DialogInterface.OnClickListener { _, _ ->
          // Request permission
          ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
          )
        })
    } else {
      ActivityCompat.requestPermissions(
        this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        REQUEST_PERMISSIONS_REQUEST_CODE
      )
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
      when {
        grantResults.isEmpty() -> {
          // If user interaction was interrupted,
          // the permission request is cancelled and you receive empty arrays.
          mCallback?.invoke(mOrigin, false, false);
        }
        grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
          // Permission granted
          mCallback?.invoke(mOrigin, true, false);
        }
        else -> {
          // Permission denied
          mCallback?.invoke(mOrigin, false, false);
        }
      }
    }
  }
}

class CustomWebViewClient : WebViewClient() {

  override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
    val uri = Uri.parse(url).buildUpon()
      .appendQueryParameter(MOBILE_APP_QUERY_KEY, MOBILE_APP_QUERY_VALUE)
      .build()
    view.loadUrl(uri.toString(), headers)
    return true
  }
}
