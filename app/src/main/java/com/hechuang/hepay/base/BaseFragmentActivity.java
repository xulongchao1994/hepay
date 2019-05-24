package com.hechuang.hepay.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.hechuang.hepay.R;
import com.hechuang.hepay.util.AppManager;
import com.hechuang.hepay.util.Eyes;
import com.hechuang.hepay.util.UiDensity;

/**
 * Created by Android_xu on 2017/12/25.
 * 基础activity
 */

public abstract class BaseFragmentActivity<P extends BasePersenter> extends FragmentActivity {
    protected P mPersenter;

    protected abstract int initlayout();

    protected abstract void initPersenter();

    protected abstract void initView();


    protected ProgressDialog mLoading;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiDensity.setDefault(this);
        AppManager.getAppManager().addActivity(this);
        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.appbar));
        mLoading = new ProgressDialog(this);
        mLoading.setMessage("正在加载...");
        mLoading.setCanceledOnTouchOutside(false);
        setContentView(initlayout());
        initPersenter();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPersenter != null) {
            mPersenter.setMContext(null);
            mPersenter.setMView(null);
            mPersenter.cannelRequest();
        }
        mLoading.dismiss();
        mLoading = null;
        mPersenter = null;
    }


}
