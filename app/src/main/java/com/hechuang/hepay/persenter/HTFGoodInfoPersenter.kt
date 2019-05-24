package com.hechuang.hepay.persenter

import android.content.Context
import android.util.AndroidException
import com.chad.library.adapter.base.BaseViewHolder
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.BusinessClickShoppingBan
import com.hechuang.hepay.bean.BusinessShoppingBean
import com.hechuang.hepay.bean.HTFGoodInfoBean
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.view.IHTFGoodInfoView
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class HTFGoodInfoPersenter(itemview: IHTFGoodInfoView, context: Context) : BasePersenter<IHTFGoodInfoView>(itemview, context) {

    fun getinfodata(proid: String) {
        mSubscription = mApi!!.HTF_GOOD_INFO_BEAN_OBSERVABLE(UserData.username, UserData.tokenid, proid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<HTFGoodInfoBean>() {
                    override fun onNext(t: HTFGoodInfoBean?) {
                        if (t!!.data.status == "1") {
                            mView.getinfodata_success(t)
                        } else {
                            mView.getinfodata_error(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        mView.getinfodata_error(e!!.message.toString())
                    }
                })
    }


    /**
     * 购物车数量加
     */
    fun addgoods_shop(supplierid: String, proid: String, styleid: String, agenid: String, token: String, type: Int) {
//        Log.d("bussiness_shoppingadd", "$supplierid $proid $styleid $agenid $token $holper $type")
        mSubscription = mApi!!.BUSINESS_SHOPPING_ADD(supplierid, proid, styleid, agenid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BusinessClickShoppingBan>() {
                    override fun onNext(t: BusinessClickShoppingBan?) {
                        if (t!!.data.status == 1) {
                            mView.addgoodsuccess(t, type)
                        } else {
                            mView.addgooderror(t.data.msg, type)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        if (e!!.message.toString() != "null") {
                            mView.addgooderror(e.message.toString(), type)
                        }
                    }
                })
    }

    /**
     * 购物车数量减
     */
    fun reducegoods_shop(supplierid: String, proid: String, styleid: String, agenid: String, token: String, type: Int) {
//        Log.d("bussiness_shoppingadd", "$supplierid $proid $styleid $agenid $holder $type")
        mSubscription = mApi!!.BUSINESS_SHOPPING_REDUCE(supplierid, proid, styleid, agenid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BusinessClickShoppingBan>() {
                    override fun onNext(t: BusinessClickShoppingBan?) {
                        if (t!!.data.status == 1) {
                            mView.reducegoodsuccess(t, type)
                        } else {
                            mView.reducegooderror(t.data.msg, type)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        if (e!!.message.toString() != "null") {
                            mView.reducegooderror(e.message.toString(), type)
                        }
                    }
                })
    }


    fun getshopping(supid: String, agenid: String, token: String, type: Boolean) {
        mSubscription = mApi!!.BUSINESS_SHOPPING(supid, agenid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BusinessShoppingBean>() {
                    override fun onNext(t: BusinessShoppingBean?) {
                        if (t!!.data.status == 1) {
                            mView.getshopping(t, type)
                        } else {
                            mView.getshopping_error(t.data.msg, type)
                        }
                    }

                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable?) {
                        mView.getshopping_error(e!!.message.toString(), type)
                    }
                })
    }

    fun getgopayurl(supid: String, agenid: String, token: String) {
        mSubscription = mApi!!.BUSINESS_SHOPPING_GOPAY(supid, agenid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BusinessClickShoppingBan>() {
                    override fun onNext(t: BusinessClickShoppingBan?) {
                        if (t!!.data.status == 1) {
                            mView.getgopay(t)
                        } else {
                            mView.getgopay(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        mView.getgopay(e!!.message.toString())
                    }
                })

    }
}