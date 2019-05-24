package com.hechuang.hepay.persenter

import android.content.Context
import android.util.Log
import com.chad.library.adapter.base.BaseViewHolder
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.*
import com.hechuang.hepay.view.IBusiness_GoodsView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Bussiness_GoodsPersenter(itemview: IBusiness_GoodsView, context: Context) : BasePersenter<IBusiness_GoodsView>(itemview, context) {

    /**
     * 获取左边分类信息
     */
    fun getliftdata(userid: String, isflast: Boolean) {
        mSubscription = mApi!!.BUSINESS_GOODS_LIFT(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Business_Goods_LiftBean>() {
                    override fun onNext(t: Business_Goods_LiftBean?) {
                        if (t!!.data.status == 1) {
                            for (i in 0 until t.data.list.size) {
                                t.data.list[i].isclick = i == 0
                            }
                            mView.getliftdatasuccess(t, isflast)
                        } else {
                            mView.getliftdataerror(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        mView.getliftdataerror(e!!.message.toString())
                    }
                })

    }

    /**
     * 获取分类商品
     */
    fun getrightdata(userid: String, id: String) {
        mSubscription = mApi!!.BUSINESS_GOODS_RIGHT(userid, id, UserData.username, UserData.tokenid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Business_Goods_RightBean>() {
                    override fun onNext(t: Business_Goods_RightBean?) {
                        if (t!!.data.status == 1) {
                            mView.getrightdatasuccess(t)
                        } else {
                            mView.getrightdataerror(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        mView.getrightdataerror(e!!.message.toString())
                    }
                })

    }

    /**
     * 购物车数量加
     */
    fun addgoods_shop(supplierid: String, proid: String, styleid: String, agenid: String, token: String, holper: BaseViewHolder?, type: Int) {
        mSubscription = mApi!!.BUSINESS_SHOPPING_ADD(supplierid, proid, styleid, agenid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BusinessClickShoppingBan>() {
                    override fun onNext(t: BusinessClickShoppingBan?) {
                        if (t!!.data.status == 1) {
//                            Log.d("bussiness_add——ontextn", t!!.toString())
                            if (holper == null) {
                                mView.addshopping(t, null, type)
                            } else {
                                mView.addshopping(t, holper!!, type)
                            }
                        } else {
                            mView.addshopping(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        if (e!!.message.toString() != "null") {
                            mView.addshopping(e.message.toString())
                        }
                    }
                })
    }

    /**
     * 购物车数量减
     */
    fun reducegoods_shop(supplierid: String, proid: String, styleid: String, agenid: String, token: String, holder: BaseViewHolder?, type: Int) {
        mSubscription = mApi!!.BUSINESS_SHOPPING_REDUCE(supplierid, proid, styleid, agenid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BusinessClickShoppingBan>() {
                    override fun onNext(t: BusinessClickShoppingBan?) {
                        if (t!!.data.status == 1) {
                            if (holder != null) {
                                mView.redrceshopping(t, holder!!, type)
                            } else {
                                mView.redrceshopping(t, null, type)
                            }
                        } else {
                            mView.redrceshopping(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        if (e!!.message.toString() != "null") {
                            mView.redrceshopping(e!!.message.toString())
                        }
                    }
                })
    }

    fun getinfodata(proid: String) {
        Log.d("goodsinfo", UserData.username + "  " + UserData.tokenid + "  " + proid)
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