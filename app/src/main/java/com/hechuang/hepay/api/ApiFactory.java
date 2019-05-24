package com.hechuang.hepay.api;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android_xu on 2017/12/25.
 * 网络工具类
 */

public class ApiFactory {
    public static String DATABASE_DIRE = "/com.hechuang.hepay/Database/";
            public static final String HOST = "http://htf.99xyg.com/";
//    public static final String HOST = "http://www.hetianpay.com/";
    public static final String APK_DOWN = "http://apk.hshc618.com/";
//    public static final String BUSINESS = "http://unshop.99xyg.com/";

    /**
     * 位置转化为坐标
     * address  名称
     * city 城市
     * ak
     */
    public static String ADDERS_MAPS = "http://api.map.baidu.com/geocoder/v2/?callback=renderOption&output=json";
    /**
     * 将百度的坐标系转换成高德的坐标系
     * locations=116.481499,39.990475
     * &coordsys=baidu
     * &output=JSON
     * &key=46908be89194ec0cfcdf35c8c74dc3fd
     */
    public static String BAIDU_GAODE = "http://restapi.amap.com/v3/assistant/coordinate/convert?";
    protected static Api mService;
    protected static OkHttpClient mOkHttpClient;

    public static Api getInstance(Context context) {
        if (mService == null) {
            createRetrofit(context);
        }
        return mService;
    }


    private static void createRetrofit(Context context) {
        mOkHttpClient = getOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST)
                .build();
        mService = retrofit.create(Api.class);
    }

    public static OkHttpClient getOkHttpClient(Context context) {
        Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"),
                1024 * 1024 * 24);
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)))
                .build();
    }
}
