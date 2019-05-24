package com.hechuang.hepay.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.hechuang.hepay.R
import com.hechuang.hepay.base.RvAdapter
import com.hechuang.hepay.base.RvHolder
import com.hechuang.hepay.base.RvListener
import com.hechuang.hepay.bean.Home_ClassifyBean
import kotlinx.android.synthetic.main.adapter_homepage_grid.view.*

/**
 * Created by Android_xu on 2018/3/16.
 */
class Homepage_H_Adapter(list: List<Home_ClassifyBean.DataBean>, context: Context, listener: RvListener)
    : RvAdapter<Home_ClassifyBean.DataBean>(list, context, listener) {
    override fun getLayoutId(viewtype: Int): Int {
        return R.layout.adapter_homepage_grid
    }

    override fun getHolder(view: View?, viewtype: Int): RvHolder<*> {
        return Homepage_H_Viewholder(view!!, viewtype, lsitener)
    }

    internal inner class Homepage_H_Viewholder(itemView: View, type: Int, rvListener: RvListener)
        : RvHolder<Home_ClassifyBean.DataBean>(itemView, type, rvListener) {
        override fun bindHolder(t: Home_ClassifyBean.DataBean?, position: Int) {
//            Log.d("asd", t!!.name + t.imgs)
            itemView.adapter_homepage_tv.text = t!!.name
            Glide.with(mContext).load(t.imgs).override(100, 100).error(R.drawable.icon_error).into(itemView.adapter_homepage_im)
        }
    }
}
