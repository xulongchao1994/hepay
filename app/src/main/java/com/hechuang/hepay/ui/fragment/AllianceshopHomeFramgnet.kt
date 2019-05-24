package com.hechuang.hepay.ui.fragment

import android.view.View
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseFragment
import com.hechuang.hepay.persenter.AlliacceShopHomePersenter
import com.hechuang.hepay.view.IAllianceShopHomeView

class AllianceshopHomeFramgnet : BaseFragment<AlliacceShopHomePersenter>(), IAllianceShopHomeView {
    override fun initLayout(): Int {
        return R.layout.fragment_allianceshophome
    }

    override fun initViews(view: View?) {
    }

    override fun initPersenter() {
        mPersenter = AlliacceShopHomePersenter(this, activity!!)
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }

}