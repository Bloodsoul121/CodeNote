package com.blood.nativedemo.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.annotation.NonNull
import com.blood.nativedemo.R
import com.blood.nativedemo.databinding.ActivityWebviewBinding

class WebviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        binding.webview.apply {
            webViewClient = BloodWebViewClient()
            webChromeClient = BloodWebChromeClient()
            settings.javaScriptEnabled = true // 可能触发跨站脚本攻击 XSS
            loadUrl("https://www.baidu.com")
        }
    }

}