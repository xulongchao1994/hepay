package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.Home_goods_oneBean
import com.hechuang.hepay.bean.Home_goods_twoBean

interface IHome_GoodsView : BaseView {
    /**
     * 获取一级分类成功
     */
    fun getonedataseccess(dataBean: Home_goods_oneBean, number: Int)

    /**
     * 获取一级分类失败
     */
    fun getonedatafailure(str: String)

    /**
     * 获取二三级分类成功
     */
    fun gettwodataseccess(databean: Home_goods_twoBean, position: Int)

    /**
     * 获取二三级分类失败
     */
    fun gettwodatafailure(str: String)
}