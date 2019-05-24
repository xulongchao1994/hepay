package com.hechuang.hepay.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.activity.CaptureActivity;
import com.hechuang.hepay.R;
import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.api.PathConstant;
import com.hechuang.hepay.api.Web_Url;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.persenter.WebPerwenter;
import com.hechuang.hepay.util.Eyes;
import com.hechuang.hepay.util.ImageUtils;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.util.Utils;
import com.hechuang.hepay.view.IWebView;
import com.hechuang.hepay.wxapi.WXEntryActivity;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 加载web页面
 * Created by Android_xu on 2018/2/3.
 */

public class UserWebActivity extends BaseAppCompatActivity<WebPerwenter> implements IWebView, View.OnClickListener {
    @BindView(R.id.web_tencent)
    WebView mWebView;
    @BindView(R.id.web_layout)
    LinearLayout mLinearLayout;
    @BindView(R.id.web_title_layout)
    RelativeLayout mTitleLayout;
    @BindView(R.id.web_refresh)
    Button mRefresh;
    @BindView(R.id.web_title_text)
    TextView title_text;
    @BindView(R.id.web_back)
    ImageView mback;
    private ValueCallback<Uri> mUploadFile;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private CookieManager cookieManager;
    private String url;
    private String red_ulr = "";
    private List<String> hostor_url;
    private SharedPreferences sp;
    public static UserWebActivity mWebActivity;

    private static final int REQUEST_CODE = 0x03;

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

    private static final String TAG = "UserWebActivity";

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
        Eyes.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.white));
        ButterKnife.bind(this);
        mWebActivity = this;
        url = getIntent().getStringExtra("web_url");
        hostor_url = new ArrayList<>();
        mback.setOnClickListener(this);
        JPushInterface.setAlias(this, 0, UserData.username);
        if (url.contains(PathConstant.BUSINESS)) {
            syncCookie(UserWebActivity.this, PathConstant.BUSINESS, "PHPSESSID=" + UserData.sessionid);
        } else {
            syncCookie(UserWebActivity.this, ApiFactory.HOST, "PHPSESSID=" + UserData.sessionid);
        }
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);//设置允许访问文件数据
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setPluginsEnabled(true);//支持插件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        String cacheDirPath = getFilesDir().getAbsolutePath() + "userweb";
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        mWebView.setWebViewClient(new MyWebviewClient());
        mWebView.setWebChromeClient(new MYWebChromeClient());
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        mWebView.loadUrl(url);
        mWebView.addJavascriptInterface(new javascriptinterface(this), "android");
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });
    }

    /**
     * 将cookie同步到WebView
     * shangchuanceshi
     *
     * @param url    WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void syncCookie(Context context, String url, String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
        cookieManager.setCookie(url, cookie);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
        String cookies = cookieManager.getCookie(url);
//        Log.d(TAG, "syncCookie: " + cookies);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_back:
                back();
                break;
        }
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
            UserData.islogin = false;
            editor.commit();
            UserData.sessionid = "";
            UserData.tokenid = "";
            UserData.username = "";
            startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
            JPushInterface.deleteAlias(UserWebActivity.this, 0);
            finish();
        }

        @JavascriptInterface
        public void back() {
//            Log.d("web", "back");
            UserWebActivity.this.back();
//            startActivity(new Intent(WebActivity.this, MainActivity.class));
//            finish();
        }

        @JavascriptInterface
        public void finish_web() {
//            Log.d(TAG, "finish_web: ");
            finish();
        }
    }

    String imgurl = "";

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
//            Log.i("tag", "url=" + url);
//            Log.i("tag", "userAgent=" + userAgent);
//            Log.i("tag", "contentDisposition=" + contentDisposition);
//            Log.i("tag", "mimetype=" + mimetype);
//            Log.i("tag", "contentLength=" + contentLength);
            String payt = contentDisposition.substring(21);
//            Log.d(TAG, "onDownloadStart: " + payt);
            imgurl = ApiFactory.HOST + "Upload/unshop/PayEwm/" + payt;
            if (Build.VERSION.SDK_INT >= 23) {
                int neicunquanxian = ContextCompat.checkSelfPermission(UserWebActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                if (neicunquanxian != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 321);
                } else {
                    ImageUtils.downloadimg(UserWebActivity.this, imgurl);
                }
            } else {
                ImageUtils.downloadimg(UserWebActivity.this, imgurl);
            }

        }
    }
//    private void downloadpng() {
//        Glide.with(this).load(ApiFactory.HOST + "Upload/unshop/PayEwm/" + payt).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + PathConstant.IMG_DIR);
//                if (!appDir.exists()) {
//                    Boolean isSuccess = appDir.mkdirs();
//                } else {
//                }
//                String fileName = PathConstant.APP_NAME + System.currentTimeMillis() + ".jpg";
//                File file = new File(appDir, fileName);
//                try {
////                    DebugLog.i("开始保存");
//                    FileOutputStream fos = new FileOutputStream(file);
//                    //通过io流的方式来压缩保存图片
//                    boolean isSuccess = resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                    fos.flush();
//                    fos.close();
//                    //把文件插入到系统图库
//                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);
//                    //保存图片后发送广播通知更新数据库
//                    Uri uri = Uri.fromFile(file);
//                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
//                    if (isSuccess) {
//                        MyToast.showMsg("图片下载完成");
//                    } else {
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Intent intent = new Intent(WebActivity.this, DownLoadFileService.class);
//        intent.putExtra("url", ApiFactory.HOST + "Upload/unshop/PayEwm/" + payt);
//        startService(intent);
//        Snackbar.make(mWebView, "图片保存在文件管理/" + PathConstant.IMG_DIR + "中", Snackbar.LENGTH_INDEFINITE).setAction("完成", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).show();
//    }

    String loadurl = "";

    private class MyWebviewClient extends WebViewClient {


        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            Log.d("userwebs_3", s);
            if (Utils.ishasurl(s)) {
                return super.shouldInterceptRequest(webView, s);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            Log.d("userwebs_2", webResourceRequest.getUrl().getHost());
            if (Utils.ishasurl(webResourceRequest.getUrl().getHost())) {
                return super.shouldInterceptRequest(webView, webResourceRequest);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest, Bundle bundle) {
            Log.d("userwebs_3", webResourceRequest.getUrl().getHost());
            if (Utils.ishasurl(webResourceRequest.getUrl().getHost())) {
                return super.shouldInterceptRequest(webView, webResourceRequest, bundle);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        }


        @SuppressLint("MissingPermission")
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {

            if (loadurl.equals(s)) {
                return false;
            } else {
                loadurl = s;
            }
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
            mLinearLayout.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
            hostor_url.add(s);
            Log.d("userwebs_s", s);
            if (hostor_url.size() > 1 && hostor_url.get(hostor_url.size() - 1).equals(hostor_url.get(hostor_url.size() - 2))) {
                hostor_url.remove(hostor_url.size() - 1);
            }
            if (getMLoading() != null)
                getMLoading().show();
            if (s.equals(ApiFactory.HOST)) {
                red_ulr = ApiFactory.HOST;
                if (hostor_url != null && hostor_url.size() > 1) {
                    hostor_url.clear();
                }
            }

            if (s.equals(ApiFactory.HOST + "index.php/Home/Index") || s.equals(ApiFactory.HOST + "index.php/Home/Index/")) {
                if (getWindow() != null && getMLoading().isShowing())
                    getMLoading().dismiss();
                red_ulr = ApiFactory.HOST + "index.php/Home/Index";
                if (!Utils.isExistMainActivity(UserWebActivity.this, HomeActivity.class)) {
                    startActivity(new Intent(UserWebActivity.this, HomeActivity.class));
                }
                hostor_url.clear();
                finish();
            }
            if (s.equals(Web_Url.HOME) || s.equals(Web_Url.HOME + "/")) {
                if (getWindow() != null && getMLoading().isShowing())
                    getMLoading().dismiss();
                red_ulr = Web_Url.HOME;
                if (!Utils.isExistMainActivity(UserWebActivity.this, HomeActivity.class)) {
                    startActivity(new Intent(UserWebActivity.this, HomeActivity.class));
                }
                hostor_url.clear();
                finish();
            }

            if (s.equals(ApiFactory.HOST + "index.php/Home/Login/info")) {
                UserData.namestatus = "1";
            }
            if (s.equals(Web_Url.ME_URL)) {
                red_ulr = Web_Url.ME_URL;
            }
            if (s.equals(Web_Url.SHANGPIN)) {
                red_ulr = Web_Url.SHANGPIN;
            }
            if (s.equals(Web_Url.OREDER_URL)) {
                red_ulr = Web_Url.OREDER_URL;
            }
            if (s.contains(ApiFactory.HOST + "index.php/Home/Cart/order_pay")) {
                if (hostor_url != null && hostor_url.size() > 0) {
                    hostor_url.remove(hostor_url.size() - 1);
                    hostor_url.remove(hostor_url.size() - 1);
                }
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Login/index.html")) {
                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Login/index")) {
//                ARouter.getInstance().build("/activity/login").navigation();
                startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Login/index.html")) {
                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "home/")) {
                hostor_url.clear();
                finish();
            }
            if (s.equals(PathConstant.BUSINESS + "index.php/Home/Login/index")) {
//                ARouter.getInstance().build("/activity/login").navigation();
                startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(PathConstant.BUSINESS + "index.php/Home/Login/index.html")) {
                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(PathConstant.BUSINESS + "index.php/Home/Login/Login_user")) {
                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/home/UserPass/user_password_passismodify")) {//强制修改密码

                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }

            if (s.equals(ApiFactory.HOST + "index.php/Home/Unshop/Location")) {//跳转到地图

                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                mWebView.loadUrl(hostor_url.get(hostor_url.size() - 1));
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                startActivity(new Intent(UserWebActivity.this, BaiDuMapActivity.class));
            }
            if (s.equals(ApiFactory.HOST + "index.php/home/UserPass/user_password_passismodify.html")) {//强制修改密码

                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                startActivity(new Intent(UserWebActivity.this, LoginActivity.class));
                hostor_url.clear();
                finish();
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Unshop/validate_pay")) {
                red_ulr = s;
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Unshop/unshop_pic")) {//跳转到相册上传页面
                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                mWebView.loadUrl(hostor_url.get(hostor_url.size() - 1));
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                Intent intent = new Intent(UserWebActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Unshop/unshop_pic.html")) {//跳转到相册上传页面
                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                mWebView.loadUrl(hostor_url.get(hostor_url.size() - 1));
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                Intent intent = new Intent(UserWebActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
            if (s.equals(ApiFactory.HOST + "index.php/Home/Article/article_list/")) {
                if (getWindow() != null && getMLoading() != null) {
                    if (getMLoading().isShowing()) {
                        getMLoading().dismiss();
                    }
                }
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                startActivity(new Intent(UserWebActivity.this, NoticeActivity.class));
            }

            if (s.equals("http://www.hetianpay.com/Scan")) {//扫码
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                webView.stopLoading();
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(
                            UserWebActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{
                                Manifest.permission.CAMERA}, 321);
                    } else {
                        Intent intent = new Intent(UserWebActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                } else {
                    Intent intent = new Intent(UserWebActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
            if (s.contains("index.php/Home/Product/shop_details/id")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                int sds = s.indexOf("id/");
                String id = s.substring(sds + 3, s.length());
                Intent intent = new Intent(UserWebActivity.this, BusinessActivity.class);
                intent.putExtra("shopid", id);
                intent.putExtra("shopname", "");
                startActivity(intent);
            }
            if (s.equals(PathConstant.BUSINESS + "index.php/Home/Index/index")) {
                Intent intent = new Intent(UserWebActivity.this, AllianceShopActivity.class);
                startActivity(intent);
                finish();
            }
            if (s.equals(PathConstant.BUSINESS + "index.php/Home/Index")) {
                Intent intent = new Intent(UserWebActivity.this, AllianceShopActivity.class);
                startActivity(intent);
                finish();
            }

            if (s.contains("index.php/Home/Product/commodity_details/proid/")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                int sds = s.indexOf("proid/");
                String id = s.substring(sds + 6, s.length());
                Intent intent = new Intent(UserWebActivity.this, HTFGoodInfoActivity.class);
                intent.putExtra("proid", id);
//                intent.putExtra("shopname", "");
                startActivity(intent);
            }

            if (s.contains("openapi.alipay.com")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                isfiash = true;
            }
            if (s.contains("mclient.alipay.com")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                isfiash = true;
            }
            if (s.contains("vip.hshc618.com/alipay/wappay/pay_htflmxf_reg.php")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                isfiash = true;
            }
//            if (s.contains("vip.hshc618.com/alipay/wappay/pay_htflmxf_reg.php")){
//                if (hostor_url != null && hostor_url.size() > 0)
//                    hostor_url.remove(hostor_url.size() - 1);
//            }
            if (s.contains("vip.hshc618.com/index.php/Master/FreeLogin/user_wpay_htf_lmxf?trade_no")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                isfiash = true;
            }
            if (s.contains("vip.hshc618.com/index.php/Master/FreeLogin/user_wpay_htf_lmxf?action=ok")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                isfiash = true;
            }
            if (s.contains("wx.tenpay.com")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                isfiash = true;
            }
            if (s.contains("un.hetianpay.com/index.php/Home/Cart/order_pay")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
            }
            if (s.contains("un.hetianpay.com/index.php/Home/Cart/shopping_statement/data")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
            }

            if (s.contains("wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id")) {
                if (hostor_url != null && hostor_url.size() > 0)
                    hostor_url.remove(hostor_url.size() - 1);
                isfiash = true;
            }
            Log.d("userweb", hostor_url.toString());
        }

        @Override
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            mLinearLayout.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            if (hostor_url != null && hostor_url.size() > 0) {
                hostor_url.remove(hostor_url.size() - 1);
            }
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            mLinearLayout.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            if (hostor_url != null && hostor_url.size() > 0) {
                hostor_url.remove(hostor_url.size() - 1);
            }
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            if (getWindow() != null && getMLoading() != null)
                getMLoading().dismiss();
            if (s.equals(ApiFactory.HOST + "index.php/Home/Article/article_list/")) {
                mWebView.loadUrl(Web_Url.HOME);
            }
            super.onPageFinished(webView, s);
        }
    }

    private class MYWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            if (webView.getUrl().contains("index.php/Home/User/integral_exchange")
                    || webView.getUrl().contains("index.php/Home/Product/commodity_details/proid")
                    || webView.getUrl().contains("index.php/Home/Cart/shopping_statement/data")
                    || webView.getUrl().contains("index.php/Home/FreeLogin/user_ypayoralipay/shopid/")
                    ) {
                mTitleLayout.setVisibility(View.VISIBLE);
                title_text.setText(s);
            } else {
                mTitleLayout.setVisibility(View.GONE);
            }
        }

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
            new AlertDialog.Builder(UserWebActivity.this)
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
            new AlertDialog.Builder(UserWebActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> {
                                result.confirm();
//                                }
                            }).setCancelable(false)
                    .setOnKeyListener((dialogInterface, i, kytEvent) -> {
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
        public boolean onJsAlert(final WebView view, final String urls, final String message, final JsResult result) {
            new AlertDialog.Builder(UserWebActivity.this)
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
            return back();
        }
        return super.onKeyDown(keyCode, event);
    }

    public Boolean back() {
        if (hostor_url != null && hostor_url.size() > 0) {
            hostor_url.remove(hostor_url.size() - 1);
            if (hostor_url.size() > 0) {
                mWebView.loadUrl(hostor_url.get(hostor_url.size() - 1));
                hostor_url.remove(hostor_url.size() - 1);
            } else {
                if (!mWebView.getUrl().equals(PathConstant.BUSINESS + "index.php/Home/Index/index") || !mWebView.getUrl().equals(PathConstant.BUSINESS + "index.php/Home/Index")) {
                    if (!Utils.isExistMainActivity(UserWebActivity.this, AllianceShopActivity.class)) {
                        startActivity(new Intent(UserWebActivity.this, AllianceShopActivity.class));
                    }
                    finish();
                } else {
                    finish();
                }
            }
            return true;
        } else

        {
            finish();
            return true;
        }

    }

    @Override
    protected void onDestroy() {
//        if (mWebView != null)
//            mWebView.destroy();
        if (getMLoading() != null && getMLoading().isShowing())
            getMLoading().dismiss();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            for (String per : permissions) {
                switch (per) {
                    case "android.permission.READ_EXTERNAL_STORAGE":
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        } else {
                            MyToast.showMsg("读写权限已禁止，部分功能暂时无法使用，建议在权限管理中打开");
                        }
                        break;
                    case "android.permission.WRITE_EXTERNAL_STORAGE":
                        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                            ImageUtils.downloadimg(UserWebActivity.this, imgurl);
                        } else {
                            MyToast.showMsg("读写权限已禁止，部分功能暂时无法使用，建议在权限管理中打开");
                        }
                        break;
                }
            }
        }
    }

    public static final int RESULT_CODE_QR_SCAN = 0xA1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_CODE_QR_SCAN) { //RESULT_OK = -1
                    Bundle bundle = intent.getExtras();
                    String scanResult = bundle.getString("qr_scan_result");

                    String orderid = scanResult.substring(scanResult.indexOf("/shopid/") - 12, scanResult.indexOf("/shopid/"));
                    String shopid = scanResult.substring(scanResult.indexOf("shopid/") + 7, scanResult.length());
//                    Log.d("userwebs", orderid + "  " + shopid);
                    if (shopid.equals(UserData.username)) {
                        mWebView.loadUrl(ApiFactory.HOST + "index.php/Home/Unshop/validate_list/orderpaynum/" + orderid);
                    } else {
                        MyToast.showMsg("订单信息有误");
                    }
//                    AlertDialog dialog = new AlertDialog.Builder(this).setTitle("是否改变订单状态")
//                            .setMessage("订单号：" + orderid)
//                            .setCancelable(false)
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    validata_pay(orderid);
//                                }
//                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            }).show();
//                    Kontlin_Utils.setdialotwidth(UserWebActivity.this, dialog);
                    //将扫描出的信息显示出来
//                    if (scanResult.indexOf("hcyh618") > 0 || scanResult.indexOf("hshc618") > 0 || scanResult.indexOf("hetianpay") > 0) {
//                        Intent intent1 = new Intent(UserWebActivity.this, ScanWebActivity.class);
//                        intent1.putExtra("scanweb_url", scanResult);
//                        startActivity(intent1);
//                    } else {
//                        MyToast.showMsg("扫描二维码出错");
//                    }
                }
                break;
        }
    }

    private void validata_pay(String scanResult) {
        RequestBody body = new FormBody.Builder().add("userid", UserData.username)
                .add("token", UserData.tokenid)
                .add("orderpaynum", scanResult).build();
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Unshop/Validate_pay.php", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject object = jsonObject.getJSONObject("data");
                    String status = String.valueOf(object.get("status"));
                    String msg = String.valueOf(object.get("msg"));
                    String url = String.valueOf(object.get("url"));
                    if (status.equals("1")) {//成功
                        mWebView.loadUrl(url);
                    }
                    MyToast.showMsg(msg);
                } catch (JSONException e) {

                }
            }

            @Override
            public void fail(Request request, Exception e) {

            }
        }, null);
    }

    private boolean isfiash = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (!red_ulr.equals(ApiFactory.HOST + "index.php/Home/Unshop/validate_pay")) {
            if (hostor_url != null && hostor_url.size() > 0) {
                mWebView.loadUrl(hostor_url.get(hostor_url.size() - 1));
                if (hostor_url.size() > 1)
                    hostor_url.remove(hostor_url.get(hostor_url.size() - 1));
            } else {
                if (isfiash) {
                    finish();
                }
            }
        }
    }
}
