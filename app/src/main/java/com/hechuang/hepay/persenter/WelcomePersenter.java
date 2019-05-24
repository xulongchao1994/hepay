package com.hechuang.hepay.persenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.LoginBean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.view.IWelcomeView;
import com.hechuang.hepay.wxapi.WXEntryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 欢迎
 * Created by Android_xu on 2018/1/9.
 */

public class WelcomePersenter extends BasePersenter<IWelcomeView> {
    public WelcomePersenter(IWelcomeView view, Context context) {
        super(view, context);
    }

    private static final String TAG = "WelcomePersenter";

    public void loginapp(Context context) {
//        Log.d(TAG, "loginapp: ");
        String username;
        String usertoken;
        SharedPreferences sp = context.getSharedPreferences("userInfo", 0);
        boolean isoutlogin = sp.getBoolean("islogin", false);
        if (!isoutlogin) {
            username = "";
            usertoken = "";
        } else {
            username = sp.getString("username", "");
            usertoken = sp.getString("token_id", "");
        }
//        String password2 = MD5Builder.getMD5Str(password);
//        Log.d(TAG, "loginapp: " + username + "  " + usertoken);
        final SharedPreferences.Editor editor = sp.edit();
//        if (!username.equals("")) {
        String url = ApiFactory.HOST + "Api/login/automatic_login.php";
        RequestBody body = new FormBody.Builder().add("userid", username)
                .add("token", usertoken).build();
        MyOkHttp.getInstance().post(url, body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
//                Log.d(TAG, "success: " + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject datas = jsonObject.getJSONObject("data");
                    String status = String.valueOf(datas.get("status"));
                    String msg = String.valueOf(datas.get("msg"));
                    if (status.equals("1")) {
                        JSONObject listbean = datas.getJSONObject("list");
                        String username = String.valueOf(listbean.get("username"));
                        String usertype = String.valueOf(listbean.get("usertype"));
                        String userid = String.valueOf(listbean.get("userid"));
                        String token = String.valueOf(listbean.get("token"));
                        String servicefee = String.valueOf(listbean.get("servicefee"));
                        String url = String.valueOf(listbean.get("url"));
                        String sessionid = String.valueOf(listbean.get("sessionid"));
                        LoginBean.DataBean.ListBean listBean = new LoginBean.DataBean.ListBean(userid, username, usertype, servicefee, sessionid, "", url, token);
                        LoginBean.DataBean dataBean = new LoginBean.DataBean(listBean, status, msg, "", "");
                        LoginBean loginBean = new LoginBean(dataBean);

                        UserData.tokenid = loginBean.getData().getList().getToken();
                        UserData.userid = loginBean.getData().getList().getUserid();
                        UserData.usertyep = loginBean.getData().getList().getUsertype();
                        UserData.serviceffe = loginBean.getData().getList().getServicefee();
                        UserData.sessionid = loginBean.getData().getList().getSessionid();
                        UserData.username = loginBean.getData().getList().getUsername();
                        UserData.namestatus = loginBean.getData().getList().getNamestatus();
                        UserData.isurl = loginBean.getData().getList().getUrl();
                        editor.putString("token_id", UserData.tokenid);
                        editor.putString("urserid", UserData.userid);
                        editor.putString("usertype", UserData.usertyep);
                        editor.putString("name", UserData.username);
                        editor.putBoolean("islogin", true);
                        editor.putBoolean("isforce", true);
                        editor.apply();
                        UserData.islogin = true;
                        getMView().login_success(loginBean.getData().getMsg());
                    } else {
                        editor.putBoolean("islogin", false);
                        editor.putBoolean("isforce", false);
                        UserData.islogin = false;
                        getMView().login_success(msg);
                    }
                } catch (JSONException e) {
//                    Log.d(TAG, "success: " + e.getMessage());
                    getMView().login_success(e.getMessage());
                }
            }

            @Override
            public void fail(Request request, Exception e) {
//                Log.d(TAG, "success: " + e.getMessage());
                getMView().login_success(e.getMessage());
            }
        }, null);
//        setMSubscription(getMApi().postzidongLogin(username, usertoken).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<LoginBean>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onStartCommand_onError: " + e.getMessage());
//                        getMView().login_success(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(LoginBean loginBean) {
//                        Log.d(TAG, "onStartCommand_onNext: " + loginBean.getData().toString());
//                        Intent intent1;
//                        if (loginBean.getData().getStatus().equals("1")) {
//                            UserData.tokenid = loginBean.getData().getList().getToken();
//                            UserData.userid = loginBean.getData().getList().getUserid();
//                            UserData.usertyep = loginBean.getData().getList().getUsertype();
//                            UserData.serviceffe = loginBean.getData().getList().getServicefee();
//                            UserData.sessionid = loginBean.getData().getList().getSessionid();
//                            UserData.username = loginBean.getData().getList().getUsername();
//                            UserData.namestatus = loginBean.getData().getList().getNamestatus();
//                            UserData.isurl = loginBean.getData().getList().getUrl();
//                            editor.putString("token_id", UserData.tokenid);
//                            editor.putString("urserid", UserData.userid);
//                            editor.putString("usertype", UserData.usertyep);
//                            editor.putString("name", UserData.username);
//                            editor.putBoolean("islogin", true);
//                            editor.putBoolean("isforce", true);
//                            editor.apply();
//                            UserData.islogin = true;
//                            JPushInterface.setAlias(context.getApplicationContext(), 0, UserData.username);
//                            Log.d(TAG, "login_ok: " + UserData.tokenid + UserData.username + UserData.sessionid + "   " + UserData.islogin);
//                            getMView().login_success(loginBean.getData().getMsg());
//                        } else {
//                            editor.putBoolean("islogin", false);
//                            editor.putBoolean("isforce", false);
//                            UserData.islogin = false;
//                            getMView().login_success(loginBean.getData().getMsg());
//                        }
//                    }
//                }));
//        } else {
//        }
    }
}
