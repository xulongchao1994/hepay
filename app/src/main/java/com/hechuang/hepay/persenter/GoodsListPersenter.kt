package com.hechuang.hepay.persenter

import android.content.Context
import android.util.Log
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.GoodsListBean
import com.hechuang.hepay.view.IGoodsListView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class GoodsListPersenter(itemview: IGoodsListView, context: Context) : BasePersenter<IGoodsListView>(itemview, context) {
    fun getlistdata(ranking: String, type: String, page: String, infos: String) {
        mSubscription = mApi!!.GOODSLIST_POST(ranking, type, page, infos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<GoodsListBean>() {
                    override fun onNext(t: GoodsListBean?) {
                        Log.d("goods", t!!.toString())
                        if (t!!.status == 1) {
                            mView.getlistdatasuccess(t)
                        } else {
                            mView.getdataerror(t.message)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("goods", e!!.toString())
                        mView.getdataerror(e!!.message.toString())
                    }
                })
    }
}