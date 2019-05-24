package com.hechuang.hepay.ui.fragment

import android.view.View
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseFragment
import com.hechuang.hepay.persenter.AllianceShopGoodsPersenter
import com.hechuang.hepay.view.IAllianceShopGoodsView

class AllianceShopGoodsFragment : BaseFragment<AllianceShopGoodsPersenter>(), IAllianceShopGoodsView {
    override fun initLayout(): Int {
        return R.layout.fragment_allianceshopgoods
    }

    override fun initViews(view: View?) {
    }

    override fun initPersenter() {
        mPersenter = AllianceShopGoodsPersenter(this, activity!!)
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }
}