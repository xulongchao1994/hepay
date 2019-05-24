package com.hechuang.hepay.view

import com.chad.library.adapter.base.BaseViewHolder
import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.BusinessClickShoppingBan
import com.hechuang.hepay.bean.Business_Goods_LiftBean
import com.hechuang.hepay.bean.Business_Goods_RightBean
import com.hechuang.hepay.bean.HTFGoodInfoBean

interface IBusiness_GoodsView : BaseView {

    /**
     * 获取分类信息成功
     */
    fun getliftdatasuccess(business_Goods_LiftBean: Business_Goods_LiftBean,isflast:Boolean)

    /**
     * 获取分了信息失败
     */
    fun getliftdataerror(string: String)

    /**
     * 获取分类商品成功
     */
    fun getrightdatasuccess(business_Goods_RightBean: Business_Goods_RightBean)

    /**
     * 获取分类商品失败
     */
    fun getrightdataerror(string: String)

    /**
     * 购物车添加成功
     */
    fun addshopping(businessclickShoppingBean: BusinessClickShoppingBan, holder: BaseViewHolder?, type: Int)

    /**
     * 购物车添加失败
     */
    fun addshopping(string: String)

    /**
     * 购物车删除成功
     */
    fun redrceshopping(businessclickShoppingBean: BusinessClickShoppingBan, holder: BaseViewHolder?, type: Int)

    /**
     * 购物车删除失败
     */
    fun redrceshopping(string: String)

    /**
     * 获取数据成功
     */
    fun getinfodata_success(goodinfobean: HTFGoodInfoBean)
    /**
     * 获取数据失败
     */
    fun  getinfodata_error(msg:String)

    /**
     * 查询购物车商品成功
     */
    fun getgopay(businessClickShoppingBan: BusinessClickShoppingBan)

    /**
     * 查询购物车商品失败
     */
    fun  getgopay(string: String)
}