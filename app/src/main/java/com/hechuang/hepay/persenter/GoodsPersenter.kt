package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.GoodsBean
import com.hechuang.hepay.view.IGoodsView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Android_xu on 2018/3/19.
 */
class GoodsPersenter(view: IGoodsView, context: Context) : BasePersenter<IGoodsView>(view, context) {

    fun getdata() {
        mSubscription = mApi!!.post_goods_data().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<GoodsBean>() {
                    override fun onError(e: Throwable?) {
                        mView.getdataerror("网络异常，请稍后重试")
                    }

                    override fun onNext(t: GoodsBean?) {
                        mView.getdata_ok(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }
}