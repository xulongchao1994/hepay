package com.hechuang.hepay.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.hechuang.hepay.R
import com.hechuang.hepay.bean.Union_top_classify_bean

/**
 * Created by Android_xu on 2018/3/7.
 */

class Union_top_classify_adapter(val mInflater: LayoutInflater, val mUnion_top_classify_beanList: List<Union_top_classify_bean>?, val mContext: Context)
    : RecyclerView.Adapter<Union_top_classify_adapter.Union_top_classify_viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Union_top_classify_viewholder {
        return Union_top_classify_viewholder(mInflater.inflate(R.layout.adapter_union_top_classify, parent, false))
    }

    override fun onBindViewHolder(holder: Union_top_classify_viewholder, position: Int) {
        Glide.with(mContext).load(R.mipmap.ic_launcher).into(holder.mClassify_img)
        //        holder.mClassify_tv.setText(mUnion_top_classify_beanList.get(position).getName());
    }

    override fun getItemCount(): Int {
        return mUnion_top_classify_beanList?.size ?: 0
    }

    inner class Union_top_classify_viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mClassify_img: ImageView
        var mClassify_tv: TextView

        init {
            mClassify_img = itemView.findViewById(R.id.adapter_unidon_top_classify_iv)
            mClassify_tv = itemView.findViewById(R.id.adapter_unidon_top_classify_tv)
        }
    }
}
