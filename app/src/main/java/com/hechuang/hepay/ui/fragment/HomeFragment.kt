package com.hechuang.hepay.ui.fragment

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseFragment
import com.hechuang.hepay.bean.Home_goods_oneBean
import com.hechuang.hepay.bean.Home_goods_twoBean
import com.hechuang.hepay.bean.Home_goods_twolistBean
import com.hechuang.hepay.persenter.Home_GoodsPersenter
import com.hechuang.hepay.view.IHome_GoodsView
import kotlinx.android.synthetic.main.fragment_homegoods.*

/**
 * 首页（首页
 * Created by Android_xu on 2018/3/19.
 */

class HomeFragment : BaseFragment<Home_GoodsPersenter>(), IHome_GoodsView, View.OnClickListener {
    var onelist = arrayListOf<Home_goods_oneBean.DataBean.ListBean>()
    override fun getonedataseccess(dataBean: Home_goods_oneBean, unmber: Int) {
        if (unmber == 1) {
            mPersenter.gettwodata(dataBean.data.list[0].id, 0)
        }
        onelist.addAll(dataBean.data.list)
        adapter_one!!.setNewData(onelist)
        adapter_one!!.setOnItemClickListener { adapter, view, position ->
            if (!onelist[position].ischeck) {
                mPersenter.gettwodata(onelist[position].id, position)
            }
        }
    }

    override fun getonedatafailure(str: String) {
    }

    override fun gettwodataseccess(databean: Home_goods_twoBean, position: Int) {
        for (i in 0 until onelist.size) {
            onelist[position].ischeck = i == position
        }
        adapter_one!!.notifyDataSetChanged()
        listdata.clear()
        for (i in 0 until databean.data.list.size) {
            listdata.add(Home_goods_twolistBean(true, databean.data.list[i].two_name, databean.data.list[i].two_id))
            if (databean.data.list[i].three != null && databean.data.list[i].three.size > 0) {
                for (j in 0 until databean.data.list[i].three.size) {
                    listdata.add(Home_goods_twolistBean(databean.data.list[i].three[j]))
                }
            }
        }
        adapter_two!!.setNewData(listdata)
    }

    override fun gettwodatafailure(str: String) {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
        }
    }

    override fun showloading() {

    }

    override fun dissmissloading() {

    }

    override fun getdataerror(msg: String) {

    }

    override fun initLayout(): Int {
        return R.layout.fragment_homegoods
    }

    var adapter_one: BaseQuickAdapter<Home_goods_oneBean.DataBean.ListBean, BaseViewHolder>? = null
    var adapter_two: BaseSectionQuickAdapter<Home_goods_twolistBean, BaseViewHolder>? = null
    var listdata = arrayListOf<Home_goods_twolistBean>()
    override fun initViews(view: View) {
        val layoutmanager = LinearLayoutManager(activity!!)
        layoutmanager.orientation = LinearLayoutManager.VERTICAL
        home_goods_one.layoutManager = layoutmanager
        val gridlayoutmanager = GridLayoutManager(activity!!, 3)
        home_goods_two.layoutManager = gridlayoutmanager
        adapter_one = object : BaseQuickAdapter<Home_goods_oneBean.DataBean.ListBean, BaseViewHolder>(R.layout.adapter_goodslift) {
            override fun convert(helper: BaseViewHolder?, item: Home_goods_oneBean.DataBean.ListBean?) {
                var text = helper!!.getView<TextView>(R.id.adapter_item_goodslift_name)
                var view = helper!!.getView<View>(R.id.adapter_item_goodslift_view)
                text.text = item!!.name
                if (item.ischeck) {
                    text.setTextColor(ContextCompat.getColor(activity!!, R.color.main_text))
                    view.visibility = View.GONE
                } else {
                    text.setTextColor(ContextCompat.getColor(activity!!, R.color.black))
                    view.visibility = View.VISIBLE
                }
            }
        }
        adapter_one!!.setEnableLoadMore(false)
        home_goods_one.adapter = adapter_one
        adapter_two = object : BaseSectionQuickAdapter<Home_goods_twolistBean, BaseViewHolder>(R.layout.adapter_item_homeright_item, R.layout.adapter_item_homeright_title, listdata) {
            override fun convertHead(helper: BaseViewHolder?, item: Home_goods_twolistBean?) {
                helper!!.setText(R.id.home_goods_item_title_text, item!!.header)
            }

            override fun convert(helper: BaseViewHolder?, item: Home_goods_twolistBean?) {
                helper!!.setText(R.id.home_goods_item_name, item!!.t.name)
                var icon = helper.getView<ImageView>(R.id.home_goods_item_icon)
                Glide.with(activity!!).load(item.t.imgpath).error(R.mipmap.hetianpay_ic_round).into(icon)
            }
        }
        home_goods_two.adapter = adapter_two
        mPersenter.getonedata(1)
    }

    override fun initPersenter() {
        mPersenter = Home_GoodsPersenter(this, activity!!)
    }
}
