package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.VersionBean;
import com.hechuang.hepay.bean.WthrcdnBean;
import com.hechuang.hepay.view.IHePayView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 天气
 * Created by Android_xu on 2018/1/9.
 */

public class HePayPersenter extends BasePersenter<IHePayView> {
    public HePayPersenter(IHePayView view, Context context) {
        super(view, context);
    }

//    private static final String TAG = "HePayPersenter";

    public void getversion() {
        setMSubscription(getMApi().post_version().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(VersionBean versionBean) {
//                        Log.d(TAG, "onNext: " + versionBean.toString());
                        if (getMView() != null)
                            getMView().getversion(versionBean);
                    }
                }));
    }

    public void getcitywthrcdn(String city) {
        MyOkHttp.getInstance().get("http://wthrcdn.etouch.cn/weather_mini?city=" + city, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
//                Log.d(TAG, "success: " + data);
                List<WthrcdnBean.DataBean.ForecastBean> forecastBeans = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String status = String.valueOf(jsonObject.get("status"));
                    String desc = String.valueOf(jsonObject.get("desc"));
                    if (status.equals("1000")) {
                        JSONObject mjsong = jsonObject.getJSONObject("data");
                        String city = String.valueOf(mjsong.get("city"));
//                        String api = String.valueOf(mjsong.get("aqi"));
                        String ganmao = String.valueOf(mjsong.get("ganmao"));
                        String wendu = String.valueOf(mjsong.get("wendu"));
                        JSONArray forecast = mjsong.getJSONArray("forecast");
                        if (forecast != null && forecast.length() > 0) {
                            for (int i = 0; i < forecast.length(); i++) {
                                JSONObject forecastitme = forecast.getJSONObject(i);
                                String date = String.valueOf(forecastitme.get("date"));
                                String high = String.valueOf(forecastitme.get("high"));
                                String fengli = String.valueOf(forecastitme.get("fengli"));
                                String low = String.valueOf(forecastitme.get("low"));
                                String fengxiang = String.valueOf(forecastitme.get("fengxiang"));
                                String type = String.valueOf(forecastitme.get("type"));
                                WthrcdnBean.DataBean.ForecastBean forecastBean = new WthrcdnBean.DataBean.ForecastBean(date, high, fengli, low, fengxiang, type);
                                forecastBeans.add(forecastBean);
                            }

                        } else {
                        }
                        WthrcdnBean.DataBean dataBean = new WthrcdnBean.DataBean(city, ganmao, wendu, forecastBeans);
                        if (getMView() != null) {
                            getMView().getwthrcdnok(dataBean);
                        }
                    }

                } catch (JSONException e) {
//                    Log.d(TAG, "success: " + e.getMessage());
                }
            }

            @Override
            public void fail(Request request, Exception e) {

            }
        }, null);
    }

    public void getname() {
        RequestBody body = new FormBody.Builder().build();
        final String[] textstring = new String[5];
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Index/Startover.php?act=data", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
//                Log.d(TAG, "success: " + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject datas = jsonObject.getJSONObject("data");
                    String shopping = String.valueOf(datas.get("shopping"));
                    String business = String.valueOf(datas.get("business"));
                    String member = String.valueOf(datas.get("member"));
                    String integral = String.valueOf(datas.get("integral"));
                    String bee = String.valueOf(datas.get("bee"));
                    String status = String.valueOf(datas.get("status"));
                    String msg = String.valueOf(datas.get("msg"));
                    if (status.equals("1")) {
                        textstring[0] = shopping;
                        textstring[1] = business;
                        textstring[2] = member;
                        textstring[3] = integral;
                        textstring[4] = bee;
//                        textstring = new String[]{shopping, business, member, integral, bee};
                        getMView().getname(textstring);
                    } else {
                        getMView().getnameerror();
                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void fail(Request request, Exception e) {

            }
        }, null);

    }
}
