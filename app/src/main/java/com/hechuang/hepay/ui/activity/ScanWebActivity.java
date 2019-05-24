package com.hechuang.hepay.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;

import com.hechuang.hepay.R;
import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.Web_Url;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.persenter.WebPerwenter;
import com.hechuang.hepay.util.Utils;
import com.hechuang.hepay.view.IWebView;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载web页面
 * Created by Android_xu on 2018/2/3.
 */

public class ScanWebActivity extends BaseAppCompatActivity<WebPerwenter> implements IWebView {
    @BindView(R.id.web_tencent)
    WebView mWebView;
    private ValueCallback<Uri> mUploadFile;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private CookieManager cookieManager;
    private String url;
    private String red_ulr = "";
    private List<String> hostor_url;
    private SharedPreferences sp;

    @Override
    public void showloading() {
        getMLoading().show();

    }

    @Override
    public void dissmissloading() {
        getMLoading().dismiss();
    }

    @Override
    public void getdataerror(String msg) {

    }

    private static final String TAG = "ScanWebActivity";

    @Override
    protected int initlayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new WebPerwenter(this, this));
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("scanweb_url");
        hostor_url = new ArrayList<>();
        @SuppressWarnings("unused")
        CookieSyncManager cookieSyncMngr =
                CookieSyncManager.createInstance(ScanWebActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowFileAccess(true);//设置允许访问文件数据
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setPluginsEnabled(true);//支持插件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        mWebView.setWebViewClient(new MyWebviewClient());
        mWebView.setWebChromeClient(new MYWebChromeClient());
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        mWebView.loadUrl(url);
        mWebView.addJavascriptInterface(new javascriptinterface(this), "android");
    }


    public class javascriptinterface {
        private Context mContext;

        public javascriptinterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void outlogin() {
//            Log.d(TAG, "outlogin: ");
            sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("token_id", "");
            editor.putString("urserid", "");
            editor.putString("usertype", "");
            editor.putBoolean("islogin", false);
            editor.putBoolean("isoutlogin", true);
            UserData.islogin = false;
            editor.commit();
            UserData.sessionid = "";
            UserData.tokenid = "";
            UserData.username = "";
            startActivity(new Intent(ScanWebActivity.this, LoginActivity.class));
            finish();
        }

        @JavascriptInterface
        public void back() {
            startActivity(new Intent(ScanWebActivity.this, HomeActivity.class));
            finish();
        }


    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
//            Log.i("tag", "url=" + url);
//            Log.i("tag", "userAgent=" + userAgent);
//            Log.i("tag", "contentDisposition=" + contentDisposition);
//            Log.i("tag", "mimetype=" + mimetype);
//            Log.i("tag", "contentLength=" + contentLength);
        }
    }

    private class MyWebviewClient extends WebViewClient {


        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {

//            Log.d("webs_shouidintercept_1", s);
            if (Utils.ishasurl(s)) {
                return super.shouldInterceptRequest(webView, s);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
//            Log.d("webs_shouidintercept_2", webResourceRequest.getUrl().getHost());

            if (Utils.ishasurl(webResourceRequest.getUrl().getHost())) {
                return super.shouldInterceptRequest(webView, webResourceRequest);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest, Bundle bundle) {
//            Log.d("webs_shouidintercept_3", webResourceRequest.getUrl().getHost());
            if (Utils.ishasurl(webResourceRequest.getUrl().getHost())) {
                return super.shouldInterceptRequest(webView, webResourceRequest);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        }


        @SuppressLint("MissingPermission")
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            if (parseScheme(url)) {
                try {
                    Intent intent;
                    intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (s.startsWith("weixin:") || s.startsWith("alipays:") ||
                            s.startsWith("mailto:") || s.startsWith("tel:")
                        //其他自定义的scheme
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }
            if (webView.getUrl().toLowerCase().startsWith("http:") || webView.getUrl().toLowerCase().startsWith("https:")) {
                return false;
            }
            mWebView.loadUrl(url);
            return true;
        }

        // 调起支付宝并跳转到指定页面
        public boolean parseScheme(String url) {

            if (url.contains("platformapi/startapp")) {
                return true;
            } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                    && (url.contains("platformapi") && url.contains("startapp"))) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
//            Log.d(TAG, "onPageStarted: " + s);
            if (getMLoading() != null)
                getMLoading().show();
            if (s.equals(ApiFactory.HOST)) {
                red_ulr = ApiFactory.HOST;
                hostor_url.add(s);
                if (hostor_url != null && hostor_url.size() > 1) {
                    hostor_url.clear();
                }
            }
            if (s.equals(ApiFactory.HOST + "home")) {
                red_ulr = ApiFactory.HOST + "home";
                startActivity(new Intent(ScanWebActivity.this, HomeActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Index/index")) {
                red_ulr = ApiFactory.HOST + "home";
                startActivity(new Intent(ScanWebActivity.this, HomeActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Index") || s.equals(ApiFactory.HOST + "index.php/Home/Index/")) {
                if (getWindow() != null && getMLoading().isShowing())
                    getMLoading().dismiss();
                red_ulr = ApiFactory.HOST + "index.php/Home/Index";
                if (!Utils.isExistMainActivity(ScanWebActivity.this, HomeActivity.class)) {
                    startActivity(new Intent(ScanWebActivity.this, HomeActivity.class));
                }
                hostor_url.clear();
                finish();
            }

            if (s.equals(ApiFactory.HOST + "index.php/Home/Login/info")) {
                UserData.namestatus = "1";
            }
            if (s.equals(Web_Url.HOME)) {
                red_ulr = Web_Url.HOME;
                hostor_url.add(s);
                if (hostor_url != null && hostor_url.size() > 0) {
                    hostor_url.clear();
                }
            }
            if (s.equals(Web_Url.ME_URL)) {
                red_ulr = Web_Url.ME_URL;
                hostor_url.add(s);
                if (hostor_url != null && hostor_url.size() > 1) {
                    hostor_url.clear();
                }
            }
            if (s.equals(Web_Url.SHANGPIN)) {
                red_ulr = Web_Url.SHANGPIN;
                hostor_url.add(s);
                if (hostor_url != null && hostor_url.size() > 1) {
                    hostor_url.clear();
                }
            }
            if (s.equals(Web_Url.OREDER_URL)) {
                red_ulr = Web_Url.OREDER_URL;
                hostor_url.add(s);
                if (hostor_url != null && hostor_url.size() > 1) {
                    hostor_url.clear();
                }
            }
//            if (s.equals(ApiFactory.HOST + "index.php/Home/User/redpacket")) {
//                hostor_url.add(s);
//                red_ulr = ApiFactory.HOST + "index.php/Home/User/redpacket";
//                startActivity(new Intent(ScanWebActivity.this, RedPacketListActivity.class));
//                if (hostor_url != null && hostor_url.size() > 0)
//                    hostor_url.remove(hostor_url.size() - 1);
//            }
//            if (s.equals(ApiFactory.HOST + "index.php/Home/User/redpacket.html")) {
//                hostor_url.add(s);
//                red_ulr = ApiFactory.HOST + "index.php/Home/User/redpacket.html";
//                startActivity(new Intent(ScanWebActivity.this, RedPacketListActivity.class));
//                if (hostor_url != null && hostor_url.size() > 0)
//                    hostor_url.remove(hostor_url.size() - 1);
//            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Login/Login_user.html")) {
//                ARouter.getInstance().build("/activity/login").navigation();
                startActivity(new Intent(ScanWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Login/index")) {
//                ARouter.getInstance().build("/activity/login").navigation();
                startActivity(new Intent(ScanWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Login/Login_user")) {
                startActivity(new Intent(ScanWebActivity.this, LoginActivity.class));
//                ARouter.getInstance().build("/activity/login").navigation();
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/home/UserPass/user_password_passismodify")) {//强制修改密码
                startActivity(new Intent(ScanWebActivity.this, LoginActivity.class));
//                ARouter.getInstance().build("/activity/login").navigation();
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/home/UserPass/user_password_passismodify.html")) {//强制修改密码
                startActivity(new Intent(ScanWebActivity.this, LoginActivity.class));
//                ARouter.getInstance().build("/activity/login").navigation();
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Article/article_list/")) {
                hostor_url.add(s);
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                startActivity(new Intent(ScanWebActivity.this, NoticeActivity.class));
//                ARouter.getInstance().build("/activity/notice").navigation();
            }
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            if (getWindow() != null && getMLoading().isShowing())
                getMLoading().dismiss();
            if (s.equals(ApiFactory.HOST + "index.php/Home/Article/article_list/")) {
                mWebView.loadUrl(Web_Url.HOME);
            }
            super.onPageFinished(webView, s);
        }
    }

    private class MYWebChromeClient extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }


        @Override
        public boolean onJsConfirm(WebView view, String urls, String message, final JsResult result) {
//            Log.d(TAG, "onJsConfirm: ");
            new AlertDialog.Builder(ScanWebActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> {
                                result.confirm();
                            }).setCancelable(false)
                    .setOnKeyListener((dialogInterface, i, keyevent) -> {
                        if (i == KeyEvent.KEYCODE_SEARCH) {
                            return true;
                        } else {
                            return false; //默认返回 false
                        }
                    })
                    .create()
                    .show();
            return true;
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String urls, String message, final JsResult result) {
//            Log.d(TAG, "onJsBeforeUnload: ");
            new AlertDialog.Builder(ScanWebActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> {
                                result.confirm();
                            }).setCancelable(false)
                    .setOnKeyListener((dialogInterface, i, kytEvent) -> {
                        if (i == KeyEvent.KEYCODE_SEARCH) {
                            return true;
                        } else {
                            return false; //默认返回 false
                        }
//                        }
                    })
                    .create()
                    .show();
//            }
//            }
            return true;
        }

        @Override
        public boolean onJsAlert(final WebView view, final String urls, final String message, final JsResult result) {
//            Log.d(TAG, "onJsAlert: ");
            new AlertDialog.Builder(ScanWebActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> {
                                result.confirm();
                            }).setCancelable(false)
                    .setOnKeyListener((dialogInterface, i, keyEvent) -> {
                        if (i == KeyEvent.KEYCODE_SEARCH) {
                            return true;
                        } else {
                            return false; //默认返回 false
                        }
//                        }
                    })
                    .create()
                    .show();
//            }
//            }

            return true;
        }

        // 3.0++
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadFile = uploadMsg;
            doPickPhotoFromGallery();
        }

        //3.0--
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadFile = uploadMsg;
            doPickPhotoFromGallery();
        }

        //4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }


        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
            }
            mUploadCallbackAboveL = valueCallback;
            doPickPhotoFromGallery();
            return true;
        }
    }

    private final int REQUESTCODE_PICK = 2;

    protected void doPickPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUESTCODE_PICK);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (hostor_url != null && hostor_url.size() > 0) {
                hostor_url.remove(hostor_url.size() - 1);
                if (hostor_url.size() > 0) {
                    mWebView.loadUrl(hostor_url.get(hostor_url.size() - 1));
                    hostor_url.remove(hostor_url.size() - 1);
                } else {
                    finish();
                }
                return true;
            } else {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        if (getMLoading() != null && getMLoading().isShowing())
            getMLoading().dismiss();
        super.onDestroy();
    }

}
