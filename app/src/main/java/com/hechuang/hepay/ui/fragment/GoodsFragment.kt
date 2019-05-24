package com.hechuang.hepay.ui.fragment

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.Goods_list_Adapter
import com.hechuang.hepay.api.ApiFactory
import com.hechuang.hepay.base.BaseFragment
import com.hechuang.hepay.base.RvListener
import com.hechuang.hepay.bean.GoodsBean
import com.hechuang.hepay.persenter.GoodsPersenter
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.util.Utils
import com.hechuang.hepay.view.IGoodsView
import kotlinx.android.synthetic.main.fragment_goods.*

/**
 * 首页（商品页面
 * Created by Android_xu on 2018/3/19.
 */
class GoodsFragment : BaseFragment<GoodsPersenter>(), IGoodsView, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.goods_ll -> Utils.startwebactivity(activity, ApiFactory.HOST + "index.php/Home/Product")
        }
    }

    override fun getdata_ok(goodsBean: GoodsBean) {
        var adapter = Goods_list_Adapter(goodsBean.data.list, activity!!, RvListener { id, position ->
            MyToast.showMsg(goodsBean.data.list[position - 1].proname)
        })
        var l_adapter = LRecyclerViewAdapter(adapter)
        goods_lrl.adapter = l_adapter
        goods_lrl.setLoadMoreEnabled(false)
        goods_lrl.setPullRefreshEnabled(false)
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
        MyToast.showMsg(msg)
    }

    override fun initLayout(): Int {
        return R.layout.fragment_goods
    }

    override fun initViews(view: View?) {
        goods_lrl.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mPersenter.getdata()
        goods_ll.setOnClickListener(this)
    }

    override fun initPersenter() {
        mPersenter = GoodsPersenter(this, activity!!)
    }
}