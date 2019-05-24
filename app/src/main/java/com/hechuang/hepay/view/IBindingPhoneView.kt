package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.BindingBean

interface IBindingPhoneView : BaseView {

    /**
     * 绑定成功
     */
    fun Binding_seccess(bindingBean: BindingBean)

    /**
     *
     */
    fun binding_error(msg: String)
}