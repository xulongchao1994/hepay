package com.hechuang.hepay.ui.activity

import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.os.Build
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.persenter.NoticeInfoPersenter
import com.hechuang.hepay.view.INoticeInfoView
import kotlinx.android.synthetic.main.activity_noticeinfo.*

/**
 * Created by Android_xu on 2017/11/13.
 * 公告详情
 */
class NoticeInfoActivity : BaseAppCompatActivity<NoticeInfoPersenter>(), INoticeInfoView, View.OnClickListener {
    internal var id: String? = null
//    @BindView(R.id.noticeinfo_back)
//    internal var mBank: ImageView? = null
//    @BindView(R.id.noticeinfo_name)
//    internal var mNoticeInfo_name: TextView? = null
//    @BindView(R.id.noticeinfo_title)
//    internal var mtitle: TextView? = null
//    @BindView(R.id.noticeinfo_type)
//    internal var mType: TextView? = null
//    @BindView(R.id.noticeinfo_time)
//    internal var mTime: TextView? = null
//    @BindView(R.id.noticeinfo_context)
//    internal var mWebView: WebView? = null
    internal var url: String? = null

    override fun initlayout(): Int {
        return R.layout.activity_noticeinfo
    }

    override fun initPersenter() {
        mPersenter = NoticeInfoPersenter(this, this)
    }


    override fun initView() {
//        ButterKnife.bind(this)
        window.setFormat(PixelFormat.TRANSLUCENT)
        id = intent.getStringExtra("id")
        mPersenter!!.setinfodata(id)
        noticeinfo_back!!.setOnClickListener(this)
        val settings = noticeinfo_context!!.settings
        //        settings.setUseWideViewPort(true);
        //        settings.setAllowFileAccess(true);
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        settings.pluginState = WebSettings.PluginState.ON
        noticeinfo_context!!.webViewClient = MyWebViewClient()
    }

    override fun onClick(v: View) {
        finish()
    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String) {

    }


    override fun setinfodata(title: String, editor: String, categoryname: String, time: String, context: String) {
        noticeinfo_title!!.text = title
        noticeinfo_name!!.text = editor
        noticeinfo_type!!.text = categoryname
        noticeinfo_time!!.text = time
        url = " <style type=\"text/css\"> img{max-width: 100%; height: auto; } </style> $context"
        noticeinfo_context!!.loadDataWithBaseURL(null, url, "text/html", "utf-8", null)
        //        Log.d(TAG, "setinfodata: " + url);
    }

    internal inner class MyWebViewClient : WebViewClient() {

        //        @Override
        //        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        ////            webView.loadUrl(url);
        //            return super.shouldOverrideUrlLoading(webView, url);
        //
        //        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
            //            Log.d(TAG, "onPageStarted: " + url);
        }
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {

        private val TAG = "NoticeInfoActivity"
    }

}
