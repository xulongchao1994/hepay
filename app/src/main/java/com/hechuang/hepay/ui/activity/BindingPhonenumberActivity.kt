package com.hechuang.hepay.ui.activity

import android.content.Context
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.hechuang.hepay.R
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.BindingBean
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.persenter.BindingPhonePersenter
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IBindingPhoneView
import kotlinx.android.synthetic.main.activity_bindingphone.*

/**
 * 微信登录后绑定用户
 */
class BindingPhonenumberActivity : BaseAppCompatActivity<BindingPhonePersenter>(), IBindingPhoneView {
    var yaoqingma = ""
    var username = ""
    var psw = ""
    var usertype = 0 // 0 是老用户  1是新用户
    override fun Binding_seccess(bindingBean: BindingBean) {
        UserData.username = bindingBean.list.username
        UserData.userid = bindingBean.list.userid
        UserData.usertyep = bindingBean.list.usertype
        UserData.tokenid = bindingBean.list.token
        UserData.sessionid = bindingBean.list.sessionid
        UserData.serviceffe = bindingBean.list.servicefee
//        Log.d("binding", UserData.username + "  " + UserData.tokenid + "  " + UserData.sessionid)
        var sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean("islogin", true)
        editor.putString("username", UserData.username)
        editor.putString("token_id", UserData.tokenid)
        editor.commit()
        JPushInterface.setAlias(this, 0, UserData.username)
        UserData.islogin = true
        startActivity(Intent(this@BindingPhonenumberActivity, HomeActivity::class.java))
        LoginActivity.mLoginActivity.finish()
        finish()
    }

    override fun binding_error(msg: String) {
        MyToast.showMsg(msg)
    }

    override fun initlayout(): Int {
        return R.layout.activity_bindingphone
    }

    override fun initPersenter() {
        mPersenter = BindingPhonePersenter(this, this)
    }

    override fun initView() {
        usertype = intent.getIntExtra("usertype", 0)
        bindingphone_back.setOnClickListener { finish() }
        when (usertype) {
            0 -> {
                setoldview()
            }
            1 -> {
                setnewview()
            }
        }
        bindingphone_olduser.setOnClickListener {
            setoldview()
        }
        bindingphone_newuser.setOnClickListener {
            setnewview()
        }
        bindingphone_sure.setOnClickListener {
            yaoqingma = bindingphone_yaoqingma_et.text.toString()
            username = bindingphone_name_et.text.toString()
            psw = bindingphone_pwd_et.text.toString()
            when (usertype) {
                1 -> {
                    if (yaoqingma == "") {
                        MyToast.showMsg("请输入邀请码")
                        return@setOnClickListener
                    }
                    mPersenter!!.post_bind(UserData.unionid, "", "", yaoqingma)
                }
                0 -> {
                    if (username == "") {
                        MyToast.showMsg("请输入用户id")
                        return@setOnClickListener
                    }
                    if (psw == "") {
                        MyToast.showMsg("请输入密码")
                        return@setOnClickListener
                    }
                    mPersenter!!.post_bind(UserData.unionid, username, psw, "")
                }
            }
        }
        bindingphone_eye.setOnCheckedChangeListener { buttonView, isChecked -> showOrHide(isChecked) }
    }

    private fun showOrHide(isShow: Boolean) {
        //记住光标开始的位置
        val pos = bindingphone_pwd_et.getSelectionStart()
        if (isShow) {
            bindingphone_pwd_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
        } else {
            bindingphone_pwd_et.setTransformationMethod(PasswordTransformationMethod.getInstance())

        }
        bindingphone_pwd_et.setSelection(pos)
    }

    private fun setnewview() {
        usertype = 1
        bindingphone_title_text.text = "邀请信息"
        bindingphone_title_msg.text = "请输入您的禾田付邀请码"
        bindingphone_name_et.visibility = View.GONE
        bindingphone_psw_layout.visibility = View.GONE
        bindingphone_yaoqingma_et.visibility = View.VISIBLE
        username = ""
        psw = ""
    }

    private fun setoldview() {
        usertype = 0
        bindingphone_title_text.text = "绑定账号"
        bindingphone_title_msg.text = "请输入您的禾田付账号和密码"
        bindingphone_name_et.visibility = View.VISIBLE
        bindingphone_psw_layout.visibility = View.VISIBLE
        bindingphone_yaoqingma_et.visibility = View.GONE
        yaoqingma = ""
    }


    override fun showloading() {
        mLoading!!.isShowing
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String?) {
    }
}