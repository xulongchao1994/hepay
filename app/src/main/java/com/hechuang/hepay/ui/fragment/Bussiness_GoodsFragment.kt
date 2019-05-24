package com.hechuang.hepay.ui.fragment

import android.animation.*
import android.content.Intent
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.*
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hechuang.hepay.R
import com.hechuang.hepay.api.ApiFactory
import com.hechuang.hepay.base.BaseFragment
import com.hechuang.hepay.bean.*
import com.hechuang.hepay.persenter.Bussiness_GoodsPersenter
import com.hechuang.hepay.ui.activity.BusinessActivity
import com.hechuang.hepay.ui.activity.LoginActivity
import com.hechuang.hepay.ui.activity.UserWebActivity
import com.hechuang.hepay.util.GlideImageLoader
import com.hechuang.hepay.util.Kontlin_Utils
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.util.Utils
import com.hechuang.hepay.view.IBusiness_GoodsView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.activity_business.*
import kotlinx.android.synthetic.main.activity_htfgoodinfo.*
import kotlinx.android.synthetic.main.fragment_business_goods.*
import kotlinx.android.synthetic.main.popup_bussines_goodsinfo.*

@Suppress("UNUSED_CHANGED_VALUE")
class Bussiness_GoodsFragment : BaseFragment<Bussiness_GoodsPersenter>(), IBusiness_GoodsView {
    var rightlayoutposition = 0
    /**
     * 点击去付款是查询购物车成功
     */
    override fun getgopay(businessClickShoppingBan: BusinessClickShoppingBan) {
        if (businessClickShoppingBan.data.url != null && businessClickShoppingBan.data.url != "") {
            startActivity(Intent(activity, UserWebActivity::class.java).putExtra("web_url",
                    businessClickShoppingBan.data.url))
        } else {
            MyToast.showMsg(businessClickShoppingBan.data.msg)
        }
    }

    /**
     * 点击去付款是查询购物车失败
     */
    override fun getgopay(string: String) {
        MyToast.showMsg(string)
    }

    /**
     * 获取商品详情成功
     * 显示弹出框
     */
    override fun getinfodata_success(goodinfobean: HTFGoodInfoBean) {
        showgoodspopup(goodinfobean)
    }

    /**
     * 获取商品详情失败
     * 弹出提示
     */
    override fun getinfodata_error(msg: String) {
        MyToast.showMsg(msg)
    }

    var allprice = ""
    /***
     * 加入购物车成功
     * 根据不同的状态通知不同的控件显示
     */
    override fun addshopping(businessclickShoppingBean: BusinessClickShoppingBan, holder: BaseViewHolder?, type: Int) {
        payurl = businessclickShoppingBean.data.url
        allprice = businessclickShoppingBean.data.count
        when (type) {
            1 -> {
                addgoods(holder!!)
            }
            2 -> {
                var guigenum = goodsrightbeanlist[guigelayoutposition!!].style[guigepositions!!].pronum.toInt()
                guigenum++
                goodsrightbeanlist[guigelayoutposition!!].style[guigepositions!!].pronum = guigenum.toString()
                allcount++
                goodscount!!.text = guigenum.toString()
                goodspay!!.visibility = View.GONE
                numberlayout!!.visibility = View.VISIBLE
                if (allcount > 0) {
                    textview_car!!.visibility = View.VISIBLE
                    textview_car!!.text = allcount.toString()
                    textview_price!!.text = allprice
                }
                if (goodinfopopup != null && goodinfopopup!!.isShowing) {
                    if (allcount > 0) {
                        goodinfo_count!!.visibility = View.VISIBLE
                        goodinfo_count!!.text = allcount.toString()
                        goodinfo_allprice!!.text = allprice
                        popup_goodinfo_text!!.text = guigenum.toString()
                        popup_goodinfo_reduce!!.visibility = View.VISIBLE
                    }
                    if (businessclickShoppingBean.data.num.toInt() > 0) {
                        goodinfo_allprice!!.text = "共 ¥${businessclickShoppingBean.data.count}"
                    } else {
                        goodinfo_allprice!!.text = "共 ¥ 0.0"
                    }
                }
                addshopping!!.isClickable = true
                textview_car!!.text = allcount.toString()
            }
            3 -> {//商品详情的弹窗加入购物车   弹窗商品数量修改，底部总数量和总价修改    页面总数量总价修改  列表数量修改

                var guigenum = goodsrightbeanlist[rightlayoutposition!!].pronum.toInt()
                guigenum++
                goodsrightbeanlist[rightlayoutposition!!].pronum = guigenum.toString()
                right_adapter!!.notifyDataSetChanged()
                allcount++
                popup_goodinfo_text!!.text = guigenum.toString()
                if (allcount > 0) {
                    textview_car!!.visibility = View.VISIBLE
                    textview_car!!.text = allcount.toString()
                    textview_price!!.text = allprice

                    //弹窗的购物车控件

                    popup_goodinfo_reduce!!.visibility = View.VISIBLE
                    goodinfo_count!!.visibility = View.VISIBLE
                    goodinfo_count!!.text = allcount.toString()
                    goodinfo_allprice!!.text = allprice
                    popup_goodinfo_text!!.text = guigenum.toString()
                }

                textview_car!!.text = allcount.toString()
                if (businessclickShoppingBean.data.num.toInt() > 0) {
                    goodinfo_allprice!!.text = "共 ¥${businessclickShoppingBean.data.count}"
                } else {
                    goodinfo_allprice!!.text = "共 ¥ 0.0"
                }
            }
            else -> {
            }

        }
        if (businessclickShoppingBean.data.num.toInt() > 0) {
            textview_price!!.text = "共 ¥${businessclickShoppingBean.data.count}"
        } else {
            textview_price!!.text = "共 ¥ 0.0"
        }
//        Log.d("addshopping", goodsrightbeanlist.toString())
    }

    /**
     * 删除购物车商品成功
     * 根据不同的状态判断控件不同的显示
     */
    override fun redrceshopping(businessclickShoppingBean: BusinessClickShoppingBan, holder: BaseViewHolder?, type: Int) {
        if (businessclickShoppingBean.data.count == null) {
            allprice = businessclickShoppingBean.data.count
        } else {
            allprice = "0.0"
        }
        if (businessclickShoppingBean.data.url == null) {
            payurl = ""
        } else {
            payurl = businessclickShoppingBean.data.url
        }

        when (type) {
            1 -> {
                reducegoods(holder!!)
            }
            2 -> {
                var guigenum = goodsrightbeanlist[guigelayoutposition!!].style[guigepositions!!].pronum.toInt()
                guigenum--
                goodsrightbeanlist[guigelayoutposition!!].style[guigepositions!!].pronum = guigenum.toString()
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
                    textview_car!!.visibility = View.GONE
                    textview_price!!.text = "共 ¥0.0"
                    payurl = ""
                }
                reduceshopping!!.isClickable = true
//                if (businessclickShoppingBean.data.count == null) {
//                    allcount = 0
//                } else {
//                    allcount = businessclickShoppingBean.data.count.toInt()
//                }
                textview_car!!.text = allcount.toString()
                if (goodinfopopup != null && goodinfopopup!!.isShowing) {
                    if (allcount <= 0) {
                        goodinfo_count!!.visibility = View.GONE
                        goodinfo_count!!.text = allcount.toString()
                        goodinfo_allprice!!.text = "共 ¥0.0"
                        popup_goodinfo_text!!.text = ""
                        popup_goodinfo_reduce!!.visibility = View.GONE
                    }
                    if (businessclickShoppingBean.data.num.toInt() > 0) {
                        goodinfo_allprice!!.text = "共 ¥${businessclickShoppingBean.data.count}"
                    } else {
                        goodinfo_allprice!!.text = "共 ¥ 0.0"
                    }
                }
            }
            3 -> {//商品详情的弹窗加入购物车   弹窗商品数量修改，底部总数量和总价修改    页面总数量总价修改  列表数量修改
                var guigenum = goodsrightbeanlist[rightlayoutposition!!].pronum.toInt()
                guigenum--
                goodsrightbeanlist[rightlayoutposition!!].pronum = guigenum.toString()
                right_adapter!!.notifyDataSetChanged()
                allcount--
                popup_goodinfo_text!!.text = guigenum.toString()
                if (allcount <= 0) {
                    textview_car!!.visibility = View.GONE
                    textview_price!!.text = "共 ¥0.0"
                    payurl = ""
                }

                textview_car!!.text = allcount.toString()
                if (allcount <= 0) {
                    popup_goodinfo_reduce!!.visibility = View.GONE
                    goodinfo_count!!.visibility = View.GONE
//                    goodinfo_allprice!!.text = "共 ¥0.0"
                } else {
                    goodinfo_count!!.text = allcount.toString()
                }
                if (businessclickShoppingBean.data.num.toInt() > 0) {
                    goodinfo_allprice!!.text = "共 ¥${businessclickShoppingBean.data.count}"
                } else {
                    goodinfo_allprice!!.text = "共 ¥ 0.0"
                }
            }
            else -> {
            }
        }
        if (businessclickShoppingBean.data.num.toInt() > 0) {
            textview_price!!.text = "共 ¥${businessclickShoppingBean.data.count}"
        } else {
            textview_price!!.text = "共 ¥ 0.0"
        }
//        if (goodinfopopup != null && goodinfopopup!!.isShowing) {
//            if (businessclickShoppingBean.data.num.toInt() > 0) {
//                goodinfo_allprice!!.text = "共 ¥${businessclickShoppingBean.data.count}"
//            } else {
//                goodinfo_allprice!!.text = "共 ¥ 0.0"
//            }
//        }
    }

    /**
     * 购物车添加错误
     * 弹出提示
     */
    override fun addshopping(string: String) {
        MyToast.showMsg(string)
    }

    /**
     * 购物车减少错误
     * 弹出提示
     */
    override fun redrceshopping(string: String) {
        MyToast.showMsg(string)
    }

    /**
     * activity中调用方法
     * 修改总数量和 根据proid通知列表更新商品显示的加入购物车的数量
     */
    fun clickshopingcar(allCount: Int, proid: String, num: String) {
        this.allcount = allCount
        if (allcount == 0) {
            for (i in 0 until goodsrightbeanlist.size) {
                if (goodsrightbeanlist[i].style != null && goodsrightbeanlist[i].style.size > 0) {
                    for (j in 0 until goodsrightbeanlist[i].style.size) {
                        goodsrightbeanlist[i].style[j].pronum = "0"
                    }
                } else {
                    goodsrightbeanlist[i].pronum = "0"
                }
            }
        } else {
            for (i in 0 until goodsrightbeanlist.size) {
//                Log.d("bussiness", goodsrightbeanlist[i].proid + "  " + proid)
                if (goodsrightbeanlist[i].proid == proid) {
                    goodsrightbeanlist[i].pronum = num
                }
            }
        }
//        mPersenter.getrightdata(shopid, liftid)
//        if (right_adapter != null) {
        right_adapter!!.notifyDataSetChanged()
//        }
    }

    var allcount = 0
    var payurl = ""
    /**
     * 获取左边分类数据成功
     * 设置列表数据
     */
    override fun getliftdatasuccess(business_Goods_LiftBean: Business_Goods_LiftBean, isflast: Boolean) {
        lift_adapter!!.setNewData(business_Goods_LiftBean.data.list)
        lift_adapter!!.setOnItemClickListener { adapter, view, position ->
            for (i in 0 until business_Goods_LiftBean.data.list.size) {
                business_Goods_LiftBean.data.list[i].isclick = i == position
            }
            adapter.notifyDataSetChanged()
            liftid = business_Goods_LiftBean.data.list[position].id
            mPersenter!!.getrightdata(shopid, liftid)
        }
        if (isflast) {
            liftid = business_Goods_LiftBean.data.list[0].id
            mPersenter.getrightdata(shopid, liftid)
        }
    }

    /**
     * 获取左边数据失败
     */
    override fun getliftdataerror(string: String) {
        MyToast.showMsg(string)
    }

    var goodsrightbeanlist = arrayListOf<Business_Goods_RightBean.DataBean.ListBean>()
    /**
     * 获取右边商品数据成功
     * 设置列表数据
     */
    override fun getrightdatasuccess(business_Goods_RightBean: Business_Goods_RightBean) {
        if (business_Goods_RightBean.data.list != null) {
            if (goodsrightbeanlist.size > 0) {
                goodsrightbeanlist.clear()
            }
            goodsrightbeanlist.addAll(business_Goods_RightBean.data.list)
            right_adapter!!.setNewData(goodsrightbeanlist)
        } else {
            MyToast.showMsg("暂时没有商品")
        }


    }

    var goodinfo_count: TextView? = null
    var goodinfo_allprice: TextView? = null
    var goodinfo_gopay: Button? = null
    var popup_goodinfo_text: TextView? = null
    var goodinfo_shopping: ImageView? = null
    var goodinfopopup: PopupWindow? = null
    var popup_goodinfo_reduce: ImageView? = null
    /**
     * 弹出商品详情弹窗
     */
    private fun showgoodspopup(goodinfobean: HTFGoodInfoBean) {
        if (goodinfopopup != null && goodinfopopup!!.isShowing) {
            return return
        }
        var view = LayoutInflater.from(activity).inflate(R.layout.popup_bussines_goodsinfo, null)
        goodinfopopup = PopupWindow(activity)
        goodinfopopup!!.contentView = view
        goodinfopopup!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        goodinfopopup!!.height = ViewGroup.LayoutParams.MATCH_PARENT

        goodinfopopup!!.animationStyle = R.style.PopupWindowAnimation
        goodinfopopup!!.setBackgroundDrawable(BitmapDrawable())
        goodinfopopup!!.isFocusable = true
        goodinfopopup!!.isOutsideTouchable = true
        var popuptext = view.findViewById<TextView>(R.id.popup_goodinfo_name)
        popuptext.text = goodinfobean.data.proname
        var popupprice = view.findViewById<TextView>(R.id.popup_goodinfo_price)
        var popup_number_layout = view.findViewById<LinearLayout>(R.id.popup_goodinfo_number_layout)
        popup_goodinfo_reduce = view.findViewById<ImageView>(R.id.popup_goodinfo_number_reduce)
        popup_goodinfo_text = view.findViewById<TextView>(R.id.popup_goodinfo_number_text)
        var popup_goodinfo_add = view.findViewById<ImageView>(R.id.popup_goodinfo_number_add)
        var popup_guige_bt = view.findViewById<Button>(R.id.popup_goodinfo_guige_bt)
        var popup_info_text = view.findViewById<TextView>(R.id.popup_goodinfo_info)
        var popup_goodinfo_banner = view.findViewById<ImageView>(R.id.popup_goodinfo_banner)
        var popup_layout = view.findViewById<CoordinatorLayout>(R.id.popup_goodinfo_layout)
        var popup_gopay = view.findViewById<Button>(R.id.tv_shopping_cart_pay)
        var popup_finah = view.findViewById<ImageView>(R.id.popup_goodinfo_clocr)
        var popup_context = view.findViewById<WebView>(R.id.popup_goodinfo_context)

        var settings = popup_context.settings
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.javaScriptEnabled = true
        settings.displayZoomControls = true

        popup_context.loadDataWithBaseURL(null, " <style type=\"text/css\"> img{ max-width: 100%; height: auto; } </style> ${goodinfobean.data.procontent}", "text/html", "utf-8", null)


        popup_finah.setOnClickListener {
            goodinfopopup!!.dismiss()
        }
        var dispaly = resources.displayMetrics
        var layoutparams = LinearLayout.LayoutParams(dispaly.widthPixels, dispaly.widthPixels / 2)
        popup_goodinfo_banner.layoutParams = layoutparams
        Glide.with(this).load(ApiFactory.HOST + goodinfobean.data.proimg).into(popup_goodinfo_banner)
        popup_info_text.text = goodinfobean.data.description
        popup_guige_bt.setOnClickListener {
            showguige(rightlayoutposition, goodinfobean.data.proname)
        }
        popup_gopay.setOnClickListener {
            goodinfopopup!!.dismiss()
            var bussiness = activity!! as BusinessActivity
            bussiness.gopay()
        }
        popup_layout.setOnClickListener { goodinfopopup!!.dismiss() }
        goodinfo_count = view.findViewById(R.id.tv_shopping_cart_count)
        goodinfo_allprice = view.findViewById(R.id.business_allprice)
        goodinfo_gopay = view.findViewById(R.id.tv_shopping_cart_pay)
        goodinfo_shopping = view.findViewById(R.id.business_shopping)
        goodinfo_shopping!!.setOnClickListener {
            goodinfopopup!!.dismiss()
            var bussingss = activity!! as BusinessActivity
            bussingss.showshoppingpopup()
        }
        if (goodinfobean.data.list.size > 1) {
            popupprice.text = "¥" + goodinfobean.data.list[0].price
            popup_number_layout.visibility = View.GONE
            popup_guige_bt.visibility = View.VISIBLE
        } else {
            popup_number_layout.visibility = View.VISIBLE
            popup_guige_bt.visibility = View.GONE
            if (goodinfobean.data.list.size > 0) {
                popupprice.text = "¥" + goodinfobean.data.list[0].price
                if (goodinfobean.data.list[0].pronum.toInt() > 0) {
                    popup_goodinfo_reduce!!.visibility = View.VISIBLE
                    popup_goodinfo_text!!.text = goodinfobean.data.list[0].pronum
                } else {
                    popup_goodinfo_reduce!!.visibility = View.GONE
                    popup_goodinfo_text!!.text = ""
                }
            }
        }
        popup_goodinfo_reduce!!.setOnClickListener {
            //数量减
            if (UserData.islogin) {
                mPersenter!!.reducegoods_shop(shopid, goodinfobean.data.proid, goodinfobean.data.list[0].styleid, UserData.username, UserData.tokenid, null, 3)
            } else {
                startActivity(Intent(activity!!, LoginActivity::class.java))
            }
        }
        popup_goodinfo_add.setOnClickListener {
            //数量加
            if (UserData.islogin) {
                mPersenter!!.addgoods_shop(shopid, goodinfobean.data.proid, goodinfobean.data.list[0].styleid, UserData.username, UserData.tokenid, null, 3)
            } else {
                startActivity(Intent(activity!!, LoginActivity::class.java))
            }
        }
        if (allcount > 0) {
            goodinfo_count!!.visibility = View.VISIBLE
            goodinfo_count!!.text = allcount.toString()
        } else {
            goodinfo_count!!.visibility = View.GONE
        }
        if (allprice == "") {
            goodinfo_allprice!!.text = ""
        } else {
            goodinfo_allprice!!.text = "共 ¥" + allprice
        }
        goodinfo_gopay!!.setOnClickListener {
            if (UserData.islogin) {
                mPersenter!!.getgopayurl(shopid, UserData.username, UserData.tokenid)
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
//        if (Build.VERSION.SDK_INT > 24) {
//            val rect = Rect()
//            textview_price!!.getGlobalVisibleRect(rect)
//            val h = textview_price!!.resources.displayMetrics.heightPixels - rect.bottom
//            popup.height = h
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        goodinfopopup!!.showAsDropDown(textview_car, Gravity.TOP, 0)
//        }
    }

    /**
     * 获取右边商品错误
     */
    override fun getrightdataerror(string: String) {
        MyToast.showMsg(string)
    }

    override fun initLayout(): Int {
        return R.layout.fragment_business_goods
    }

    var lift_adapter: BaseQuickAdapter<Business_Goods_LiftBean.DataBean.ListBean, BaseViewHolder>? = null
    var right_adapter: BaseQuickAdapter<Business_Goods_RightBean.DataBean.ListBean, BaseViewHolder>? = null
    var textview_price: TextView? = null
    var textview_car: TextView? = null
    var shopid = ""
    var liftid = ""
    override fun initViews(view: View?) {
        shopid = arguments!!.get("shopid").toString()
        allcount = arguments!!.getInt("allcount")
        payurl = arguments!!.get("payurl").toString()
//        Log.d("dfasdfasdfsafas", allcount.toString())
        textview_car = activity!!.findViewById<TextView>(R.id.tv_shopping_cart_count)
        textview_price = activity!!.findViewById<TextView>(R.id.business_allprice)
        if (allcount == 0) {
            textview_car!!.visibility = View.GONE
        } else {
            textview_car!!.text = allcount.toString()
            textview_car!!.visibility = View.VISIBLE
        }
        var layoutmanager = LinearLayoutManager(activity)
        layoutmanager.orientation = LinearLayoutManager.VERTICAL
        business_goods_lift_recycler.layoutManager = layoutmanager
        var liftlayoutmanager = LinearLayoutManager(activity)

        liftlayoutmanager.orientation = LinearLayoutManager.VERTICAL
        business_goods_right_recycler.layoutManager = liftlayoutmanager
        lift_adapter = object : BaseQuickAdapter<Business_Goods_LiftBean.DataBean.ListBean, BaseViewHolder>(R.layout.adapter_business_fragment_lift) {
            override fun convert(helper: BaseViewHolder?, item: Business_Goods_LiftBean.DataBean.ListBean?) {
                helper!!.setText(R.id.business_fragment_lift_item_name, item!!.name)
                        .setTextColor(R.id.business_fragment_lift_item_name,
                                if (item.isclick) (ContextCompat.getColor(activity!!, R.color.main_text)) else (ContextCompat.getColor(activity!!, R.color.black)))
                        .setBackgroundColor(R.id.business_fragment_lift_item_name, if (item.isclick) (ContextCompat.getColor(activity!!, R.color.white)) else (ContextCompat.getColor(activity!!, R.color.background)))
            }
        }
        business_goods_lift_recycler.adapter = lift_adapter
        lift_adapter!!.setEnableLoadMore(false)
        right_adapter = object : BaseQuickAdapter<Business_Goods_RightBean.DataBean.ListBean, BaseViewHolder>(R.layout.adapter_business_fragment_right) {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun convert(helper: BaseViewHolder?, item: Business_Goods_RightBean.DataBean.ListBean?) {
                Glide.with(activity).load(item!!.proimg).error(R.mipmap.hetianpay_ic_round).into(helper!!.getView(R.id.adapter_business_fiagment_goods_right_icon))
                helper.setText(R.id.adapter_business_fiagment_goods_right_name, item.proname)
                        .setText(R.id.adapter_business_fiagment_goods_right_price, item.price)
                        .setText(R.id.adapter_business_fiagment_goods_right_zhekou, item.coupon)
                        .setText(R.id.adapter_business_fiagment_goods_right_count, if (item.pronum == null || item.pronum == "0") "" else item.pronum)
                        .setGone(R.id.adapter_business_fiagment_goods_right_zhekou, item.coupon != null && item.coupon != "")
                if (item.style == null || item.style.size <= 0) {
                    helper.setGone(R.id.adapter_business_fragment_goods_right_guige, false)
                            .setGone(R.id.adapter_business_fragment_goods_right_number, true)
                } else {
                    helper.setGone(R.id.adapter_business_fragment_goods_right_guige, true)
                            .setGone(R.id.adapter_business_fragment_goods_right_number, false)
                }
                /**
                 * 选规格
                 */
                var guigebuttong = helper.getView<Button>(R.id.adapter_business_fragment_goods_right_guige)
                guigebuttong.setOnClickListener {
                    showguige(helper.layoutPosition, item.proname)
                }
                /**
                 * 数量减
                 */
                var reduceimg = helper.getView<ImageView>(R.id.adapter_business_fiagment_goods_right_reduce)
                reduceimg.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        reduceLeft = reduceimg.left
                        reduceimg.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
                reduceimg.setOnClickListener {
                    //此处应该是加入购物车成功后改变数据
                    reduceimg.isClickable = false
                    if (UserData.islogin) {
                        mPersenter!!.reducegoods_shop(shopid, item.proid, item.styleid, UserData.username, UserData.tokenid, helper, 1)
                    } else {
                        startActivity(Intent(activity!!, LoginActivity::class.java))
                    }
                }

                /**
                 * 数量加
                 */
                var addimg = helper.getView<ImageView>(R.id.adapter_business_fiagment_goods_right_add)
                addimg.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        addLeft = addimg.left
                        addimg.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
                addimg.setOnClickListener {
                    addimg.isClickable = false
                    //此处应该修改购物车成功后修改购物车显示的数量
                    if (UserData.islogin) {
                        mPersenter!!.addgoods_shop(shopid, item.proid, item.styleid, UserData.username, UserData.tokenid, helper, 1)
                    } else {
                        startActivity(Intent(activity!!, LoginActivity::class.java))
                    }

                }
                helper.getView<ImageView>(R.id.adapter_business_fiagment_goods_right_icon).setOnClickListener {
                    rightlayoutposition = helper.layoutPosition
                    mPersenter!!.getinfodata(goodsrightbeanlist[rightlayoutposition].proid)
                }

                helper.getView<TextView>(R.id.adapter_business_fiagment_goods_right_name).setOnClickListener {
                    rightlayoutposition = helper.layoutPosition
                    mPersenter!!.getinfodata(goodsrightbeanlist[rightlayoutposition].proid)
                }
                var count = if (item.pronum == null) 0 else item.pronum.toInt()

                reduceimg.visibility = if (count == 0) View.GONE else View.VISIBLE
            }
        }
        business_goods_right_recycler.adapter = right_adapter
        right_adapter!!.setEnableLoadMore(false)
        mPersenter.getliftdata(shopid, true)
    }

    /**
     * 列表加入购物车动画
     */
    private fun addgoods(helper: BaseViewHolder) {
        var position = helper.layoutPosition
        var reduceimg = helper.getView<ImageView>(R.id.adapter_business_fiagment_goods_right_reduce)
        var addimg = helper.getView<ImageView>(R.id.adapter_business_fiagment_goods_right_add)
        var goodsbean = goodsrightbeanlist[position]
        var count = goodsbean.pronum.toInt()
        count++
        allcount++
        if (count == 1) {
            reduceimg.visibility = View.VISIBLE
            animOpen(reduceimg)
        }
        addGoods2CartAnim(addimg)
        helper.setText(R.id.adapter_business_fiagment_goods_right_count, count.toString())
        goodsbean.pronum = count.toString()
        textview_car!!.visibility = View.VISIBLE
        textview_car!!.text = allcount.toString()
        textview_price!!.text = allprice
        reduceimg.isClickable = true
        addimg.isClickable = true
    }

    private fun reducegoods(helper: BaseViewHolder) {
        var position = helper.layoutPosition
        var goodsbean = goodsrightbeanlist[position]
        var count = goodsbean.pronum.toInt()
        count--
        allcount--
        var reduceimg = helper.getView<ImageView>(R.id.adapter_business_fiagment_goods_right_reduce)
        if (count == 0) {
            animClose(reduceimg)
            helper.setText(R.id.adapter_business_fiagment_goods_right_count, "")
        } else if (count < 0) {
            count = 0
        } else {
            helper.setText(R.id.adapter_business_fiagment_goods_right_count, count.toString())
        }
        if (allcount <= 0) {
            textview_car!!.visibility = View.GONE
            textview_price!!.text = "共 ¥0.0"
        } else {
            textview_car!!.visibility = View.VISIBLE
            textview_car!!.text = allcount.toString()
            textview_price!!.text = allprice
        }
        reduceimg.isClickable = true
        goodsbean.pronum = count.toString()

    }

    /**
     * 显示规格弹窗
     */
    var dialog_guige: AlertDialog.Builder? = null
    var goodscount: TextView? = null
    var numberlayout: RelativeLayout? = null
    var goodspay: Button? = null
    var guigepositions = 0
    var guigelayoutposition: Int? = null
    var guigepaylayout: RelativeLayout? = null
    var addshopping: ImageView? = null
    var reduceshopping: ImageView? = null
    /**
     * 列表显示规格弹窗
     */
    private fun showguige(layoutPosition: Int, titlename: String) {
        var styleid = ""
        guigelayoutposition = layoutPosition
        var styleitem = goodsrightbeanlist[layoutPosition].style
        dialog_guige = AlertDialog.Builder(activity!!)
        var view = activity!!.layoutInflater.inflate(R.layout.dialog_business_goodstype, null)
        var goodstype_type = view.findViewById<TagFlowLayout>(R.id.dlalog_business_goodstype_type)
        var goodsprice = view.findViewById<TextView>(R.id.dlalog_business_goodstype_price)
        var goodsname = view.findViewById<TextView>(R.id.dlalog_business_goodstype_goodsname)
        goodspay = view.findViewById(R.id.dlalog_business_goodstype_pay)
        guigepaylayout = view.findViewById(R.id.dlalog_business_goodstype_pay_layout)
        guigepaylayout!!.visibility = View.GONE
        numberlayout = view.findViewById(R.id.dlalog_business_goodstype_number)
        addshopping = view.findViewById(R.id.dlalog_business_goodstype_add)
        reduceshopping = view.findViewById(R.id.dlalog_business_goodstype_reduce)
        goodscount = view.findViewById(R.id.dlalog_business_goodstype_count)
        var finshimg = view.findViewById<ImageView>(R.id.dlalog_business_goodstype_finsh);
        goodspay!!.setOnClickListener {
            //数量加
            if (UserData.islogin) {
                mPersenter.addgoods_shop(shopid, goodsrightbeanlist[layoutPosition].proid, styleid, UserData.username, UserData.tokenid, null, 2)
            } else {
                startActivity(Intent(activity!!, LoginActivity::class.java))
            }
        }
        goodsname.text = titlename
        addshopping!!.setOnClickListener {
            //数量加
            addshopping!!.isClickable = false
            if (UserData.islogin) {
                mPersenter.addgoods_shop(shopid, goodsrightbeanlist[layoutPosition].proid, styleid, UserData.username, UserData.tokenid, null, 2)
            } else {
                startActivity(Intent(activity!!, LoginActivity::class.java))
            }
        }
        reduceshopping!!.setOnClickListener {
            reduceshopping!!.isClickable = false
            //数量减
            if (UserData.islogin)
                mPersenter.reducegoods_shop(shopid, goodsrightbeanlist[layoutPosition].proid, styleid, UserData.username, UserData.tokenid, null, 2)
            else
                startActivity(Intent(activity!!, LoginActivity::class.java))
        }
        goodstype_type.adapter = object : TagAdapter<Business_Goods_RightBean.DataBean.ListBean.StyleBean>(styleitem) {
            override fun getView(parent: FlowLayout?, position: Int, t: Business_Goods_RightBean.DataBean.ListBean.StyleBean?): View {
                var textview = LayoutInflater.from(activity!!).inflate(R.layout.goodsindo_guige_tv, parent, false) as TextView
                textview.text = t!!.stylename
                return textview
            }
        }
        goodstype_type.setOnSelectListener {
            if (it.size > 0) {
                guigepaylayout!!.visibility = View.VISIBLE
                for (guigeposition in it) {
                    guigepositions = guigeposition
//                    Log.d("bussiness", styleitem[guigepositions!!].stylename)
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
        Kontlin_Utils.setdialotwidth(activity!!, dia)
        finshimg.setOnClickListener {
            if (dia != null && dia.isShowing)
                dia.dismiss()
        }
    }

    override fun initPersenter() {
        mPersenter = Bussiness_GoodsPersenter(this, activity!!)

    }

    override fun showloading() {

    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }

    private val TIME: Long = 300   // 动画的执行时间
    var reduceLeft = 0
    var addLeft = 0
    /**
     * 开启动画
     */
    fun animOpen(imageView: ImageView) {
        val animatorSet = AnimatorSet()
        val translationAnim = ObjectAnimator.ofFloat(imageView, "translationX", (addLeft - reduceLeft).toFloat(), 0f)
        val rotationAnim = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 180f)
        animatorSet.play(translationAnim).with(rotationAnim)
        animatorSet.setDuration(TIME).start()
    }

    fun animClose(imageView: ImageView) {
        val animatorSet = AnimatorSet()
        val translationAnim = ObjectAnimator.ofFloat(imageView, "translationX", 0f, (addLeft - reduceLeft).toFloat())
        val rotationAnim = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 180f)
        animatorSet.play(translationAnim).with(rotationAnim)
        animatorSet.setDuration(TIME).start()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // TODO: 2018/5/19 因为属性动画会改变位置,所以当结束的时候,要回退的到原来的位置,同时用补间动画的位移不好控制
                val oa = ObjectAnimator.ofFloat(imageView, "translationX", (addLeft - reduceLeft).toFloat(), 0f)
                oa.setDuration(0)
                oa.start()
                imageView.visibility = View.GONE
            }
        })
    }

    private val mCurrentPosition = FloatArray(2)
    /**
     * 贝塞尔曲线动画
     *
     * @param goodsImageView
     */
    fun addGoods2CartAnim(goodsImageView: ImageView) {
        val goods = ImageView(activity)
        goods.setImageResource(R.drawable.icon_goods_add)
        val size = Utils.dip2px(activity, 24f)
        val lp = ViewGroup.LayoutParams(size, size)
        goods.layoutParams = lp
        fragment_business_goods_relative.addView(goods)
        // 控制点的位置
        val recyclerLocation = IntArray(2)
        fragment_business_goods_relative.getLocationInWindow(recyclerLocation)
        // 加入点的位置起始点
        val startLocation = IntArray(2)
        goodsImageView.getLocationInWindow(startLocation)
        // 购物车的位置终点
        val endLocation = IntArray(2)
        activity!!.findViewById<ImageView>(R.id.business_shopping).getLocationInWindow(endLocation)
        // TODO: 2018/5/21 0021 考虑到状态栏的问题，不然会往下偏移状态栏的高度
        val startX = startLocation[0] - recyclerLocation[0]
        val startY = startLocation[1] - recyclerLocation[1]
        // TODO: 2018/5/21 0021 和上面一样
        val endX = endLocation[0] - recyclerLocation[0]
        val endY = endLocation[1] - recyclerLocation[1]
        // 开始绘制贝塞尔曲线
        val path = Path()
        // 移动到起始点位置(即贝塞尔曲线的起点)
        path.moveTo(startX.toFloat(), startY.toFloat())
        // 使用二阶贝塞尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo(((startX + endX) / 2).toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
        // mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，如果是true，path会形成一个闭环
        val pathMeasure = PathMeasure(path, false)
        // 属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）f
        val valueAnimator = ValueAnimator.ofFloat(0f, pathMeasure.length)
        // 计算距离
        val tempX = Math.abs(startX - endX)
        val tempY = Math.abs(startY - endY)
        // 根据距离计算时间
        val time = (0.5 * Math.sqrt((tempX * tempX + tempY * tempY).toDouble())).toInt()
        valueAnimator.duration = time.toLong()
        valueAnimator.start()
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            // 当插值计算进行时，获取中间的每个值，
            // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
            val value = animation.animatedValue as Float
            // 获取当前点坐标封装到mCurrentPosition
            // boolean getPosTan(float distance, float[] pos, float[] tan) ：
            // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
            // mCurrentPosition此时就是中间距离点的坐标值
            pathMeasure.getPosTan(value, mCurrentPosition, null)
            // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
            goods.translationX = mCurrentPosition[0]
            goods.translationY = mCurrentPosition[1]
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // 移除图片
                fragment_business_goods_relative.removeView(goods)
                // 购物车数量增加
//                mTvShoppingCartCount.setText(allCount.toString())
            }
        })
    }


}