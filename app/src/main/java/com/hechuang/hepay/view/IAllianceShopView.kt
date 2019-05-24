package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.*

interface IAllianceShopView : BaseView {


    /**
     * 获取轮播图
     *
     * @param union_top_banner_bean
     */
    fun getbanner_data(union_top_banner_bean: AllianceShopBannerBean)

    /**
     * 分类
     *
     * @param union_top_classify_bean
     */
    fun getclassify_data(union_top_classify_bean: Union_top_classify_bean)


    /**
     * 获取列表数据出错
     */
    fun getdataerror_list()


    /**
     * 获取列表数据
     */

    fun getlist_data(mlistdata: List<AlianceshopHostShopBean.DataBean.ListBean>)

    /**
     * 获取列表数据失败
     */
    fun getlistdata_error(string: String)

    /**
     * 获取列表商品数据
     */
    fun getgoods_list_success(alliancesShopHotGoodsBean: AlliancesShopHotGoodsBean)

    /**
     * 获取列表商品数据失败
     */
    fun getgoods_list_error(string: String)


    /**
     * 获取新闻信息
     */
    fun getnewlist_success(listdata: NewListBean)
}