package com.hechuang.hepay.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.LinearLayout
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.BusinessFragmentAdapter
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.*
import com.hechuang.hepay.customview.ShoppingCartDialog
import com.hechuang.hepay.persenter.BusinessPersenter
import com.hechuang.hepay.ui.fragment.Bussiness_GoodsFragment
import com.hechuang.hepay.ui.fragment.Bussiness_InfoFragment
import com.hechuang.hepay.util.Eyes
import com.hechuang.hepay.util.GlideImageLoader
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IBusinessView
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_business.*
import kotlinx.android.synthetic.main.business_goods_shopping.*
import kotlinx.android.synthetic.main.fragment_business_info.*

class BusinessActivity : BaseAppCompatActivity<BusinessPersenter>(), IBusinessView {

    var goodsframgent: Bussiness_GoodsFragment? = null
    var proid = "0"

    var allcount: Int? = null
    var pay_url: String? = null
    var businesshsopping: BusinessShoppingBean? = null
    var shopingcar: ShoppingCartDialog? = null
    var viewadapter: BusinessFragmentAdapter? = null
    var titles = arrayListOf<String>("商品", "商家")
    var fragmentlist = arrayListOf<Fragment>()
    var shopid = ""
    var userid = ""
    override fun getgopay(businessClickShoppingBan: BusinessClickShoppingBan) {
        if (businessClickShoppingBan.data.url != null && businessClickShoppingBan.data.url != "") {
            startActivity(Intent(this@BusinessActivity, UserWebActivity::class.java).putExtra("web_url", businessClickShoppingBan.data.url))
        } else {
            MyToast.showMsg(businessClickShoppingBan.data.msg)
        }
    }

    override fun getgopay(string: String) {
        MyToast.showMsg(string)
    }

    override fun getshopping(businessShoppingBean: BusinessShoppingBean, type: Boolean) {
        businesshsopping = businessShoppingBean
        allcount = businessShoppingBean.data.num
        pay_url = businessShoppingBean.data.url
//        Log.d("bussiness", type.toString())
        if (type) {
            if (businesshsopping!!.data.num <= 0) {
                MyToast.showMsg("购物车空空如也")
                business_allprice.text = "共 ¥ 0.0"
            } else {
                var mGoodsList = arrayListOf<BusinessShoppingBean.DataBean.ListBean>()
                mGoodsList.addAll(businesshsopping!!.data.list)
//                Log.d("bussiness", mGoodsList.size.toString())
                var bundle = Bundle()
                bundle.putString("shopid", userid)
                bundle.putInt("count", businesshsopping!!.data.num)
                bundle.putString("allprice", businesshsopping!!.data.count)
                bundle.putSerializable(ShoppingCartDialog.CART_GOODS, mGoodsList)
                shopingcar = ShoppingCartDialog()
                shopingcar!!.arguments = bundle
                shopingcar!!.show(fragmentManager, "cartGoods")
                shopingcar!!.setCartGoodsDialogListener(object : ShoppingCartDialog.CartGoodsDialogListener {
                    override fun add(allCount: Int, proid: String, num: String, allprice: String) {
//                        Log.d("business_cart_add", allCount.toString())
                        tv_shopping_cart_count.text = allCount.toString()
                        business_allprice.text = "共 ¥ $allprice"
//                        //通知商品列表更新
                        allcount = allCount
                        goodsframgent!!.clickshopingcar(allCount, proid, num)
                    }

                    override fun reduce(allCount: Int, proid: String, num: String, allprice: String) {
                        tv_shopping_cart_count.text = allCount.toString()
//                        Log.d("business_cart_reduce", allCount.toString())
                        if (allCount <= 0) {
                            business_allprice.text = "共 ¥ 0.0"
                        } else {
                            business_allprice.visibility = View.VISIBLE
                            business_allprice.text = "共 ¥ $allprice"
                        }
                        //通知商品列表更新
                        allcount = allCount
                        goodsframgent!!.clickshopingcar(allCount, proid, num)
                    }

                    override fun clear() {
                        tv_shopping_cart_count.visibility = View.GONE
                        allcount = 0
                        business_allprice.text = "共 ¥ 0.0"
                        //通知商品列表更新
                        goodsframgent!!.clickshopingcar(0, "", "")
                        shopingcar!!.dismiss()

                    }

                    override fun gopay() {
                        shopingcar!!.dismiss()
                        if (UserData.islogin) {
                            mPersenter!!.getgopayurl(userid, UserData.username, UserData.tokenid)
                        } else {
                            startActivity(Intent(this@BusinessActivity, LoginActivity::class.java))
                        }
                    }
                })

            }
        } else {
            if (businesshsopping!!.data.num != null) {
                allcount = businesshsopping!!.data.num
                business_allprice.text = "共${businesshsopping!!.data.count}"
            } else {
                allcount = 0
                business_allprice.text = "共 ¥ 0.0"
            }
            pay_url = businesshsopping!!.data.url
            when (proid) {
                "0" -> {//没有商品
                    business_tabs.visibility = View.GONE
                    business_viewPager.visibility = View.GONE
                    business_shopping_cart.visibility = View.GONE
                    business_info.visibility = View.VISIBLE
                    mPersenter!!.getinfo(userid)
                }
                "1" -> {//有商品
                    business_tabs.visibility = View.VISIBLE
                    business_viewPager.visibility = View.VISIBLE
                    business_shopping_cart.visibility = View.VISIBLE
                    business_info.visibility = View.GONE

                    business_nestedScrollView.isFillViewport = true
                    var budile = Bundle()
                    budile.putString("shopid", userid)
                    budile.putInt("allcount", allcount!!)
                    budile.putString("payurl", pay_url)
                    goodsframgent = Bussiness_GoodsFragment()
                    goodsframgent!!.arguments = budile
                    var infoFragment = Bussiness_InfoFragment()
                    infoFragment.arguments = budile
                    fragmentlist.add(goodsframgent!!)
                    fragmentlist.add(infoFragment)
                    var viewadapter = BusinessFragmentAdapter(supportFragmentManager, fragmentlist, titles)
                    business_tabs.setupWithViewPager(business_viewPager)
                    business_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabReselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabSelected(tab: TabLayout.Tab?) {
//                            Log.d("business", tab!!.text.toString())
                            when (tab!!.text.toString()) {
                                "商品" -> {
                                    business_shopping_cart.visibility = View.VISIBLE
                                }
                                "商家" -> {
                                    business_shopping_cart.visibility = View.GONE
                                }
                            }
                        }
                    })
                    business_viewPager.adapter = viewadapter
                    //"15167056", "p820628", "2d6beb20fecb15586efe62c4b8b3979f"
                    business_shopping.setOnClickListener {
                        showshoppingpopup()
                    }
                }
                else -> {//其他 按没有商品算
                }
            }

        }

    }


    fun showshoppingpopup() {
        if (UserData.islogin)
            mPersenter!!.getshopping(userid, UserData.username, UserData.tokenid, true)
        else
            startActivity(Intent(this@BusinessActivity, LoginActivity::class.java))
    }

    override fun getshopping_error(string: String, type: Boolean) {
        allcount = 0
        business_allprice.text = "共 ¥ 0.0"
        tv_shopping_cart_count.text = "0"
        tv_shopping_cart_count.visibility = View.GONE
        pay_url = ""
        if (!type) {
            when (proid) {
                "0" -> {//没有商品
                    business_tabs.visibility = View.GONE
                    business_viewPager.visibility = View.GONE
                    business_shopping_cart.visibility = View.GONE
                    business_info.visibility = View.VISIBLE
                    mPersenter!!.getinfo(userid)
                }
                "1" -> {//有商品
                    business_tabs.visibility = View.VISIBLE
                    business_viewPager.visibility = View.VISIBLE
                    business_shopping_cart.visibility = View.VISIBLE
                    business_info.visibility = View.GONE
                    business_nestedScrollView.isFillViewport = true
                    var budile = Bundle()
                    budile.putString("shopid", userid)
                    budile.putInt("allcount", allcount!!)
                    budile.putString("payurl", pay_url)
                    goodsframgent = Bussiness_GoodsFragment()
                    goodsframgent!!.arguments = budile
                    var infoFragment = Bussiness_InfoFragment()
                    infoFragment.arguments = budile
                    fragmentlist.add(goodsframgent!!)
                    fragmentlist.add(infoFragment)
                    if (viewadapter == null) {
                        viewadapter = BusinessFragmentAdapter(supportFragmentManager, fragmentlist, titles)
                    }
                    business_tabs.setupWithViewPager(business_viewPager)
                    business_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabReselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabSelected(tab: TabLayout.Tab?) {
//                            Log.d("business", tab!!.text.toString())
                            when (tab!!.text.toString()) {
                                "商品" -> {
                                    business_shopping_cart.visibility = View.VISIBLE
                                }
                                "商家" -> {
                                    business_shopping_cart.visibility = View.GONE
                                }
                            }
                        }
                    })
                    business_viewPager.adapter = viewadapter
                    business_shopping.setOnClickListener {
                        if (UserData.islogin)
                            mPersenter!!.getshopping(userid, UserData.username, UserData.tokenid, true)
                        else
                            startActivity(Intent(this@BusinessActivity, LoginActivity::class.java))
                    }
                }
                else -> {//其他 按没有商品算
                }
            }
        } else {
            if (string == "请先登录！") {
                startActivity(Intent(this@BusinessActivity, LoginActivity::class.java))
            }
        }
    }


    override fun getinfo_success(businessInfoBean: BusinessInfoBean) {
        business_fragment_info_name.text = businessInfoBean.data.list.shopName
        business_fragment_info_name.visibility = View.VISIBLE
        business_fragment_info_phone.setOnClickListener {
            //拨打电话
            call(businessInfoBean.data.list.mobile)
        }
        business_fragment_info_zhekoutext.text = businessInfoBean.data.list.ggfeelv
        business_fragment_info_time.text = businessInfoBean.data.list.address
        business_fragment_info_pay.visibility = View.VISIBLE
        business_fragment_info_address.text = businessInfoBean.data.list.time
        business_fragment_info_pay.setOnClickListener {
            //买单
            if (UserData.islogin) {
                if (businessInfoBean.data.list.url != null && businessInfoBean.data.list.url != "") {
                    startActivity(Intent(this@BusinessActivity, UserWebActivity::class.java).putExtra("web_url", businessInfoBean.data.list.url))
                }
            } else {
                startActivity(Intent(this@BusinessActivity, LoginActivity::class.java))
            }
            finish()
        }
        business_fragment_info_howpeople.text = businessInfoBean.data.list.sale
        business_fragment_info_context.text = businessInfoBean.data.list.shopContent
        tv_title.text = businessInfoBean.data.list.shopName
    }

    private fun call(phone: String) {
        if (Build.VERSION.SDK_INT >= 23) {
            val permmision = ContextCompat.checkSelfPermission(this@BusinessActivity, android.Manifest.permission.CALL_PHONE)
            if (permmision != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), 123)
                return
            } else {
                val intent2 = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent2)
            }
        } else {
            val intent2 = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent2)
        }
    }

    override fun getinfo_error(string: String) {
        MyToast.showMsg("string")
    }

    override fun getbanner_success(bannerBean: BusinessBannerBean) {
        if (bannerBean.data.list == null) {
            MyToast.showMsg(bannerBean.data.msg)
            return
        }
        userid = bannerBean.data.userid
        var imglist = arrayListOf<String>()
        for (i in 0 until bannerBean.data.list.size) {
            imglist.add(bannerBean.data.list[i].shopPhoto)
        }
        business_imageView.isAutoPlay(true)
        //轮播时间
        business_imageView.setDelayTime(3000)
        //banner的动画样式
        business_imageView.setIndicatorGravity(BannerConfig.CENTER)
        business_imageView.setBannerStyle(BannerConfig.NUM_INDICATOR)
        business_imageView.setIndicatorGravity(BannerConfig.RIGHT)
        business_imageView.setImageLoader(GlideImageLoader())
        business_imageView.setImages(imglist)
        business_imageView.start()
        proid = bannerBean.data.prod
        mPersenter!!.getshopping(userid, UserData.username, UserData.tokenid, false)
    }

    override fun getbanner_error(string: String) {
        MyToast.showMsg(string)
        business_tabs.visibility = View.GONE
        business_viewPager.visibility = View.GONE
        business_shopping_cart.visibility = View.GONE
        business_info.visibility = View.VISIBLE
        mPersenter!!.getinfo(userid)
    }

    override fun initlayout(): Int {
        return R.layout.activity_business
    }

    override fun initPersenter() {
        mPersenter = BusinessPersenter(this, this)
    }

    override fun initView() {
        Eyes.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.white))
        shopid = intent.getStringExtra("shopid")
        business_back.setOnClickListener { finish() }
        tv_title.text = intent.getStringExtra("shopname")
        var dispaly = resources.displayMetrics
        var layoutparams = LinearLayout.LayoutParams(dispaly.widthPixels, dispaly.widthPixels / 2)
        business_imageView.layoutParams = layoutparams
        mPersenter!!.getbanner(shopid)
        tv_shopping_cart_pay.setOnClickListener {
            gopay()
//                Log.d("business", goodsframgent!!.getallcount().toString())
//                if (goodsframgent!!.getallcount() <= 0) {
//                    MyToast.showMsg("购物车空空如也")
//                } else {
//                    if (UserData.islogin) {
//                        var payurl = goodsframgent!!.getpayurl()
//                        Log.d("business", payurl)
//                        if (payurl != null && payurl != "") {
//                            startActivity(Intent(this@BusinessActivity, UserWebActivity::class.java).putExtra("web_url", payurl))
//                        }
//                    } else {
//                        startActivity(Intent(this@BusinessActivity, LoginActivity::class.java))
//                    }
//                }
        }
    }

    fun gopay() {
        if (UserData.islogin) {
            mPersenter!!.getgopayurl(userid, UserData.username, UserData.tokenid)
        } else {
            startActivity(Intent(this@BusinessActivity, LoginActivity::class.java))
        }
    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String?) {
    }

    override fun onStop() {
        super.onStop()
        shopingcar = null
    }
}