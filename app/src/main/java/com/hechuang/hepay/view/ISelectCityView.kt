package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.SelectCityBean

/**
 * 城市选择
 * Created by Android_xu on 2018/3/24.
 */

interface ISelectCityView : BaseView {
    fun getprovinceok(selectCityBean: SelectCityBean.DataBean)
    fun getcityok(selectCityBean: SelectCityBean.DataBean)
    fun getcountok(selectCityBean: SelectCityBean.DataBean)
}
