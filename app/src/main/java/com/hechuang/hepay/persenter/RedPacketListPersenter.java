package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.RedPacketListBean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.view.IRedPacketListView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 红包列表
 * Created by Android_xu on 2018/1/9.
 */

public class RedPacketListPersenter extends BasePersenter<IRedPacketListView> {
    public RedPacketListPersenter(IRedPacketListView view, Context context) {
        super(view, context);
    }

//    private static final String TAG = "RedPacketListPersenter";

    /**
     * 获取红包列表
     *
     * @param page
     */
    public void getlistdata(String page) {
        if (getMView() != null) getMView().showloading();

        setMSubscription(getMApi().postredpacklistdata(UserData.username, UserData.tokenid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RedPacketListBean>() {
                    @Override
                    public void onCompleted() {
                        if (getMView() != null) getMView().dissmissloading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getMView() != null) {
                            getMView().dissmissloading();
                            getMView().getdataerror("网络错误，请稍后重试");
                        }
                    }

                    @Override
                    public void onNext(RedPacketListBean redPacketListBean) {
//                        Log.d(TAG, "onNext: " + redPacketListBean.getData().toString());
                        if (getMView() != null) {
                            getMView().dissmissloading();
                            if (redPacketListBean.getData().getStatus() == 1) {
//                                for (int i = 0; i < redPacketListBean.getData().getList().size(); i++) {
//                                    redPacketListBean.getData().getList().get(i).setStatus("0");
//                                }
                                getMView().getlistdatasuccess(redPacketListBean);
                            } else
                                getMView().getdataerror(redPacketListBean.getData().getMsg());
                        }
                    }
                }));
    }

//    /**
//     * 获取红包详情
//     *
//     * @param id
//     */
//    public void getredpacketinfo(String id) {
////        RequestBody body = new FormBody.Builder()
////                .add("userid", UserData.username)
////                .add("usertoken", UserData.tokenid)
////                .add("id", id)
////                .build();
////        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Bonus/RedDetial.php", body, new MyOkHttp.RequestCallBack() {
////            @Override
////            public void success(String data) {
////                Log.d(TAG, "success: " + data);
////                try {
////                    JSONObject jsonObject = new JSONObject(data);
////                    JSONObject datas = jsonObject.getJSONObject("data");
////                    String status = String.valueOf(datas.get("status"));
////                    String msg = String.valueOf(datas.get("msg"));
////                    if (status.equals("1")) {
////                        JSONObject itemdata = datas.getJSONObject("list");
////                        String id = String.valueOf(itemdata.get("id"));
////                        String userid = String.valueOf(itemdata.get("userid"));
////                        String bonusmoney = String.valueOf(itemdata.get("bonusmoney"));
////                        String statuss = String.valueOf(itemdata.get("status"));
////                        String adddate = String.valueOf(itemdata.get("adddate"));
////                        String modifydate = String.valueOf(itemdata.get("modifydate"));
////                        RedPacketInfoBean.DataBean.ListBean dataBean = new RedPacketInfoBean.DataBean.ListBean(id, userid, bonusmoney, statuss, adddate, modifydate);
////                        RedPacketInfoBean.DataBean dataBean1 = new RedPacketInfoBean.DataBean(1, msg, dataBean);
////                        RedPacketInfoBean redPacketInfoBean1 = new RedPacketInfoBean(dataBean1);
////                        if (mView != null) {
////                            mView.getredpacketsuccess(redPacketInfoBean1);
////                        }
////                    } else {
////                        mView.getdataerror("");
////                    }
////
////                } catch (JSONException e) {
////
////                }
////            }
////
////            @Override
////            public void fail(Request request, Exception e) {
////
////            }
////        }, null);
//        mSubscription = mApi.postredpackinfodata(UserData.username, UserData.tokenid, id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<RedPacketInfoBean>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "onCompleted: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
//                        if (mView != null) {
//                            mView.getredpacketinfoerror("网络出错，请稍后重试");
//                        }
//                    }
//
//                    @Override
//                    public void onNext(RedPacketInfoBean redPacketInfoBean) {
//                        Log.d(TAG, "onNext: " + redPacketInfoBean.getData().toString());
//                        if (redPacketInfoBean.getData().getStatus() == 1) {
//                            if (mView != null) {
//                                mView.getredpacketsuccess(redPacketInfoBean);
//                            }
//                        } else {
//                            mView.getdataerror(redPacketInfoBean.getData().getMsg());
//                        }
//                    }
//                });
//    }
}
