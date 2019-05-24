package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.BusinessInfoBean
import com.hechuang.hepay.view.IBusiness_InfoView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Bussiness_InfoPersenter(itemview: IBusiness_InfoView, context: Context) : BasePersenter<IBusiness_InfoView>(itemview, context) {

    fun getinfo(userid: String) {
        mSubscription = mApi!!.BUSINESS_INFO(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BusinessInfoBean>() {
                    override fun onNext(t: BusinessInfoBean?) {
//                        Log.d("bussiness_info", t!!.data.list.toString())
                        if (t!!.data.status == 1) {
                            mView.getinfo_success(t)
                        } else {
                            mView.getinfo_error(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
//                        Log.d("bussiness_info", e!!.message.toString())
                        mView.getinfo_error(e!!.message.toString())
                    }
                })
    }
}