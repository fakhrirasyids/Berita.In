package com.fakhrirasyids.beritain.ui.main.ui.webview

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fakhrirasyids.beritain.databinding.ActivityWebviewBinding
import com.fakhrirasyids.beritain.utils.Constants.Companion.NEWS_URL

class WebviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding
    private lateinit var webUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webUrl = intent.getStringExtra(NEWS_URL)!!

        setWebviewContent()
        setListeners()
    }

    private fun setWebviewContent() {
        binding.apply {
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressBar.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar.visibility = View.GONE
                    super.onPageFinished(view, url)
                }
            }
            webView.loadUrl(webUrl)
            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(true)
        }
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}