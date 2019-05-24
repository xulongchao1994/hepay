package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.NoticeListBean;
import com.hechuang.hepay.view.INoticeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Android_xu on 2017/11/13.
 * 公告页面
 */

public class NoticePersenter extends BasePersenter<INoticeView> {

    List<NoticeListBean> noticeListBeans = null;

    public NoticePersenter(INoticeView view, Context context) {
        super(view, context);
    }

    public void getnoicelist(String id, final String page, String name) {
        if (getMView() != null) getMView().showloading();
        RequestBody body;
//        if (id != null && !id.equals("")) {
        //.add("id", id)
        body = new FormBody.Builder().add("Page", page).build();
//        } else {
//            if (name != null && name.equals("")) {
//                body = new FormBody.Builder().add("Page", page).build();
//            } else {
//                body = new FormBody.Builder().add("name", name).add("Page", page).build();
//            }
//        }
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Index/article.php?act=data", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
//                Log.d("notice", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject datas = jsonObject.getJSONObject("data");
                    String status = String.valueOf(datas.get("status"));
                    String msg = String.valueOf(datas.get("status"));
                    if (status.equals("1")) {
                        if (page.equals("1")) {
                            noticeListBeans = new ArrayList<>();
                        }
                        JSONArray jsonArray = datas.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id = String.valueOf(jsonObject1.get("id"));
                            String title = String.valueOf(jsonObject1.get("title"));
                            String categoryid = String.valueOf(jsonObject1.get("categoryid"));
                            String addtime = String.valueOf(jsonObject1.get("addtime"));
                            String RowNumber = String.valueOf(jsonObject1.get("RowNumber"));
                            String categoryname = String.valueOf(jsonObject1.get("categoryname"));
                            String imgpath = String.valueOf(jsonObject1.get("imgpath"));
                            NoticeListBean listBean = new NoticeListBean(id, title, categoryid, addtime, RowNumber, categoryname, imgpath);
                            noticeListBeans.add(listBean);
                        }
                        if (getMView() != null) getMView().setnoticelistdata(noticeListBeans);
                        if (getMView() != null) getMView().dissmissloading();
                    } else {
                        getMView().dissmissloading();
                        getMView().setnotmore(msg);
                    }
                } catch (JSONException e) {
                    if (getMView() != null) getMView().dissmissloading();
                }
            }

            @Override
            public void fail(Request request, Exception e) {
                if (getMView() != null) getMView().dissmissloading();
            }
        }, null);
    }
}
