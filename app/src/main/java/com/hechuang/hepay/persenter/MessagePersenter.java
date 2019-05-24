package com.hechuang.hepay.persenter;

import android.content.Context;

import com.hechuang.hepay.base.BasePersenter;
import com.hechuang.hepay.view.IMessageView;

/**
 * Created by Android_xu on 2018/1/15.
 */

public class MessagePersenter extends BasePersenter<IMessageView> {
    public MessagePersenter(IMessageView view, Context context) {
        super(view, context);
    }
}
