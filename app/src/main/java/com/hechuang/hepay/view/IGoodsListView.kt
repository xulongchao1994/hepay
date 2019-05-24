package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.GoodsListBean

interface IGoodsListView : BaseView {
    /**
     * 获取数据成功
     */
    fun getlistdatasuccess(goodsListBean: GoodsListBean)

}