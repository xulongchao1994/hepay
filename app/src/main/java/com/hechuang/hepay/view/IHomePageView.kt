package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.Home_Banner_Bean
import com.hechuang.hepay.bean.Home_ClassifyBean
import com.hechuang.hepay.bean.Home_NewsBean
import com.hechuang.hepay.bean.Home_shopBean

/**
 * Created by Android_xu on 2018/1/15.
 */

interface IHomePageView : BaseView {


    /**
     * 获取首页分区成功
     */
    fun gethome_classify_success(databean: Home_ClassifyBean)

    /**
     * 获取首页分区失败
     */
    fun gethome_classify_failure(str: String)

    /**
     * 获取轮播图成功
     */
    fun gethomebanner_success(databean: Home_Banner_Bean)

    /**
     * 获取轮播图失败
     */
    fun gethomebanner_failure(str: String)

    /**
     * 获取商品成功
     */
    fun gethomeshopdata_success(type: Int, data: Home_shopBean)

    /**
     * 获取商品失败
     */
    fun gethomeshopdata_error(type: Int, str: String)

    /**
     * 获取新闻成功
     */
    fun gethomenews_success(data: Home_NewsBean)

    /**
     * 获取新闻失败
     */
    fun gethomenews_failure(str: String)
}
