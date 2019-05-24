package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.NewListBean
import com.hechuang.hepay.bean.VersionBean

interface IMainView : BaseView {
    /**
     * 获取版本信息
     */

    fun getversion(versionBean: VersionBean)

    /**
     * 获取新闻信息
     */
    fun getnewlist_success(listdata: NewListBean)

}