package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.api.MyOkHttp
import com.hechuang.hepay.api.PathConstant
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.SelectCityBean
import com.hechuang.hepay.view.ISelectCityView
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject

/**
 * 城市选择
 * Created by Android_xu on 2018/3/24.
 */
class SelectVityPersenter(view: ISelectCityView, mcontext: Context) : BasePersenter<ISelectCityView>(view, mcontext) {
    fun getProvince() {
        if (mView != null)
            mView.showloading()
        var mCityList = ArrayList<String>()
        val body = FormBody.Builder().build()
//        Log.d("select",PathConstant.GET_PROVINCE)
        MyOkHttp.getInstance().post(PathConstant.GET_PROVINCE, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String) {
//                Log.d("city", data)
                try {
                    val datas = JSONObject(data)
                    val data_itme = datas.getJSONObject("data")
                    val status = data_itme.get("status").toString()
                    val msg = data_itme.get("msg").toString()
                    if (status == "1") {
                        val list = data_itme.getJSONArray("list")
                        for (i in 0 until list.length()) {
                            val pr = list.get(i).toString()
                            mCityList.add(pr)
                        }
                        val beans = SelectCityBean.DataBean(status, msg, mCityList)

                        if (mView != null)
                            mView.getprovinceok(beans)
                    }
                } catch (e: JSONException) {

                }
                if (mView != null)
                    mView.dissmissloading()

            }

            override fun fail(request: Request, e: Exception) {
                if (mView != null)
                    mView.dissmissloading()

            }
        }, null)
    }

    fun getCity(province: String) {
        if (mView != null)
            mView.showloading()
        var mCityList = ArrayList<String>()
        val body = FormBody.Builder()
                .add("province", province).build()

        MyOkHttp.getInstance().post(PathConstant.GET_CITY, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String) {
//                Log.d("city", province + data)
                try {
                    val datas = JSONObject(data)
                    val data_itme = datas.getJSONObject("data")
                    val status = data_itme.get("status").toString()
                    val msg = data_itme.get("msg").toString()
                    if (status == "1") {
                        val list = data_itme.getJSONArray("list")
                        for (i in 0 until list.length()) {
                            val pr = list.get(i).toString()
                            mCityList.add(pr)
                        }
                        val beans = SelectCityBean.DataBean(status, msg, mCityList)

                        if (mView != null)
                            mView.getcityok(beans)
                    }
                } catch (e: JSONException) {

                }

                if (mView != null)
                    mView.dissmissloading()
            }

            override fun fail(request: Request, e: Exception) {

                if (mView != null)
                    mView.dissmissloading()

            }
        }, null)
    }

    fun getCount(province: String) {
        if (mView != null)
            mView.showloading()
        var mCityList = ArrayList<String>()
        val body = FormBody.Builder()
                .add("city", province).build()
        MyOkHttp.getInstance().post(PathConstant.GET_COUNT, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String) {
//                Log.d("select", data)
                try {
                    val datas = JSONObject(data)
                    val data_itme = datas.getJSONObject("data")
                    val status = data_itme.get("status").toString()
                    val msg = data_itme.get("msg").toString()
                    if (status == "1") {
                        val list = data_itme.getJSONArray("list")
                        for (i in 0 until list.length()) {
                            val pr = list.get(i).toString()
                            mCityList.add(pr)
                        }
                        val beans = SelectCityBean.DataBean(status, msg, mCityList)
                        if (mCityList != null && mCityList.size > 0) {
                            if (mView != null) {
                                mView.getcountok(beans)
                            }
                        } else
                            if (mView != null)
                                mView.getdataerror("查询出错")
                    }
                } catch (e: JSONException) {
                }
                if (mView != null)
                    mView.dissmissloading()
            }

            override fun fail(request: Request, e: Exception) {
                if (mView != null)
                    mView.dissmissloading()
            }
        }, null)
    }
}