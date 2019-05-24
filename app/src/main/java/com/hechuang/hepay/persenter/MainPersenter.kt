package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.NewListBean
import com.hechuang.hepay.bean.VersionBean
import com.hechuang.hepay.view.IMainView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainPersenter(itemview: IMainView, context: Context) : BasePersenter<IMainView>(itemview, context) {


    fun getversion() {
        mSubscription = mApi!!.post_version().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<VersionBean>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
//                        Log.d("main", "onError: " + e.message)
                    }

                    override fun onNext(versionBean: VersionBean) {
                        mView.getversion(versionBean)
                    }
                })
    }

    fun getnewlist() {
        mSubscription = mApi!!.post_newlsit().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<NewListBean>() {
                    override fun onNext(t: NewListBean?) {
//                        Log.d("main", t!!.toString())
                        if (t!!.data.status == 1) {
                            mView.getnewlist_success(t)
                        } else {
                            mView.getdataerror("")
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
//                        Log.d("main", e!!.message.toString())
                        mView.getdataerror("")
                    }
                })
    }
}