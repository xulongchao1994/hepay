package com.hechuang.hepay.persenter;

import android.content.Context;
import android.util.Log;

import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.api.PathConstant;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.Union_list_Bean;
import com.hechuang.hepay.bean.Union_top_banner_bean;
import com.hechuang.hepay.bean.Union_top_classify_bean;
import com.hechuang.hepay.view.IUnionShopView;

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
 * 联盟商家
 * Created by Android_xu on 2018/2/4.
 */

public class UnionShopPersenter extends BasePersenter<IUnionShopView> {
    public UnionShopPersenter(IUnionShopView view, Context context) {
        super(view, context);
    }

    private List<Union_list_Bean.DataBean.ListBean> mListBeans;
    private static final String TAG = "UnionShopPersenter";

    public void getbanner2() {
        /**
         * banner点击判断
         * 0 无连接
         * 1 商家
         * 2.自定义
         * 3.商家列表
         */
        setMSubscription(getMApi().post_banner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Union_top_banner_bean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getMView() != null)
                            getMView().getdataerror("网络连接出错，请稍后重试");
                    }

                    @Override
                    public void onNext(Union_top_banner_bean union_top_banner_bean) {
                        if (union_top_banner_bean.getData().getStatus() == 1) {
                            getMView().getbanner_data(union_top_banner_bean);
                        } else {
                            getMView().getdataerror(union_top_banner_bean.getData().getMsg());
                        }
                    }
                }));

    }

    public void getclassify() {
        setMSubscription(getMApi().post_classify().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Union_top_classify_bean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getMView() != null)
                            getMView().getdataerror_list();
                    }

                    @Override
                    public void onNext(Union_top_classify_bean union_top_classify_bean) {
//                        Log.d("unionshop", union_top_classify_bean.getData().getList().toString());
                        if (union_top_classify_bean.getData().getStatus() == 1) {
                            getMView().getclassify_data(union_top_classify_bean);
                        } else {
                            getMView().getdataerror(union_top_classify_bean.getData().getMsg());
                        }
                    }
                }));
    }

    public void getlistdata(String lng, String lat, String keywords, String pid, String aid, String qid, int Page, String id, String hitid, String disid) {
        Log.d(TAG, "getlistdata: " +
                "lng:" + lng + "  " +
                "lat:" + lat + "  " +
                "keywords:" + keywords + "  " +
                "pid:" + pid + "  " +
                "aid:" + aid + "  " +
                "qid:" + qid + "  " +
                "id:" + id + "   " +
                "hitid:" + hitid + "   " +
                "disid:" + disid
        );
        getMView().showloading();
        setMSubscription(getMApi().post_list_data(lng, lat, keywords, pid, aid, qid, String.valueOf(Page), id, hitid, disid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Union_list_Bean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        if (getMView() != null) {
                            getMView().getdataerror_list();
                            getMView().dissmissloading();
                        }
                    }

                    @Override
                    public void onNext(Union_list_Bean union_list_bean) {
                        Log.d(TAG, "onNext: " + union_list_bean.getData().toString());
                        if (union_list_bean.getData().getStatus() == 1) {
                            if (mListBeans == null) {
                                mListBeans = new ArrayList<>();
                            } else {
                                if (Page == 1) {
                                    mListBeans.clear();
                                }
                            }
                            for (Union_list_Bean.DataBean.ListBean mbean : union_list_bean.getData().getList())
                                mListBeans.add(mbean);
                            getMView().getlist_data(mListBeans);
                        } else {
                            getMView().getdataerror(union_list_bean.getData().getMsg());
                        }
                        getMView().dissmissloading();
                    }
                }));
    }

    private ArrayList<String> discrictList;

    /***
     *
     * @param city
     * @param type 用于判断是从哪个地方进入
     */
    public void postcount(String city, final int type) {
//        Log.d(TAG, "postcount: " + city);
        discrictList = new ArrayList<>();
        RequestBody body = new FormBody.Builder()
                .add("city", city).build();
        MyOkHttp.getInstance().post(PathConstant.GET_CITY, body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                try {
                    JSONObject datas = new JSONObject(data);
                    JSONObject data_itme = datas.getJSONObject("data");
                    String status = String.valueOf(data_itme.get("status"));
                    String msg = String.valueOf(data_itme.get("msg"));
                    if (status.equals("1")) {
                        JSONArray list = data_itme.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            String pr = String.valueOf(list.get(i));
                            discrictList.add(pr);
                        }
                        switch (type) {
                            case 1://初始化页面时
                                getMView().setinitview(discrictList);
                                break;
                            case 2://返回当前页面时
                                getMView().setunview(discrictList);
                                break;
                        }
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
