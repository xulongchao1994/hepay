package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.OrderInfoBean
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.view.IOrderInfoView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class OrderinfoPersenter(itemview: IOrderInfoView, mContext: Context) : BasePersenter<IOrderInfoView>(itemview, mContext) {
    fun getdata(orderid: String) {
        mSubscription = mApi!!.post_orderinfodata(UserData.username, UserData.tokenid, orderid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<OrderInfoBean>() {
                    override fun onNext(t: OrderInfoBean?) {
                        if (t!!.data.status == 1) {
                            mView.setdata(t)
                        } else {
                            mView.getdataerror("获取数据失败")
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        mView.getdataerror(e!!.message)
                    }
                })

    }
}