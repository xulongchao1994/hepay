package com.hechuang.hepay.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.persenter.ForceModifyPasswordPersenter
import com.hechuang.hepay.util.MD5Builder
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.util.Utils
import com.hechuang.hepay.view.IForceModifyPasswordView
import kotlinx.android.synthetic.main.activity_farcemodifypassword.*

/**
 * Created by Android_xu on 2017/12/4.
 * 强制修改密码
 */
class ForceModifyPasswordActivity : BaseAppCompatActivity<ForceModifyPasswordPersenter>(), IForceModifyPasswordView, View.OnClickListener {

    private var mName_str: String? = null
    private var mPhone_str: String? = null
    private var mOnepwd_str: String? = null
    private var mTwopwd_str: String? = null
    private var sp: SharedPreferences? = null


    override fun onClick(v: View) {
        when (v.id) {
            R.id.twopassword_bt -> {
                if (mName_str == null || mName_str == "") {
                    MyToast.showMsg("请输入姓名")
                    return
                }
                if (mPhone_str == null || mPhone_str == "") {
                    MyToast.showMsg("请输入手机号")
                    return
                }
                if (mOnepwd_str == null || mOnepwd_str == "") {
                    MyToast.showMsg("请输入原密码")
                    return
                }
                if (mTwopwd_str == null || mTwopwd_str == "") {
                    MyToast.showMsg("请确认密码")
                    return
                }
                if (mTwopwd_str != mOnepwd_str) {
                    MyToast.showMsg("两次输入的密码不一致")
                    return
                }
                if (!Utils.Pwdisok(mTwopwd_str)) {
                    MyToast.showMsg("新密码必须至少包含大小写字母和数字的组合，长度在8-30之间")
                    return
                }
                val onepassword = MD5Builder.getMD5Str(mOnepwd_str)
                val twopassword = MD5Builder.getMD5Str(mTwopwd_str)
                mPersenter!!.settwopwd(mName_str!!, mPhone_str!!, onepassword, twopassword)
            }
        }
    }


    override fun PwdSeccess(status: Int, msg: String) {
        if (status == 1) {// 成功，退出登录
            MyToast.showMsg(msg)
            startActivity(Intent(this@ForceModifyPasswordActivity, LoginActivity::class.java))
            finish()
        } else {//失败，提示信息
            MyToast.showMsg(msg)
        }
    }


    override fun initlayout(): Int {
        return R.layout.activity_farcemodifypassword
    }

    override fun initPersenter() {
        mPersenter = ForceModifyPasswordPersenter(this, this)
    }

    override fun initView() {
        sp = getSharedPreferences("userInfo", 0)
        twopassword_bt.setOnClickListener(this)
        twopassword_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                mName_str = twopassword_name.text.toString()
            }
        })
        twopassword_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {

                mPhone_str = twopassword_phone.text.toString()
            }
        })
        twopassword_onepwd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                mOnepwd_str = twopassword_onepwd.text.toString()
            }
        })
        twopassword_twopwd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                mTwopwd_str = twopassword_twopwd.text.toString()
            }
        })
    }

    override fun showloading() {

    }

    override fun dissmissloading() {

    }

    override fun getdataerror(msg: String) {

    }
}
