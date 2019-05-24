package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.api.MyOkHttp
import com.hechuang.hepay.api.PathConstant
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.Uploadimglistdata
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.view.IUploadImageView
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class UploadImagePersenter(view: IUploadImageView, mContext: Context) : BasePersenter<IUploadImageView>(view, mContext) {
    var uploadlist = ArrayList<Uploadimglistdata.DataBean.ListBean>()
    fun getimagelist() {
        val body: RequestBody = FormBody.Builder()
                .add("userid", UserData.username)
                .add("usertoken", UserData.tokenid)
                .build()
//        Log.d("uploadimage", "${PathConstant.GET_IMAGELIST}  ${UserData.username}  ${UserData.tokenid}")
        MyOkHttp.getInstance().post(PathConstant.GET_IMAGELIST, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String) {
//                Log.d("uploadimage", data)
                uploadlist.clear()
                try {
                    val datas = JSONObject(data)
                    val datainfo = datas.getJSONObject("data")
                    val status = datainfo.get("status").toString()
                    val msg = datainfo.get("msg").toString()
                    if (status == "1") {
                        val list = datainfo.getJSONArray("list")
//                        if (list != null && list.length() > 0) {
                            for (i in 0 until list.length()) {
                                val listitem = list.getJSONObject(i)
                                val id = listitem.get("id").toString()
                                val uri = listitem.get("url").toString()
                                val uploadimgItem = Uploadimglistdata.DataBean.ListBean(id, uri, false, false)
                                uploadlist.add(uploadimgItem)
                            }
                            mView.getlistdata_ok(uploadlist)
//                        }else{
//                            mView.getdataerror(msg)
//                        }
                    } else {
                        mView.getdataerror(msg)
                    }
                } catch (e: JSONException) {

                }
            }


            override fun fail(request: Request?, e: Exception?) {
            }
        }, null)
//        mSubscription = mApi.post_imgaes(UserData.username, UserData.tokenid).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<Uploadimglistdata>() {
//                    override fun onNext(t: Uploadimglistdata?) {
//                        mView.getlistdata_ok(t!!)
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        Log.d("uploadimage", e!!.message)
//                    }
//                })
    }

    fun uploadimg(file: String) {
        val body: RequestBody = FormBody.Builder()
                .add("userid", UserData.username)
                .add("usertoken", UserData.tokenid)
                .add("file", file).build()

        MyOkHttp.getInstance().post(PathConstant.UPLOADIMAGE, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
//                Log.d("uploadimage", data
//                )
                val datas = JSONObject(data)
                val datainfo = datas.getJSONObject("data")
                val status = datainfo.get("status").toString()
                val msg = datainfo.get("msg").toString()
                if (status == "1") {
                    mView.upimage_ok(msg)
                } else {
                    mView.getdataerror(msg)
                }
            }

            override fun fail(request: Request?, e: Exception?) {

            }

        }, null)
    }

    fun deleteimages(images: String) {
        val body: RequestBody = FormBody.Builder()
                .add("userid", UserData.username)
                .add("usertoken", UserData.tokenid)
                .add("filename", images).build()
        MyOkHttp.getInstance().post(PathConstant.DELETEIMAGE, body, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
//                Log.d("uploadimage", data)
                val datas = JSONObject(data)
                val datainfo = datas.getJSONObject("data")
                val status = datainfo.get("status").toString()
                val msg = datainfo.get("msg").toString()
                if (status == "1") {
                    mView.dtimgage_ok(msg)
                } else {
                    mView.getdataerror(msg)
                }
            }

            override fun fail(request: Request?, e: Exception?) {

            }

        }, null)
    }
}