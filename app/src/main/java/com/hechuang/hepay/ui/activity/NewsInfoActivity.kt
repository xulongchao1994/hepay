package com.hechuang.hepay.ui.activity

import android.graphics.Bitmap
import android.os.Build
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.persenter.NewsInfoPersenter
import com.hechuang.hepay.view.INewsInfoView
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_newsinfo.*

/**
 * Created by Android_xu on 2017/9/27.
 * 消息详情
 */
class NewsInfoActivity : BaseAppCompatActivity<NewsInfoPersenter>(), INewsInfoView {
    //    @BindView(R.id.newsinfo_back)
//    internal var mBack: ImageView? = null
//    @BindView(R.id.newsinfo_text)
//    internal var mWebView: WebView? = null
//    @BindView(R.id.newsinfo_title)
//    internal var title: TextView? = null
//    @BindView(R.id.newsinfo_title_time)
//    internal var title_tiem: TextView? = null
    private var id: String? = null
    private var url: String? = null

    override fun initlayout(): Int {
        return R.layout.activity_newsinfo
    }

    override fun initPersenter() {
        mPersenter = NewsInfoPersenter(this, this)
    }


    override fun initView() {
//        ButterKnife.bind(this)
        id = intent.getStringExtra("newsid")
        mPersenter!!.getnewsinfo(id)
        val settings = newsinfo_text!!.settings
        //        settings.setUseWideViewPort(true);
        //        settings.setLoadWithOverviewMode(true);
        settings.allowFileAccess = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
        settings.pluginState = WebSettings.PluginState.ON
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.javaScriptEnabled = true
        //        settings.setLoadsImagesAutomatically(true);
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            settings.setMixedContentMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //        }
        //        settings.setSupportZoom(false);
        settings.setSupportZoom(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        settings.pluginState = WebSettings.PluginState.ON
        newsinfo_text.setWebViewClient(MyWebViewClient())
        newsinfo_back.setOnClickListener { finish() }

    }

//    @OnClick(R.id.newsinfo_back, R.id.newsinfo_title)
//    fun onClick(v: View) {
//        finish()
//    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String) {

    }


    override fun setwebview(userid: String, addtime: String, Title: String, editorValue: String) {
        newsinfo_title.text = Title
        newsinfo_title_time!!.text = "$userid $addtime"
        url = " <style type=\"text/css\"> img{ max-width: 100%; height: auto;} </style> $editorValue"
        newsinfo_text.loadDataWithBaseURL(null, url, "text/html", "utf-8", null)
        //        Log.d(TAG, "setwebview: " + url);
    }


    internal inner class MyWebViewClient : WebViewClient() {

        //        @Override
        //        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        //            webView.loadUrl(url);
        //            return true;
        //        }

        override fun onPageStarted(webView: WebView, s: String, bitmap: Bitmap) {
            super.onPageStarted(webView, s, bitmap)
            //            Log.d(TAG, "onPageStarted: " + s);
        }

    }

    override fun onPause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            newsinfo_text.onPause() // 暂停网页中正在播放的视频
        }
        super.onPause()
    }

    companion object {
        private val TAG = "NewsInfoActivity"
    }
}
