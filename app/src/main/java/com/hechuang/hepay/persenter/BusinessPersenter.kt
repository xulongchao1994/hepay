package com.hechuang.hepay.persenter

import android.content.Context
import android.util.Log
import com.hechuang.hepay.api.MyOkHttp
import com.hechuang.hepay.api.PathConstant
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.BusinessBannerBean
import com.hechuang.hepay.bean.BusinessClickShoppingBan
import com.hechuang.hepay.bean.BusinessInfoBean
import com.hechuang.hepay.bean.BusinessShoppingBean
import com.hechuang.hepay.view.IBusinessView
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONObject
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class BusinessPersenter(itemview: IBusinessView, context: Context) : BasePersenter<IBusinessView>(itemview, context) {

    fun getbanner(userid: String) {
        var body = FormBody.Builder().add("id", userid)
                .build()
        Log.d("business", userid)
        MyOkHttp.getInstance().post(PathConstant.BUSINESS_BANNER, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
                Log.d("business", data)
                var prod = "0"
                var userid = ""
                var beanlist = arrayListOf<BusinessBannerBean.DataBean.ListBean>()
                var databean: BusinessBannerBean.DataBean? = null
                var json = JSONObject(data)
                var datas = json.getJSONObject("data")
                var status = datas.get("status").toString().toInt()
                var msg = datas.get("msg").toString()
                if (datas.has("prod")) {
                    prod = datas.get("prod").toString()
                } else {
                    prod = "0"
                }
                if (datas.has("userid")) {
                    userid = datas.get("userid").toString()
                } else {
                    userid = ""
                }
                if (status == 1) {
                    if (datas.has("list")) {
                        var list = datas.getJSONArray("list")
                        if (list != null && list.length() > 0) {
                            for (i in 0 until list.length()) {
                                var userid = ""
                                var jsonitem = list.getJSONObject(i)
                                userid = jsonitem.get("Userid").toString()
                                var shopphoto = jsonitem.get("ShopPhoto").toString()
                                var bean = BusinessBannerBean.DataBean.ListBean(userid, shopphoto)
                                beanlist.add(bean)
                            }
                        }
                        databean = BusinessBannerBean.DataBean(msg, status, prod, userid, beanlist)
                    } else {
                        databean = BusinessBannerBean.DataBean(msg, status, prod, userid, null)
                    }
                } else {
                    databean = BusinessBannerBean.DataBean(msg, status, prod, userid, null)
                }
                var businessbannerbean = BusinessBannerBean(databean)
                mView.getbanner_success(businessbannerbean)
            }

            override fun fail(request: Request?, e: Exception?) {
                mView.getbanner_error(e!!.message.toString())
            }
        }, null)
    }

    fun getinfo(userid: String) {
        mSubscription = mApi!!.BUSINESS_INFO(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BusinessInfoBean>() {
                    override fun onNext(t: BusinessInfoBean?) {
                        if (t!!.data.status == 1) {
                            mView.getinfo_success(t)
                        } else {
                            mView.getinfo_error(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        mView.getinfo_error(e!!.message.toString())
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