package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.BusinessClickShoppingBan
import com.hechuang.hepay.bean.BusinessShoppingBean
import com.hechuang.hepay.bean.HTFGoodInfoBean

interface IHTFGoodInfoView : BaseView {
    /**
     * 获取数据成功
     */
    fun getinfodata_success(goodinfobean: HTFGoodInfoBean)

    /**
     * 获取数据失败
     */
    fun getinfodata_error(msg: String)


    /**
     * 获取购物车列表成功
     */
    fun getshopping(businessShoppingBean: BusinessShoppingBean, type: Boolean)


    /**
     * 获取购物车列表失败
     */
    fun getshopping_error(string: String, tyep: Boolean)


    /**
     * 查询购物车商品成功
     */
    fun getgopay(businessClickShoppingBan: BusinessClickShoppingBan)

    /**
     * 查询购物车商品失败
     */
    fun getgopay(string: String)

    /**
     * 数量加成功
     */
    fun addgoodsuccess(beandata: BusinessClickShoppingBan, type: Int)

    /**
     * 数量加成功
     */
    fun addgooderror(msg: String, type: Int)

    /**
     * 商品减成功
     */
    fun reducegoodsuccess(beandata: BusinessClickShoppingBan, type: Int)

    /**
     * 商品减失败
     */
    fun reducegooderror(msg: String, type: Int)
}