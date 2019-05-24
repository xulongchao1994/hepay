package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.api.MyOkHttp
import com.hechuang.hepay.api.PathConstant
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.AllorderBean
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.view.IAllOrderView
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONObject

class AllOrderPersenter(view: IAllOrderView, context: Context) : BasePersenter<IAllOrderView>(view, context) {
    var mListdata = ArrayList<AllorderBean.DataBean.ListBean>()
    fun getlistdata(unm: String, orderstatus: String, page: String, clean: Boolean) {
        var body = FormBody.Builder().add("username", UserData.username)
                .add("token", UserData.tokenid)
                .add("num", unm)
                .add("orderstatus", orderstatus)
                .add("Page", page).build()
        MyOkHttp.getInstance().post(PathConstant.ALLORDER_URL, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
                if (clean) {
                    if (mListdata.size > 0) {
                        mListdata.clear()
                    }
                }

                val datas = JSONObject(data)
                var itemdata = datas.getJSONObject("data")
                val status = itemdata.get("status").toString()
                val msg = itemdata.get("message").toString()
                val InnerOrderId = itemdata.get("InnerOrderId").toString()
                val chong = itemdata.get("zhong").toString()
                val list = itemdata.getJSONArray("list")
                val orderinfolist = ArrayList<AllorderBean.DataBean.ListBean.OrderdetailBean>()
                if (list != null && list.length() > 0) {
                    for (i in 0 until list.length()) {
                        val listitem = list.getJSONObject(i)
                        val proname = listitem.get("ProName").toString()
                        val stylename = listitem.get("StyleName").toString()
                        val pronum = listitem.get("proNum").toString()
                        val styleid = listitem.get("StyleId").toString()
                        val Supplier = listitem.get("Supplier").toString()
                        val ProImg = listitem.get("ProImg").toString()
                        val money = listitem.get("money").toString()
                        val orderdata = AllorderBean.DataBean.ListBean.OrderdetailBean(proname, stylename
                                , pronum, styleid, Supplier, ProImg, money)
                        orderinfolist.add(orderdata)
                    }
                }
                val orders = AllorderBean.DataBean.ListBean(status, msg, InnerOrderId, chong, orderinfolist)
                mListdata.add(orders)
                mView.getallorderdataok(mListdata)
            }

            override fun fail(request: Request?, e: Exception?) {
            }
        }, null)
//        mSubscription = mApi!!.post_allorderdata(UserData.username, UserData.tokenid, unm, orderstatus, page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<AllorderBean>() {
//                    override fun onNext(t: AllorderBean?) {
//                        if (clean) {
//                            if (mListdata.size > 0) {
//                                mListdata.clear()
//                            }
//                            mView.getallorderdataok(t!!.data.list)
//                        } else {
//                            for (i in 0 until t!!.data.list.size) {
//                                mListdata.add(t.data.list[i])
//                            }
//                            mView.getallorderdataok(mListdata)
//                        }
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        mView.getdataerror(e.toString())
//                    }
//
//                })
    }
}