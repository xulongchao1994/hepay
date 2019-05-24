package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.api.ApiFactory
import com.hechuang.hepay.api.MyOkHttp
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.BindingBean
import com.hechuang.hepay.view.IBindingPhoneView
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONObject

class BindingPhonePersenter(itemview: IBindingPhoneView, context: Context) : BasePersenter<IBindingPhoneView>(itemview, context) {
    fun post_bind(unionid: String, userid: String, password: String, rmdcode: String) {
        var body = FormBody.Builder()
                .add("unionid", unionid)
                .add("userid", userid)
                .add("password", password)
                .add("rmdcode", rmdcode)
                .build()
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/login/binding.php", body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
                var dafdar = ""
                for (i in 0 until data!!.length) {
                    if (data.substring(i, i + 1).equals("{")) {
                        dafdar = data.substring(i)
                        break
                    }
                }
                var bind: BindingBean.ListBean? = null
                var datas = JSONObject(dafdar)
                var status = datas.get("status").toString()
                var msg = datas.get("msg").toString()
                if (status == "1") {
                    if (datas.has("list")) {
                        var listjson = datas.getJSONObject("list")
                        var userid = listjson.get("userid").toString()
                        var username = listjson.get("username").toString()
                        var usertype = listjson.get("usertype").toString()
                        var token = listjson.get("token").toString()
                        var sessionid = listjson.get("sessionid").toString()
                        var servicefee = listjson.get("servicefee").toString()
                        bind = BindingBean.ListBean(userid, username, usertype, token, sessionid, servicefee)
                    } else {
                        bind = BindingBean.ListBean("", "", "", "", "", "")
                    }
                    var bean = BindingBean(bind, status, msg)
                    mView.Binding_seccess(bean)
                } else {
                    mView.binding_error(msg)
                }
            }

            override fun fail(request: Request?, e: Exception?) {
                mView.binding_error(e!!.message.toString())
            }
        }, null)
//        mSubscription = mApi!!.post_bindphone(unionid, userid, password, rmdcode)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<BindingBean>() {
//                    override fun onNext(t: BindingBean?) {
//                        if (t!!.status == "1") {
//                            mView.Binding_seccess(t)
//                        } else {
//                            mView.binding_error(t.msg)
//                        }
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        mView.binding_error(e!!.message.toString())
//                    }
//                })
    }
}