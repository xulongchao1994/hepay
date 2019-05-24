package com.hechuang.hepay.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.PopupWindow
import android.widget.Toast
import butterknife.ButterKnife
import cn.jpush.android.api.JPushInterface
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.Login_nameAdapter
import com.hechuang.hepay.adapter.Login_popup_user_adapter
import com.hechuang.hepay.api.Web_Url
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.base.RvListener
import com.hechuang.hepay.bean.*
import com.hechuang.hepay.customview.RedPacketPopupWindow
import com.hechuang.hepay.persenter.LoginPersenter
import com.hechuang.hepay.util.Eyes
import com.hechuang.hepay.util.KeyBoardUtils
import com.hechuang.hepay.util.MD5Builder
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.ILoginView
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * 登录
 */
class LoginActivity : BaseAppCompatActivity<LoginPersenter>(), ILoginView {
    private var name_str: String? = ""
    private var psw_str: String? = ""
    private var sp: SharedPreferences? = null
    //    private val mWelcomeAcitivity: WelcomeAcitivity? = null
    private var rememberpsw_b: Boolean = false
    private var mNameList: MutableList<Login_nameBean>? = null
    private var namebuffer: StringBuffer? = null
    private var pswbuffer: StringBuffer? = null
    private var username_list: String? = null
    private var password_list: String? = null
    private var isagreement_b: Boolean? = true
    private var wxAPI: IWXAPI? = null
    private var logintype = 1//0是短信验证登录，1是账号密码登录
    private var authcode = ""
    private var authcode_et_str = ""

    @SuppressLint("HandlerLeak")
    private val hand = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.toString()) {
                "1" -> time!!.cancel()
            }
        }
    }
    private var time: CountDownTimer? = null

    internal var login_popupWindow: RedPacketPopupWindow? = null

    internal var dialog: AlertDialog? = null

    internal var popupWindow: PopupWindow? = null
    internal var adapter: Login_nameAdapter? = null


    internal var xiangtong = false
    internal var fasdfasd = 0//记录namelist的位置

    override fun initlayout(): Int {
        return R.layout.activity_login
    }

    override fun initPersenter() {
        mPersenter = LoginPersenter(this, this)
    }

    override fun initView() {
        ButterKnife.bind(this)
        Eyes.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.white))
        mLoginActivity = this
        wxAPI = WXAPIFactory.createWXAPI(this, UserData.wxappid, true)
        wxAPI!!.registerApp(UserData.wxappid)
        mNameList = ArrayList()
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        login_wexinlogin!!.setOnClickListener {
            //跳转微信登录
            wexinlogin()
        }
        login_pswlogin!!.setOnClickListener {
            val str = login_pswlogin_text!!.text.toString()
            //                Log.d(TAG, "onClick: " + str);
            if (str == "账号密码登录") {
                logintype = 1
                login_pswlogin_text!!.text = "短信登录"
                login_rememberpsw!!.visibility = View.VISIBLE
                login_psw_layout!!.visibility = View.VISIBLE
                login_yanzhengma_layout!!.visibility = View.GONE
                showOrHide(false)
                login_name!!.hint = "请输入用户ID"
                login_name!!.setText("")
                login_name!!.inputType = EditorInfo.TYPE_CLASS_TEXT
            } else if (str == "短信登录") {
                logintype = 0
                login_pswlogin_text!!.text = "账号密码登录"
                login_rememberpsw!!.visibility = View.GONE
                login_psw_layout!!.visibility = View.GONE
                login_yanzhengma_layout!!.visibility = View.VISIBLE
                //                    login_psw.setHint("输入验证码");
                login_name!!.hint = "请输入手机号"
                login_name!!.setText("")
                login_name!!.inputType = EditorInfo.TYPE_CLASS_PHONE
                login_name_list!!.visibility = View.GONE
                showOrHide(true)
            }
        }
        login_yanzhenma!!.setOnClickListener(View.OnClickListener {
            name_str = login_name!!.text.toString().trim { it <= ' ' }
            if (name_str == "" || name_str!!.length != 11) {
                Toast.makeText(this@LoginActivity, "请输入正确的手机号", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            login_yanzhenma!!.isClickable = false
            mPersenter!!.getauthcode(name_str)
        })
        login_agreement!!.setOnClickListener { startActivity(Intent(this@LoginActivity, Login_AgreementActivity::class.java)) }
        login_login_bt!!.setOnClickListener { view ->
            login_login_bt!!.isClickable = false
            name_str = login_name!!.text.toString().trim { it <= ' ' }
            psw_str = login_psw!!.text.toString().trim { it <= ' ' }
            authcode_et_str = login_yanzhengma_et!!.text.toString().trim { it <= ' ' }
            if (logintype == 0) {
                if (popupWindow != null && popupWindow!!.isShowing) {
                    popupWindow!!.dismiss()
                }
                if (authcode_et_str != authcode) {
                    Toast.makeText(this@LoginActivity, "验证码输入错误", Toast.LENGTH_SHORT).show()
                    login_login_bt!!.isClickable = true
                    return@setOnClickListener
                }
                KeyBoardUtils.closeKeyBoard(login_name!!)
                KeyBoardUtils.closeKeyBoard(login_psw!!)
                KeyBoardUtils.closeKeyBoard(login_yanzhengma_et)
                mPersenter!!.getuserlist(name_str, authcode_et_str)
            } else {
                if (name_str == "") {
                    Toast.makeText(this@LoginActivity, "请输入用户ID", Toast.LENGTH_SHORT).show()
                    login_login_bt!!.isClickable = true
                    return@setOnClickListener
                }
                if (psw_str == "") {
                    Toast.makeText(this@LoginActivity, "请输入密码", Toast.LENGTH_SHORT).show()
                    login_login_bt!!.isClickable = true
                    return@setOnClickListener
                }
                if (!isagreement_b!!) {
                    Toast.makeText(this@LoginActivity, "请阅读并同意《用户服务协议》", Toast.LENGTH_SHORT).show()
                    login_login_bt!!.isClickable = true
                    return@setOnClickListener
                }
                KeyBoardUtils.closeKeyBoard(login_name!!)
                KeyBoardUtils.closeKeyBoard(login_psw!!)
                KeyBoardUtils.closeKeyBoard(login_yanzhengma_et!!)
                val password = MD5Builder.getMD5Str(psw_str!!)
                mPersenter!!.login(name_str, password)
            }
        }
        login_rememberpsw!!.setOnCheckedChangeListener {//记住密码
            buttonView, isChecked ->
            rememberpsw_b = isChecked
        }

        logind_register!!.setOnClickListener {//注册
            view ->
            //            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            val intent = Intent(this@LoginActivity, ScanWebActivity::class.java)
            intent.putExtra("scanweb_url", Web_Url.REG_URL)
            startActivity(intent)
            finish()
        }
        login_eye!!.setOnCheckedChangeListener { buttonView, isChecked -> showOrHide(isChecked) }
        login_isagreement!!.setOnCheckedChangeListener { buttonView, isChecked -> isagreement_b = isChecked }
        logind_zhaohui!!.setOnClickListener { view ->
            val intent = Intent(this@LoginActivity, ScanWebActivity::class.java)
            intent.putExtra("scanweb_url", Web_Url.FIND_PSW)
            startActivity(intent)
        }
        shownamelist()
        login_name_list!!.setOnClickListener { view ->
            if (popupWindow!!.isShowing) {
                popupWindow!!.dismiss()
            } else {
                popupWindow!!.showAsDropDown(login_name_layout)
            }
            login_name_list!!.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.top_g))
        }
    }

    private fun starttiem() {
        time = object : CountDownTimer((60 * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                login_yanzhenma!!.text = (millisUntilFinished / 1000).toString() + "s"
                if (millisUntilFinished <= 1000) {
                    login_yanzhenma!!.isClickable = true
                    login_yanzhenma!!.text = "获取验证码"
                    val msg = Message.obtain()
                    //                    msg.obj = data;
                    msg.what = 1   //标志消息的标志
                    hand.sendMessage(msg)
                }
            }

            override fun onFinish() {
                login_yanzhenma!!.isClickable = true
                login_yanzhenma!!.text = "获取验证码"
                val msg = Message.obtain()
                //                    msg.obj = data;
                msg.what = 1   //标志消息的标志
                hand.sendMessage(msg)
            }
        }
        time!!.start()
    }

    private fun showpopup(phoneLoginBean: PhoneLoginBean) {
        val view = LayoutInflater.from(this).inflate(R.layout.view_popup_user, null)
        val recyclerview = view.findViewById<RecyclerView>(R.id.login_popup_recycler)
        val linearlayoutmanager = LinearLayoutManager(this)
        linearlayoutmanager.orientation = LinearLayoutManager.VERTICAL
        recyclerview.layoutManager = linearlayoutmanager
        login_popupWindow = RedPacketPopupWindow(this)
        login_popupWindow!!.contentView = view
        login_popupWindow!!.showAtLocation(login_relative, Gravity.BOTTOM, 0, 0)
        val adapter = Login_popup_user_adapter(R.layout.adapter_login_popup_item, phoneLoginBean.data.list)
        recyclerview.adapter = adapter
        adapter.setNewData(phoneLoginBean.data.list)
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (login_popupWindow != null && login_popupWindow!!.isShowing) {
                login_popupWindow!!.dismiss()
            }
            mPersenter!!.getphonelogin(phoneLoginBean.data.token, phoneLoginBean.data.list[position].userId)
        }
    }

    private fun wexinlogin() {
        if (!wxAPI!!.isWXAppInstalled) {//未安装微信
            //            Intent intent = new Intent(this, MyDialogActivity.class);
            //            startActivity(intent);
            MyToast.showMsg("未安装微信")
        } else {//已安装微信，唤醒微信登录
            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = System.currentTimeMillis().toString()
            wxAPI!!.sendReq(req)
        }

    }

    /**
     * 显示
     */
    private fun shownamelist() {
        val view = LayoutInflater.from(this).inflate(R.layout.popup_login_namelist, null)
        val nameList = view.findViewById<RecyclerView>(R.id.popup_name_list)
        popupWindow = PopupWindow(this)
        popupWindow!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        popupWindow!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow!!.contentView = view
        popupWindow!!.setBackgroundDrawable(ColorDrawable(0x00000000))
        //        popupWindow.setTouchable(false);
        popupWindow!!.isFocusable = true
        popupWindow!!.isOutsideTouchable = true
        popupWindow!!.setOnDismissListener { login_name_list!!.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.down_g)) }
        val manager = LinearLayoutManager(this)
        manager.orientation = OrientationHelper.VERTICAL
        nameList.layoutManager = manager
        adapter = Login_nameAdapter(mNameList!!, this@LoginActivity, object : RvListener {
            override fun onItemListener(id: Int, position: Int) {
                name_str = mNameList!![position].name
                psw_str = mNameList!![position].psw
                login_name!!.setText(name_str)
                if (psw_str != " ") {
                    login_psw!!.setText(psw_str)
                } else {
                    login_psw!!.setText("")
                }
                popupWindow!!.dismiss()
            }
        })
        adapter!!.setOnItemDeleterLinener(object : Login_nameAdapter.OnItemDeleteLinener {
            override fun setOnItemDeleteLinener(position: Int) {
                namebuffer = StringBuffer("")
                pswbuffer = StringBuffer("")
                if (mNameList!!.size > 1) {
                    mNameList!!.removeAt(position)
                    //                        baseadater.remove(position)
                    //                        Log.d("logind", mNameList.size.toString())
                    for (i in mNameList!!.indices) {
                        if (mNameList!![i].name != "") {
                            namebuffer!!.append(mNameList!![i].name!! + ",")
                            pswbuffer!!.append(mNameList!![i].psw!! + ",")
                        }
                    }
                    username_list = namebuffer!!.toString()
                    password_list = pswbuffer!!.toString()
                    adapter!!.notifyDataSetChanged()
                } else {
                    mNameList!!.clear()
                    username_list = ""
                    password_list = ""
                    login_name_list!!.visibility = View.GONE
                    if (popupWindow!!.isShowing) {
                        popupWindow!!.dismiss()
                    }
                }
                val editor = sp!!.edit()
                editor.putString("username_list", username_list)
                editor.putString("password_list", password_list)
                editor.commit()
            }
        })
        nameList.adapter = adapter
    }

    private fun showOrHide(isShow: Boolean) {
        //记住光标开始的位置
        val pos = login_psw!!.selectionStart
        if (isShow) {
            login_psw!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            login_psw!!.transformationMethod = PasswordTransformationMethod.getInstance()

        }
        login_psw!!.setSelection(pos)
    }

    override fun onDestroy() {
        super.onDestroy()
        name_str = null
        psw_str = null
        if (time != null) {
            time!!.cancel()
        }
    }

    override fun login_ok(msg: String) {
        if (logintype == 1) {
            namebuffer = StringBuffer()
            pswbuffer = StringBuffer()
            for (i in mNameList!!.indices) {
                if (mNameList!![i].name == name_str) {
                    xiangtong = true
                }
            }
            if (mNameList!!.size > 4) {
                mNameList!!.removeAt(mNameList!!.size - 1)
            }

            if (!xiangtong) {
                namebuffer!!.append(name_str!! + ",")
                if (rememberpsw_b) {
                    pswbuffer!!.append(psw_str!! + ",")
                } else {
                    pswbuffer!!.append(" " + ",")
                }
            } else {
                if (!rememberpsw_b) {
                    for (i in mNameList!!.indices) {
                        if (mNameList!![i].name == name_str) {
                            fasdfasd = i
                        }
                    }
                    mNameList!!.removeAt(fasdfasd)
                    namebuffer!!.append(name_str!! + ",")
                    pswbuffer!!.append(" " + ",")
                } else {
                    for (i in mNameList!!.indices) {
                        if (mNameList!![i].name == name_str) {
                            fasdfasd = i
                        }
                    }
                    mNameList!!.removeAt(fasdfasd)
                    namebuffer!!.append(name_str!! + ",")
                    pswbuffer!!.append(psw_str!! + ",")
                }
            }
            for (i in mNameList!!.indices) {
                if (mNameList!![i].name != "") {
                    namebuffer!!.append(mNameList!![i].name!! + ",")
                    pswbuffer!!.append(mNameList!![i].psw!! + ",")
                }
            }
        }
        MyToast.showMsg(msg)
        username_list = namebuffer!!.toString()
        password_list = pswbuffer!!.toString()
        val editor = sp!!.edit()
        editor.putString("name", UserData.username)
        editor.putString("username", name_str)
        editor.putString("password", psw_str)
        editor.putString("username_list", username_list)
        editor.putString("password_list", password_list)
        editor.putString("token_id", UserData.tokenid)
        editor.putString("urserid", UserData.userid)
        editor.putString("usertype", UserData.usertyep)
        editor.putBoolean("islogin", true)
        editor.putInt("isforce", 0)
        editor.putBoolean("isoutlogin", false)
        editor.putBoolean("remeberpsw", rememberpsw_b)
        editor.commit()
        UserData.islogin = true
        JPushInterface.setAlias(this, 0, UserData.username)
        //        if (UserData.namestatus.equals("0")) {
        //            Utils.startwebactivity(this, UserData.isurl);
        //        } else {
        //            Intent intent = new Intent(LoginActivity.this, UserWebActivity.class);
        //            intent.putExtra("web_url", Web_Url.ME_URL);
        //            startActivity(intent);
        ////            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        //        }
        login_login_bt!!.isClickable = true
        finish()
    }

    override fun login_error(msg: String) {
        login_login_bt!!.isClickable = true
        MyToast.showMsg(msg)
    }

    override fun startmodifypsw(msg: String) {
        val editor = sp!!.edit()
        editor.putString("name", UserData.username)
        editor.putString("username", name_str)
        editor.putString("password", psw_str)
        editor.putString("username_list", username_list)
        editor.putString("password_list", password_list)
        editor.putString("token_id", UserData.tokenid)
        editor.putString("urserid", UserData.userid)
        editor.putBoolean("islogin", false)
        editor.putBoolean("isoutlogin", true)
        editor.putBoolean("remeberpsw", false)
        editor.commit()
        login_login_bt!!.isClickable = true
        UserData.islogin = false
        MyToast.showMsg(msg)
        startActivity(Intent(this@LoginActivity, ForceModifyPasswordActivity::class.java))
    }

    override fun getauthcode(codeBean: AuthCodeBean) {
        starttiem()
        authcode = codeBean.data.vcode
    }

    override fun getautherror(msg: String) {
        MyToast.showMsg(msg)
        login_yanzhenma!!.isClickable = true
        login_yanzhenma!!.text = "获取验证码"
        if (time != null) {
            time!!.onFinish()
            time!!.cancel()
        }
    }

    override fun getphoneuserlist(phoneLoginBean: PhoneLoginBean) {
        if (phoneLoginBean.data.list.size == 1) {
            mPersenter!!.getphonelogin(phoneLoginBean.data.token, phoneLoginBean.data.list[0].userId)
        } else {
            showpopup(phoneLoginBean)
        }
        login_login_bt!!.isClickable = true
    }

    override fun getphoneuserlisterror(msg: String) {
        MyToast.showMsg(msg)
        login_login_bt!!.isClickable = true
    }

    override fun getphoneloginsuccess(phoneSuccessBean: PhoneSuccessBean) {
        UserData.username = phoneSuccessBean.data.list[0].userId
        UserData.usertyep = phoneSuccessBean.data.list[0].userType
        UserData.unionid = phoneSuccessBean.data.list[0].trueName
        UserData.userimg = phoneSuccessBean.data.list[0].avatarUrl
        UserData.sessionid = phoneSuccessBean.data.list[0].sessionid
        UserData.tokenid = phoneSuccessBean.data.list[0].token
        UserData.islogin = true

        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val editor = sp!!.edit()
        editor.putBoolean("islogin", true)
        editor.putString("username", UserData.username)
        editor.putString("token_id", UserData.tokenid)
        editor.commit()
        if (login_popupWindow != null && login_popupWindow!!.isShowing) {
            login_popupWindow!!.dismiss()
        }
        JPushInterface.setAlias(this, 0, UserData.username)
        val intent = Intent(this@LoginActivity, UserWebActivity::class.java)
        intent.putExtra("web_url", Web_Url.ME_URL)
        startActivity(intent)
        //        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish()
    }

    override fun getphoneloginerror(msg: String) {
        MyToast.showMsg(msg)
        UserData.islogin = false
        if (login_popupWindow != null && login_popupWindow!!.isShowing) {
            login_popupWindow!!.dismiss()
        }
    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String) {
        MyToast.showMsg(msg)
    }

    override fun onResume() {
        super.onResume()
        if (logintype == 1) {
            if (mNameList != null && mNameList!!.size > 0)
                mNameList!!.clear()
            rememberpsw_b = sp!!.getBoolean("remeberpsw", false)
            login_rememberpsw!!.isChecked = rememberpsw_b
            name_str = sp!!.getString("username", "")
            username_list = sp!!.getString("username_list", "")
            password_list = sp!!.getString("password_list", "")
            val nameshuzu = username_list!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val pswshuzu = password_list!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val namel = nameshuzu.size
            for (i in 0 until namel) {
                val bean = Login_nameBean(nameshuzu[i], pswshuzu[i])
                mNameList!!.add(bean)
            }
            login_name!!.setText(name_str)
            if (rememberpsw_b) {
                psw_str = sp!!.getString("password", "")
                //            Log.d(TAG, "onResume: " + psw_str);
                login_psw!!.setText(psw_str)
            } else {
                psw_str = ""
                login_psw!!.setText("")
            }
            if (username_list == null || username_list == "" || username_list!!.length < 0) {
                login_name_list!!.visibility = View.GONE
            }
        }
    }

    companion object {
        private val TAG = "LoginActivity"
        var mLoginActivity: LoginActivity = LoginActivity()
    }

}
