package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.OrderInfoBean

interface IOrderInfoView : BaseView {
    fun setdata(orderInfoBean: OrderInfoBean)
}