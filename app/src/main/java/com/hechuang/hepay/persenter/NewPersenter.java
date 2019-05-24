package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.NewsBean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.view.INewsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Android_xu on 2017/9/27.
 * 消息页面
 */

public class NewPersenter extends BasePersenter<INewsView> {


    List<NewsBean> newsbeanlist;

    public NewPersenter(INewsView view, Context context) {
        super(view, context);
    }

    public void getmsg(final int page) {
        RequestBody body = new FormBody.Builder()
                .add("userid", UserData.userid)
                .add("Page", String.valueOf(page))
                .add("token", UserData.tokenid).build();
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Inform/index.php?act=notic", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                if (page == 1) {
                    newsbeanlist = new ArrayList<>();
                }
                String msg = "";
                try {
                    JSONObject jsonobject = new JSONObject(data);
                    JSONObject datas = jsonobject.getJSONObject("data");
                    String status = String.valueOf(datas.get("status"));
                    msg = String.valueOf(datas.get("msg"));
                    if (status.equals("1")) {
                        JSONArray list = datas.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject listtiem = list.getJSONObject(i);
                            String ID = String.valueOf(listtiem.get("ID"));
                            String Title = String.valueOf(listtiem.get("Title"));
                            String SourceID = String.valueOf(listtiem.get("SourceID"));
                            String MsgType = String.valueOf(listtiem.get("MsgType"));
                            String UserId = String.valueOf(listtiem.get("UserId"));
                            String IsHasRead = String.valueOf(listtiem.get("IsHasRead"));
                            String Addtime = String.valueOf(listtiem.get("Addtime"));
                            String intro = String.valueOf(listtiem.get("intro"));
                            NewsBean bean = new NewsBean(ID, Title, SourceID, MsgType, UserId, IsHasRead, Addtime, intro);
                            newsbeanlist.add(bean);

                        }
                        if (getMView() != null) getMView().setnewlistview(newsbeanlist);
                    } else {
                        if (getMView() != null) getMView().listerror(msg);
                    }
                } catch (JSONException e) {
                    if (getMView() != null) getMView().listerror(msg);
                }
            }

            @Override
            public void fail(Request request, Exception e) {
                if (getMView() != null) getMView().listerror("加载失败，请稍后重试");

            }
        }, null);
    }
}
