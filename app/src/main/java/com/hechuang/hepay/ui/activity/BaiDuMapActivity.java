package com.hechuang.hepay.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.BaiDuMap_Popup_Adapter;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.base.RvListener;
import com.hechuang.hepay.bean.BaiduListBean;
import com.hechuang.hepay.customview.RedPacketPopupWindow;
import com.hechuang.hepay.persenter.BaiDuMapPersenter;
import com.hechuang.hepay.util.Eyes;
import com.hechuang.hepay.util.KeyBoardUtils;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.util.Utils;
import com.hechuang.hepay.view.IBaiDuMapView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class BaiDuMapActivity extends BaseAppCompatActivity<BaiDuMapPersenter> implements IBaiDuMapView, View.OnClickListener {
    @BindView(R.id.baidumap_map)
    MapView mapview;
    private BaiduMap mBaiduMap;
    @BindView(R.id.baidumap_et)
    TextView baidu_et;
    @BindView(R.id.baidumap_bt)
    Button baidu_bt;
    @BindView(R.id.baidumap_city)
    TextView mCity_tx;
    @BindView(R.id.baidumap_dingwei)
    ImageView mDingwei;
    @BindView(R.id.baidumap_fanhui)
    Button map_fanhui;
    @BindView(R.id.baidumap_button_address)
    TextView map_button_address;
    @BindView(R.id.baidumap_button_name)
    TextView map_button_name;
    @BindView(R.id.baidumap_button_ok)
    Button map_button_ok;
    @BindView(R.id.baidumap_city_layout)
    LinearLayout city_layout;
    private List<Poi> mPoiList;
    public LocationClient mLocationClient = null;
    private static final String TAG = "BaiDuMapActivity";
    private String chu_province;
    private String chu_city;
    private String chu_count;
    private String province;
    private String city;
    private String count;
    private String address;
    private double latitude;
    private double lontitude;
    private int BAIDUMAP_REQULE = 884;
    private String name;
    private LatLng mLatLng;//用于记录选择之后的位置

    LinearLayout popup_city_layout;
    TextView popup_city;
    EditText popup_store;
    Button popup_quxiao;
    RecyclerView popup_recycler;
    TextView popup_noting;

    RedPacketPopupWindow mBaseHomePopupWindow;
    List<BaiduListBean> mBaiduListBeans;
    @Override
    protected int initlayout() {
        SDKInitializer.initialize(getApplicationContext());
        return R.layout.activity_baidumap;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new BaiDuMapPersenter(this, this));
    }

    @Override
    protected void initView() {
        Eyes.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.white));
        ButterKnife.bind(this);
        map_fanhui.setOnClickListener(this);
        mDingwei.setTranslationY(Utils.dip2px(this, -15));
        baidu_bt.setOnClickListener(this);
        city_layout.setOnClickListener(this);
        map_button_ok.setOnClickListener(this);
        mLocationClient = new LocationClient(getApplicationContext());
        initLocation();
        mLocationClient.registerLocationListener(baiduDbListener);
        location();
    }


    private void location() {
        mLocationClient.start();
        mLocationClient.registerLocationListener(bdLocation -> {
            Log.d(TAG, "location: " + bdLocation.getLocType());
            switch (bdLocation.getLocType()) {
                case 161:
                    mLocationClient.stop();
                    startmaps();
                    dissmissloading();
                    break;
                case 61:
                    mLocationClient.stop();
                    startmaps();
                    dissmissloading();
                    break;
                default:
                    MyToast.showMsg("定位失败,请查看位置服务（gps）是否开启");
                    dissmissloading();
                    break;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baidumap_bt:
                finish();
                break;
            case R.id.baidumap_fanhui:
                showloading();
                location();
                break;
            case R.id.baidumap_city_layout:
                Intent intent = new Intent(BaiDuMapActivity.this, SelectCityActivity.class);
                startActivityForResult(intent, BAIDUMAP_REQULE);
                break;
            case R.id.baidumap_popup_city_layout:
                Intent intent2 = new Intent(BaiDuMapActivity.this, SelectCityActivity.class);
                startActivityForResult(intent2, BAIDUMAP_REQULE);
                break;
            case R.id.baidumap_popup_city:
                Intent intent3 = new Intent(BaiDuMapActivity.this, SelectCityActivity.class);
                startActivityForResult(intent3, BAIDUMAP_REQULE);
                break;
            case R.id.baidumap_popup_bt:
                if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing())
                    mBaseHomePopupWindow.dismiss();
                province = chu_province;
                city = chu_city;
                count = chu_count;
                break;
            case R.id.baidumap_button_ok:
                if (city.equals("") || count.equals("") || name.equals("")) {
                    MyToast.showMsg("地址检索出错，请重试");
                } else {
                    getMPersenter().postdata(province, city, count, address, name);
                }
                break;
        }

    }

    /**
     * 显示弹出框
     */
    private void showpopup(String mcity, String keyv) {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_baidumap, null);
        popup_city = view.findViewById(R.id.baidumap_popup_city);
        popup_city_layout = view.findViewById(R.id.baidumap_popup_city_layout);
        popup_city_layout.setOnClickListener(this);
        popup_city.setOnClickListener(this);
        popup_quxiao = view.findViewById(R.id.baidumap_popup_bt);
        popup_store = view.findViewById(R.id.baidumap_popup_et);
        popup_recycler = view.findViewById(R.id.baidumap_popup_recycler1);
        popup_noting = view.findViewById(R.id.baidumap_popup_noting);
        popup_recycler.setLayoutManager(new LinearLayoutManager(this));
        popup_quxiao.setOnClickListener(this);
        mBaseHomePopupWindow = new RedPacketPopupWindow(this);
        mBaseHomePopupWindow.setContentView(view);
        mBaseHomePopupWindow.showAsDropDown(baidu_bt);
        startpoi(mcity, keyv);
        popup_store.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                startpoi(city, baidu_et.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
//                startpoi(city, popup_store.getText().toString().trim());
                SuggestionSearch(city, popup_store.getText().toString().trim());

            }
        });
        popup_store.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (popup_store.getText().toString().trim() != null && popup_store.getText().toString().trim().equals(""))
                        SuggestionSearch(city, popup_store.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
        popup_city.setText(mcity);
    }

    /**
     * 开启地图
     */
    private void startmaps() {
        baidu_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopup(city, count);
            }
        });
        View child = mapview.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        mapview.showScaleControl(false);
        mapview.showZoomControls(false);
        mBaiduMap = mapview.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        UiSettings muisetting = mBaiduMap.getUiSettings();
        muisetting.setOverlookingGesturesEnabled(false);//禁用俯视功能
        muisetting.setRotateGesturesEnabled(false);//禁用旋转功能
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(0)
                .latitude(latitude)
                .longitude(lontitude).build();
        mBaiduMap.setMyLocationData(locData);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.baidumap_chushi);
        MyLocationConfiguration myLocation = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, false,
                bitmapDescriptor,
                0xAAFFFF88,
                0xAA00FF00
        );
        mBaiduMap.setMyLocationConfiguration(myLocation);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        LatLng latLng = new LatLng(latitude, lontitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(latLng, 17.0f);
        mBaiduMap.animateMapStatus(u);
//        redius(latLng);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng latLng1 = mapStatus.target;
//                Log.d(TAG, "onMapStatusChangeFinish: " + latLng1.latitude + "   " + latLng1.longitude);
                GeoCoder geoCoder = GeoCoder.newInstance();
                // 设置反地理经纬度坐标,请求位置时,需要一个经纬度
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng1));
//                Log.d(TAG, "onMapStatusChangeFinish: " + is);
                // 设置地址或经纬度反编译后的监听,这里有两个回调方法
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

                        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        } else {
                            if (result.getSematicDescription() == null || result.getSematicDescription().equals("")) {
//                                MyToast.showMsg("位置信息获取失败，请重试");
                                province = "";
                                city = "";
                                count = "";
                                name = "";
                                address = "";
                                mCity_tx.setText("unknown");
                                map_button_name.setText("unknown");
                                map_button_address.setText("");
                                return;
                            }
                            if (result.getAddressDetail().province == null) {
                                province = "";
                            } else {
                                province = result.getAddressDetail().province;
                            }
                            if (result.getAddressDetail().city == null)
                                city = "";
                            else
                                city = result.getAddressDetail().city;
                            if (result.getAddressDetail().district == null)
                                count = "";
                            else
                                count = result.getAddressDetail().district;
                            mCity_tx.setText(city);
//                            Log.d(TAG, "onGetReverseGeoCodeResult: " +
//                                    result.getAddressDetail().street + "\n" +
//                                    result.getPoiList().get(0).name + "\n" +
//                            );
                            if (result.getPoiList() == null || result.getPoiList().size() == 0) {
                            } else {
                                if (result.getPoiList().get(0).address == null)
                                    address = "";
                                else
                                    address = result.getPoiList().get(0).address;
                                if (result.getPoiList().get(0).name == null)
                                    name = "";
                                else
                                    name = result.getPoiList().get(0).name;
                                String pcc = province + city + count;
                                String province_str;
                                String city_str;
//                            Log.d(TAG, "onGetReverseGeoCodeResult: " + province + "\n" + city + "\n" + count + "\n" + address);
                                if (address.length() > pcc.length()) {
                                    if (province.contains("省")) {
                                        province_str = province.substring(0, province.length() - 1);
                                    } else {
                                        province_str = province;
                                    }
                                    if (address.contains(province_str)) {
                                        String province_2 = address.substring(0, province_str.length() + 1);
//                                        if (address.substring(province_str.length() + 1).contains("省"))
//                                            address = address.substring(province.length());
//                                        else
//                                            address = address.substring(province_str.length());
//                                    Log.d(TAG, "onGetReverseGeoCodeResult: " + address);
                                        if (province_2.contains("省")) address = address.substring(province.length());
                                        else address = address.substring(province_str.length());
                                    }
                                    Log.d(TAG, "onGetReverseGeoCodeResult: " + province_str + "  " + address);
                                    if (city.contains("市")) {
                                        city_str = city.substring(0, city.length() - 1);
                                    } else {
                                        city_str = city;
                                    }
                                    if (address.contains(city_str)) {
                                        String city_2 = address.substring(0, city_str.length() + 1);

//                                    Log.d(TAG, "onGetReverseGeoCodeResult: " + city_2 + "  " + address);
                                        if (city_2.contains("市"))
                                            address = address.substring(city.length());
                                        else
                                            address = address.substring(city_str.length());
                                    }

                                    Log.d(TAG, "onGetReverseGeoCodeResult: " + city_str + "  " + address);
                                    if (address.contains(count)) {
                                        address = address.substring(count.length());
                                    }
                                }
                                map_button_name.setText(name);
                                map_button_address.setText(address);
                            }
                        }
                    }

                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult result) {
                        // 详细地址转换在经纬度

                    }
                });
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng1));
            }
        });
    }

    /**
     * 热词poi搜索
     *
     * @param keyword
     */
    private void SuggestionSearch(String mcity, String keyword) {
        SuggestionSearch suggestionSearch = SuggestionSearch.newInstance();
        OnGetSuggestionResultListener listener = suggestionResult -> {
//            Log.d(TAG, "SuggestionSearch: " + suggestionResult.error + city + keyword);
            if (mBaiduListBeans != null && mBaiduListBeans.size() > 0) {
                mBaiduListBeans.clear();
            } else {
                mBaiduListBeans = new ArrayList<>();
            }
            if (suggestionResult.getAllSuggestions() != null && suggestionResult.getAllSuggestions() != null) {
                for (int i = 0; i < suggestionResult.getAllSuggestions().size(); i++) {
                    BaiduListBean baiduListBean = new BaiduListBean(suggestionResult.getAllSuggestions().get(i).uid, suggestionResult.getAllSuggestions().get(i).key
                            , suggestionResult.getAllSuggestions().get(i).city + suggestionResult.getAllSuggestions().get(i).district
                            , suggestionResult.getAllSuggestions().get(i).pt == null ? 0 : suggestionResult.getAllSuggestions().get(i).pt.latitude
                            , suggestionResult.getAllSuggestions().get(i).pt == null ? 0 : suggestionResult.getAllSuggestions().get(i).pt.longitude);
                    mBaiduListBeans.add(baiduListBean);
                }
                if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing()) {
                    popup_recycler.setVisibility(View.VISIBLE);
                    popup_noting.setVisibility(View.GONE);
                    BaiDuMap_Popup_Adapter adapter = new BaiDuMap_Popup_Adapter(mBaiduListBeans, BaiDuMapActivity.this, new RvListener() {
                        @Override
                        public void onItemListener(int id, int position) {
                            KeyBoardUtils.closeKeyBoard(popup_store);
                            mLatLng = new LatLng(mBaiduListBeans.get(position).getLat(), mBaiduListBeans.get(position).getLon());
                            if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing()) {
                                mBaseHomePopupWindow.dismiss();
                            }
                            getMyLocation();
                            zhuandizhi(new LatLng(mBaiduListBeans.get(position).getLat(), mBaiduListBeans.get(position).getLon()));
                            resultinfo(mBaiduListBeans.get(position).getUid());
                        }
                    });
                    popup_recycler.setAdapter(adapter);
                }
            } else {
                if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing()) {
                    popup_recycler.setVisibility(View.GONE);
                    popup_noting.setVisibility(View.VISIBLE);
                }
            }
        };
        suggestionSearch.setOnGetSuggestionResultListener(listener);
        suggestionSearch.requestSuggestion(new SuggestionSearchOption().keyword(keyword).city(mcity));
        suggestionSearch.destroy();
    }

    /**
     * 详细商家
     */
    private void resultinfo(String uid) {
        PoiSearch mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

            public void onGetPoiResult(PoiResult result) {

            }

            public void onGetPoiDetailResult(PoiDetailResult result) {
                if (result.name == null) {
                    name = "";
                } else
                    name = result.name;
                if (result.address == null) {
                    address = "";
                } else
                    address = result.address;
                String pcc = province + city + count;
                String province_str;
                String city_str;
//                            Log.d(TAG, "onGetReverseGeoCodeResult: " + province + "\n" + city + "\n" + count + "\n" + address);
                if (address.length() > pcc.length()) {
                    if (province.contains("省")) {
                        province_str = province.substring(0, province.length() - 1);
                    } else {
                        province_str = province;
                    }
                    if (address.contains(province_str)) {
                        String province_2 = address.substring(0, province_str.length() + 1);
//                                        if (address.substring(province_str.length() + 1).contains("省"))
//                                            address = address.substring(province.length());
//                                        else
//                                            address = address.substring(province_str.length());
//                                    Log.d(TAG, "onGetReverseGeoCodeResult: " + address);
                        if (province_2.contains("省")) address = address.substring(province.length());
                        else address = address.substring(province_str.length());
                    }
//                    Log.d(TAG, "onGetReverseGeoCodeResult: " + province_str + "  " + address);
                    if (city.contains("市")) {
                        city_str = city.substring(0, city.length() - 1);
                    } else {
                        city_str = city;
                    }
                    if (address.contains(city_str)) {
                        String city_2 = address.substring(0, city_str.length() + 1);

//                                    Log.d(TAG, "onGetReverseGeoCodeResult: " + city_2 + "  " + address);
                        if (city_2.contains("市"))
                            address = address.substring(city.length());
                        else
                            address = address.substring(city_str.length());
                    }

//                    Log.d(TAG, "onGetReverseGeoCodeResult: " + city_str + "  " + address);
                    if (address.contains(count)) {
                        address = address.substring(count.length());
                    }
                }
                map_button_name.setText(name);
                map_button_address.setText(address);
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(uid));
    }

    /**
     * 坐标转地址
     */
    private void zhuandizhi(LatLng latLng) {
        GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {

            public void onGetGeoCodeResult(GeoCodeResult result) {

                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                }

                //获取地理编码结果
            }

            @Override

            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                }

                city = result.getAddressDetail().city;
                count = result.getAddressDetail().district;
                province = result.getAddressDetail().province;
                chu_city = city;
                chu_count = count;
                chu_province = province;
                mCity_tx.setText(city);
                //获取反向地理编码结果
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(latLng));
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");
        //可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(0);
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        //可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(false);
        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setWifiCacheTimeOut(5 * 60 * 1000);
//可选，7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        mLocationClient.setLocOption(option);
    }

    public void getMyLocation() {
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(mLatLng);
        mBaiduMap.setMapStatus(msu);
    }


    /**
     * 普通poi查询
     *
     * @param keyow
     */
    private void startpoi(String mcity, String keyow) {
        PoiSearch mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            public void onGetPoiResult(PoiResult result) {
                //获取POI检索结果
                if (mBaiduListBeans != null && mBaiduListBeans.size() > 0) {
                    mBaiduListBeans.clear();
                } else {
                    mBaiduListBeans = new ArrayList<>();
                }
                if (result != null && result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                    for (int i = 0; i < result.getAllPoi().size(); i++) {
                        BaiduListBean baiduListBean = new BaiduListBean(result.getAllPoi().get(i).uid, result.getAllPoi().get(i).name, result.getAllPoi().get(i).address
                                , result.getAllPoi().get(i).location.latitude, result.getAllPoi().get(i).location.longitude);
                        mBaiduListBeans.add(baiduListBean);
                    }
                    if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing()) {
                        popup_recycler.setVisibility(View.VISIBLE);
                        popup_noting.setVisibility(View.GONE);
                        BaiDuMap_Popup_Adapter adapter = new BaiDuMap_Popup_Adapter(mBaiduListBeans, BaiDuMapActivity.this, new RvListener() {
                            @Override
                            public void onItemListener(int id, int position) {
                                KeyBoardUtils.closeKeyBoard(popup_store);
                                mLatLng = new LatLng(mBaiduListBeans.get(position).getLat(), mBaiduListBeans.get(position).getLon());
                                if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing()) {
                                    mBaseHomePopupWindow.dismiss();
                                }
                                getMyLocation();
                                resultinfo(mBaiduListBeans.get(position).getUid());

                                chu_province = province;
                                chu_city = city;
                                chu_count = count;
                                mCity_tx.setText(city);
                            }
                        });
                        popup_recycler.setAdapter(adapter);
                    }
                } else {
                    if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing()) {
                        popup_recycler.setVisibility(View.GONE);
                        popup_noting.setVisibility(View.VISIBLE);
                    }
                }
            }

            public void onGetPoiDetailResult(PoiDetailResult result) {
                //获取Place详情页检索结果
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(mcity)
                .keyword(keyow)
                .pageNum(20));
    }

    BDLocationListener baiduDbListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
//            Log.d(TAG, "onReceiveLocation: " + bdLocation.getLatitude() + "\n" + bdLocation.getLongitude());
            latitude = bdLocation.getLatitude();
            lontitude = bdLocation.getLongitude();
            mPoiList = bdLocation.getPoiList();
            chu_province = bdLocation.getProvince();
            chu_city = bdLocation.getCity();
            chu_count = bdLocation.getDistrict();
            province = chu_province;
            city = chu_city;
            count = chu_count;
            mCity_tx.setText(city);
            address = bdLocation.getStreet();
            map_button_address.setText(bdLocation.getStreet());
            map_button_name.setText(mPoiList.get(0).getName());
            name = bdLocation.getPoiList().get(0).getName();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == BAIDUMAP_REQULE) {
                province = data.getStringExtra("province");
                city = data.getStringExtra("city");
                count = data.getStringExtra("district");
//                Log.d(TAG, "onActivityResult: " + province + city + count);
//                mCity_tx.setText(city);
                if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing()) {
                    popup_city.setText(city);
                    startpoi(city, count);
                } else {
                    showpopup(city, count);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void showloading() {
        getMLoading().show();
    }

    @Override
    public void dissmissloading() {
        getMLoading().dismiss();
    }

    @Override
    public void getdataerror(String msg) {
        MyToast.showMsg(msg);
    }

    @Override
    public void setdataok(String msg) {
        MyToast.showMsg(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBaseHomePopupWindow != null && mBaseHomePopupWindow.isShowing()) {
                mBaseHomePopupWindow.dismiss();
                province = chu_province;
                city = chu_city;
                count = chu_count;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
