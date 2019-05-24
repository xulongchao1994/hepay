package com.hechuang.hepay.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.KeyEvent
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.UIData
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.bumptech.glide.Glide
import com.google.zxing.activity.CaptureActivity
import com.hechuang.hepay.R
import com.hechuang.hepay.api.Web_Url
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.NewListBean
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.bean.VersionBean
import com.hechuang.hepay.listener.MyLocationListener
import com.hechuang.hepay.persenter.MainPersenter
import com.hechuang.hepay.util.*
import com.hechuang.hepay.view.IMainView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : BaseAppCompatActivity<MainPersenter>(), IMainView {
    var newlist = arrayListOf<String>()
    var mLocationClient: LocationClient? = null
    override fun getnewlist_success(listdata: NewListBean) {
        for (i in 0 until listdata.data.list.size) {
            newlist.add(listdata.data.list[i].title)
        }
        main_runtext.setTextList(newlist)
        main_runtext.setText(15f, 5, -0x666666)
        main_runtext.setTextStillTime(3000)//设置停留时长间隔
        main_runtext.setAnimTime(300)//设置进入和退出的时间间隔
        main_runtext.setOnItemClickListener {
            var intent = Intent(this@MainActivity, NoticeActivity::class.java)
            intent.putExtra("id", listdata.data.list[it].id)
            startActivity(intent)
//            Utils.startwebactivity(this, Web_Url.ARTICE_URL)
        }
        main_runtext.startAutoScroll()
    }

    override fun getdataerror(msg: String?) {
    }

    override fun getversion(versionBean: VersionBean) {
        if (versionBean.status == "1") {
            val int_version = Integer.valueOf(versionBean.versionName)
            Log.d("main", int_version.toString() + "  " + Utils.getVersionCode(this@MainActivity))
            if (int_version > Utils.getVersionCode(this@MainActivity)) {
                when (versionBean.browser) {
                    "1" -> {
                        var isgengxin = versionBean.auto == "1"
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
        bundle.isShowNotification = false
//        bundle.notificationBuilder = NotificationBuilder.create().setRingtone(true)
//                .setIcon(R.mipmap.ic_launcher)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(msg)
        bundle.excuteMission(this)
    }

    //打开扫描界面请求码
    val REQUEST_CODE = 0x01

    override fun initlayout(): Int {
        return R.layout.activity_main
    }

    override fun initPersenter() {
        mPersenter = MainPersenter(this, this)
    }

    var button_imglist = arrayListOf<Int>()
    override fun initView() {
        exit()
        userexit()
        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_appbar))

        var myListener: BDLocationListener
        mLocationClient = LocationClient(applicationContext)
        initLocation()
        myListener = MyLocationListener()
        mLocationClient!!.registerLocationListener(myListener)
        mPersenter!!.getversion()
        mPersenter!!.getnewlist()
        Glide.with(this).load(R.drawable.main_scan).override(800, 800).into(main_title_img)
        button_imglist.add(R.drawable.main_htf)
        button_imglist.add(R.drawable.main_hc)
        button_imglist.add(R.drawable.main_labee)
        button_imglist.add(R.drawable.main_integral)
        main_mine.setOnClickListener {
            if (UserData.islogin) {
                var url = ""
                if (UserData.namestatus == "0") {
                    url = UserData.isurl
                } else {
                    url = Web_Url.ME_URL
                }
                var intetn = Intent(this@MainActivity, UserWebActivity::class.java)
                intetn.putExtra("web_url", url)
                startActivity(intetn)
            } else {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
        }
        Glide.with(this).load(R.drawable.main_htf).override(600, 200).into(main_htf)
        Glide.with(this).load(R.drawable.main_hc).override(200, 100).into(main_hc)
        Glide.with(this).load(R.drawable.main_labee).override(600, 200).into(main_lf)
        Glide.with(this).load(R.drawable.main_integral).override(200, 100).into(main_jifen)
//        main_htf.setOnClickListener { startActivity(Intent(this@MainActivity, UnionShop_Activity::class.java)) }
        main_htf.setOnClickListener { startActivity(Intent(this@MainActivity, AllianceShopActivity::class.java)) }
        main_hc.setOnClickListener {
            starthc()
        }
        main_lf.setOnClickListener {
            startlabee()
        }
        main_jifen.setOnClickListener {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }
        main_runtext_img.setOnClickListener {
            var intent = Intent(this@MainActivity, NoticeActivity::class.java)
            startActivity(intent)
        }
        main_htf_layout.setOnClickListener { startActivity(Intent(this@MainActivity, AllianceShopActivity::class.java)) }
        main_hc_layout.setOnClickListener {
            starthc()
        }
        main_lf_layout.setOnClickListener {
            startlabee()
        }
        main_jifen_layout.setOnClickListener {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }

        main_scan_bt.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 23) {
                val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                        this@MainActivity, Manifest.permission.CAMERA)
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), 321)
                } else {
                    val intent = Intent(this@MainActivity, CaptureActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            } else {
                val intent = Intent(this@MainActivity, CaptureActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(
                        //定位需要的权限
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 123)
            } else {
                location()
            }
        } else {
            location()
        }
        if (Utils.isExistMainActivity(this@MainActivity, WelcomeAcitivity::class.java)) {
            WelcomeAcitivity.mWelcomeAcitivity.finish()
        }
    }

    fun startlabee() {
        if (Utils.checkPackInfo(this@MainActivity, "com.hechuang.labeego"))
            Utils.startapp(this@MainActivity, "com.hechuang.labeego")
        else {
            val uri = Uri.parse("http://lafeng.hetianpay.com/public/Appload/App_down.html")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    fun starthc() {
        if (Utils.checkPackInfo(this@MainActivity, "com.feidu.hechaung_img")) {
            val intent2 = Intent()
            intent2.setClassName("com.feidu.hechaung_img", "com.feidu.hechaung_img.ui.activity.MainActivity")
            intent2.putExtra("name", UserData.username)
            startActivity(intent2)
        } else {
            val uri = Uri.parse("https://www.pgyer.com/hechuangyinghang")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun initLocation() {
        val option = LocationClientOption()
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll")
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(0)
        option.setIsNeedAddress(true)//可选，设置是否需要地址信息，默认不需要
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.isOpenGps = false
        option.isLocationNotify = false//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true)//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true)//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false)//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false)//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false)//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setWifiCacheTimeOut(5 * 60 * 1000)
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        mLocationClient!!.setLocOption(option)
    }

    private fun location() {
        mLocationClient!!.start()
        mLocationClient!!.registerLocationListener { bdLocation ->
            when (bdLocation.getLocType()) {
                161 -> {
                    mLocationClient!!.stop()
                }
                61 -> {
                    mLocationClient!!.stop()
                }
                else -> {
                    UserData.province = ""
                    UserData.city = ""
                    UserData.discrict = ""
                }
            }
        }
    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            123 -> for (ste in permissions) {
                when (ste) {
                    "android.permission.ACCESS_COARSE_LOCATION" -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        mLocationClient!!.restart()
                    } else {
                        MyToast.showMsg("定位权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                    }
                    "android.permission.ACCESS_FINE_LOCATION" -> if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        mLocationClient!!.restart()
                    } else {
                        MyToast.showMsg("定位权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                    }
                }
            }
            321 -> for (permission in permissions) {
                when (permission) {
                    "android.permission.CAMERA" -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        val intent = Intent(this@MainActivity, CaptureActivity::class.java)
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

    val RESULT_CODE_QR_SCAN = 0xA1
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("main", "$requestCode $resultCode $data")
        if (data != null) {
            when (requestCode) {
                REQUEST_CODE -> if (resultCode == RESULT_CODE_QR_SCAN) { //RESULT_OK = -1
                    val bundle = data.extras
                    val scanResult = bundle!!.getString("qr_scan_result")
//                    Log.d("main", "onActivityResult: " + scanResult!!)
                    //将扫描出的信息显示出来
                    if (scanResult.indexOf("hcyh618") > 0 || scanResult.indexOf("hshc618") > 0 || scanResult.indexOf("hetianpay") > 0) {
                        val intent1 = Intent(this@MainActivity, ScanWebActivity::class.java)
                        intent1.putExtra("scanweb_url", scanResult)
                        startActivity(intent1)
                    } else {
                        MyToast.showMsg("扫描二维码出错")
                    }
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            outlogin()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun outlogin() {
        var dialog = AlertDialog.Builder(this).setTitle("会员管理").setMessage("是否退出应用").setPositiveButton("确定") { dialog, which ->
            AppManager.getAppManager().AppExit(this@MainActivity)
        }.show()
        Kontlin_Utils.setdialotwidth(this@MainActivity, dialog)
    }

    private fun exit() {
        try {
            deleteDatabase("webview.db")
            deleteDatabase("webviewCache.db")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //WebView 缓存文件
        val appCacheDir = File(filesDir.absolutePath + "123")
        val webviewCacheDir = File(cacheDir.absolutePath + "/webviewCache")
        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir)
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir)
        }
    }

    internal fun deleteFile(file: File) {
        if (file.exists()) {
            if (file.isFile) {
                file.delete()
            } else if (file.isDirectory) {
                val files = file.listFiles()
                for (i in files!!.indices) {
                    deleteFile(files[i])
                }
            }
            file.delete()
        } else {
//            Log.e("mainactivity", "delete file no exists " + file.absolutePath)
        }
    }

    private fun userexit() {
        try {
            deleteDatabase("webview.db")
            deleteDatabase("webviewCache.db")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //WebView 缓存文件
        val appCacheDir = File(filesDir.absolutePath + "userweb")
        val webviewCacheDir = File(cacheDir.absolutePath + "/webviewCache")
        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir)
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir)
        }
    }


}