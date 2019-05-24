package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.StoreinfoListBean;
import com.hechuang.hepay.view.IStoreInfoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 */

public class StoreInfoPersenter extends BasePersenter<IStoreInfoView> {
    private static final String TAG = "StoreInfoPersenter";

    public StoreInfoPersenter(IStoreInfoView view, Context context) {
        super(view, context);
    }


    public void getdata(final String id, String lat, String lng) {
        getMView().showloading();
        RequestBody body;
        if (lat.equals("") || lng.equals("")) {
            body = new FormBody.Builder().add("id", id).build();
        } else {
            body = new FormBody.Builder().add("id", id).add("lat", lat).add("lng", lng).build();
        }
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Unshop/UnshopDetail.php?act=detail", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                for (int i = 0; i < data.length(); i++) {
                    if (data.substring(i, i + 1).equals("{")) {
                        data = data.substring(i);
                        break;
                    }
                }
                String shareaccounths = "";
                String status = "";
                String msg = "";
                String id = "";
                String n_shareaccounths = "";
                String isshow = "";
                String btnurl = "";
                List<StoreinfoListBean.tags> tags_list = null;
                List<StoreinfoListBean.nearby> nearby_list = null;
                List<StoreinfoListBean.nearby.tags> n_tags_list = null;
                List<String> minis = null;
                StoreinfoListBean bean = null;
                try {
                    JSONObject json = new JSONObject(data);
                    JSONObject datas = json.getJSONObject("data");
                    status = String.valueOf(datas.get("status"));
                    msg = String.valueOf(datas.get("msg"));
                    if (status.equals("1")) {
                        JSONObject list = datas.getJSONObject("list");
                        if (list.has("id")) {
                            id = String.valueOf(list.get("id"));
                        } else {
                            id = "";
                        }
                        String userid = String.valueOf(list.get("UserId"));
                        String ShopName = String.valueOf(list.get("ShopName"));
                        String ShopPhoto = String.valueOf(list.get("ShopPhoto"));
                        JSONArray tags = list.getJSONArray("tags");
                        if (tags != null) {
                            tags_list = new ArrayList<StoreinfoListBean.tags>();
                            for (int i = 0; i < tags.length(); i++) {
                                JSONObject tags_data = tags.getJSONObject(i);
                                String tags_str = String.valueOf(tags_data.get("tagname"));
                                StoreinfoListBean.tags tagsname = new StoreinfoListBean.tags(tags_str);
                                tags_list.add(tagsname);
                            }
                        }
                        String ShopMapX = String.valueOf(list.get("ShopMapX"));
                        String ShopMapY = String.valueOf(list.get("ShopMapY"));
                        String Address = String.valueOf(list.get("Address"));
                        String Mobile = String.valueOf(list.get("Mobile"));
                        String tese = String.valueOf(list.get("tese"));
                        String Shopculture = String.valueOf(list.get("Shopculture"));
                        String ShopContent = String.valueOf(list.get("ShopContent"));
                        if (list.has("shareaccounths")) {
                            shareaccounths = String.valueOf(list.get("shareaccounths"));
                        } else {
                            shareaccounths = "0";
                        }
                        String distance = String.valueOf(list.get("distance"));
                        if (list.has("isshow")) {
                            isshow = String.valueOf(list.get("isshow"));
                        } else {
                            isshow = "";
                        }
                        if (list.has("btnurl")) {
                            btnurl = String.valueOf(list.get("btnurl"));
                        } else {
                            btnurl = "";
                        }
                        JSONArray mini = list.getJSONArray("mini");
                        if (mini != null) {
                            minis = new ArrayList<>();
                            for (int i = 0; i < mini.length(); i++) {
                                String imageuri = String.valueOf(mini.get(i));
                                minis.add(imageuri);
                            }
                        }
                        JSONArray nearbylist = list.getJSONArray("nearby");
                        if (nearbylist != null) {
                            nearby_list = new ArrayList<StoreinfoListBean.nearby>();
                            for (int i = 0; i < nearbylist.length(); i++) {
                                JSONObject nearby = nearbylist.getJSONObject(i);
                                String n_id = String.valueOf(nearby.get("id"));
                                String n_ShopPhoto = String.valueOf(nearby.get("ShopPhoto"));
                                String n_ShopName = String.valueOf(nearby.get("ShopName"));
                                String n_ShopMapX = String.valueOf(nearby.get("ShopMapX"));
                                String n_ShopMapY = String.valueOf(nearby.get("ShopMapY"));
                                String n_Province = String.valueOf(nearby.get("Province"));
                                String n_City = String.valueOf(nearby.get("City"));
                                String n_County = String.valueOf(nearby.get("County"));
                                String n_Address = String.valueOf(nearby.get("Address"));
                                String n_categoryid = String.valueOf(nearby.get("categoryid"));
                                JSONArray n_tags = nearby.getJSONArray("tags");
                                if (n_tags != null) {
                                    n_tags_list = new ArrayList<StoreinfoListBean.nearby.tags>();
                                    for (int j = 0; j < n_tags.length(); j++) {
                                        JSONObject n_tags_obj = n_tags.getJSONObject(j);
                                        String n_tagdname = String.valueOf(n_tags_obj.get("tagname"));
                                        StoreinfoListBean.nearby.tags n_tags_bean = new StoreinfoListBean.nearby.tags(n_tagdname);
                                        n_tags_list.add(n_tags_bean);
                                    }
                                }
                                if (nearby.has("shareaccounths")) {
                                    n_shareaccounths = String.valueOf(nearby.get("shareaccounths"));
                                } else {
                                    n_shareaccounths = "0";
                                }
                                String n_categoryname = String.valueOf(nearby.get("categoryname"));
                                String n_dis = String.valueOf(nearby.get("dis"));
                                StoreinfoListBean.nearby nearbybean = new StoreinfoListBean.nearby(n_id, n_ShopPhoto, n_ShopName, n_ShopMapX
                                        , n_ShopMapY, n_Province, n_City,
                                        n_County, n_Address, n_categoryid, n_tags_list, n_shareaccounths, n_categoryname,
                                        n_dis);
                                nearby_list.add(nearbybean);
                            }

                        }

                        bean = new StoreinfoListBean(id, userid, ShopName, ShopPhoto, tags_list, ShopMapX,
                                ShopMapY, Address, Mobile, tese, Shopculture, ShopContent, shareaccounths, distance, isshow, btnurl, nearby_list, minis);
                    }
                    getMView().dissmissloading();
                } catch (JSONException e) {
                    if (getMView() != null) getMView().data_error("网络繁忙,请稍后重试!");
                }
                if (status.equals("1")) {
                    if (getMView() != null) getMView().setdata(bean, msg);
                } else {
                    if (getMView() != null) getMView().data_error(msg);
                }
            }

            @Override
            public void fail(Request request, Exception e) {
                if (getMView() != null) getMView().dissmissloading();
                if (getMView() != null) getMView().data_error("网络繁忙,请稍后重试!");
            }
        }, null);
    }
}
