package com.hechuang.hepay.persenter

import android.content.Context
import android.util.Log
import com.hechuang.hepay.api.ApiFactory
import com.hechuang.hepay.api.MyOkHttp
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.*
import com.hechuang.hepay.view.IAllianceShopView
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONObject
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class AllianceShopPersenter(itemview: IAllianceShopView, context: Context) : BasePersenter<IAllianceShopView>(itemview, context) {
    var TAG = "alliancesshoppersenter"
    fun getbanner() {
        mSubscription = mApi!!.ALLIANCESHOP_BANNER().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<AllianceShopBannerBean>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {
                        Log.d("allianceshop", e.message)
                        if (mView != null)
                            mView.getdataerror("网络连接出错，请稍后重试")
                    }

                    override fun onNext(allianceshopbannerbean: AllianceShopBannerBean) {
                        Log.d("allianceshop", allianceshopbannerbean.data.toString())
                        if (allianceshopbannerbean.data.status == 1) {
                            mView.getbanner_data(allianceshopbannerbean)
                        } else {
                            mView.getdataerror(allianceshopbannerbean.data.msg)
                        }
                    }
                })

    }

    fun getclassify() {
        mSubscription = mApi!!.post_classify().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Union_top_classify_bean>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        if (mView != null)
                            mView.getdataerror_list()
                    }

                    override fun onNext(union_top_classify_bean: Union_top_classify_bean) {
                        Log.d("unionshop", union_top_classify_bean.data.list.toString())
                        if (union_top_classify_bean.data.status == 1) {
                            mView.getclassify_data(union_top_classify_bean)
                        } else {
                            mView.getdataerror(union_top_classify_bean.data.msg)
                        }
                    }
                })
    }

    fun getlistdata(lng: String, lat: String, pid: String, aid: String, qid: String) {
        Log.d(TAG, "getlistdata: " +
                "lng:" + lng + "  " +
                "lat:" + lat + "  " +
                "pid:" + pid + "  " +
                "aid:" + aid + "  " +
                "qid:" + qid + "  "
        )
        mView.showloading()
        var body = FormBody.Builder().add("lat", lat)
                .add("lng", lng)
                .add("pid", pid)
                .add("aid", aid)
                .add("qid", qid)
                .build()
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Mobile/unshop.php?act=unshop", body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
                Log.d("allianceshop", data)
                var beanlist = arrayListOf<AlianceshopHostShopBean.DataBean.ListBean>()
                var datas = ""
                for (i in 0 until data!!.length) {
                    if (data.substring(i, i + 1) == "{") {
                        datas = data.substring(i)
                        break
                    }
                }
                Log.d(TAG, datas)
                var jsons = JSONObject(datas)
                var beandata = jsons.getJSONObject("data")
                var status = beandata.get("status").toString()
                var msg = beandata.get("msg").toString()
                if (status == "1") {
                    var list = beandata.getJSONArray("list")
                    if (list != null && list.length() > 0) {
                        for (i in 0 until list.length()) {
                            var listitem = list.getJSONObject(i)
                            var id = listitem.get("ID").toString()
                            var userid = listitem.get("userid").toString()
                            var ShopName = listitem.get("ShopName").toString()
                            var ShopPhoto = listitem.get("ShopPhoto").toString()
                            var Address = listitem.get("Address").toString()
                            var ggfeelv = listitem.get("ggfeelv").toString()
                            var sale = listitem.get("sale").toString()
                            var distance = listitem.get("distance").toString()
                            var bean = AlianceshopHostShopBean.DataBean.ListBean(id, userid, ShopName, ShopPhoto, Address, ggfeelv, sale, distance)
                            beanlist.add(bean)
                        }
                        mView.getlist_data(beanlist)
                    } else {
                        mView.getlistdata_error(msg)
                    }
                } else {
                    mView.getlistdata_error(msg)
                }

            }

            override fun fail(request: Request?, e: Exception?) {
                mView.getlistdata_error(e!!.message.toString())
            }
        }, null)
//        mSubscription = mApi!!.ALLIANCESHOPHOST(lat, lng, pid, aid, qid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<AlianceshopHostShopBean>() {
//                    override fun onNext(t: AlianceshopHostShopBean?) {
//                        Log.d(TAG, "onNext: " + t!!.data.toString())
//                        if (t.data.status == 1) {
//                            mView.getlist_data(t.data.list)
//                        } else {
//                            mView.getdataerror(t.data.msg)
//                        }
//                        mView.dissmissloading()
//                    }
//
//                    override fun onCompleted() {
//                        Log.d(TAG, "onCompleted: ")
//
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        Log.d(TAG, "onError: " + e!!.message)
//                        if (mView != null) {
//                            mView.getdataerror_list()
//                            mView.dissmissloading()
//                        }
//                    }
//                })
    }

    fun getgoods(province: String, city: String, discrict: String) {
//        var body = FormBody.Builder().add("pid", province)
//                .add("aid", city)
//                .add("qid", discrict)
//                .build()
//        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Unshop/hotcommodity.php", body, object : MyOkHttp.RequestCallBack {
//            override fun success(data: String?) {
//                var listdata = arrayListOf<AlliancesShopHotGoodsBean.DataBean.ListBean>()
//                Log.d("alliance_goods", data)
//                var datas = ""
//                for (i in 0 until data!!.length) {
//                    if (data.substring(i, i + 1) == "{") {
//                        datas = data.substring(i)
//                        break
//                    }
//                }
//                var jsonObject = JSONObject(datas)
//                var datajson = jsonObject.getJSONObject("data")
//                var msg = datajson.get("msg").toString()
//                var status = datajson.get("status").toString().toInt()
//                if (status == 1) {
//                    if (datajson.has("list")) {
//                        var list = datajson.getJSONArray("list")
//                        if (list != null) {
//                            for (i in 0 until list.length()) {
//                                var itemlist = list.getJSONObject(i)
//                                var proid = itemlist.get("proid").toString()
//                                var proname = itemlist.get("proname").toString()
//                                var proimg = itemlist.get("proimg").toString()
//                                var price = itemlist.get("price").toString()
//                                var marketprice = itemlist.get("marketprice").toString()
//                                var url = itemlist.get("url").toString()
//                                var coupon = itemlist.get("coupon").toString()
//                                var pro = itemlist.get("pro").toString()
//                                var goodsbean = AlliancesShopHotGoodsBean.DataBean.ListBean(proid, proname, proimg, price, marketprice, url, coupon, pro)
//                                listdata.add(goodsbean)
//                            }
//                            var databean = AlliancesShopHotGoodsBean.DataBean(msg, status, listdata)
//                            var alliancesShopHotGoodsBean = AlliancesShopHotGoodsBean(databean)
//                            mView.getgoods_list_success(alliancesShopHotGoodsBean)
//                        } else {
//                            mView.getgoods_list_error(msg)
//                        }
//
//                    } else {
//                        mView.getgoods_list_error(msg)
//                    }
//                } else {
//                    mView.getgoods_list_error(msg)
//                }
//            }
//
//            override fun fail(request: Request?, e: java.lang.Exception?) {
//                mView.getgoods_list_error(e!!.message.toString())
//            }
//        }, null)
        mSubscription = mApi!!.ALLIANCES_SHOP_HOT_GOODS(province, city, discrict)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<AlliancesShopHotGoodsBean>() {
                    override fun onNext(t: AlliancesShopHotGoodsBean?) {
                        if (t!!.data.status == 1) {
                            mView.getgoods_list_success(t)
                        } else {
                            mView.getgoods_list_error(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        Log.d(TAG, e!!.message.toString())
                        mView.getgoods_list_error(e!!.message.toString())
                    }
                })

    }


    fun getnewlist() {
        mSubscription = mApi!!.post_newlsit().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<NewListBean>() {
                    override fun onNext(t: NewListBean?) {
                        Log.d("main", t!!.toString())
                        if (t!!.data.status == 1) {
                            mView.getnewlist_success(t)
                        } else {
                            mView.getdataerror("")
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("main", e!!.message.toString())
                        mView.getdataerror("")
                    }
                })
    }
}