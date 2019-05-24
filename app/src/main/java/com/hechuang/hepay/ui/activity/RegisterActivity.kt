package com.hechuang.hepay.ui.activity

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.RegisterBean
import com.hechuang.hepay.persenter.RegisterPersenter
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IRegisterView
import kotlinx.android.synthetic.main.activity_register.*

/**
 * 用户注册
 */
class RegisterActivity : BaseAppCompatActivity<RegisterPersenter>(), IRegisterView, View.OnClickListener {
    override fun register_success(registerBean: RegisterBean) {

    }

    override fun register_error(string: String) {
    }

    var isagreement_b = true
    var register_type = 1;//1是密码注册 2是短信注册
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.register_sure -> {//注册
                when (register_type) {
                    1 -> {
                    }
                    2 -> {
                    }
                }
            }
            R.id.register_agreement -> {//协议
                startActivity(Intent(this@RegisterActivity, Login_AgreementActivity::class.java))
            }
        }
    }

    override fun initlayout(): Int {
        return R.layout.activity_register
    }

    override fun initPersenter() {
        mPersenter = RegisterPersenter(this, this)
    }

    override fun initView() {
        register_eye.setOnCheckedChangeListener({ buttonView, isChecked -> showOrHide(isChecked) })
        register_sure.setOnClickListener(this)
        register_isagreement.setOnCheckedChangeListener { buttonView, isChecked -> isagreement_b = isChecked }
        register_inputtype.setOnClickListener {
            when (register_inputtype.text) {
                "手机短信注册" -> {
                    login_psw_layout.visibility = View.GONE
                    register_vcode_layout.visibility = View.VISIBLE
                    register_type = 2
                    register_phone_et.hint = "请输入手机号"
                    register_inputtype.text = "账号密码注册"

                }
                "账号密码注册" -> {
                    register_phone_et.hint = "请输入用户ID"
                    login_psw_layout.visibility = View.VISIBLE
                    register_vcode_layout.visibility = View.GONE
                    register_type = 1
                    register_inputtype.text = "手机短信注册"
                }
            }
        }
        register_sure.setOnClickListener { register() }
    }

    var username_str = ""
    var invitenumber = ""
    var password_str = ""
    var vcode_str = ""
    fun register() {
        username_str = register_phone_et.text.toString()
        invitenumber = register_yaoping_et.toString()
        password_str = register_pwd_et.toString()
        vcode_str = register_vcode_et.toString()
        if (username_str == "") {
            MyToast.showMsg("请输入用户ID")
            return
        }
        if (invitenumber == "") {
            MyToast.showMsg("请输入邀请码")
            return
        }
        if (register_type == 1) {
            if (password_str == "") {
                MyToast.showMsg("请输入密码")
                return
            }
        } else if (register_type == 2) {
            if (vcode_str == "") {
                MyToast.showMsg("请输入验证码")
                return
            }
        }
        mPersenter!!.register(username_str, invitenumber, password_str, vcode_str, register_type.toString())
    }

    fun showOrHide(isShow: Boolean) {
        //记住光标开始的位置
        val pos = register_pwd_et.getSelectionStart()
        if (isShow) {
            register_pwd_et.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            register_pwd_et.transformationMethod = PasswordTransformationMethod.getInstance()

        }
        register_pwd_et.setSelection(pos)
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