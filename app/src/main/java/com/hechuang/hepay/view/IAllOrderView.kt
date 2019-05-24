package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.AllorderBean

interface IAllOrderView : BaseView {
    fun getallorderdataok(listdata: List<AllorderBean.DataBean.ListBean>)
}