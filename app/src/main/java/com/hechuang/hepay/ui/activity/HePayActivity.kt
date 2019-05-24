package com.hechuang.hepay.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder
import com.allenliu.versionchecklib.v2.builder.UIData
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.bumptech.glide.Glide
import com.google.zxing.activity.CaptureActivity
import com.hechuang.hepay.R
import com.hechuang.hepay.api.ApiFactory
import com.hechuang.hepay.api.Web_Url
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.bean.VersionBean
import com.hechuang.hepay.bean.WthrcdnBean
import com.hechuang.hepay.customview.BaseHomePopupWindow
import com.hechuang.hepay.customview.CircleMenuLayout
import com.hechuang.hepay.listener.MyLocationListener
import com.hechuang.hepay.persenter.HePayPersenter
import com.hechuang.hepay.util.Eyes
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.util.Utils
import com.hechuang.hepay.view.IHePayView
import kotlinx.android.synthetic.main.activity_hepay.*
import java.io.File
import java.util.*

/**
 * 首页(弃用)
 */
class HePayActivity : BaseAppCompatActivity<HePayPersenter>(), IHePayView, View.OnClickListener {
    var mLocationClient: LocationClient? = null
    var myListener: BDLocationListener?=null
//    @BindView(R.id.id_menulayout)
//    internal var mCircleMenuLayout: CircleMenuLayout? = null
//    @BindView(R.id.hepay_city)
//    internal var mCity: TextView? = null
//    @BindView(R.id.hepay_home)
//    internal var hepay_home: ImageView? = null
//    @BindView(R.id.hepay_notify)
//    internal var hepay_notify: ImageView? = null
//    @BindView(R.id.hepay_shopping)
//    internal var hepay_shopping: ImageView? = null
//    @BindView(R.id.hepay_mine)
//    internal var hepay_mine: ImageView? = null
//    @BindView(R.id.hepay_title_img)
//    internal var hepay_title_img: ImageView? = null
//    @BindView(R.id.hepay_w)
//    internal var hepay_wthrckn: TextView? = null
//    @BindView(R.id.hepay_tianqi)
//    internal var hepay_tianqi_icon: ImageView? = null
//    @BindView(R.id.hepay_dingwei_layout)
//    internal var mdingwei_layout: LinearLayout? = null
    private val mItemTexts = arrayOf("联盟商家", "会员中心", "积分商城", "啦蜂", "社区商城")
    private val mItemImgs = intArrayOf(

            R.drawable.unshop, R.drawable.user, R.drawable.integral, R.drawable.home_facesun, R.drawable.hcshop)
    private var mtitle_string: MutableList<String>? = null
    private var sp: SharedPreferences? = null

    internal var mHandler: Handler?=null

    internal var popupWindow: BaseHomePopupWindow? = null

    override fun initlayout(): Int {
        return R.layout.activity_hepay
    }

    override fun initPersenter() {
        mPersenter = HePayPersenter(this, this)
    }

    override fun initView() {
        exit()
        sHePayActivity = this
        ButterKnife.bind(this)
        mHandler = Handler()
        mtitle_string = ArrayList()
        mtitle_string!!.add("商品分类")
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        mLocationClient = LocationClient(applicationContext)
        initLocation()
        myListener = MyLocationListener()
        mLocationClient!!.registerLocationListener(myListener)
        hepay_home!!.setOnClickListener(this)
        hepay_notify!!.setOnClickListener(this)
        hepay_shopping!!.setOnClickListener(this)
        hepay_mine!!.setOnClickListener(this)
        hepay_title_img!!.setOnClickListener(this)
        setimgrotaing()
        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_appbar))
        if (Build.VERSION.SDK_INT >= 23) {
            val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                    this@HePayActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
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
        if (Build.VERSION.SDK_INT >= 23) {
            val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                    this@HePayActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 111)
            } else {
                mPersenter!!.getversion()
            }
        } else {
            mPersenter!!.getversion()
        }
        if (Build.VERSION.SDK_INT >= 26) {
            val b = packageManager.canRequestPackageInstalls()
            if (!b) {
                requestPermissions(arrayOf(Manifest.permission.REQUEST_INSTALL_PACKAGES), 222)
            } else {
            }
        } else {
        }
        id_menulayout!!.setMenuItemIconsAndTexts(mItemImgs, mItemTexts)
        id_menulayout!!.setOnMenuItemClickListener(object : CircleMenuLayout.OnMenuItemClickListener {

            override fun itemClick(view: View, pos: Int) {
                //                Log.d(TAG, "itemClick: " + pos);
                when (pos) {
                    4 -> if (Utils.checkPackInfo(this@HePayActivity, "com.feidu.hechaung_img")) {
                        val intent2 = Intent()
                        intent2.setClassName("com.feidu.hechaung_img", "com.feidu.hechaung_img.ui.activity.MainActivity")
                        intent2.putExtra("name", UserData.username)
                        startActivity(intent2)
                    } else {
                        val uri = Uri.parse("https://www.pgyer.com/hechuangyinghang")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                    0 -> startActivity(Intent(this@HePayActivity, UnionShop_Activity::class.java))
                    1 ->
                        //                        Log.d(TAG, "itemClick: " + UserData.islogin);
                        if (UserData.islogin) {
                            var url = ""
                            if (UserData.namestatus == "0") {
                                url = UserData.isurl
                            } else {
                                url = ApiFactory.HOST
                            }
                            Utils.startwebactivity(this@HePayActivity, url)
                        } else
                            startActivity(Intent(this@HePayActivity, LoginActivity::class.java))
                    2 -> Utils.startwebactivity(this@HePayActivity, Web_Url.HOME)
                    3 -> if (Utils.checkPackInfo(this@HePayActivity, "com.hechuang.labeego"))
                        Utils.startapp(this@HePayActivity, "com.hechuang.labeego")
                    else {
                        val uri = Uri.parse("http://lafeng.hetianpay.com/public/Appload/App_down.html")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                }
            }

            override fun itemCenterClick(view: View) {
                if (Build.VERSION.SDK_INT >= 23) {
                    val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                            this@HePayActivity, Manifest.permission.CAMERA)
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.CAMERA), 321)
                    } else {
                        val intent = Intent(this@HePayActivity, CaptureActivity::class.java)
                        startActivityForResult(intent, REQUEST_CODE)
                    }
                } else {
                    val intent = Intent(this@HePayActivity, CaptureActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        })
        //        getMPersenter().getname();
        hepay_city!!.setOnClickListener(this)
        if (Utils.isExistMainActivity(this@HePayActivity, WelcomeAcitivity::class.java)) {
            WelcomeAcitivity.mWelcomeAcitivity.finish()
        }

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
                for (i in files.indices) {
                    deleteFile(files[i])
                }
            }
            file.delete()
        } else {
            //            Log.e("mainactivity", "delete file no exists " + file.getAbsolutePath());
        }
    }

    //    protected String appId = "11212186";
    //
    //    protected String appKey = "DbNKXfU3Za11gAwcyHKbM2cp";
    //
    //    protected String secretKey = "liEHOaFONkTERKxMnICClRO1oBRdUhAE";
    //
    //    private void ceshibaidu() {
    //        LoggerProxy.printable(true);
    //        SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();
    //        mSpeechSynthesizer.setContext(this);
    //        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
    //            @Override
    //            public void onSynthesizeStart(String s) {
    //                Log.d(TAG, "onSynthesizeStart: " + s);
    //            }
    //
    //            @Override
    //            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
    //                Log.d(TAG, "onSynthesizeDataArrived: " + s);
    //            }
    //
    //            @Override
    //            public void onSynthesizeFinish(String s) {
    //                Log.d(TAG, "onSynthesizeFinish: " + s);
    //            }
    //
    //            @Override
    //            public void onSpeechStart(String s) {
    //                Log.d(TAG, "onSpeechStart: " + s);
    //            }
    //
    //            @Override
    //            public void onSpeechProgressChanged(String s, int i) {
    //                Log.d(TAG, "onSpeechProgressChanged: " + s + "\n" + i);
    //            }
    //
    //            @Override
    //            public void onSpeechFinish(String s) {
    //                Log.d(TAG, "onSpeechFinish: " + s);
    //            }
    //
    //            @Override
    //            public void onError(String s, SpeechError speechError) {
    //                Log.d(TAG, "onError: " + s + "\n" + speechError);
    //            }
    //        });
    //        mSpeechSynthesizer.setAppId(appId);
    //        mSpeechSynthesizer.setApiKey(appKey, secretKey);
    //        mSpeechSynthesizer.auth(TtsMode.MIX);
    //        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置发声的人声音，在线生效
    //        // 设置合成的音量，0-9 ，默认 5
    //        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
    //        // 设置合成的语速，0-9 ，默认 5
    //        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
    //        // 设置合成的语调，0-9 ，默认 5
    //        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
    //        mSpeechSynthesizer.initTts(TtsMode.MIX); // 初始化离在线混合模式，如果只需要在线合成功能，使用 TtsMode.ONLINE
    //        mSpeechSynthesizer.speak("禾田付到账100万");
    //    }

    private fun setimgrotaing() {
        val bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.home)
        val bitma2 = Utils.rotaingImageView(-15, bitmap1)
        hepay_home!!.setImageBitmap(bitma2)
        val bitmap_mine1 = BitmapFactory.decodeResource(resources, R.drawable.my)
        val bitmap_mine2 = Utils.rotaingImageView(15, bitmap_mine1)
        hepay_mine!!.setImageBitmap(bitmap_mine2)
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

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
        initLocation()
        myListener = MyLocationListener()
        mLocationClient!!.registerLocationListener(myListener)
        if (UserData.zhuan_username != null && UserData.zhuan_username != "" && UserData.zhuan_username != UserData.username) {//用于对比账号
            UserData.zhuan_username = ""
            android.support.v7.app.AlertDialog.Builder(this).setMessage("登录账号与禾田付不同，是否更换账号？").setPositiveButton("确定") { dialog, which ->
                sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                val editor = sp!!.edit()
                editor.clear()
                editor.apply()
                startActivity(Intent(this@HePayActivity, LoginActivity::class.java))
                finish()
            }.setNegativeButton("取消", null).show()
        }
    }

    private fun location() {
        mLocationClient!!.start()
        mLocationClient!!.registerLocationListener { bdLocation ->
            when (bdLocation.locType) {
                161 -> {
                    mLocationClient!!.stop()
                    hepay_city!!.text = UserData.city
                    mPersenter!!.getcitywthrcdn(UserData.city)
                }
                61 -> {
                    mLocationClient!!.stop()
                    hepay_city!!.text = UserData.city
                    mPersenter!!.getcitywthrcdn(UserData.city)
                }
                else -> {
                    hepay_dingwei_layout!!.visibility = View.INVISIBLE
                    UserData.province = ""
                    UserData.city = ""
                    UserData.discrict = ""
                    MyToast.showMsg("定位失败")
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.hepay_city//选择城市
            ->
                //                if (hasSDCard && istruesd)
                city_puanxian()
            R.id.hepay_home//底部首页
            ->
                //                Utils.startwebactivity(HePayActivity.this, Web_Url.HOME);
                // 直接授权登录京东​
                //                LocationClientOption locationClientOption = new LocationClientOption();
                //                locationClientOption.isOpenGps();
                startActivity(Intent(this@HePayActivity, AllOrderAcitivity::class.java))
            R.id.hepay_notify//底部消息
            -> startActivity(Intent(this@HePayActivity, NoticeActivity::class.java))
            R.id.hepay_shopping//底部购物车
            -> if (UserData.islogin) {
                Utils.startwebactivity(this@HePayActivity, Web_Url.SHOPPING)
            } else {
                startActivity(Intent(this@HePayActivity, LoginActivity::class.java))
            }
            R.id.hepay_mine//底部个人中心
            ->
                //                Log.d(TAG, "onClick: " + UserData.islogin);
                if (UserData.islogin) {
                    Utils.startwebactivity(this@HePayActivity, Web_Url.ME_URL)
                } else {
                    startActivity(Intent(this@HePayActivity, LoginActivity::class.java))
                }
            R.id.hepay_title_img//首页弹出框
            -> showpopup()
            R.id.homepopup_shangpinfenlei -> {
                Utils.startwebactivity(this, ApiFactory.HOST + "index.php/Home/Product/category")
                if (popupWindow != null && popupWindow!!.isShowing) popupWindow!!.dismiss()
            }
        }//                else
        //                    MyToast.showMsg("正在查询省市数据，请稍后");
        //                if (ContextCompat.checkSelfPermission(
        //                        HePayActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //                    MyToast.showMsg("定位权限未开启");
        //                } else {
        //                    if (Utils.isOPen(this)) {
        //                        Intent intent = new Intent(HePayActivity.this, BaiDuMapActivity.class);
        //                        startActivity(intent);
        //                    } else {
        ////                        new LocationClientOption().setOpenGps(true);
        //                        MyToast.showMsg("定位失败,请尝试打开位置信息（gps）和位置权限后重试");
        //                    }
        //                }
        //                ARouter.getInstance().build("/activity/home").navigation();
    }

    /**
     * 显示首页弹出框
     */
    private fun showpopup() {
        val view = LayoutInflater.from(this).inflate(R.layout.view_homepopup, null)
        val mshangpin = view.findViewById<TextView>(R.id.homepopup_shangpinfenlei)
        mshangpin.setOnClickListener(this)
        popupWindow = BaseHomePopupWindow(this)
        popupWindow!!.contentView = view
        popupWindow!!.showAsDropDown(hepay_title_img)
    }

    /**
     * 检查权限并跳转到选择城市列表
     */
    fun city_puanxian() {
        val intent3 = Intent(this@HePayActivity, SelectCityActivity::class.java)
        intent3.putExtra("type", 1)
        startActivityForResult(intent3, USERINFOADDRESS)
        //            }
        //        } else {
        //            ARouter.getInstance().build("/activity/selectcity").withInt("type", 1)
        //                    .navigation(this, USERINFOADDRESS);
        ////            Intent intent3 = new Intent(HePayActivity.this, SelectCityActivity.class);
        ////            intent3.putExtra("type", 1);
        ////            startActivityForResult(intent3, USERINFOADDRESS);
        //        }
    }

    //    private void checkDB() {
    //        SharedPreferences.Editor editor = sp.edit();
    //        editor.putInt("first", 2);
    //        editor.commit();
    //        hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    //        if (hasSDCard) {
    //            istruesd = new Utils().hasDb(this, "hechuang.db");
    //        } else {
    //        }
    //    }

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
        mLocationClient!!.locOption = option
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            123 -> for (ste in permissions) {
                when (ste) {
                    "android.permission.ACCESS_COARSE_LOCATION" -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        mLocationClient!!.restart()
                        //                            Log.d(TAG, "onRequestPermissionsResult: " + "android.permission.ACCESS_COARSE_LOCATION");
                    } else {
                        MyToast.showMsg("定位权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                    }
                    "android.permission.ACCESS_FINE_LOCATION" -> if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        mLocationClient!!.restart()
                        //                            Log.d(TAG, "onRequestPermissionsResult: " + "android.permission.ACCESS_FINE_LOCATION");
                    } else {
                        MyToast.showMsg("定位权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                    }
                }
            }
            321 -> for (permission in permissions) {
                when (permission) {
                    "android.permission.CAMERA" -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        val intent = Intent(this@HePayActivity, CaptureActivity::class.java)
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

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            USERINFOADDRESS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    val selectProvince = intent.getStringExtra("province")
                    val selectCity = intent.getStringExtra("city")
                    val selectDistrict = intent.getStringExtra("district")
                    hepay_city!!.text = selectDistrict
                }
            }
            REQUEST_CODE -> if (resultCode == RESULT_CODE_QR_SCAN) { //RESULT_OK = -1
                val bundle = intent.extras
                val scanResult = bundle!!.getString("qr_scan_result")
                //                    Log.d(TAG, "onActivityResult: " + scanResult);
                //将扫描出的信息显示出来
                if (scanResult!!.indexOf("hcyh618") > 0 || scanResult.indexOf("hshc618") > 0 || scanResult.indexOf("hetianpay") > 0) {
                    val intent1 = Intent(this@HePayActivity, ScanWebActivity::class.java)
                    intent1.putExtra("scanweb_url", scanResult)
                    startActivity(intent1)
                } else {
                    MyToast.showMsg("扫描二维码出错")
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
        AlertDialog.Builder(this).setTitle("会员管理").setMessage("是否退出应用").setPositiveButton("确定") { dialog, which -> finish() }.show()
    }

    override fun getwthrcdnok(wthrcdnBean: WthrcdnBean.DataBean) {
        //        Log.d(TAG, "getwthrcdnok: " + wthrcdnBean.getForecast().get(0).getType());
        val tianqi = wthrcdnBean.forecast[0].type
        hepay_w!!.text = tianqi + "   " + wthrcdnBean.wendu + "℃"
        if (tianqi.indexOf("雨") > 0 || tianqi.indexOf("雪") > 0 || tianqi == "雨" || tianqi == "雪") {
            setimgicon_tianqi(R.drawable.yuxue, hepay_tianqi)
        } else if (tianqi.length <= 2 && tianqi.indexOf("晴") > 0 || tianqi == "晴") {
            setimgicon_tianqi(R.drawable.sunny, hepay_tianqi)
        } else if (tianqi.length <= 2 && tianqi.indexOf("阴") > 0 || tianqi == "阴") {
            setimgicon_tianqi(R.drawable.overcast, hepay_tianqi)
        } else if (tianqi.indexOf("多云") > 0 || tianqi == "多云") {
            setimgicon_tianqi(R.drawable.cloudy, hepay_tianqi)
        } else {
            setimgicon_tianqi(R.drawable.teshu, hepay_tianqi)
        }
    }

    private fun setimgicon_tianqi(teshu: Int, icon: ImageView?) {
        Glide.with(this).load(teshu).override(30, 30).into(icon!!)
    }


    override fun getversion(versionBean: VersionBean) {
        //        Log.d("hepay", versionBean.toString());
        if (versionBean.status == "1") {
            val int_version = Integer.valueOf(versionBean.versionName)
            //            Log.d("hepay", int_version + "  " + Utils.getVersionCode(HePayActivity.this));
            if (int_version > Utils.getVersionCode(this@HePayActivity)) {
                when (versionBean.browser) {
                    "1" -> {
                        val isgengxin: Boolean
                        if (versionBean.auto == "1") {
                            isgengxin = true
                        } else {
                            isgengxin = false
                        }
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
                }//                        Intent intent2 = new Intent(HePayActivity.this, DownLoadFileService.class);
                //                        intent2.putExtra("url", "http://apk.hshc618.com/hetianpay.apk");
                //                        intent2.putExtra("type", "apk");

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
        //
        //
        //            }
        //
        //            override fun getCustomDownloadingDialog(context: Context?, progress: Int, versionBundle: UIData?): Dialog {
        //            }
        //        }
        bundle.excuteMission(this)
    }

    override fun getname(namestr: Array<String>) {
        //        id_menulayout.setMenuItemIconsAndTexts(mItemImgs, namestr);
        //        id_menulayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
        //
        //            @Override
        //            public void itemClick(View view, int pos) {
        ////                Log.d(TAG, "itemClick: " + pos);
        //                switch (pos) {
        //                    case 0:
        //                        if (Utils.checkPackInfo(HePayActivity.this, "com.feidu.hechaung_img")) {
        //                            Intent intent2 = new Intent();
        //                            intent2.setClassName("com.feidu.hechaung_img", "com.feidu.hechaung_img.ui.activity.MainActivity");
        //                            intent2.putExtra("name", UserData.username);
        //                            startActivity(intent2);
        //                        } else {
        //                            Uri uri = Uri.parse("https://www.pgyer.com/hechuangyinghang");
        //                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //                            startActivity(intent);
        //                        }
        //                        break;
        //                    case 1:
        //                        startActivity(new Intent(HePayActivity.this, UnionShop_Activity.class));
        //                        break;
        //                    case 2:
        //                        if (UserData.islogin)
        //                            Utils.startwebactivity(HePayActivity.this, Web_Url.ME_URL);
        //                        else
        //                            startActivity(new Intent(HePayActivity.this, LoginActivity.class));
        //                        break;
        //                    case 3:
        //                        Utils.startwebactivity(HePayActivity.this, Web_Url.HOME);
        //                        break;
        //                    case 4:
        //                        if (Utils.checkPackInfo(HePayActivity.this, "com.hechuang.labeego"))
        //                            Utils.startapp(HePayActivity.this, "com.hechuang.labeego");
        //                        else {
        //                            Uri uri = Uri.parse("http://lafeng.hetianpay.com/public/Appload/App_down.html");
        //                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //                            startActivity(intent);
        //                        }
        //                        break;
        //                }
        //            }
        //
        //            @Override
        //            public void itemCenterClick(View view) {
        //                if (Build.VERSION.SDK_INT >= 23) {
        //                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(
        //                            HePayActivity.this, Manifest.permission.CAMERA);
        //                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
        //                        requestPermissions(new String[]{
        //                                Manifest.permission.CAMERA}, 321);
        //                        return;
        //                    } else {
        //                        Intent intent = new Intent(HePayActivity.this, CaptureActivity.class);
        //                        startActivityForResult(intent, REQUEST_CODE);
        //                    }
        //                } else {
        //                    Intent intent = new Intent(HePayActivity.this, CaptureActivity.class);
        //                    startActivityForResult(intent, REQUEST_CODE);
        //                }
        //            }
        //        });
    }

    override fun getnameerror() {
        //        id_menulayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        //        id_menulayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
        //
        //            @Override
        //            public void itemClick(View view, int pos) {
        ////                Log.d(TAG, "itemClick: " + pos);
        //                switch (pos) {
        //                    case 0:
        //                        if (Utils.checkPackInfo(HePayActivity.this, "com.feidu.hechaung_img")) {
        //                            Intent intent2 = new Intent();
        //                            intent2.setClassName("com.feidu.hechaung_img", "com.feidu.hechaung_img.ui.activity.MainActivity");
        //                            intent2.putExtra("name", UserData.username);
        //                            startActivity(intent2);
        //                        } else {
        //                            Uri uri = Uri.parse("https://www.pgyer.com/hechuangyinghang");
        //                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //                            startActivity(intent);
        //                        }
        //                        break;
        //                    case 1:
        //                        startActivity(new Intent(HePayActivity.this, UnionShop_Activity.class));
        //                        break;
        //                    case 2:
        //                        if (UserData.islogin)
        //                            Utils.startwebactivity(HePayActivity.this, Web_Url.ME_URL);
        //                        else
        //                            startActivity(new Intent(HePayActivity.this, LoginActivity.class));
        //                        break;
        //                    case 3:
        //                        Utils.startwebactivity(HePayActivity.this, Web_Url.HOME);
        //                        break;
        //                    case 4:
        //                        if (Utils.checkPackInfo(HePayActivity.this, "com.hechuang.labeego"))
        //                            Utils.startapp(HePayActivity.this, "com.hechuang.labeego");
        //                        else {
        //                            Uri uri = Uri.parse("http://lafeng.hetianpay.com/public/Appload/App_down.html");
        //                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //                            startActivity(intent);
        //                        }
        //                        break;
        //                }
        //            }
        //
        //            @Override
        //            public void itemCenterClick(View view) {
        //                if (Build.VERSION.SDK_INT >= 23) {
        //                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(
        //                            HePayActivity.this, Manifest.permission.CAMERA);
        //                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
        //                        requestPermissions(new String[]{
        //                                Manifest.permission.CAMERA}, 321);
        //                        return;
        //                    } else {
        //                        Intent intent = new Intent(HePayActivity.this, CaptureActivity.class);
        //                        startActivityForResult(intent, REQUEST_CODE);
        //                    }
        //                } else {
        //                    Intent intent = new Intent(HePayActivity.this, CaptureActivity.class);
        //                    startActivityForResult(intent, REQUEST_CODE);
        //                }
        //            }
        //        });
    }

    companion object {
        var sHePayActivity: HePayActivity = HePayActivity()
        val USERINFOADDRESS = 2222
        //打开扫描界面请求码
        private val REQUEST_CODE = 0x01
        private val TAG = "HePayActivity"

        val RESULT_CODE_QR_SCAN = 0xA1
    }

}
