package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.view.INoticeInfoView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Android_xu on 2017/11/13.
 * 公告详情
 */

public class NoticeInfoPersenter extends BasePersenter<INoticeInfoView> {


    public NoticeInfoPersenter(INoticeInfoView view, Context context) {
        super(view, context);
    }

    public void setinfodata(String id) {
        if (getMView() != null) getMView().showloading();
        RequestBody body = new FormBody.Builder().add("id", id).build();
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Index/article.php?act=detial", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
//                Log.d("noticeinfo", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject datas = jsonObject.getJSONObject("data");
                    String status = String.valueOf(datas.get("status"));
                    String msg = String.valueOf(datas.get("msg"));
                    if (status.equals("1")) {
                        JSONObject listdata = datas.getJSONObject("list");
                        String title = String.valueOf(listdata.get("title"));
                        String categoryid = String.valueOf(listdata.get("categoryid"));
                        String content = String.valueOf(listdata.get("content"));
                        String creater = String.valueOf(listdata.get("creater"));
                        String editor = String.valueOf(listdata.get("editor"));
                        String addtime = String.valueOf(listdata.get("addtime"));
                        String categoryname = String.valueOf(listdata.get("categoryname"));
                        getMView().setinfodata(title, editor, categoryname, addtime, content);
                        if (getMView() != null) getMView().dissmissloading();
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
