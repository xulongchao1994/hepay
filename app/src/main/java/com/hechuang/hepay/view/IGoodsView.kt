package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.GoodsBean

/**
 * Created by Android_xu on 2018/3/19.
 */
interface IGoodsView : BaseView {
    fun getdata_ok(goodsBean: GoodsBean)
}