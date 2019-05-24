package com.hechuang.hepay.ui.activity

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.jdsjlzx.interfaces.OnLoadMoreListener
import com.github.jdsjlzx.interfaces.OnRefreshListener
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.recyclerview.ProgressStyle
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.AllOrderListAdapter
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.AllorderBean
import com.hechuang.hepay.bean.Temporarydata
import com.hechuang.hepay.persenter.AllOrderPersenter
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IAllOrderView
import kotlinx.android.synthetic.main.activity_allorder.*

class AllOrderAcitivity : BaseAppCompatActivity<AllOrderPersenter>(), IAllOrderView, View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    var num: String? = null
    var page: Int? = 1
    var titletexts: ArrayList<String>? = null
    var orderstatus: String? = null
    var mAdapter: AllOrderListAdapter? = null
    var mLAdapter: LRecyclerViewAdapter? = null
    override fun onLoadMore() {
        page = page!! + 1
        mPersenter!!.getlistdata(num!!, orderstatus!!, page.toString(), false)

    }

    override fun onRefresh() {
        page = 1
        mPersenter!!.getlistdata(num!!, orderstatus!!, page.toString(), true)
    }


    override fun getallorderdataok(listdata: List<AllorderBean.DataBean.ListBean>) {
        if (mAdapter != null) {
            mAdapter!!.notifyDataSetChanged()
        } else {
            mAdapter = AllOrderListAdapter(this, listdata)
            mLAdapter = LRecyclerViewAdapter(mAdapter)
        }
        allorder_recycler.adapter = mLAdapter
        mAdapter!!.setOnItemListener(object : AllOrderListAdapter.OnItemListener {
            override fun OnItemListener(position: Int) {
                var intent = Intent(this@AllOrderAcitivity, OrderInfoActivity::class.java)
                intent.putExtra("orderid", listdata.get(position).innerOrderId)
                startActivity(intent)
//                MyToast.showMsg(listdata.get(position).innerOrderId)
            }
        })
        allorder_recycler.refreshComplete(10)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.allorder_back -> finish()
        }
    }

    override fun initlayout(): Int {
        return R.layout.activity_allorder
    }

    override fun initPersenter() {
        mPersenter = AllOrderPersenter(this, this)
    }

    override fun initView() {
        num = "2"
        orderstatus = "0"
        allorder_back.setOnClickListener(this)
        val mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        allorder_recycler.layoutManager = mLinearLayoutManager
        allorder_recycler.setOnRefreshListener(this)
        allorder_recycler.setOnLoadMoreListener(this)
        //设置头部加载颜色
        allorder_recycler.setHeaderViewColor(R.color.appbar, R.color.black_overlay, android.R.color.white)
//设置底部加载颜色
        allorder_recycler.setFooterViewColor(R.color.appbar, R.color.black_overlay, android.R.color.white)
//设置底部加载文字提示
        allorder_recycler.setFooterViewHint(Temporarydata.recycler_loadinghint, Temporarydata.recycler_nomorehint, Temporarydata.recycler_nonetworkhint)
        allorder_recycler.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader)
        allorder_recycler.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader)
        page = 1
        settitletab()
        mPersenter!!.getlistdata("2", "0", page.toString(), true)
    }

    private fun settitletab() {
        titletexts = ArrayList()
        titletexts!!.add("全部")
        titletexts!!.add("待付款")
        titletexts!!.add("待发货")
        titletexts!!.add("待收货")
        titletexts!!.add("已完成")
        titletexts!!.add("已取消")
        for (i in 0 until titletexts!!.size) {
            allorder_tablayout.addTab(allorder_tablayout.newTab().setText(titletexts!![i]))
        }
        allorder_tablayout.tabMode = TabLayout.MODE_SCROLLABLE
        allorder_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.text) {
                    "全部" -> orderstatus = "0"
                    "待付款" -> orderstatus = "1"
                    "待发货" -> orderstatus = "2"
                    "待收货" -> orderstatus = "3"
                    "已完成" -> orderstatus = "4"
                    "已取消" -> orderstatus = "5"
                }
                page = 1
                mPersenter!!.getlistdata("2", orderstatus!!, page.toString(), true)
            }
        })
    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String?) {
        MyToast.showMsg(msg)
    }
}