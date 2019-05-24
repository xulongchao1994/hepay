package com.hechuang.hepay.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Android_xu on 2018/1/15.
 * 基础fragment
 */

abstract class BasektFragment<P : BasePersenter<*>> : Fragment() {
    protected var mPersenter: P? = null
    protected var mView: View? = null

    protected var mLoading: ProgressDialog? = null
    protected abstract fun initLayout(): Int

    protected abstract fun initViews(view: View)

    protected abstract fun initPersenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPersenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(initLayout(), container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoading = ProgressDialog(activity)
        mLoading!!.setMessage("正在加载...")
        mLoading!!.setCanceledOnTouchOutside(false)
        initViews(view)
    }

    override fun onStop() {
        super.onStop()
//        if (mPersenter != null) {
            mPersenter!!.cannelRequest()
//        }
//        mLoading!!.dismiss()
//        mLoading = null
//        mPersenter = null
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        if (mPersenter != null) {
//            mPersenter!!.cannelRequest()
//        }
//        mLoading!!.dismiss()
//        mLoading = null
//        mPersenter = null
//    }
}
