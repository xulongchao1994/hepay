package com.hechuang.hepay.ui.fragment

import android.view.View
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseFragment
import com.hechuang.hepay.persenter.AllianceShopShopsPersenter
import com.hechuang.hepay.view.IAllIanceShopShopsView

class AllianceshopShopFramgent : BaseFragment<AllianceShopShopsPersenter>(), IAllIanceShopShopsView {
    override fun initLayout(): Int {
        return R.layout.fragment_allianceshopshop
    }

    override fun initViews(view: View?) {
    }

    override fun initPersenter() {
        mPersenter = AllianceShopShopsPersenter(this, activity!!)
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }
}