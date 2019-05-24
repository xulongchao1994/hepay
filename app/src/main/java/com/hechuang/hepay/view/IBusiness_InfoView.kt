package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.BusinessInfoBean

interface IBusiness_InfoView:BaseView {
    /**
     * 获取详情信息成功
     */
    fun getinfo_success(businessInfoBean: BusinessInfoBean)

    /**
     * 获取详情信息失败
     */
    fun getinfo_error(string: String)
}