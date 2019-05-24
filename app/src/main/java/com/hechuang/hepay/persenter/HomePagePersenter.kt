package com.hechuang.hepay.persenter

import android.content.Context
import android.util.Log
import com.hechuang.hepay.api.MyOkHttp
import com.hechuang.hepay.api.PathConstant
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.Home_Banner_Bean
import com.hechuang.hepay.bean.Home_ClassifyBean
import com.hechuang.hepay.bean.Home_NewsBean
import com.hechuang.hepay.bean.Home_shopBean
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IHomePageView
import okhttp3.Request
import org.json.JSONObject

/**
 * Created by Android_xu on 2018/1/15.
 */

class HomePagePersenter(view: IHomePageView, context: Context) : BasePersenter<IHomePageView>(view, context) {
    fun getbannerdata() {
        mView.showloading()
        var listbanner = arrayListOf<Home_Banner_Bean.DataBean.BannerBean>()
        MyOkHttp.getInstance().get(PathConstant.HOEM_BANNER, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
                Log.e("homepagepersenter", data)
                if (data!!.contains("<html")) {
                    MyToast.showMsg("接口访问出错，请稍后重试")
                } else {
                    var jsong = JSONObject(data)
                    var status = jsong.get("status").toString()
                    var msg = jsong.get("msg").toString()
                    if (status == "1") {
                        var Jsondata = jsong.getJSONObject("data")
                        var bannerarray = Jsondata.getJSONArray("banner")
                        for (i in 0 until bannerarray.length()) {
                            var jsong_item = bannerarray.getJSONObject(i)
                            var style = jsong_item.get("style").toString()
                            var url = jsong_item.get("url").toString()
                            var urlid = jsong_item.get("urlid").toString()
                            var imgs = jsong_item.get("imgs").toString()
                            var itembean = Home_Banner_Bean.DataBean.BannerBean(style, url, urlid, imgs)
                            listbanner.add(itembean)
                        }
                        var listdata = Home_Banner_Bean.DataBean(listbanner)
                        var beandata = Home_Banner_Bean(status, msg, listdata)
                        if (mView != null) {
                            mView.gethomebanner_success(beandata)
                        }
                    } else {
                        mView.gethomebanner_failure(msg)
                    }
                }
            }

            override fun fail(request: Request?, e: Exception?) {
                mView.gethomebanner_failure(e!!.message.toString())
            }
        }, null)

//        mSubscription = mApi!!.post_homebeean()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<Home_Banner_Bean>() {
//                    override fun onNext(t: Home_Banner_Bean?) {
//                        Log.d("hoempage", t!!.toString())
//                        if (t!!.status == "1") {
//                            mView.gethomebanner_success(t)
//                        } else {
//                            mView.gethomebanner_failure(t.msg)
//                        }
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        Log.d("hoempage", e!!.message.toString())
//                        mView.gethomebanner_failure(e!!.message.toString())
//                    }
//                })
    }


    fun gethome_classify() {
        mView!!.showloading()
        var listdata = arrayListOf<Home_ClassifyBean.DataBean>()
        MyOkHttp.getInstance().get(PathConstant.HOEM_CLASSIFY, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
                var id = ""
//                Log.d("homepage", data)
                var jsong = JSONObject(data)
                var status = jsong.get("status").toString()
                var msg = jsong.get("msg").toString()
                if (status == "1") {
                    var bannerarray = jsong.getJSONArray("data")
                    for (i in 0 until bannerarray.length()) {
                        var jsong_item = bannerarray.getJSONObject(i)
                        if (jsong_item.has("id")) {
                            id = jsong_item.get("id").toString()
                        } else {
                            id = ""
                        }
                        var name = jsong_item.get("name").toString()
                        var url = jsong_item.get("url").toString()
                        var imgs = jsong_item.get("imgs").toString()
                        var bean = Home_ClassifyBean.DataBean(id, name, url, imgs)
                        listdata.add(bean)
                    }
                    var databean = Home_ClassifyBean(status, msg, listdata)
                    mView.gethome_classify_success(databean)
                } else {
                    mView.gethome_classify_failure(msg)
                }

            }

            override fun fail(request: Request?, e: Exception?) {
//                Log.d("homepage", e!!.message.toString())
                mView.gethome_classify_failure(e!!.message.toString())
            }
        }, null)
//        mSubscription = mApi!!.post_homeclassify().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<Home_ClassifyBean>() {
//                    override fun onNext(t: Home_ClassifyBean?) {
//                        Log.d("homepage", t!!.toString())
//                        if (t!!.status == 1) {
//                            mView.gethome_classify_success(t)
//                        } else {
//                            mView.gethome_classify_failure(t.msg)
//                        }
//
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        Log.d("homepage", e!!.message.toString())
//                        mView.gethome_classify_failure(e!!.message.toString())
//                    }
//                })
    }

    fun getnews() {
        mView.showloading()
        var itemslist = arrayListOf<Home_NewsBean.DataBean.ListBean>()
        MyOkHttp.getInstance().get(PathConstant.HOME_NEWS, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
                var josns = JSONObject(data)
                var jsondata = josns.getJSONObject("data")
                var status = jsondata.get("status").toString()
                var msg = jsondata.get("msg").toString()
                if (status == "1") {
                    var listdata = jsondata.getJSONArray("list")
                    for (i in 0 until listdata.length()) {
                        var itemlist = listdata.getJSONObject(i)
                        var id = itemlist.get("id").toString()
                        var title = itemlist.get("title").toString()
                        var addtime = itemlist.get("addtime").toString()
                        var itembean = Home_NewsBean.DataBean.ListBean(id, title, addtime)
                        itemslist.add(itembean)
                    }
                    var listbean = Home_NewsBean.DataBean(status, msg, itemslist)
                    var bean = Home_NewsBean(listbean)
                    mView.gethomenews_success(bean)
                } else {
                    mView.gethomenews_failure(msg)
                }
            }

            override fun fail(request: Request?, e: Exception?) {
                mView.gethomenews_failure(e!!.message.toString())
            }
        }, null)
//        mSubscription = mApi!!.post_homenews()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<Home_NewsBean>() {
//                    override fun onNext(t: Home_NewsBean?) {
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                    }
//                })
    }

    fun getdata1() {
        mView.showloading()
        var itemslist = arrayListOf<Home_shopBean.DataBean.BannerBean>()
        MyOkHttp.getInstance().get(PathConstant.HOME_SHOP1, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
//                Log.d("homepage_shop1", data)
                var josns = JSONObject(data)
                var jsondata = josns.getJSONObject("data")
                var status = josns.get("status").toString()
                var msg = josns.get("msg").toString()
                if (status == "1") {
                    var listdata = jsondata.getJSONArray("banner")
                    for (i in 0 until listdata.length()) {
                        var itemlist = listdata.getJSONObject(i)
                        var id = itemlist.get("id").toString()
                        var style = itemlist.get("style").toString()
                        var price = itemlist.get("price").toString()
                        var name = itemlist.get("name").toString()
                        var Integral = itemlist.get("Integral").toString()
                        var url = itemlist.get("url").toString()
                        var urlid = itemlist.get("urlid").toString()
                        var imgs = itemlist.get("imgs").toString()
                        var itembean = Home_shopBean.DataBean.BannerBean(id, style, price, Integral, url, urlid, imgs, name)
                        itemslist.add(itembean)
                    }
                    var databean = Home_shopBean.DataBean(itemslist)
                    var bean = Home_shopBean(status, msg, databean)
                    mView.gethomeshopdata_success(1, bean)
                    mView.dissmissloading()
                } else {
                    mView.dissmissloading()
                    mView.gethomeshopdata_error(1, msg)
                }

            }

            override fun fail(request: Request?, e: Exception?) {
                mView.dissmissloading()
                mView.gethomeshopdata_error(1, e!!.message.toString())
            }
        }, null)
//        mSubscription = mApi!!.post_homeshop1()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<Home_shopBean>() {
//                    override fun onNext(t: Home_shopBean?) {
//                        if (t!!.status == 1) {
//                            mView.gethomeshopdata_success(1, t)
//                        } else {
//                            mView.gethomeshopdata_error(1, t.msg)
//                        }
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        mView.gethomeshopdata_error(1, e!!.message.toString())
//                    }
//                })
    }

    fun getdata2() {
        mView.showloading()
        var itemslist = arrayListOf<Home_shopBean.DataBean.BannerBean>()
        MyOkHttp.getInstance().get(PathConstant.HOME_SHOP2, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
//                Log.d("homepage_shop2", data)
                var josns = JSONObject(data)
                var jsondata = josns.getJSONObject("data")
                var status = josns.get("status").toString()
                var msg = josns.get("msg").toString()
                if (status == "1") {
                    var listdata = jsondata.getJSONArray("banner")
                    for (i in 0 until listdata.length()) {
                        var itemlist = listdata.getJSONObject(i)
                        var id = itemlist.get("id").toString()
                        var style = itemlist.get("style").toString()
                        var price = itemlist.get("price").toString()
                        var name = itemlist.get("name").toString()
                        var Integral = itemlist.get("Integral").toString()
                        var url = itemlist.get("url").toString()
                        var urlid = itemlist.get("urlid").toString()
                        var imgs = itemlist.get("imgs").toString()
                        var itembean = Home_shopBean.DataBean.BannerBean(id, style, price, Integral, url, urlid, imgs, name)
                        itemslist.add(itembean)
                    }
                    var databean = Home_shopBean.DataBean(itemslist)
                    var bean = Home_shopBean(status, msg, databean)
                    mView.gethomeshopdata_success(2, bean)
                    mView.dissmissloading()
                } else {
                    mView.gethomeshopdata_error(2, msg)
                    mView.dissmissloading()
                }
            }

            override fun fail(request: Request?, e: Exception?) {
                mView.dissmissloading()
                mView.gethomeshopdata_error(2, e!!.message.toString())
            }
        }, null)
//        mSubscription = mApi!!.post_homeshop2()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<Home_shopBean>() {
//                    override fun onNext(t: Home_shopBean?) {
//                        if (t!!.status == "1") {
//                            mView.gethomeshopdata_success(2, t)
//                        } else {
//                            mView.gethomeshopdata_error(2, t.msg)
//                        }
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        mView.gethomeshopdata_error(2, e!!.message.toString())
//                    }
//                })
    }

    fun getdata3() {
        mView.showloading()
        var itemslist = arrayListOf<Home_shopBean.DataBean.BannerBean>()
        MyOkHttp.getInstance().get(PathConstant.HOME_SHOP3, object : MyOkHttp.RequestCallBack {
            override fun success(data: String?) {
//                Log.d("homepage_shop3", data)
                var josns = JSONObject(data)
                var jsondata = josns.getJSONObject("data")
                var status = josns.get("status").toString()
                var msg = josns.get("msg").toString()
                if (status == "1") {
                    var listdata = jsondata.getJSONArray("banner")
                    for (i in 0 until listdata.length()) {
                        var itemlist = listdata.getJSONObject(i)
                        var id = itemlist.get("id").toString()
                        var style = itemlist.get("style").toString()
                        var price = itemlist.get("price").toString()
                        var name = itemlist.get("name").toString()
                        var Integral = itemlist.get("Integral").toString()
                        var url = itemlist.get("url").toString()
                        var urlid = itemlist.get("urlid").toString()
                        var imgs = itemlist.get("imgs").toString()
                        var itembean = Home_shopBean.DataBean.BannerBean(id, style, price, Integral, url, urlid, imgs, name)
                        itemslist.add(itembean)
                    }
                    var databean = Home_shopBean.DataBean(itemslist)
                    var bean = Home_shopBean(status, msg, databean)
                    mView.gethomeshopdata_success(3, bean)
                    mView.dissmissloading()
                } else {
                    mView.gethomeshopdata_error(3, msg)
                    mView.dissmissloading()
                }
            }

            override fun fail(request: Request?, e: Exception?) {
                mView.dissmissloading()
                mView.gethomeshopdata_error(3, e!!.message.toString())
            }
        }, null)
//        mSubscription = mApi!!.post_homeshop3()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<Home_shopBean>() {
//                    override fun onNext(t: Home_shopBean?) {
//                        if (t!!.status == "1") {
//                            mView.gethomeshopdata_success(3, t)
//                        } else {
//                            mView.gethomeshopdata_error(3, t.msg)
//                        }
//                    }
//
//                    override fun onCompleted() {
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        mView.gethomeshopdata_error(3, e!!.message.toString())
//                    }
//                })
    }
}
