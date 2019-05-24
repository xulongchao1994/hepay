package com.hechuang.hepay.base

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

import com.hechuang.hepay.R
import com.hechuang.hepay.util.AppManager
import com.hechuang.hepay.util.Eyes
import com.hechuang.hepay.util.UiDensity

/**
 * Created by Android_xu on 2017/12/25.
 * 基础activity
 */

abstract class BaseAppCompatActivity<P : BasePersenter<*>> : AppCompatActivity() {
    protected var mPersenter: P? = null
    protected var mLoading: ProgressDialog? = null
    protected abstract fun initlayout(): Int
    protected abstract fun initPersenter()
    protected abstract fun initView()

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiDensity.setDefault(this)
        AppManager.getAppManager().addActivity(this)
        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_appbar))
        mLoading = ProgressDialog(this)
        mLoading!!.setMessage("正在加载...")
        mLoading!!.setCanceledOnTouchOutside(false)
        setContentView(initlayout())
        initPersenter()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPersenter != null) {
            //            mPersenter.setMContext(null);
            //            mPersenter.setMView(null);
            mPersenter!!.cannelRequest()
        }
        mLoading!!.dismiss()
        mLoading = null
        mPersenter = null
    }


}
