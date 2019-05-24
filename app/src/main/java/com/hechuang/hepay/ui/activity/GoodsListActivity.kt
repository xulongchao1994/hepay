package com.hechuang.hepay.ui.activity

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.GoodsListBean
import com.hechuang.hepay.customview.Goodslist_Classify
import com.hechuang.hepay.customview.Goodslist_Sort
import com.hechuang.hepay.persenter.GoodsListPersenter
import com.hechuang.hepay.util.Utils
import com.hechuang.hepay.view.IGoodsListView
import kotlinx.android.synthetic.main.activity_goodslist.*

/***
 * 商品列表和搜索
 */
class GoodsListActivity : BaseAppCompatActivity<GoodsListPersenter>(), IGoodsListView, View.OnClickListener {
    var page = 1
    var datalist = arrayListOf<GoodsListBean.ListBean>()
    var stagger_adapter: BaseQuickAdapter<GoodsListBean.ListBean, BaseViewHolder>? = null
    var goods_sort: Goodslist_Sort? = null
    var goods_classify: Goodslist_Classify? = null
    var viewarray = arrayListOf<View>()

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.goodslist_search_et -> {
            }
            R.id.goodslist_search_img -> {
            }
            R.id.goodslist_home_img -> {

            }
            R.id.goodslist_conversion -> {
            }
        }
    }

    override fun getlistdatasuccess(goodsListBean: GoodsListBean) {
//        if (page == 1) {
//            datalist.clear()
//        }
//        datalist.addAll(goodsListBean.list)
        stagger_adapter!!.setNewData(goodsListBean.list)
    }

    override fun initlayout(): Int {
        return R.layout.activity_goodslist
    }

    override fun initPersenter() {
        mPersenter = GoodsListPersenter(this, this)
    }

    override fun initView() {
        goodslist_search_et.setOnClickListener(this)
        goodslist_search_img.setOnClickListener(this)
        goodslist_home_img.setOnClickListener(this)
        goodslist_conversion.setOnClickListener(this)
        goods_sort = Goodslist_Sort(this)
        goods_classify = Goodslist_Classify(this)
        var staggergridlayout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        goodslist_recycler.layoutManager = staggergridlayout
        stagger_adapter = object : BaseQuickAdapter<GoodsListBean.ListBean, BaseViewHolder>(R.layout.adapter_goodsitem) {
            override fun convert(helper: BaseViewHolder?, item: GoodsListBean.ListBean?) {
                var icon = helper!!.getView<ImageView>(R.id.goodslist_item_icon)
                //获取图片真正的宽高
                var width = item!!.weight.toFloat()
                var height = item.height.toFloat()
                var conlayoutparams = icon.layoutParams
                var itemwidth = (Utils.getwindow_w(this@GoodsListActivity) / 2).toFloat()
                conlayoutparams.width = itemwidth.toInt()
                var iemhi = (height / width)
                var itemhight = (itemwidth * iemhi).toInt()
                conlayoutparams.height = itemhight
                icon.layoutParams = conlayoutparams
                Glide.with(this@GoodsListActivity).load(item.proimg).error(R.mipmap.hetianpay_ic_round).into(icon)
//                Log.d("获取到的图片宽高", "$width $height $itemwidth $itemhight")
                helper.setText(R.id.goodslist_item_name, item.proname)
                        .setText(R.id.goodslist_item_price, item.price)
            }
        }
        goodslist_recycler.adapter = stagger_adapter
        stagger_adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
            override fun onLoadMoreRequested() {
                page += 1
                mPersenter!!.getlistdata("1", "1", page.toString(), "")
                stagger_adapter!!.loadMoreComplete()
            }
        }, goodslist_recycler)
        goodslist_swipe.setOnRefreshListener {
            page = 1
            mPersenter!!.getlistdata("1", "1", page.toString(), "")
        }
        viewarray.add(goods_sort!!)
        viewarray.add(goods_classify!!)
        var titlearray = arrayListOf<String>()
        titlearray.add("综合排序")
        titlearray.add("商品分类")
        goodslist_et.setValue(titlearray, viewarray)
        goodslist_et.setTitle(goods_sort!!.showText, 0)
        goodslist_et.setTitle(goods_classify!!.showText, 0)
        goodslist_et.setviewlog(goodslist_et!!)
        initvaule()
        mPersenter!!.getlistdata("1", "1", "1", "")
    }

    private fun initvaule() {
        goods_sort!!.setOnSelectListener { distance, showText ->
            var position = getPositon(goods_sort!!)
            goodslist_et.setTitle(showText, position)

        }
        goods_classify!!.setOnSelectListener { distance, showText ->
            goodslist_et.setTitle(showText, getPositon(goods_classify!!))

        }
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }

    private fun getPositon(tView: View): Int {
        for (i in 0 until viewarray.size) {
            if (viewarray[i] == tView) {
                return i
            }
        }
        return -1
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!goodslist_et.onPressBack()) {
            finish()
        }
    }
}