package com.hechuang.hepay.base

import android.content.Context
import com.hechuang.hepay.api.Api
import com.hechuang.hepay.api.ApiFactory
import rx.Subscription

/**
 * Created by Android_xu on 2017/12/25.
 * 基础persenter
 */

abstract class BasePersenter<V : BaseView>(var mView: V, var mContext: Context) {
    protected var mApi: Api? = null
    protected var mSubscription: Subscription? = null

    init {
        if (mApi == null)
            mApi = ApiFactory.getInstance(mContext.applicationContext)
    }

    /**
     * 取消网络请求(这里是有RxJava,即为取消订阅结果)
     */

    fun cannelRequest() {
        if (mSubscription != null)
            mSubscription!!.unsubscribe()
    }

}
