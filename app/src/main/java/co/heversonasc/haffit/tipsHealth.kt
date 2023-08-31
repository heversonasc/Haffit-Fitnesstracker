package co.heversonasc.haffit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat

class tipsHealth : AppCompatActivity() {
    private lateinit var webView: WebView
    private var URL = "https://blog.partmedsaude.com.br/como-ter-uma-alimentacao-saudavel-mesmo-com-uma-rotina-acelerada/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips_health)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = true
        }

        webView = findViewById(R.id.tips_health)
        webView.apply {
            loadUrl(URL)
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true

        }
    }
}

