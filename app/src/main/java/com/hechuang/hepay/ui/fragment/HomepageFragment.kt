package com.hechuang.hepay.ui.fragment

import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.Homepage_H_Adapter
import com.hechuang.hepay.api.ApiFactory
import com.hechuang.hepay.base.BasektFragment
import com.hechuang.hepay.base.RvListener
import com.hechuang.hepay.bean.Home_Banner_Bean
import com.hechuang.hepay.bean.Home_ClassifyBean
import com.hechuang.hepay.bean.Home_NewsBean
import com.hechuang.hepay.bean.Home_shopBean
import com.hechuang.hepay.persenter.HomePagePersenter
import com.hechuang.hepay.ui.activity.GoodsListActivity
import com.hechuang.hepay.ui.activity.NoticeActivity
import com.hechuang.hepay.ui.activity.WebActivity
import com.hechuang.hepay.util.GlideImageLoader
import com.hechuang.hepay.util.GridDividerItemDecoration
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IHomePageView
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_homepage.*

/**
 * Created by Android_xu on 2018/1/15.
 * 首页
 */

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class HomepageFragment : BasektFragment<HomePagePersenter>(), IHomePageView {
    var newlist = arrayListOf<String>()
    override fun gethomenews_success(data: Home_NewsBean) {
        if (activity != null) {
            for (i in 0 until data.data.list.size) {
                newlist.add(data.data.list[i].title)
            }
            rtvv_home_vertica.setTextList(newlist)
            rtvv_home_vertica.setText(15f, 5, resources.getColor(R.color.userorder_text_color))
            rtvv_home_vertica.setTextStillTime(3000)//设置停留时长间隔
            rtvv_home_vertica.setAnimTime(300)//设置进入和退出的时间间隔
            rtvv_home_vertica.startAutoScroll()
            rtvv_home_vertica.setOnItemClickListener {
                var intent = Intent(activity, NoticeActivity::class.java)
                startActivity(intent)
            }

//        if (!activity!!.isDestroyed) {
            dissmissloading()
            mPersenter!!.getdata1()
        }
//        }
    }

    override fun gethomenews_failure(str: String) {

        if (activity != null) {
            dissmissloading()
            mPersenter!!.getdata1()
        }
    }

    override fun gethome_classify_success(databean: Home_ClassifyBean) {
        if (activity != null) {
            var adapter = Homepage_H_Adapter(databean.data, activity!!, object : RvListener {
                override fun onItemListener(id: Int, position: Int) {
//                    startActivity(Intent(activity, GoodsListActivity::class.java))
                    when (databean.data[position].id) {
                        "1" -> {
                            clicklistener(databean.data[position].url)
                        }
                        "2" -> {
                            clicklistener(databean.data[position].url)
                        }
                        "3" -> {
                            clicklistener(databean.data[position].url)
                        }
                        "4" -> {
                            clicklistener(databean.data[position].url)
                        }
                        else -> {
                            clicklistener(databean.data[position].url)
                        }
                    }
                }
            })
            rl_home_recycler1.adapter = adapter

//        if (!activity!!.isDestroyed) {
            dissmissloading()
            mPersenter!!.getnews()
        }
//        }
    }

    override fun gethome_classify_failure(str: String) {

        if (activity != null) {
            dissmissloading()
            mPersenter!!.getnews()
        }
    }


    var imglist = arrayListOf<String>()
    override fun gethomebanner_success(databean: Home_Banner_Bean) {
        if (activity != null) {
            for (i in 0 until databean.data.banner.size) {
                imglist.add(databean.data.banner[i].imgs)
            }
            try {
                banner_home.setOnBannerListener {
                    when (databean.data.banner[it].style) {
                        "1" -> {
                            clicklistener(databean.data.banner[it].url)
                        }
                        "2" -> {
                            clicklistener(databean.data.banner[it].url)
                        }
                        "3" -> {
                            clicklistener(databean.data.banner[it].url)
                        }
                        "4" -> {
                            clicklistener(databean.data.banner[it].url)
                        }
                        else -> {
                            clicklistener(databean.data.banner[it].url)
                        }
                    }
                }
                banner_home.setImages(imglist)
                banner_home.start()
            } catch (e: NullPointerException) {

            }
            mPersenter!!.gethome_classify()
        }
    }

    override fun gethomebanner_failure(str: String) {

        if (activity != null) {
            mPersenter!!.gethome_classify()
            dissmissloading()
        }
    }

    override fun gethomeshopdata_success(type: Int, data: Home_shopBean) {
        if (activity != null) {
            when (type) {
                1 -> {
                    try {
                        Glide.with(activity).load(data.data.banner[0].imgs).override(200, 200).into(img_home_ad_lift)
                        Glide.with(activity).load(data.data.banner[1].imgs).override(200, 200).into(img_home_ad_right_top)
                        Glide.with(activity).load(data.data.banner[2].imgs).override(200, 200).into(img_home_ad_right_button_lift)
                        Glide.with(activity).load(data.data.banner[3].imgs).override(200, 200).into(img_home_ad_right_button_right)
                        img_home_ad_lift.setOnClickListener { clicklistener(data.data.banner[0].url) }
                        img_home_ad_right_top.setOnClickListener { clicklistener(data.data.banner[1].url) }
                        img_home_ad_right_button_lift.setOnClickListener { clicklistener(data.data.banner[2].url) }
                        img_home_ad_right_button_right.setOnClickListener { clicklistener(data.data.banner[3].url) }
                    } catch (e: NullPointerException) {
                    }
//                if (!activity!!.isDestroyed) {
                    mPersenter!!.getdata2()
//                }
                }
                2 -> {
                    var imglist = arrayListOf<Home_shopBean.DataBean.BannerBean>()
                    var datalsit = arrayListOf<Home_shopBean.DataBean.BannerBean>()
                    for (i in 0 until data.data.banner.size) {
                        if (data.data.banner[i].style == "0") {
                            imglist.add(data.data.banner[i])
                        } else {
                            datalsit.add(data.data.banner[i])
                        }
                    }
                    try {
                        Glide.with(activity).load(imglist[0].imgs).override(200, 200).into(img_home_boon_lift_top)
                        Glide.with(activity).load(imglist[1].imgs).override(200, 200).into(img_home_boon_lift_button)
                        img_home_boon_lift_top.setOnClickListener { clicklistener(imglist[0].url) }
                        img_home_boon_lift_button.setOnClickListener { clicklistener(imglist[1].url) }
                        adapter!!.setOnItemClickListener { adapter, view, position ->
                            clicklistener(datalsit[position].url)
                        }
                        adapter!!.setNewData(datalsit)

//                if (!activity!!.isDestroyed) {
                        mPersenter!!.getdata3()
//                }
                    } catch (e: NullPointerException) {
                    }

                }
                3 -> {
                    var imglist = arrayListOf<Home_shopBean.DataBean.BannerBean>()
                    var datalsit = arrayListOf<Home_shopBean.DataBean.BannerBean>()
                    for (i in 0 until data.data.banner.size) {
                        if (data.data.banner[i].style == "0") {
                            imglist.add(data.data.banner[i])
                        } else {
                            datalsit.add(data.data.banner[i])
                        }
                    }
//                Log.d("homepage_shop3", "${imglist.size} ${datalsit.size}")
                    try {

                        Glide.with(activity).load(imglist[0].imgs).override(200, 200).into(img_home_gs_top_lift)
                        Glide.with(activity).load(imglist[1].imgs).override(200, 200).into(img_home_gs_top_right)
                        img_home_gs_top_lift.setOnClickListener { clicklistener(imglist[0].url) }
                        img_home_gs_top_right.setOnClickListener { clicklistener(imglist[1].url) }
                        adapter2!!.setOnItemClickListener { adapter, view, position ->
                            clicklistener(datalsit[position].url)
                        }
                    } catch (e: NullPointerException) {
                    }

//                if (!activity!!.isDestroyed) {
                    adapter2!!.setNewData(datalsit)
//                }
                }
            }
        }
    }

    private fun clicklistener(url: String?) {
        if (url != null && url != "") {
            var intent = Intent(activity, WebActivity::class.java)
            intent.putExtra("web_url", url)
            startActivity(intent)
            activity!!.finish()
        } else {
            MyToast.showMsg("功能即将上线，敬请期待")
        }

    }

    override fun gethomeshopdata_error(type: Int, str: String) {
        if (activity != null) {
            when (type) {
                1 -> {
                    mPersenter!!.getdata2()
//                }
                }
                2 -> {

//                if (!activity!!.isDestroyed) {
                    mPersenter!!.getdata3()
                }
                3 -> {
                }
            }
        }
    }


    override fun showloading() {
        mLoading!!.dismiss()
    }

    override fun dissmissloading() {
        if (mLoading!!.isShowing)
            mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String) {

    }

    override fun initLayout(): Int {
        return R.layout.fragment_homepage
    }

    var adapter: BaseQuickAdapter<Home_shopBean.DataBean.BannerBean, BaseViewHolder>? = null
    var adapter2: BaseQuickAdapter<Home_shopBean.DataBean.BannerBean, BaseViewHolder>? = null
    override fun initViews(view: View) {
//        Log.d("homepage", "initviews")
        banner_home.setImageLoader(GlideImageLoader())
        banner_home.setDelayTime(2000)
        banner_home.isAutoPlay(true)
        banner_home.setIndicatorGravity(BannerConfig.CENTER)
        banner_home.setBannerStyle(BannerConfig.NUM_INDICATOR)
        banner_home.setIndicatorGravity(BannerConfig.RIGHT)
        img_home_title_close.setOnClickListener { activity!!.finish() }
        img_home_title_sousuo.setOnClickListener { clicklistener(ApiFactory.HOST + "index.php/Home/Product/search") }
        tv_home_news_move.setOnClickListener {
            var intent = Intent(activity, NoticeActivity::class.java)
            startActivity(intent)
        }
        val display = resources.displayMetrics
        val params = LinearLayout.LayoutParams(display.widthPixels, display.widthPixels / 2)
        banner_home.layoutParams = params
        rl_home_recycler1.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        var gr_layoutmanager = GridLayoutManager(activity, 3)
        rl_home_boon.layoutManager = gr_layoutmanager
        rl_home_boon.isNestedScrollingEnabled = false
        rl_home_boon.addItemDecoration(GridDividerItemDecoration(2, resources.getColor(R.color.background)))
        val grbutton_layoutmanager = GridLayoutManager(activity, 4)
        rv_home_gs.layoutManager = grbutton_layoutmanager
        rv_home_gs.isNestedScrollingEnabled = false
        rv_home_gs.addItemDecoration(GridDividerItemDecoration(2, resources.getColor(R.color.background)))
        adapter = object : BaseQuickAdapter<Home_shopBean.DataBean.BannerBean, BaseViewHolder>(R.layout.recycler_item_home_top) {
            override fun convert(helper: BaseViewHolder?, item: Home_shopBean.DataBean.BannerBean?) {
                helper!!.setText(R.id.tv_home_item_name, item!!.name)
                helper!!.setText(R.id.tv_home_item_intarge, item!!.integral)
                var img = helper.getView<ImageView>(R.id.img_home_item_top)
                Glide.with(activity).load(item.imgs).override(100, 100).into(img)
            }
        }
        adapter2 = object : BaseQuickAdapter<Home_shopBean.DataBean.BannerBean, BaseViewHolder>(R.layout.recycler_item_home_top) {
            override fun convert(helper: BaseViewHolder?, item: Home_shopBean.DataBean.BannerBean?) {
                helper!!.setText(R.id.tv_home_item_name, item!!.name)
                helper!!.setText(R.id.tv_home_item_intarge, item!!.integral)
                var img = helper.getView<ImageView>(R.id.img_home_item_top)
                Glide.with(activity).load(item.imgs).override(100, 100).into(img)
            }
        }
        rl_home_boon.adapter = adapter
        rv_home_gs.adapter = adapter2
        adapter!!.setEnableLoadMore(false)
        adapter2!!.setEnableLoadMore(false)

//        if (!activity!!.isDestroyed) {
        mPersenter!!.getbannerdata()
//        }
    }

    override fun initPersenter() {
        mPersenter = HomePagePersenter(this, activity!!)
    }

}
