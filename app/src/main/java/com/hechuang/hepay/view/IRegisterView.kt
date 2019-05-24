package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.RegisterBean

interface IRegisterView : BaseView {
    /**
     * 注册成功
     */
    fun register_success(registerBean: RegisterBean)

    /**
     * 注册失败
     */
    fun register_error(string: String)
}