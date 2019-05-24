package com.hechuang.hepay.ui.activity

import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.persenter.HTF_GoodsListPersenter
import com.hechuang.hepay.view.IHtf_GoodsListView

class HTF_GoodsListActivity : BaseAppCompatActivity<HTF_GoodsListPersenter>(), IHtf_GoodsListView {
    override fun initlayout(): Int {
        return R.layout.activity_htf_goodslist
    }

    override fun initPersenter() {
        mPersenter = HTF_GoodsListPersenter(this, this)
    }

    override fun initView() {
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }
}