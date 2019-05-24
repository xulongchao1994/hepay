package com.hechuang.hepay.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebSettings
import android.widget.*
import com.bumptech.glide.Glide
import com.hechuang.hepay.R
import com.hechuang.hepay.api.ApiFactory
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.*
import com.hechuang.hepay.customview.ShoppingCartDialog
import com.hechuang.hepay.persenter.HTFGoodInfoPersenter
import com.hechuang.hepay.util.Kontlin_Utils
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IHTFGoodInfoView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.activity_htfgoodinfo.*

class HTFGoodInfoActivity : BaseAppCompatActivity<HTFGoodInfoPersenter>(), IHTFGoodInfoView {
    override fun addgoodsuccess(beandata: BusinessClickShoppingBan, type: Int) {
        allcount++
        htf_goodinfo_shopping_number.text = allcount.toString()
        htf_goodinfo_shopping_allprice.text = beandata.data.count
        when (type) {
            1 -> {
                htf_goodinfo_paynumber_add.isClickable = true
                htf_goodinfo_paynumber_reduce.visibility = View.VISIBLE
                htf_goodinfo_shopping_number.visibility = View.VISIBLE
            }
            2 -> {
                var guigenum = guigelist[guigepositions!!].pronum.toInt()
                guigenum++
                guigelist[guigepositions!!].pronum = guigenum.toString()
                allcount++
                goodscount!!.text = guigenum.toString()
                goodspay!!.visibility = View.GONE
                numberlayout!!.visibility = View.VISIBLE
                if (allcount > 0) {
                    htf_goodinfo_shopping_number.visibility = View.VISIBLE
                    htf_goodinfo_shopping_number.text = allcount.toString()
//                    textview_price!!.text = allprice
                }
                addshopping!!.isClickable = true
            }
        }
    }

    override fun addgooderror(msg: String, type: Int) {
        htf_goodinfo_paynumber_add.isClickable = true
    }

    override fun reducegoodsuccess(beandata: BusinessClickShoppingBan, type: Int) {
        allcount--
        when (type) {
            1 -> {
                htf_goodinfo_paynumber_reduce.isClickable = true
                if (allcount <= 0) {
                    htf_goodinfo_paynumber_reduce.visibility = View.GONE
                    htf_goodinfo_shopping_number.visibility = View.VISIBLE
                    htf_goodinfo_shopping_number.text = allcount.toString()
                    htf_goodinfo_shopping_allprice.text = beandata.data.count
                } else {
                    htf_goodinfo_paynumber_reduce.visibility = View.GONE
                    htf_goodinfo_shopping_number.visibility = View.GONE
                    htf_goodinfo_shopping_allprice.text = "共 ¥ 0.0"
                }
            }
            2 -> {
                var guigenum = guigelist[guigepositions!!].pronum.toInt()
                guigenum--
                guigelist[guigepositions!!].pronum = guigenum.toString()
                if (guigenum <= 0) {
                    goodspay!!.visibility = View.VISIBLE
                    numberlayout!!.visibility = View.GONE
                } else {
                    goodspay!!.visibility = View.GONE
                    numberlayout!!.visibility = View.VISIBLE
                }
                allcount--
                goodscount!!.text = guigenum.toString()
                if (allcount <= 0) {
                    htf_goodinfo_shopping_number.visibility = View.GONE
                    htf_goodinfo_shopping_allprice.text = "共 ¥0.0"
//                    payurl = ""
                }
                reduceshopping!!.isClickable = true
                htf_goodinfo_shopping_number.text = allcount.toString()
            }
        }
    }

    override fun reducegooderror(msg: String, type: Int) {
        htf_goodinfo_paynumber_reduce.isClickable = true
    }

    override fun getgopay(businessClickShoppingBan: BusinessClickShoppingBan) {
        if (businessClickShoppingBan.data.url != null && businessClickShoppingBan.data.url != "") {
            startActivity(Intent(this@HTFGoodInfoActivity, UserWebActivity::class.java).putExtra("web_url", businessClickShoppingBan.data.url))
        } else {
            MyToast.showMsg(businessClickShoppingBan.data.msg)
        }
    }

    override fun getgopay(string: String) {
        MyToast.showMsg(string)
    }

    var businesshsopping: BusinessShoppingBean? = null
    var shopingcar: ShoppingCartDialog? = null
    var allcount = 0
    var pay_url: String? = null
    override fun getshopping(businessShoppingBean: BusinessShoppingBean, type: Boolean) {
        businesshsopping = businessShoppingBean
        allcount = businessShoppingBean.data.num
        pay_url = businessShoppingBean.data.url
        if (type) {
            if (businesshsopping!!.data.num <= 0) {
                MyToast.showMsg("购物车空空如也")
                htf_goodinfo_shopping_allprice.text = "共 ¥ 0.0"
            } else {
                var mGoodsList = arrayListOf<BusinessShoppingBean.DataBean.ListBean>()
                mGoodsList.addAll(businesshsopping!!.data.list)
                var bundle = Bundle()
                bundle.putString("shopid", shopid)
                bundle.putInt("count", businesshsopping!!.data.num)
                bundle.putString("allprice", businesshsopping!!.data.count)
                bundle.putSerializable(ShoppingCartDialog.CART_GOODS, mGoodsList)
                shopingcar = ShoppingCartDialog()
                shopingcar!!.arguments = bundle
                shopingcar!!.show(fragmentManager, "cartGoods")
                shopingcar!!.setCartGoodsDialogListener(object : ShoppingCartDialog.CartGoodsDialogListener {
                    override fun add(allCount: Int, proid: String, num: String, allprice: String) {
                        htf_goodinfo_shopping_number.text = allCount.toString()
                        htf_goodinfo_shopping_allprice.text = "共 ¥ $allprice"
//                        //通知商品列表更新
                        allcount = allCount
                    }

                    override fun reduce(allCount: Int, proid: String, num: String, allprice: String) {
                        htf_goodinfo_shopping_number.text = allCount.toString()
                        if (allCount <= 0) {
                            htf_goodinfo_shopping_allprice.text = "共 ¥ 0.0"
                        } else {
                            htf_goodinfo_shopping_allprice.visibility = View.VISIBLE
                            htf_goodinfo_shopping_allprice.text = "共 ¥ $allprice"
                        }
                        //通知商品列表更新
                        allcount = allCount
                    }

                    override fun clear() {
                        htf_goodinfo_shopping_number.visibility = View.GONE
                        allcount = 0
                        htf_goodinfo_shopping_allprice.text = "共 ¥ 0.0"
                        //通知商品列表更新
                        shopingcar!!.dismiss()

                    }

                    override fun gopay() {
                        shopingcar!!.dismiss()
                        if (UserData.islogin) {
                            mPersenter!!.getgopayurl(shopid, UserData.username, UserData.tokenid)
                        } else {
                            startActivity(Intent(this@HTFGoodInfoActivity, LoginActivity::class.java))
                        }
                    }
                })

            }
        } else {
            if (businesshsopping!!.data.num != null) {
                allcount = businesshsopping!!.data.num
                htf_goodinfo_shopping_number.visibility = View.VISIBLE
                htf_goodinfo_shopping_allprice.text = "共${businesshsopping!!.data.count}"
                htf_goodinfo_shopping_number.text = allcount.toString()
            } else {
                allcount = 0
                htf_goodinfo_shopping_allprice.text = "共 ¥ 0.0"
                htf_goodinfo_shopping_number.visibility = View.GONE
            }
            pay_url = businesshsopping!!.data.url

        }

    }

    override fun getshopping_error(string: String, type: Boolean) {

    }

    var proid = ""
    var guigelist = arrayListOf<HTFGoodInfoBean.DataBean.ListBean>()
    var shopid = ""
    var styleid = ""
    var beandata_goodinfo: HTFGoodInfoBean? = null
    override fun getinfodata_success(goodinfobean: HTFGoodInfoBean) {
        beandata_goodinfo = goodinfobean
        shopid = goodinfobean.data.supplierid
        styleid = goodinfobean.data.list[0].styleid
        guigelist.addAll(goodinfobean.data.list)
        Glide.with(this).load(ApiFactory.HOST + goodinfobean.data.proimg).into(htf_goodinfo_banner)
        htf_goodinfo_title.text = goodinfobean.data.proname
        htf_goodinfo_proname.text = goodinfobean.data.proname
        if (goodinfobean.data.list != null && goodinfobean.data.list.size > 0) {
            if (goodinfobean.data.list.size > 1) {
                htf_goodinfo_paynumber_layout.visibility = View.GONE
                htf_goodinfo_guige.visibility = View.VISIBLE
            } else {
                htf_goodinfo_paynumber_layout.visibility = View.VISIBLE
                htf_goodinfo_guige.visibility = View.GONE
                if (goodinfobean.data.list[0].pronum.toInt() > 0) {
                    htf_goodinfo_paynumber_reduce.visibility = View.VISIBLE
                } else {
                    htf_goodinfo_paynumber_reduce.visibility = View.GONE
                }
                if (goodinfobean.data.list[0].pronum.toInt() > 0)
                    htf_goodinfo_paynumber.text = goodinfobean.data.list[0].pronum
                else
                    htf_goodinfo_paynumber.text = ""
            }
        }
        htf_goodinfo_description.text = goodinfobean.data.description
        htf_goodinfo_info.loadDataWithBaseURL(null, " <style type=\"text/css\"> img{ max-width: 100%; height: auto; } </style> ${goodinfobean.data.procontent}", "text/html", "utf-8", null)
        mPersenter!!.getshopping(goodinfobean.data.supplierid, UserData.username, UserData.tokenid, false)
        htf_goodinfo_shopping_img.setOnClickListener { mPersenter!!.getshopping(goodinfobean.data.supplierid, UserData.username, UserData.tokenid, true) }
    }

    override fun getinfodata_error(msg: String) {
    }

    override fun initlayout(): Int {
        return R.layout.activity_htfgoodinfo
    }

    override fun initPersenter() {
        mPersenter = HTFGoodInfoPersenter(this, this)
    }

    override fun initView() {
        proid = intent.getStringExtra("proid")
        mPersenter!!.getinfodata(proid)
        htf_goodinfo_guige.setOnClickListener {
            //选择规格加入购物车
            showguige()
        }
        htf_goodinfo_paynumber_add.setOnClickListener {
            //单一规格数量加
            htf_goodinfo_paynumber_add.isClickable = false
            if (UserData.islogin) {
                mPersenter!!.addgoods_shop(shopid, proid, styleid, UserData.username, UserData.tokenid, 1)
            } else {
                startActivity(Intent(this@HTFGoodInfoActivity, LoginActivity::class.java))
            }
        }
        htf_goodinfo_paynumber_reduce.setOnClickListener {
            htf_goodinfo_paynumber_reduce.isClickable = false
            //单一规格数量减
            if (UserData.islogin)
                mPersenter!!.reducegoods_shop(shopid, proid, styleid, UserData.username, UserData.tokenid, 1)
            else
                startActivity(Intent(this@HTFGoodInfoActivity, LoginActivity::class.java))
        }
//        var dispaly = resources.displayMetrics
//        var layoutparams = LinearLayout.LayoutParams(dispaly.widthPixels, dispaly.widthPixels / 2)
//        htf_goodinfo_banner.layoutParams = layoutparams
        var settings = htf_goodinfo_info.settings
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.javaScriptEnabled = true
        settings.displayZoomControls = true
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }

    /**
     * 显示规格弹窗
     */
    var dialog_guige: AlertDialog.Builder? = null
    var goodscount: TextView? = null
    var numberlayout: RelativeLayout? = null
    var goodspay: Button? = null
    var guigepositions: Int? = null
    var guigelayoutposition: Int? = null
    var guigepaylayout: RelativeLayout? = null
    var addshopping: ImageView? = null
    var reduceshopping: ImageView? = null
    private fun showguige() {
        var styleid = ""
        var styleitem = guigelist
        dialog_guige = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.dialog_business_goodstype, null)
        var goodstype_type = view.findViewById<TagFlowLayout>(R.id.dlalog_business_goodstype_type)
        var goodsprice = view.findViewById<TextView>(R.id.dlalog_business_goodstype_price)
        var goodsname = view.findViewById<TextView>(R.id.dlalog_business_goodstype_goodsname)
        goodspay = view.findViewById(R.id.dlalog_business_goodstype_pay)
        guigepaylayout = view.findViewById(R.id.dlalog_business_goodstype_pay_layout)
        guigepaylayout!!.visibility = View.GONE
        numberlayout = view.findViewById(R.id.dlalog_business_goodstype_number)
        addshopping = view.findViewById<ImageView>(R.id.dlalog_business_goodstype_add)
        reduceshopping = view.findViewById<ImageView>(R.id.dlalog_business_goodstype_reduce)
        goodscount = view.findViewById(R.id.dlalog_business_goodstype_count)
        var finshimg = view.findViewById<ImageView>(R.id.dlalog_business_goodstype_finsh);
        goodspay!!.setOnClickListener {
            //数量加
            if (UserData.islogin) {
                mPersenter!!.addgoods_shop(shopid, proid, styleid, UserData.username, UserData.tokenid, 2)
            } else {
                startActivity(Intent(this@HTFGoodInfoActivity, LoginActivity::class.java))
            }
        }
        goodsname.text = "请选择规格"
        addshopping!!.setOnClickListener {
            //数量加
            addshopping!!.isClickable = false
            if (UserData.islogin) {
                mPersenter!!.addgoods_shop(shopid, proid, styleid, UserData.username, UserData.tokenid, 2)
            } else {
                startActivity(Intent(this@HTFGoodInfoActivity, LoginActivity::class.java))
            }
        }
        reduceshopping!!.setOnClickListener {
            reduceshopping!!.isClickable = false
            //数量减
            if (UserData.islogin)
                mPersenter!!.reducegoods_shop(shopid, proid, styleid, UserData.username, UserData.tokenid, 2)
            else
                startActivity(Intent(this@HTFGoodInfoActivity, LoginActivity::class.java))
        }
        goodstype_type.adapter = object : TagAdapter<HTFGoodInfoBean.DataBean.ListBean>(guigelist) {
            override fun getView(parent: FlowLayout?, position: Int, t: HTFGoodInfoBean.DataBean.ListBean?): View {
                var textview = LayoutInflater.from(this@HTFGoodInfoActivity).inflate(R.layout.goodsindo_guige_tv, parent, false) as TextView
                textview.text = t!!.sylename
                return textview
            }
        }
        goodstype_type.setOnSelectListener {
            if (it.size > 0) {
                guigepaylayout!!.visibility = View.VISIBLE
                for (guigeposition in it) {
                    guigepositions = guigeposition
                    goodsprice.text = styleitem[guigepositions!!].price
                    styleid = styleitem[guigepositions!!].styleid
                    goodscount!!.text = styleitem[guigeposition!!].pronum
                    if (styleitem[guigepositions!!].pronum.toInt() <= 0) {
                        goodspay!!.visibility = View.VISIBLE
                        numberlayout!!.visibility = View.GONE
                    } else {
                        goodspay!!.visibility = View.GONE
                        numberlayout!!.visibility = View.VISIBLE
                    }
                }
            } else {
                guigepaylayout!!.visibility = View.GONE
            }
        }

        dialog_guige!!.setView(view)
        var dia = dialog_guige!!.show()
        Kontlin_Utils.setdialotwidth(this, dia)
        finshimg.setOnClickListener {
            if (dia != null && dia.isShowing)
                dia.dismiss()
        }
    }
}