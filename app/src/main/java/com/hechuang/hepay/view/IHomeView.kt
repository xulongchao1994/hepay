package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.VersionBean

/**
 * Created by Android_xu on 2018/3/15.
 */

interface IHomeView : BaseView {


    /**
     * 获取版本信息
     */
    fun getversion(versionBean: VersionBean)

}
