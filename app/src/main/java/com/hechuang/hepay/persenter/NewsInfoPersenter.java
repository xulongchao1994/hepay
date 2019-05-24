package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.view.INewsInfoView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Android_xu on 2017/9/27.
 * 消息详情
 */

public class NewsInfoPersenter extends BasePersenter<INewsInfoView> {



    public NewsInfoPersenter(INewsInfoView view, Context context) {
        super(view, context);
    }

    public void getnewsinfo(String id) {
        RequestBody body = new FormBody.Builder()
                .add("userid", UserData.userid)
                .add("token", UserData.tokenid)
                .add("id", id
                ).build();
        MyOkHttp.getInstance().post(ApiFactory.HOST+"Api/Inform/index.php?act=detial", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                try {
                    JSONObject jsonobject = new JSONObject(data);
                    JSONObject datas = jsonobject.getJSONObject("data");
                    String status = String.valueOf(datas.get("status"));
                    String msg = String.valueOf(datas.get("msg"));
                    if (status.equals("1")) {
                        JSONObject detial = datas.getJSONObject("detial");
                        String ID = String.valueOf(detial.get("ID"));
                        String Title = String.valueOf(detial.get("Title"));
                        String editorValue = String.valueOf(detial.get("editorValue"));
                        String SourceID = String.valueOf(detial.get("SourceID"));
                        String MsgType = String.valueOf(detial.get("MsgType"));
                        String UserId = String.valueOf(detial.get("UserId"));
                        String IsHasRead = String.valueOf(detial.get("IsHasRead"));
                        String Addtime = String.valueOf(detial.get("Addtime"));
                        if (getMView() != null) getMView().setwebview(SourceID,Addtime,Title, editorValue);
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
