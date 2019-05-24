package com.hechuang.hepay.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hechuang.hepay.R
import com.hechuang.hepay.bean.OrderInfoBean

class OrderinfoListAdapter(val mContext: Context, val itemdata: List<OrderInfoBean.DataBean.ListBean.OrderdetailBean>) :
        RecyclerView.Adapter<OrderinfoListAdapter.AllorderitemViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllorderitemViewholder {
        return AllorderitemViewholder(LayoutInflater.from(mContext).inflate(R.layout.adapter_allorderitem, parent, false))
    }

    override fun getItemCount(): Int {
        return itemdata.size
    }

    override fun onBindViewHolder(holder: AllorderitemViewholder, position: Int) {
        val itemdatas = itemdata.get(position)
        Glide.with(mContext).load(itemdatas.proImg).into(holder.icon)
        holder.name.text = itemdatas.proName
        holder.proname.visibility = View.GONE
        holder.price.text = itemdatas.money
        holder.stylename.text = itemdatas.styleName
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (mAllitemListeners != null) {
                mAllitemListeners!!.allItemListener()
            }
        })
    }

    inner class AllorderitemViewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var icon: ImageView
        var name: TextView
        var proname: TextView
        var stylename: TextView
        var price: TextView
        var pronumber: TextView
        var proname_layout: LinearLayout

        init {
            icon = itemView.findViewById(R.id.adapter_allorderitem_icon)
            name = itemView.findViewById(R.id.adapter_allorderitem_name)
            proname = itemView.findViewById(R.id.adapter_allorderitem_proname)
            stylename = itemView.findViewById(R.id.adapter_allorderitem_stylename)
            price = itemView.findViewById(R.id.adapter_allorderitem_price)
            pronumber = itemView.findViewById(R.id.adapter_allorderitem_pronumber)
            proname_layout = itemView.findViewById(R.id.adapter_allorderitem_proname_layout)
        }
    }

    var mAllitemListeners: allItemListener? = null
    fun setONAllItemListener(allItemListener: allItemListener) {
        mAllitemListeners = allItemListener
    }

    interface allItemListener {
        fun allItemListener()
    }
}