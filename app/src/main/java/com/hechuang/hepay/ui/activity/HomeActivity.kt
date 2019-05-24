package com.hechuang.hepay.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.widget.CompoundButton
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder
import com.allenliu.versionchecklib.v2.builder.UIData
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener
import com.google.zxing.activity.CaptureActivity
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.Main_vp_adapter
import com.hechuang.hepay.api.ApiFactory
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.bean.VersionBean
import com.hechuang.hepay.persenter.HomePersenter
import com.hechuang.hepay.ui.fragment.HomeFragment
import com.hechuang.hepay.ui.fragment.HomepageFragment
import com.hechuang.hepay.util.Eyes
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.util.Utils
import com.hechuang.hepay.view.IHomeView
import kotlinx.android.synthetic.main.activity_home.*

/**
 * activity 首页
 * Created by Android_xu on 2018/3/15.
 */
class HomeActivity : BaseAppCompatActivity<HomePersenter>(), IHomeView, ViewPager.OnPageChangeListener, CompoundButton.OnCheckedChangeListener {
    override fun getversion(versionBean: VersionBean) {
//        Log.d("hepay", versionBean.toString())
        if (versionBean.status == "1") {
            val int_version = Integer.valueOf(versionBean.versionName)
//            Log.d("hepay", int_version.toString() + "  " + Utils.getVersionCode(this@HomeActivity))
            if (int_version > Utils.getVersionCode(this@HomeActivity)) {
                when (versionBean.browser) {
                    "1" -> {
                        val isgengxin: Boolean
                        isgengxin = versionBean.auto == "1"
                        updateapp("", versionBean.where, isgengxin)
                    }
                    "2" -> if (versionBean.auto == "0") {
                        AlertDialog.Builder(this)
                                .setMessage("有新版本，是否更新")
                                .setPositiveButton("确定") { dialog, which ->
                                    val uri = Uri.parse(versionBean.where)
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    startActivity(intent)
                                }.setNegativeButton("取消", null)
                                .show()
                    } else {
                        AlertDialog.Builder(this)
                                .setMessage("有新版本，是否更新(此版本强制更新)")
                                .setPositiveButton("确定") { dialog, which ->
                                    val uri = Uri.parse(versionBean.where)
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    startActivity(intent)
                                }.setCancelable(false).setNegativeButton("取消", null)
                                .show()
                    }
                }
            }
        }
    }

    private fun updateapp(msg: String, url: String, isqiangzhi: Boolean?) {
        var msg = msg
        if (msg == "") {
            msg = "有新版本，是否更新？"
        }
        val uiData = UIData.create().setTitle(getString(R.string.app_name)).setContent(msg).setDownloadUrl(url)
        val bundle = AllenVersionChecker.getInstance().downloadOnly(uiData)
        if (isqiangzhi!!) {
            bundle.forceUpdateListener = ForceUpdateListener { MyToast.showMsg("此版本为强制更新") }
        }
        bundle.isForceRedownload = true
        bundle.notificationBuilder = NotificationBuilder.create().setRingtone(true)
                .setIcon(R.mipmap.ic_launcher)
                //                .setTicker("custom_ticker")
                .setContentTitle(getString(R.string.app_name))
                .setContentText(msg)
        //        String finalMsg = msg;
        //        bundle.setCustomVersionDialogListener((context, versionBundle) -> {
        //            BaseDialog dialog = new BaseDialog(context, R.style.BaseDialog, R.layout.custom_dialog_one_layout);
        //            TextView textView = dialog.findViewById(R.id.tv_msg);
        //            textView.setText(finalMsg);
        //            //给窗体设置大小
        //            Window win = dialog.getWindow();
        //            win.getDecorView().setPadding(50, 0, 50, 0);
        ////            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ////            win.setAttributes(layoutParams);
        //            WindowManager.LayoutParams lp = win.getAttributes();
        //            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //            return dialog;
        //        });
        //自定义进度条页面
        //        bundle.customDownloadingDialogListener = object : CustomDownloadingDialogListener {
        //            override fun updateUI(dialog: Dialog?, progress: Int, versionBundle: UIData?) {
        //            }
        //            override fun getCustomDownloadingDialog(context: Context?, progress: Int, versionBundle: UIData?): Dialog {
        //            }
        //        }
        bundle.excuteMission(this)
    }

    //打开扫描界面请求码
    private val REQUEST_CODE = 0x01

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            when (buttonView!!.id) {
                R.id.main_home -> main_vp.currentItem = 0
                R.id.main_goods -> {
                    main_vp.currentItem = 1
                    main_home.isChecked = true
//                    if (UserData.islogin)
                    stwebactivity(ApiFactory.HOST + "index.php/Home/Product/category")
//                    else
//                        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                }
                R.id.main_shopping -> {
                    main_home.isChecked = true
                    if (UserData.islogin)
                        stwebactivity(ApiFactory.HOST + "index.php/Home/Cart/shopping_list_show")
                    else
                        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                }
                R.id.main_me -> {
                    main_home.isChecked = true
                    if (UserData.islogin)
                        stwebactivity(ApiFactory.HOST + "index.php/Home/Personal/index")
                    else
                        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                }
            }
        }
    }

    private fun stwebactivity(s: String) {
        if (s != null && s != "") {
            var intent = Intent(this@HomeActivity, WebActivity::class.java)
            intent.putExtra("web_url", s)
            startActivity(intent)
            finish()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> main_home.isChecked = true
            1 -> main_goods.isChecked = true
            2 -> main_shopping.isChecked = true
            3 -> main_me.isChecked = true
        }
    }

    var mAdaper: Main_vp_adapter? = null
    var mHome_fragment: HomepageFragment? = null
    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String) {

    }

    override fun initlayout(): Int {
        return R.layout.activity_home
    }

    override fun initPersenter() {
        mPersenter = HomePersenter(this, this)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun initView() {
        Eyes.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.white))
        val fragmentlsit = ArrayList<Fragment>()
        mHome_fragment = HomepageFragment()
        fragmentlsit.add(mHome_fragment!!)
        var Home_goodsfragment = HomeFragment()
        fragmentlsit.add(Home_goodsfragment)
        mAdaper = Main_vp_adapter(supportFragmentManager, fragmentlsit)
        main_vp.addOnPageChangeListener(this)
        main_vp.adapter = mAdaper
        main_home.setOnCheckedChangeListener(this)
        main_shopping.setOnCheckedChangeListener(this)
        main_goods.setOnCheckedChangeListener(this)
        main_me.setOnCheckedChangeListener(this)
        setr_img()
        if (Build.VERSION.SDK_INT >= 23) {
            val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                    this@HomeActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 111)
            } else {
                mPersenter!!.getversion()
            }
        } else {
            mPersenter!!.getversion()
        }
    }

    override fun onResume() {
        super.onResume()
        main_home.isChecked = true
    }

    private fun setr_img() {
        val top = 20
        val right = 20

        val scale = this.resources.displayMetrics.density.toInt()
        var home = ContextCompat.getDrawable(this@HomeActivity, R.drawable.main_home)
        home!!.setBounds(0, 0, (top * scale + 0.5f).toInt(), (right * scale + 0.5f).toInt())
        main_home.setCompoundDrawables(null, home, null, null)

        var shore = ContextCompat.getDrawable(this@HomeActivity, R.drawable.main_shore)
        shore!!.setBounds(0, 0, (top * scale + 0.5f).toInt(), (right * scale + 0.5f).toInt())
        main_goods.setCompoundDrawables(null, shore, null, null)

        var shopping = ContextCompat.getDrawable(this@HomeActivity, R.drawable.main_shopping)
        shopping!!.setBounds(0, 0, (top * scale + 0.5f).toInt(), (right * scale + 0.5f).toInt())
        main_shopping.setCompoundDrawables(null, shopping, null, null)

        var me = ContextCompat.getDrawable(this@HomeActivity, R.drawable.main_me)
        me!!.setBounds(0, 0, (top * scale + 0.5f).toInt(), (right * scale + 0.5f).toInt())
        main_me.setCompoundDrawables(null, me, null, null)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            321 -> for (permission in permissions) {
                when (permission) {
                    "android.permission.CAMERA" -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        val intent = Intent(this@HomeActivity, CaptureActivity::class.java)
                        startActivityForResult(intent, REQUEST_CODE)
                    } else {
                        MyToast.showMsg("打开相机权限被禁止")
                    }
                }

            }
            111 -> for (permission in permissions) {
                when (permission) {
                    "android.permission.READ_EXTERNAL_STORAGE" -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        mPersenter!!.getversion()
                    } else {
                        MyToast.showMsg("读写权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                    }
                    "android.permission.WRITE_EXTERNAL_STORAGE" -> if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        mPersenter!!.getversion()
                    } else {
                        MyToast.showMsg("读写权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                    }
                }

            }
        }
    }
}
