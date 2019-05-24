package com.hechuang.hepay.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.SelectCityAdapter
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.SelectCityBean
import com.hechuang.hepay.customview.RecycleViewDivider
import com.hechuang.hepay.persenter.SelectVityPersenter
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.ISelectCityView

/**
 * 切换城市
 */
class SelectCityActivity : BaseAppCompatActivity<SelectVityPersenter>(), ISelectCityView, SelectCityAdapter.OnSelectCityItmeListener, View.OnClickListener {
    internal var mRecyclerView: RecyclerView? = null
    internal var mBack: ImageView? = null
    internal var mTitle: TextView? = null
    private var province = ""
    private var city = ""
    private var district = ""
    private var province_adapter: SelectCityAdapter? = null
    private var city_adapter: SelectCityAdapter? = null
    private var district_adapter: SelectCityAdapter? = null
    private var cishu = 1

    override fun initlayout(): Int {
        return R.layout.activity_selectcity
    }

    override fun initPersenter() {
        mPersenter = SelectVityPersenter(this, this)
    }

    override fun initView() {
        mRecyclerView = findViewById(R.id.userinfocity_Province)
        mBack = findViewById(R.id.userinfocity_back)
        mTitle = findViewById(R.id.userinfocity_title)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView!!.layoutManager = linearLayoutManager
        mRecyclerView!!.addItemDecoration(RecycleViewDivider(this, 1))
        mTitle!!.setOnClickListener(this)
        mBack!!.setOnClickListener(this)
        mPersenter!!.getProvince()
    }


    private fun settwocity(name: String) {
        province = name
        mPersenter!!.getCity(name)
    }

    private fun setthreecity(name: String) {
        city = name
        mPersenter!!.getCount(name)

    }

    override fun onClick(view: View) {
        cishu = cishu - 1
        when (cishu) {
            2 -> settwocity(province)
            1 -> mPersenter!!.getProvince()
            0 -> {
                val data = Intent()
                data.putExtra("city", "")
                data.putExtra("province", "")
                data.putExtra("district", "")
                setResult(Activity.RESULT_OK, data)
                this@SelectCityActivity.finish()
            }
        }
    }

    override fun OnSelectCityItmeListener(position: Int, name: String) {
        cishu = cishu + 1
        when (cishu) {
            2 -> settwocity(name)
            3 -> setthreecity(name)
            4 -> {
                district = name
                val data = Intent()
                data.putExtra("city", city)
                data.putExtra("province", province)
                data.putExtra("district", district)
                setResult(Activity.RESULT_OK, data)
                this@SelectCityActivity.finish()
            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cishu = cishu - 1
            when (cishu) {
                2 -> mPersenter!!.getCity(province)
                1 -> mPersenter!!.getProvince()
                0 -> finish()
            }
        }
        return false

    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String) {
        MyToast.showMsg(msg + "请稍后重试")
        this@SelectCityActivity.finish()
    }

    override fun getprovinceok(selectCityBean: SelectCityBean.DataBean) {
        province_adapter = SelectCityAdapter(this, selectCityBean.list)
        province_adapter!!.setOnSelectCityItmeListener(this)
        mRecyclerView!!.adapter = province_adapter
    }

    override fun getcityok(selectCityBean: SelectCityBean.DataBean) {
        city_adapter = SelectCityAdapter(this, selectCityBean.list)
        city_adapter!!.setOnSelectCityItmeListener(this)
        mRecyclerView!!.adapter = city_adapter
    }

    override fun getcountok(selectCityBean: SelectCityBean.DataBean) {
        if (selectCityBean.list != null && selectCityBean.list.size > 0) {
            district_adapter = SelectCityAdapter(this, selectCityBean.list)
            district_adapter!!.setOnSelectCityItmeListener(this)
            mRecyclerView!!.adapter = district_adapter
        } else {
            val data = Intent()
            data.putExtra("city", city)
            data.putExtra("province", province)
            data.putExtra("district", "")
            setResult(Activity.RESULT_OK, data)
            this@SelectCityActivity.finish()
        }
    }
}
