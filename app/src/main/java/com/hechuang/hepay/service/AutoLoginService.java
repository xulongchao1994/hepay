package com.hechuang.hepay.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hechuang.hepay.api.Api;
import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.bean.LoginBean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.util.MD5Builder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 自动登录
 * Created by Android_xu on 2018/1/9.
 */
public class AutoLoginService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static OkHttpClient mOkHttpClient;

    private static Api mService;

    public AutoLoginService(String name) {
        super(name);
    }

    public AutoLoginService() {
        super("com.hechuang.hepay.service.autologinservice");
    }

    private static final String TAG = "AutoLoginService";

    /**
     * 首次启动
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String username;
        String password;
        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean isoutlogin = sp.getBoolean("islogin", false);
//        Log.d(TAG, "onHandleIntent: " + sp.getBoolean("remeberpsw", false));
        if (!isoutlogin) {
            username = "";
            password = "";
        } else {
            username = sp.getString("username", "");
            password = sp.getString("password", "");
        }
        String password2 = MD5Builder.getMD5Str(password);
        final SharedPreferences.Editor editor = sp.edit();
        mOkHttpClient = getmOkHttpClient(getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiFactory.HOST)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(Api.class);
        if (!username.equals("")) {
            mService.postLogin(username, password2, "1").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LoginBean>() {
                        @Override
                        public void onCompleted() {
//                            Log.d(TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError(Throwable e) {
//                        startActivity(new Intent(LoginService.this, MainActivity.class));
                            editor.putBoolean("islogin", false);
                            editor.putBoolean("isforce", false);
                            UserData.islogin = false;
//                            MyToast.showMsg("网络连接错误，请稍后重新登录");
                        }

                        @Override
                        public void onNext(LoginBean loginBean) {
//                            Log.d(TAG, "onHandleIntent_onNext: " + loginBean.getData().toString());
                            Intent intent1;
                            if (loginBean.getData().getStatus().equals("1")) {
                                UserData.tokenid = loginBean.getData().getList().getToken();
                                UserData.userid = loginBean.getData().getList().getUserid();
                                UserData.usertyep = loginBean.getData().getList().getUsertype();
                                UserData.serviceffe = loginBean.getData().getList().getServicefee();
                                UserData.sessionid = loginBean.getData().getList().getSessionid();
                                UserData.username = loginBean.getData().getList().getUsername();
                                UserData.namestatus = loginBean.getData().getList().getNamestatus();
                                UserData.isurl = loginBean.getData().getList().getUrl();
                                editor.putString("token_id", UserData.tokenid);
                                editor.putString("urserid", UserData.userid);
                                editor.putString("usertype", UserData.usertyep);
                                editor.putString("name", UserData.username);
                                editor.putBoolean("islogin", true);
                                editor.putBoolean("isforce", true);
                                UserData.islogin = true;
                                JPushInterface.setAlias(getApplicationContext(), 0, UserData.username);
//                                Log.d(TAG, "login_ok: " + UserData.tokenid + UserData.username + UserData.sessionid + "   " + UserData.islogin);
//                                intent1 = new Intent(AutoLoginService.this, HePayActivity.class);
//                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                                startActivity(intent1);
                            } else if (loginBean.getData().getStatus().equals("2")) {
                                UserData.tokenid = loginBean.getData().getToken();
                                UserData.userid = loginBean.getData().getUserid();
                                editor.putBoolean("islogin", true);
                                editor.putBoolean("isforce", false);
//                                intent1 = new Intent(LoginService.this, ForceModifyPasswordActivity.class);
//                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                                startActivity(intent1);
                            } else {
                                editor.putBoolean("islogin", false);
                                editor.putBoolean("isforce", false);
                                UserData.islogin = false;
//                                intent1 = new Intent(AutoLoginService.this, LoginActivity.class);
//                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                                startActivity(intent1);
                            }
                        }
                    });
        } else {
//            Intent intent1 = new Intent(AutoLoginService.this, LoginActivity.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//            startActivity(intent1);
        }
    }

    private static OkHttpClient getmOkHttpClient(Context context) {
        Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)))
                .build();
    }

    /**
     * 再次启动
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        String username;
        String password;
        SharedPreferences sp = getSharedPreferences("userInfo", 0);
        boolean isoutlogin = sp.getBoolean("islogin", false);
        if (!isoutlogin) {
            username = "";
            password = "";
        } else {
            username = sp.getString("username", "");
            password = sp.getString("password", "");
        }
        String password2 = MD5Builder.getMD5Str(password);
        final SharedPreferences.Editor editor = sp.edit();
        mOkHttpClient = getmOkHttpClient(getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiFactory.HOST)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(Api.class);
        if (!username.equals("")) {
            mService.postLogin(username, password2, "1").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LoginBean>() {
                        @Override
                        public void onCompleted() {
//                        Log.d(TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError(Throwable e) {
//                            Log.e(TAG, "onError: " + e.getMessage());
//                        startActivity(new Intent(LoginService.this, MainActivity.class));
                        }

                        @Override
                        public void onNext(LoginBean loginBean) {
//                            Log.d(TAG, "onStartCommand_onNext: " + loginBean.getData().toString());
                            Intent intent1;
                            if (loginBean.getData().getStatus().equals("1")) {
                                UserData.tokenid = loginBean.getData().getList().getToken();
                                UserData.userid = loginBean.getData().getList().getUserid();
                                UserData.usertyep = loginBean.getData().getList().getUsertype();
                                UserData.serviceffe = loginBean.getData().getList().getServicefee();
                                UserData.sessionid = loginBean.getData().getList().getSessionid();
                                UserData.username = loginBean.getData().getList().getUsername();
                                UserData.namestatus = loginBean.getData().getList().getNamestatus();
                                UserData.isurl = loginBean.getData().getList().getUrl();
                                editor.putString("token_id", UserData.tokenid);
                                editor.putString("urserid", UserData.userid);
                                editor.putString("usertype", UserData.usertyep);
                                editor.putString("name", UserData.username);
                                editor.putBoolean("islogin", true);
                                editor.putBoolean("isforce", true);
                                UserData.islogin = true;
                                JPushInterface.setAlias(getApplicationContext(), 0, UserData.username);
//                                Log.d(TAG, "login_ok: " + UserData.tokenid + UserData.username + UserData.sessionid + "   " + UserData.islogin);
//                                intent1 = new Intent(AutoLoginService.this, HePayActivity.class);
//                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                                startActivity(intent1);
                            } else {
                                editor.putBoolean("islogin", false);
                                editor.putBoolean("isforce", false);
                                UserData.islogin = false;
//                                intent1 = new Intent(AutoLoginService.this, LoginActivity.class);
//                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                                startActivity(intent1);
                            }
                        }
                    });
        } else {
//            Intent intent1 = new Intent(AutoLoginService.this, LoginActivity.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//            startActivity(intent1);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
