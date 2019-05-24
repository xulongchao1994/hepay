package com.hechuang.hepay.view

import com.hechuang.hepay.base.BaseView
import com.hechuang.hepay.bean.Uploadimglistdata

interface IUploadImageView : BaseView {
    /**
     * 获取数据成功
     */
    fun getlistdata_ok(data: ArrayList<Uploadimglistdata.DataBean.ListBean>)

    /**
     * 上传图片成功
     */
    fun upimage_ok(msg: String)

    /**
     * 删除图片成功
     */
    fun dtimgage_ok(msg: String)
}