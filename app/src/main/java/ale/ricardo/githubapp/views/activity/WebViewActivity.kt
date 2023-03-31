package ale.ricardo.githubapp.views.activity

import ale.ricardo.githubapp.common.TAG
import ale.ricardo.githubapp.databinding.ActivityWebViewBinding
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //实现状态栏图标和文字颜色为暗色

        webView = binding.webview
        progressBar = binding.progressbar

        val url  = intent.getStringExtra("html_url")

        if (url == null || url.isEmpty()) {
            Toast.makeText(this, "加载出错,请返回首页重试", Toast.LENGTH_SHORT).show()
        }else {
            webView.apply {
                loadUrl(url)
                addJavascriptInterface(this@WebViewActivity, "android")
                webViewClient = webViewClient()
                webChromeClient = webChromeClient()

                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE//不使用缓存，只从网络获取数据.

                //支持屏幕缩放
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
            }
        }

    }






    private fun webViewClient() = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            progressBar.visibility = View.GONE

        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            progressBar.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }

    }

    private fun webChromeClient() = object : WebChromeClient() {


        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            val localBuilder: AlertDialog.Builder = AlertDialog.Builder(webView.context)
            localBuilder.setMessage(message).setPositiveButton("确定", null)
            localBuilder.setCancelable(false)
            localBuilder.create().show()

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result?.confirm()
            return true


        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            Log.i(TAG, "网页标题:$title")
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progressBar.progress = newProgress

        }


    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
            webView.removeAllViews()
        }else{
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()             // activityBaseWebAddWebView.reload()
            webView.removeAllViews()    //删除webview中所以进程
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        super.onDestroy()
       webView.destroy()
    }
}