package com.hechuang.hepay.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.service.JPushService;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.util.UiDensity;
import com.mob.MobSDK;
import com.tencent.smtt.sdk.QbSdk;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Android_xu on 2017/12/25.
 * 基础application
 */

public class HePayApplication extends MultiDexApplication {

    // 京东开普勒
//    public static final String appKey = "3162631e10844d7e892eead019de2038";
//    public static final String keySecret = "f598887d2014423d8cecd716c2bb5241";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        /**
         * toast
         */
        MyToast.init(this);
        MyOkHttp.initContext(this);
        UiDensity.setDensity(this);
        MobSDK.init(this);
        Resources res = super.getResources();
        Configuration config = new Configuration();
//        ApiFactory.getInstance(this);
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        // $$isX5QBMode&&-{..{
        // // 注册 这里需要校验 您的证书(shh)，还有包名（package name）,请保证lib工程safe图片是您的
//        KeplerApiManager.asyncInitSdk(HePayApplication.this, appKey, keySecret,
//                new AsyncInitListener() {
//
//                    @Override
//                    public void onSuccess() {
//
//                        Log.e("Kepler", "Kepler asyncInitSdk onSuccess ");
//                    }
//
//                    @Override
//                    public void onFailure() {
//
//                        Log.e("Kepler",
//                                "Kepler asyncInitSdk 授权失败，请检查lib 工程资源引用；包名,签名证书是否和注册一致");
//
//                    }
//                });
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//        JPushInterface.setAlias(this, 0, "hetian");
    }

    private static final String TAG = "HePayApplication";

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }
}
