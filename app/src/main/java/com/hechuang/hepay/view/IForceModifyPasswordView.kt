package com.hechuang.hepay.view


import com.hechuang.hepay.base.BaseView

/**
 * Created by Android_xu on 2017/12/4.
 * 强制修改密码
 */

interface IForceModifyPasswordView : BaseView {

    fun PwdSeccess(status: Int, msg: String)
}
