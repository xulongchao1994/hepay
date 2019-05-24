package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.ForceModifyPwdBean
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.view.IForceModifyPasswordView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Android_xu on 2017/12/4.
 */

class ForceModifyPasswordPersenter(view: IForceModifyPasswordView, context: Context) : BasePersenter<IForceModifyPasswordView>(view, context) {

    fun settwopwd(name: String, phone: String, onepwd: String, twopwd: String) {
        mView.showloading()
//        Log.d(TAG, "settwopwd: " + UserData.userid + UserData.tokenid + phone + name + onepwd + twopwd)
        mSubscription = mApi!!.Post_modifytowpwd(UserData.userid, UserData.tokenid, phone, name, onepwd, twopwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ForceModifyPwdBean>() {
                    override fun onCompleted() {
//                        Log.d(TAG, "onCompleted: ")
                        if (mView != null) mView.dissmissloading()
                    }

                    override fun onError(e: Throwable) {
//                        Log.d(TAG, "onError: " + e.message)
                        if (mView != null) mView.dissmissloading()
                    }

                    override fun onNext(modifyTwoPwd: ForceModifyPwdBean) {
//                        Log.d(TAG, "onNext: " + modifyTwoPwd.data.toString())
                        if (mView != null) {
                            mView.dissmissloading()
                        }
                        mView.PwdSeccess(modifyTwoPwd.data.status, modifyTwoPwd.data.msg)
                    }
                })
    }

    companion object {

        private val TAG = "ModifyTowPasswordPersen"
    }
}
