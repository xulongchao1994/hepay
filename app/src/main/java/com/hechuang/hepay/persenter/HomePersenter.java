package com.hechuang.hepay.persenter;

import android.content.Context;
import android.util.Log;

import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.bean.VersionBean;
import com.hechuang.hepay.view.IHomeView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android_xu on 2018/3/15.
 */

public class HomePersenter extends BasePersenter<IHomeView> {
    public HomePersenter(IHomeView view, Context context) {
        super(view, context);
    }


    public void getversion() {
        setMSubscription(getMApi().post_version().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("home", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(VersionBean versionBean) {
//                        Log.d(TAG, "onNext: " + versionBean.toString());
                        if (getMView() != null)
                            getMView().getversion(versionBean);
                    }
                }));
    }
}
