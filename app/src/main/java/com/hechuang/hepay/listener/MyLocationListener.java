package com.hechuang.hepay.listener;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.hechuang.hepay.bean.UserData;

import java.util.List;


/**
 * 百度定位
 */


public class MyLocationListener implements BDLocationListener {
    private static final String TAG = "MyLocationListener";
    private int one = 0;
    @Override
    public void onReceiveLocation(BDLocation location) {
        StringBuffer sb = new StringBuffer(256);

        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        UserData.latitude = String.valueOf(location.getLatitude());
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        UserData.lontitude = String.valueOf(location.getLongitude());
        sb.append(location.getLongitude());
        location.getCountry();
        sb.append("\nradius : ");
        sb.append(location.getRadius());//获取位置精度
        UserData.province = String.valueOf(location.getProvince());
        UserData.city = String.valueOf(location.getCity());
        UserData.discrict = String.valueOf(location.getDistrict());
        UserData.streetnumber = String.valueOf(location.getStreetNumber());
//        UserData.addr = location.getAddrStr();    //获取详细地址信息
//        String country = location.getCountry();    //获取国家
//        String province = location.getProvince();    //获取省份
//        String city = location.getCity();    //获取城市
//        String district = location.getDistrict();    //获取区县
        UserData.street = location.getStreet();    //获取街道信息

//        Log.e(TAG, "onReceiveLocation: " + province + city + district);
        UserData.dbcode = location.getLocType();
        if (location.getLocType() == BDLocation.TypeGpsLocation) {
//            sb.append("\nspeed : ");
//            sb.append(location.getSpeed());// 单位：公里每小时
//            sb.append("\nsatellite : ");
//            sb.append(location.getSatelliteNumber());
//            sb.append("\nheight : ");
//            sb.append(location.getAltitude());// 单位：米
//            sb.append("\ndirection : ");
//            sb.append(location.getDirection());// 单位度
//            sb.append("\naddr : ");
//            sb.append(location.getAddrStr());
//            sb.append("\ndescribe : ");
            sb.append("gps定位成功");
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//            sb.append("\naddr : ");
//            sb.append(location.getAddrStr());
//            //运营商信息
//            sb.append("\noperationers : ");
//            sb.append(location.getOperators());
//            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
//            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//            sb.append("\ndescribe : ");
//            MyToast.showMsg("网络不同导致定位失败，请检查网络是否通畅");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//            sb.append("\ndescribe : ");
//            MyToast.showMsg("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        Log.d("BaiduLocationApiDem", sb.toString());
//        Log.d(TAG, "onReceiveLocation: " + location.getLocType());
    }

}