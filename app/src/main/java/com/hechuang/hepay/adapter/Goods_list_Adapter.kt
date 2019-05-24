package com.hechuang.hepay.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.hechuang.hepay.R
import com.hechuang.hepay.base.RvAdapter
import com.hechuang.hepay.base.RvHolder
import com.hechuang.hepay.base.RvListener
import com.hechuang.hepay.bean.GoodsBean
import kotlinx.android.synthetic.main.adapter_goodslist.view.*

/**
 * Created by Android_xu on 2018/3/21.
 */
class Goods_list_Adapter(list: List<GoodsBean.DataBean.ListBean>, context: Context, listener: RvListener)
    : RvAdapter<GoodsBean.DataBean.ListBean>(list, context, listener) {
    override fun getHolder(view: View?, viewtype: Int): RvHolder<*> {
        return Goods_list_ViewHolder(view!!, viewtype, lsitener)
    }

    override fun getLayoutId(viewtype: Int): Int {
        return R.layout.adapter_goodslist
    }


    internal inner class Goods_list_ViewHolder(itemView: View, type: Int, RvListener: RvListener)
        : RvHolder<GoodsBean.DataBean.ListBean>(itemView, type, RvListener) {
        override fun bindHolder(t: GoodsBean.DataBean.ListBean?, position: Int) {
            itemView.adapter_goods_name.text = t!!.proname
            itemView.adapter_goods_pr.text = t.price
            Glide.with(mContext).load(t.proimg).error(R.drawable.icon_error).into(itemView.adapter_goods_icon)
        }
    }
}