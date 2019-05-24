package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.BusinessBannerBean
import com.hechuang.hepay.bean.BusinessClickShoppingBan
import com.hechuang.hepay.bean.BusinessInfoBean
import com.hechuang.hepay.bean.BusinessShoppingBean

interface IBusinessView : BaseView {
    /**
     * 获取轮播图数据成功
     */
    fun getbanner_success(bannerBean: BusinessBannerBean)

    /**
     * 获取轮播图数据失败
     */
    fun getbanner_error(string: String)


    /**
     * 获取详情信息成功
     */
    fun getinfo_success(businessInfoBean: BusinessInfoBean)

    /**
     * 获取详情信息失败
     */
    fun getinfo_error(string: String)

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
    fun  getgopay(string: String)
}