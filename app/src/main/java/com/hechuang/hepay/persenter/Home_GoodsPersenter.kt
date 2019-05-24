package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.Home_goods_oneBean
import com.hechuang.hepay.bean.Home_goods_twoBean
import com.hechuang.hepay.view.IHome_GoodsView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Home_GoodsPersenter(itemview: IHome_GoodsView, context: Context) : BasePersenter<IHome_GoodsView>(itemview, context) {
    fun getonedata(number: Int) {
        mSubscription = mApi!!.post_homeone()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Home_goods_oneBean>() {
                    override fun onNext(t: Home_goods_oneBean?) {
                        if (t!!.data.status == "1") {
                            for (i in 0 until t.data.list.size) {
                                t.data.list[i].ischeck = i == 0
                            }
                            mView.getonedataseccess(t, number)
                        } else {
                            mView.getonedatafailure(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        mView.getonedatafailure(e!!.message.toString())
                    }
                })
    }

    /**
     * 获取二三级分类
     */
    fun gettwodata(id: String,position:Int) {
        mSubscription = mApi!!.post_hometwo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Home_goods_twoBean>() {
                    override fun onNext(t: Home_goods_twoBean?) {
                        if (t!!.data.status == "1")
                            mView.gettwodataseccess(t,position)
                        else
                            mView.gettwodatafailure(t.data.msg)
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
//                        Log.d("home_goods", e!!.message.toString())
                        mView.gettwodatafailure(e!!.message.toString())
                    }
                })

    }
}