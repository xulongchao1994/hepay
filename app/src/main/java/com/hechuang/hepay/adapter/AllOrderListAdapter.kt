package com.hechuang.hepay.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hechuang.hepay.R
import com.hechuang.hepay.bean.AllorderBean
import java.util.*


/**
 * 我的订单列表
 */

class AllOrderListAdapter(private val mContext: Context, private val listdata: List<AllorderBean.DataBean.ListBean>?) :
        RecyclerView.Adapter<AllOrderListAdapter.AllOrderViewHolder>() {
    private var adapter: AllorderItemAdapter? = null
    private var type: String? = null

    private var onQuxiaoListener: OnQuxiaoListener? = null

    private var onPayListener: OnPayListener? = null

    private var mOnItemListener: OnItemListener? = null


    fun setordertype(ordertype: String) {
        this.type = ordertype
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_allorderlist, parent, false))
    }

    fun noitemadapter() {
        if (adapter != null) {
            adapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 每一个位置的item都作为单独一项来设置
     * viewType 设置为position
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, positions: Int) {
        val data = listdata!![positions]
        holder.ordermun.text = data.innerOrderId
        holder.ordercancel.text = data.status//订单状态
        holder.orderquntity.text = data.zhong
        holder.orderamount.text = ""
        var itemBeanList: List<AllorderBean.DataBean.ListBean.OrderdetailBean> = ArrayList()
        itemBeanList = data.orderdetail
        adapter = AllorderItemAdapter(mContext, itemBeanList)
        adapter!!.setONAllItemListener(object : AllorderItemAdapter.allItemListener {
            override fun allItemListener() {
                if (mOnItemListener != null) {
                    mOnItemListener!!.OnItemListener(positions)
                }
            }
        })
        holder.orderrecycler.layoutManager = LinearLayoutManager(mContext)
        holder.orderrecycler.adapter = adapter
        holder.time.text = data.addDate
        //        holder.buy.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                int types = 0;
        //                if (data.getStatus().equals("1")) types = 0;
        //                else if (data.getStatus().equals("2")) types = 0;
        //                else if (data.getStatus().equals("4")) types = 1;
        //                if (onPayListener != null) {
        //                    onPayListener.OnPayListener(positions, types, type);
        //                }
        //            }
        //        });
        //        holder.quxiao.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                if (onQuxiaoListener != null) {
        //                    onQuxiaoListener.OnQuxiaoListener(positions, type);
        //                }
        //            }
        //        });
    }


    override fun getItemCount(): Int {
        return listdata?.size ?: 0
    }

    inner class AllOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ordertype: TextView//订单类型
        var ordermun: TextView//订单号
        var ordercancel: TextView//订单状态
        var orderquntity: TextView//订单数量
        var orderamount: TextView//订单金额
        var orderrecycler: RecyclerView//订单列表
        var order_id: TextView//id
        var ReceiveName: TextView//收货人
        var viptext: TextView//注册会员的名称
        var time: TextView//时间
        var noting_layout: LinearLayout
        var list_layout: LinearLayout
        var noting_img: ImageView
        var buy_layout: LinearLayout
        var buy: Button
        var quxiao: Button

        init {
            noting_layout = itemView.findViewById(R.id.adapter_allorder_noting_layout)
            noting_img = itemView.findViewById(R.id.adapter_allorder_noting_img)
            list_layout = itemView.findViewById(R.id.adapter_allorder_listlayout)
            ordertype = itemView.findViewById(R.id.adapter_allorder_type)
            ordermun = itemView.findViewById(R.id.adapter_allorder_num)
            ordercancel = itemView.findViewById(R.id.adapter_allorder_cancel)
            orderquntity = itemView.findViewById(R.id.adapter_allorder_quantity)
            orderamount = itemView.findViewById(R.id.adapter_allorder_amount)
            orderrecycler = itemView.findViewById(R.id.adapter_allorder_recyclerview)
            order_id = itemView.findViewById(R.id.adapter_allorder_id)
            ReceiveName = itemView.findViewById(R.id.adapter_allorder_ReceiveName)
            viptext = itemView.findViewById(R.id.adapter_allorder_vipname)
            time = itemView.findViewById(R.id.adapter_allorder_time)
            buy_layout = itemView.findViewById(R.id.adapter_allorder_buy_layout)
            buy = itemView.findViewById(R.id.adapter_allorder_buy)
            quxiao = itemView.findViewById(R.id.adapter_allorder_quxiao)
        }
    }

    fun setOnQuxiaoListener(onQuxiaoListener: OnQuxiaoListener) {
        this.onQuxiaoListener = onQuxiaoListener
    }

    interface OnQuxiaoListener {
        fun OnQuxiaoListener(position: Int, type: String)
    }

    interface OnPayListener {
        fun OnPayListener(position: Int, type: Int, ordertype: String)
    }

    fun setOnPayListener(OnPayListener: OnPayListener) {
        this.onPayListener = OnPayListener
    }

    interface OnItemListener {
        fun OnItemListener(position: Int)
    }

    fun setOnItemListener(onItemListener: OnItemListener) {
        mOnItemListener = onItemListener
    }

    companion object {
        private val TAG = "AllOrderListAdapter"
    }
}
