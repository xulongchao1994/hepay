package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.AuthCodeBean;
import com.hechuang.hepay.bean.LoginBean;
import com.hechuang.hepay.bean.PhoneLoginBean;
import com.hechuang.hepay.bean.PhoneSuccessBean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.view.ILoginView;

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
 * Created by Android_xu on 2018/1/9.
 * 登录
 */

public class LoginPersenter extends BasePersenter<ILoginView> {
    public LoginPersenter(ILoginView view, Context context) {
        super(view, context);
    }

//    private static final String TAG = "LoginPersenter";

    /**
     * 登录
     *
     * @param username 用户id
     * @param password 密码
     */
    public void login(String username, String password) {
        getMView().showloading();
        setMSubscription(getMApi().postLogin(username, password, "1").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {
                        getMView().dissmissloading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMView().dissmissloading();
//                        Log.d(TAG, "onError: " + e.getMessage());
                        if (getMView() != null) getMView().login_error(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        getMView().dissmissloading();
//                        Log.d(TAG, "onNext: " + loginBean.getData().toString());
                        if (loginBean.getData().getStatus().equals("1")) {
                            UserData.userid = loginBean.getData().getList().getUserid();
                            UserData.serviceffe = loginBean.getData().getList().getServicefee();
                            UserData.sessionid = loginBean.getData().getList().getSessionid();
                            UserData.tokenid = loginBean.getData().getList().getToken();
                            UserData.username = loginBean.getData().getList().getUsername();
                            UserData.usertyep = loginBean.getData().getList().getUsertype();
                            UserData.namestatus = loginBean.getData().getList().getNamestatus();
                            UserData.isurl = loginBean.getData().getList().getUrl();
                            if (getMView() != null) getMView().login_ok(loginBean.getData().getMsg());
                        } else if (loginBean.getData().getStatus().equals("2")) {
                            UserData.tokenid = loginBean.getData().getToken();
                            UserData.userid = loginBean.getData().getUserid();
//                            UserData.usertyep = loginBean.getData().getList().getUsertype();
                            getMView().startmodifypsw(loginBean.getData().getMsg());
                        } else {
                            if (getMView() != null) getMView().login_error(loginBean.getData().getMsg());
                        }
                    }
                }));
    }

    public void getauthcode(String mobile) {
        setMSubscription(getMApi().post_authCode(mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
                        if (getMView() != null) {
                            getMView().getautherror(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(AuthCodeBean authCodeBean) {
//                        Log.d(TAG, "onNext: " + authCodeBean.toString());
                        if (getMView() != null) {
                            if (authCodeBean.getData().getStatus() == 1) {
                                getMView().getauthcode(authCodeBean);
                            } else {
                                getMView().getautherror(authCodeBean.getData().getMsg());
                            }
                        }
                    }
                })
        );
    }

    public void getuserlist(String mobile, String authcode) {
        RequestBody body = new FormBody.Builder()
                .add("mobile", mobile)
                .add("vcode", authcode)
                .build();
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Home/token.php", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                for (int i = 0; i < data.length(); i++) {
                    if (data.substring(i, i + 1).equals("{")) {
                        data = data.substring(i);
                        break;
                    }
                }
//                Log.d(TAG, "getuserlis-success: " + data);
                List<PhoneLoginBean.DataBean.ListBean> listBeans = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject datas = jsonObject.getJSONObject("data");
                    String status = String.valueOf(datas.get("status"));
                    String msg = String.valueOf(datas.get("msg"));
                    if (status.equals("1")) {
                        String token = String.valueOf(datas.get("token"));
                        JSONArray listarray = datas.getJSONArray("list");
                        if (listarray != null && listarray.length() > 0) {
                            for (int i = 0; i < listarray.length(); i++) {
                                JSONObject item = listarray.getJSONObject(i);
                                String UserId = String.valueOf(item.get("UserId"));
                                String TrueName = String.valueOf(item.get("TrueName"));
                                String AvatarUrl = String.valueOf(item.get("AvatarUrl"));
                                PhoneLoginBean.DataBean.ListBean listBean = new PhoneLoginBean.DataBean.ListBean(UserId, TrueName, AvatarUrl);
                                listBeans.add(listBean);
                            }
                            PhoneLoginBean.DataBean dataBean = new PhoneLoginBean.DataBean(status, msg, token, listBeans);
                            PhoneLoginBean loginBean = new PhoneLoginBean(dataBean);
                            getMView().getphoneuserlist(loginBean);
                        }
                    } else {
                        getMView().getphoneuserlisterror(msg);
                    }
                } catch (JSONException e) {
//                    Log.d(TAG, "getuserlis-Exception: " + e.getMessage());
                    getMView().getphoneuserlisterror(e.getMessage());
                }
            }

            @Override
            public void fail(Request request, Exception e) {
//                Log.d(TAG, "getuserlis-fail: " + e.getMessage());
                getMView().getphoneuserlisterror(e.getMessage());
            }
        }, null);

//        setMSubscription(getMApi().post_phonelogin(mobile, authcode).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<PhoneLoginBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
//                        if (getMView() != null) {
//                            getMView().getphoneuserlisterror(e.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onNext(PhoneLoginBean phoneLoginBean) {
//                        Log.d(TAG, "onNext: " + phoneLoginBean.toString());
//                        if (getMView() != null) {
//                            if (phoneLoginBean.getData().getStatus().equals("1")) {
//                                getMView().getphoneuserlist(phoneLoginBean);
//                            } else {
//                                getMView().getphoneuserlisterror(phoneLoginBean.getData().getMsg());
//                            }
//                        }
//                    }
//                })
//        );
    }

    public void getphonelogin(String token, String userid) {
//        Log.d(TAG, "getphonelogin: " + token + "  " + userid);
//        RequestBody body = new FormBody.Builder()
//                .add("token", token)
//                .add("userid", userid)
//                .build();
//        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Home/logon.php", body, new MyOkHttp.RequestCallBack() {
//            @Override
//            public void success(String data) {
//                Log.d(TAG, "getphonelogin-success: " + data);
//            }
//
//            @Override
//            public void fail(Request request, Exception e) {
//                Log.d(TAG, "getphonelogin-fail: " + e.getMessage());
//            }
//        }, null);
        setMSubscription(getMApi().post_phonelogin_success(token, userid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhoneSuccessBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
                        if (getMView() != null) {
                            getMView().getphoneloginerror(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(PhoneSuccessBean phoneLoginBean) {
//                        Log.d(TAG, "onNext: " + phoneLoginBean.toString());
                        if (getMView() != null) {
                            if (phoneLoginBean.getData().getStatus() == 1) {
                                getMView().getphoneloginsuccess(phoneLoginBean);
                            } else {
                                getMView().getphoneloginerror(phoneLoginBean.getData().getMsg());
                            }
                        }
                    }
                })
        );
    }
}
