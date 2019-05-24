package com.hechuang.hepay.ui.activity

import android.content.Intent
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.Classify_rl_adapter
import com.hechuang.hepay.adapter.ViewPagerAdapter
import com.hechuang.hepay.api.PathConstant
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.*
import com.hechuang.hepay.bean.UserData.city
import com.hechuang.hepay.bean.UserData.province
import com.hechuang.hepay.persenter.AllianceShopPersenter
import com.hechuang.hepay.util.Eyes
import com.hechuang.hepay.util.GlideImageLoader
import com.hechuang.hepay.view.IAllianceShopView
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.activity_allianceshop.*

class AllianceShopActivity : BaseAppCompatActivity<AllianceShopPersenter>(), IAllianceShopView, View.OnClickListener {
    var newlist = arrayListOf<String>()
    var imgpath = arrayListOf<String>()
    var mDatas = arrayListOf<Union_top_classify_bean.DataBean.ListBean>()
    /**
     * 总的页数
     */
    var pageCount: Int = 0
    /**
     * 每一页显示的个数
     */
    val pageSize = 10

    /**
     * 当前显示的是第几页
     */
    var curIndex = 0
    var tips = arrayListOf<ImageView>()
    var mPagerList = arrayListOf<View>()
    val UNION_SELECT_CITY = 1003
    override fun getnewlist_success(listdata: NewListBean) {
        for (i in 0 until listdata.data.list.size) {
            newlist.add(listdata.data.list[i].title)
        }
        rtvv_alliancesshop_vertica.setTextList(newlist)
        rtvv_alliancesshop_vertica.setText(15f, 5, -0xffffff)
        rtvv_alliancesshop_vertica.setTextStillTime(3000)//设置停留时长间隔
        rtvv_alliancesshop_vertica.setAnimTime(300)//设置进入和退出的时间间隔
        rtvv_alliancesshop_vertica.setOnItemClickListener {
            var intent = Intent(this@AllianceShopActivity, NoticeActivity::class.java)
            intent.putExtra("id", listdata.data.list[it].id)
            startActivity(intent)
        }
        rtvv_alliancesshop_vertica.startAutoScroll()
    }

    override fun getlistdata_error(string: String) {
    }

    override fun getgoods_list_success(alliancesShopHotGoodsBean: AlliancesShopHotGoodsBean) {
        var hotgoods_adapter = object : BaseQuickAdapter<AlliancesShopHotGoodsBean.DataBean.ListBean, BaseViewHolder>(R.layout.adapter_alliancesshop_hotgoods_item) {
            override fun convert(helper: BaseViewHolder?, item: AlliancesShopHotGoodsBean.DataBean.ListBean?) {
                Glide.with(this@AllianceShopActivity).load(item!!.proimg).error(R.mipmap.hetianpay_ic_round).into(helper!!.getView(R.id.alliancesshop_hotgoods_item_icon))
                helper.setText(R.id.alliancesshop_hotgoods_item_name, item.proname)
                        .setText(R.id.alliancesshop_hotgoods_item_price, item.price)
                        .setText(R.id.allianceshop_hotgoods_item_zhekou, item.coupon)
                        .setText(R.id.alliancesshop_hotgoods_item_shichangprice, item.marketprice)
                var shichangjia = helper.getView<TextView>(R.id.alliancesshop_hotgoods_item_shichangprice)
                shichangjia.text = item.marketprice
                shichangjia.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                helper.setGone(R.id.allianceshop_hotgoods_item_zhekou, item.coupon != null && item.coupon != "")
            }
        }
        hotgoods_adapter.setOnItemClickListener { adapter, view, position ->
            if (alliancesShopHotGoodsBean.data.list[position].url != null && alliancesShopHotGoodsBean.data.list[position].url != "") {
                startActivity(Intent(this@AllianceShopActivity, UserWebActivity::class.java).putExtra("web_url", alliancesShopHotGoodsBean.data.list[position].url))
                finish()
            }
        }
        hotgoods_adapter.setEnableLoadMore(false)
        alliancesshop_hotgoods.adapter = hotgoods_adapter
        hotgoods_adapter.setNewData(alliancesShopHotGoodsBean.data.list)

    }

    override fun getgoods_list_error(string: String) {
    }

    override fun getlist_data(mlistdata: List<AlianceshopHostShopBean.DataBean.ListBean>) {
        var hotshop_adapter = object : BaseQuickAdapter<AlianceshopHostShopBean.DataBean.ListBean, BaseViewHolder>(R.layout.adapter_alliancesshop_hotshop_item) {
            override fun convert(helper: BaseViewHolder?, item: AlianceshopHostShopBean.DataBean.ListBean?) {
//                Log.d("allianceshop", item!!.shopPhoto)
                Glide.with(this@AllianceShopActivity).load(item!!.shopPhoto).into(helper!!.getView(R.id.alliancesshop_hotshop_item_icon))
                helper.setText(R.id.alliancesshop_hotshop_item_name, item.shopName)
                        .setText(R.id.alliancesshop_hotshop_item_howpeople, if (item.sale == "0") "" else item.sale + "人已光临")
                        .setText(R.id.alliancesshop_hotshop_item_address, item.address)
                        .setText(R.id.alliancesshop_hotshop_item_distance, item.distance)
                        .setText(R.id.alliancesshop_hotshop_item_zhekoutext, item.ggfeelv)
                helper.setGone(R.id.alliancesshop_hotshop_item_zhekoulayout, !(item.ggfeelv == null || item.ggfeelv == ""))
            }
        }
        hotshop_adapter.setEnableLoadMore(false)
        alliancesshop_hotshop.adapter = hotshop_adapter
        hotshop_adapter.setOnItemClickListener { adapter, view, position ->
            startActivity(Intent(this@AllianceShopActivity, BusinessActivity::class.java).putExtra("shopid", mlistdata[position].id)
                    .putExtra("shopname", mlistdata[position].shopName)
            )
        }
        hotshop_adapter.setNewData(mlistdata)
        if (window != null) {
            mPersenter!!.getgoods(UserData.province, UserData.city, UserData.discrict)
        }
    }

    override fun getbanner_data(allianceshopbannerbean: AllianceShopBannerBean) {
        for (i in 0 until allianceshopbannerbean.data.banner.size) {
            imgpath.add(allianceshopbannerbean.data.banner[i].imgs)
        }
        alliancesshop_banner.setImages(imgpath)
        alliancesshop_banner.start()
        alliancesshop_banner.setOnBannerListener(object : OnBannerListener {
            override fun OnBannerClick(position: Int) {
                when (allianceshopbannerbean.data.banner[position].style) {
                    0 -> {
                    }
                    1 -> {
                        val intent = Intent(this@AllianceShopActivity, BusinessActivity::class.java)
                        intent.putExtra("shopid", allianceshopbannerbean.getData().getBanner().get(position).getUrlid())
                        startActivity(intent)
                    }
                    2 -> {
                        val intent1 = Intent(this@AllianceShopActivity, UserWebActivity::class.java)
                        intent1.putExtra("web_url", allianceshopbannerbean.getData().getBanner().get(position).getUrl())
                        startActivity(intent1)
                    }
                }
            }
        })
        if (allianceshopbannerbean.data.ad.notice != null && allianceshopbannerbean.data.ad.notice != "") {
            alliancesshop_gonggao_layout.visibility = View.VISIBLE
            alliancesshop_alwaystextview.initScrollTextView(windowManager, allianceshopbannerbean.data.ad.notice)
            alliancesshop_alwaystextview.starScroll()
            alliancesshop_gonggao_close.setOnClickListener {
                alliancesshop_gonggao_layout.visibility = View.GONE
            }
        } else {
            alliancesshop_gonggao_layout.visibility = View.GONE
        }
        if (allianceshopbannerbean.data.shopimg.imgs != null && allianceshopbannerbean.data.shopimg.imgs != "") {
            alliancesshop_img.visibility = View.VISIBLE
            Glide.with(this).load(allianceshopbannerbean.data.shopimg.imgs).asBitmap().into(alliancesshop_img)
        } else {
            alliancesshop_img.visibility = View.GONE
        }
        mPersenter!!.getclassify()
        mPersenter!!.getnewlist()
    }

    override fun getclassify_data(union_top_classify_bean: Union_top_classify_bean) {
        mDatas.addAll(union_top_classify_bean.data.list)
        setclassifyimg()
        mPersenter!!.getlistdata(UserData.lontitude, UserData.latitude, UserData.province, UserData.city, UserData.discrict)
    }


    private fun setclassifyimg() {
        pageCount = Math.ceil(mDatas.size * 1.0 / pageSize).toInt()
        mPagerList = ArrayList<View>()
        for (i in 0 until pageCount) {
            // 每个页面都是inflate出一个新实例
            val recyclerView = RecyclerView(this)
            val gridLayoutManager = GridLayoutManager(this, pageSize / 2)
            recyclerView.setLayoutManager(gridLayoutManager)
            val adapter = Classify_rl_adapter(this, mDatas, i, pageSize)
            recyclerView.setAdapter(adapter)
            mPagerList.add(recyclerView)
            adapter.setOnitemlatener { p ->
                val pos = p + curIndex * pageSize

                val intent = Intent(this@AllianceShopActivity, UnionShopActivity::class.java)
                intent.putExtra("city", UserData.city)
                intent.putExtra("id", mDatas[pos].id)
                intent.putExtra("province", UserData.province)
                intent.putExtra("discrict", UserData.discrict)
                intent.putExtra("name", mDatas[pos].name)
                startActivity(intent)
//                startActivity(Intent(this@AllianceShopActivity, UserWebActivity::class.java).putExtra("web_url", mDatas[pos].url))
//                finish()
            }
        }
        //设置适配器
        alliancesshop_recycler_classify.adapter = ViewPagerAdapter(mPagerList)
        //设置圆点
        setOvalLayout()
    }

    /**
     * 设置圆点
     */
    fun setOvalLayout() {
//        tips = arrayOfNulls<ImageView>(pageCount)
        for (i in 0 until pageCount) {
            val imageView = ImageView(this)
            tips.add(imageView)
//            tips[i] = imageView
            if (i == 0) {
                Glide.with(this).load(R.drawable.dot_selected).into(tips[i])
            } else {
                Glide.with(this).load(R.drawable.dot_normal).into(tips[i])
            }
            val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams(40,
                    40))
            layoutParams.leftMargin = 5
            layoutParams.rightMargin = 5
            alliancesshop_viewpager_dian.addView(imageView, layoutParams)
        }
        alliancesshop_recycler_classify.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                curIndex = position
                setImageBackground(position)
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

            override fun onPageScrollStateChanged(arg0: Int) {}
        })

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private fun setImageBackground(selectItems: Int) {
        for (i in tips.indices) {
            if (i == selectItems) {
                Glide.with(this)
                        .load(R.drawable.dot_selected)
                        .override(300, 300)
                        .into(tips[i])
            } else {
                Glide.with(this)
                        .load(R.drawable.dot_normal)
                        .override(300, 300)
                        .into(tips[i])
            }
        }
    }

    override fun getdataerror_list() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.alliancesshop_city_layout -> {//跳转到城市存在页面
                var intent = Intent(this@AllianceShopActivity, SelectCityActivity::class.java)
                intent.putExtra("type", 1)
                startActivityForResult(intent, UNION_SELECT_CITY)
            }
            R.id.img_alliancesshop_title_sousuo -> {//跳转到商家搜索页面
                startActivity(Intent(this@AllianceShopActivity, UserWebActivity::class.java).putExtra("web_url", PathConstant.BUSINESS_SOUSUO))
                finish()

            }
            R.id.img_alliancesshop_title_close -> {//关闭页面
                finish()
            }

        }
    }

    override fun initlayout(): Int {
        return R.layout.activity_allianceshop
    }

    override fun initPersenter() {
        mPersenter = AllianceShopPersenter(this, this)
    }

    override fun initView() {
        Eyes.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.white))
        alliancesshop_city_layout.setOnClickListener(this)
        if (UserData.discrict != null && UserData.discrict != "") {
            alliancesshop_city_tv.text = UserData.discrict
        } else {
            alliancesshop_city_tv.text = "选择城市"
        }
        img_alliancesshop_title_sousuo.setOnClickListener(this)
        img_alliancesshop_title_close.setOnClickListener(this)
        alliancesshop_banner.setImageLoader(GlideImageLoader())
        alliancesshop_banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        alliancesshop_banner.setIndicatorGravity(BannerConfig.RIGHT)
        val display = resources.displayMetrics
        val params = RelativeLayout.LayoutParams(display.widthPixels, display.widthPixels / 2)
        alliancesshop_banner.layoutParams = params
        val display2 = resources.displayMetrics
        val params2 = LinearLayout.LayoutParams(display2.widthPixels, display2.widthPixels / 2)
        alliancesshop_img.layoutParams = params2
        var layoutmanager = LinearLayoutManager(this@AllianceShopActivity)
        layoutmanager.orientation = LinearLayoutManager.VERTICAL
        alliancesshop_hotshop.layoutManager = layoutmanager
        alliancesshop_hotshop.isNestedScrollingEnabled = false
        alliancesshop_hotgoods.layoutManager = GridLayoutManager(this@AllianceShopActivity, 2)
        alliancesshop_hotgoods.isNestedScrollingEnabled = false
        mPersenter!!.getbanner()
        alliancesshop_moveshop.setOnClickListener {

            val intent = Intent(this@AllianceShopActivity, UnionShopActivity::class.java)
            intent.putExtra("city", UserData.city)
            intent.putExtra("id", "")
            intent.putExtra("province", UserData.province)
            intent.putExtra("discrict", UserData.discrict)
            intent.putExtra("name", "")
            startActivity(intent)
//            startActivity(Intent(this@AllianceShopActivity, UserWebActivity::class.java).putExtra("web_url", PathConstant.BUSINESS_MOVESHOP))
            finish()
        }
        alliancesshop_classify.setOnClickListener {

            val intent = Intent(this@AllianceShopActivity, UnionShopActivity::class.java)
            intent.putExtra("city", UserData.city)
            intent.putExtra("id", "")
            intent.putExtra("province", UserData.province)
            intent.putExtra("discrict", UserData.discrict)
            intent.putExtra("name", "")
            startActivity(intent)
//            startActivity(Intent(this@AllianceShopActivity, UserWebActivity::class.java).putExtra("web_url", PathConstant.BUSINESS_MOVESHOP))
//            finish()
        }
        alliancesshop_shopping.setOnClickListener {
            startActivity(Intent(this@AllianceShopActivity, UserWebActivity::class.java).putExtra("web_url", PathConstant.BUSINESS_SHOPPING))
            finish()
        }
        alliancesshop_mine.setOnClickListener {
            startActivity(Intent(this@AllianceShopActivity, UserWebActivity::class.java).putExtra("web_url", PathConstant.BUSINESS_MINE))
            finish()
        }
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == UNION_SELECT_CITY) {
            when (resultCode) {
                RESULT_OK -> {
                    if (intent.getStringExtra("district") != null && intent.getStringExtra("district") != "") {
                        UserData.province = intent.getStringExtra("province")
                        UserData.city = intent.getStringExtra("city")
                        UserData.discrict = intent.getStringExtra("district")
                        alliancesshop_city_tv.setText(UserData.discrict)
                        mPersenter!!.getlistdata(UserData.lontitude, UserData.latitude, province, city, UserData.discrict)
                    }
                }
            }
        }
    }

}