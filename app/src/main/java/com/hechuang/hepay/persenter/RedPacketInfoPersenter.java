package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.RedPacketInfoBean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.view.IRedPacketInfoView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 红包详情
 * Created by Android_xu on 2018/1/9.
 */

public class RedPacketInfoPersenter extends BasePersenter<IRedPacketInfoView> {
    public RedPacketInfoPersenter(IRedPacketInfoView view, Context context) {
        super(view, context);
    }

    private static final String TAG = "RedPacketInfoPersenter";

    /**
     * 获取红包详情
     *
     * @param id
     */
    public void getredpacketinfo(String id) {
//        RequestBody body = new FormBody.Builder()
//                .add("userid", UserData.username)
//                .add("usertoken", UserData.tokenid)
//                .add("id", id)
//                .build();
//        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Bonus/RedDetial.php", body, new MyOkHttp.RequestCallBack() {
//            @Override
//            public void success(String data) {
//                Log.d(TAG, "success: " + data);
//                try {
//                    JSONObject jsonObject = new JSONObject(data);
//                    JSONObject datas = jsonObject.getJSONObject("data");
//                    String status = String.valueOf(datas.get("status"));
//                    String msg = String.valueOf(datas.get("msg"));
//                    if (status.equals("1")) {
//                        JSONObject itemdata = datas.getJSONObject("list");
//                        String id = String.valueOf(itemdata.get("id"));
//                        String userid = String.valueOf(itemdata.get("userid"));
//                        String bonusmoney = String.valueOf(itemdata.get("bonusmoney"));
//                        String statuss = String.valueOf(itemdata.get("status"));
//                        String adddate = String.valueOf(itemdata.get("adddate"));
//                        String modifydate = String.valueOf(itemdata.get("modifydate"));
//                        RedPacketInfoBean.DataBean.ListBean dataBean = new RedPacketInfoBean.DataBean.ListBean(id, userid, bonusmoney, statuss, adddate, modifydate);
//                        RedPacketInfoBean.DataBean dataBean1 = new RedPacketInfoBean.DataBean(1, msg, dataBean);
//                        RedPacketInfoBean redPacketInfoBean1 = new RedPacketInfoBean(dataBean1);
//                        if (mView != null) {
//                            mView.getredpacketsuccess(redPacketInfoBean1);
//                        }
//                    } else {
//                        mView.getdataerror("");
//                    }
//
//                } catch (JSONException e) {
//
//                }
//            }
//
//            @Override
//            public void fail(Request request, Exception e) {
//
//            }
//        }, null);
        setMSubscription(getMApi().postredpackinfodata(UserData.username, UserData.tokenid, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RedPacketInfoBean>() {
                    @Override
                    public void onCompleted() {
//                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
                        if (getMView() != null) {
                            getMView().getredpacketinfoerror("网络出错，请稍后重试");
                        }
                    }

                    @Override
                    public void onNext(RedPacketInfoBean redPacketInfoBean) {
//                        Log.d(TAG, "onNext: " + redPacketInfoBean.getData().toString());
                        if (redPacketInfoBean.getData().getStatus() == 1) {
//                            redPacketInfoBean.getData().getList().setBonusmoney("1000.00");
//                            switch (type) {
//                                case "0"://chenggong
//                                    redPacketInfoBean.getData().getList().setStatus("1");
//                                    break;
//                                case "1"://chaiguo
//                                    redPacketInfoBean.getData().getList().setStatus("0");
//                                    break;
//                                case "2":
//                                    redPacketInfoBean.getData().getList().setStatus("2");
//                                    break;
//                            }
                            if (getMView() != null) {
                                getMView().getredpacketsuccess(redPacketInfoBean);
                            }
                        } else {
                            getMView().getdataerror(redPacketInfoBean.getData().getMsg());
                        }
                    }
                }));
    }
}
