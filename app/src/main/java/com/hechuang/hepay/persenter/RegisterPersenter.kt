package com.hechuang.hepay.persenter

import android.content.Context
import com.hechuang.hepay.base.BasePersenter
import com.hechuang.hepay.bean.RegisterBean
import com.hechuang.hepay.view.IRegisterView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RegisterPersenter(itemview: IRegisterView, context: Context) : BasePersenter<IRegisterView>(itemview, context) {

    fun register(name: String, invite: String, pswstr: String, vcode: String, type: String) {
        mSubscription = mApi!!.REGISTER(name, invite, pswstr, vcode, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<RegisterBean>() {
                    override fun onNext(t: RegisterBean?) {
                        if (t!!.data.status == 1) {
                            mView.register_success(t)
                        } else {
                            mView.register_error(t.data.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        mView.register_error(e!!.message.toString())
                    }
                })

    }
}