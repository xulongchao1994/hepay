package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.api.MyOkHttp
import com.hechuang.hepay.api.PathConstant
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.view.IBaiDuMapView
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONObject

class BaiDuMapPersenter(view: IBaiDuMapView, context: Context) : BasePersenter<IBaiDuMapView>(view, context) {
    fun postdata(province: String, city: String, county: String, address: String, name: String) {
        var body = FormBody.Builder().add("userid", UserData.username)
                .add("usertoken", UserData.tokenid)
                .add("province", province)
                .add("city", city)
                .add("county", county)
                .add("address", name)
                .build()
        MyOkHttp.getInstance().post(PathConstant.STORE_ADDRESS, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
                val datas = JSONObject(data)
                val datainfo = datas.getJSONObject("data")
                val status = datainfo.get("status").toString()
                val msg = datainfo.get("msg").toString()
                if (status.equals("1"))
                    mView.setdataok(msg)
                else
                    mView.getdataerror(msg)
            }

            override fun fail(request: Request?, e: java.lang.Exception?) {
            }
        }, null)
    }
}
