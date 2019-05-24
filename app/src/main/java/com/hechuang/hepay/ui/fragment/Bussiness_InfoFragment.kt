package com.hechuang.hepay.ui.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseFragment
import com.hechuang.hepay.bean.BusinessInfoBean
import com.hechuang.hepay.persenter.Bussiness_InfoPersenter
import com.hechuang.hepay.ui.activity.UserWebActivity
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IBusiness_InfoView
import kotlinx.android.synthetic.main.fragment_business_info.*

class Bussiness_InfoFragment : BaseFragment<Bussiness_InfoPersenter>(), IBusiness_InfoView {
    override fun getinfo_success(businessInfoBean: BusinessInfoBean) {
        if (businessInfoBean.data.list.ggfeelv != null && businessInfoBean.data.list.ggfeelv != "") {
            business_fragment_info_zhekoutext.text = businessInfoBean.data.list.ggfeelv
            business_fragment_info_zhekoulayout.visibility = View.VISIBLE
        } else {
            business_fragment_info_zhekoulayout.visibility = View.GONE
        }
        business_fragment_info_name.text = businessInfoBean.data.list.shopName
        business_fragment_info_phone.setOnClickListener {
            //拨打电话
            call(businessInfoBean.data.list.mobile)
        }

        business_fragment_info_time.text = businessInfoBean.data.list.address
        business_fragment_info_pay.setOnClickListener {
            //买单
            if (businessInfoBean.data.list.url != null && businessInfoBean.data.list.url != "") {
                startActivity(Intent(activity!!, UserWebActivity::class.java).putExtra("web_url", businessInfoBean.data.list.url))
            }
        }
        business_fragment_info_howpeople.text = businessInfoBean.data.list.sale
        business_fragment_info_howpeople.visibility = View.VISIBLE
        business_fragment_info_context.text = businessInfoBean.data.list.shopContent
        business_fragment_info_address.text = businessInfoBean.data.list.time
        var text_title = activity!!.findViewById<TextView>(R.id.tv_title)
        text_title.text = businessInfoBean.data.list.shopName
    }

    override fun getinfo_error(string: String) {
        MyToast.showMsg(string)
    }

    override fun initLayout(): Int {
        return R.layout.fragment_business_info
    }

    var shopid = ""
    override fun initViews(view: View?) {
        shopid = arguments!!.getString("shopid")
        mPersenter!!.getinfo(shopid)
    }

    override fun initPersenter() {
        mPersenter = Bussiness_InfoPersenter(this, activity!!)
    }

    override fun showloading() {
    }

    override fun dissmissloading() {
    }

    override fun getdataerror(msg: String?) {
    }

    private fun call(phone: String) {
        if (Build.VERSION.SDK_INT >= 23) {
            val permmision = ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.CALL_PHONE)
            if (permmision != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), 123)
                return
            } else {
                val intent2 = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent2)

            }
        } else {
            val intent2 = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent2)
        }
    }

}