package com.hechuang.hepay.util

import android.app.Activity
import android.support.v7.app.AlertDialog

object Kontlin_Utils{

    @JvmStatic
    fun setdialotwidth(context: Activity, dialog: AlertDialog) {
        val windowManager = context.windowManager
        val display = windowManager.defaultDisplay
        val lp = dialog!!.getWindow()!!.attributes
        lp.width = (display.width * 0.85).toInt() //设置宽度
        dialog!!.getWindow()!!.attributes = lp
    }
}