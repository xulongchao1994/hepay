package com.hechuang.hepay.ui.activity

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.OrderinfoListAdapter
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.OrderInfoBean
import com.hechuang.hepay.persenter.OrderinfoPersenter
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IOrderInfoView
import kotlinx.android.synthetic.main.activity_orderinfo.*

class OrderInfoActivity : BaseAppCompatActivity<OrderinfoPersenter>(), IOrderInfoView, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.orderinfo_back -> finish()
            R.id.orderinfo_copy -> {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.text = orderinfo_buyernumber.text
                MyToast.showMsg("已将订单号复制到粘贴板")
            }
        }
    }

    var orderid: String? = null
    @SuppressLint("SetTextI18n")
    override fun setdata(orderInfoBean: OrderInfoBean) {
        //设置数据
        orderinfo_type.text = orderInfoBean.data.list[0].status
        orderinfo_time.text = orderInfoBean.data.list[0].downtime
        orderinfo_consignee.text = "收货人：${orderInfoBean.data.list[0].receiveName}"
        orderinfo_phonenumber.text = orderInfoBean.data.list[0].userTel
        orderinfo_address.text = orderInfoBean.data.list[0].address
        orderinfo_liuyan_layout.visibility = View.GONE
        orderinfo_ordermoeny.text = orderInfoBean.data.list[0].zhong
        orderinfo_shorename.text = orderInfoBean.data.list[0].supplier
        orderinfo_buyernumber.text = orderInfoBean.data.list[0].innerOrderId
        orderinfo_ordertime.text = orderInfoBean.data.list[0].addDate

        var adapter = OrderinfoListAdapter(this, orderInfoBean.data.list[0].orderdetail)
        adapter.setONAllItemListener(object : OrderinfoListAdapter.allItemListener {
            override fun allItemListener() {
            }
        })
        orderinfo_lRecyclerview.adapter = adapter
    }

    override fun initlayout(): Int {
        return R.layout.activity_orderinfo
    }

    override fun initPersenter() {
        mPersenter = OrderinfoPersenter(this, this)
    }

    override fun initView() {
        orderid = intent.getStringExtra("orderid")
        mPersenter!!.getdata(orderid!!)
        orderinfo_back.setOnClickListener(this)
        orderinfo_copy.setOnClickListener(this)
        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = OrientationHelper.VERTICAL
        orderinfo_lRecyclerview.layoutManager = layoutManager
    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String?) {
    }
}
