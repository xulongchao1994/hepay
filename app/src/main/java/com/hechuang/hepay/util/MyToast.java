package com.hechuang.hepay.util;

import android.content.Context;
import android.widget.Toast;

/**
 */
public class MyToast {
    private static MyToast mInstance = null;
    private Context mContext;
    private Toast currentToast;

    private MyToast(Context context) {
        this.mContext = context;
    }

    public static void init(Context context) {
        mInstance = new MyToast(context);
    }

    public static void showMsg(String text) {
        if (mInstance.currentToast == null) {
            mInstance.currentToast = Toast.makeText(mInstance.mContext, text, Toast.LENGTH_SHORT);
        } else {
            mInstance.currentToast.setText(text);
            mInstance.currentToast.setDuration(Toast.LENGTH_SHORT);
        }
        mInstance.currentToast.show();
    }

    public static void showMsgOfLong(String text) {
        if (mInstance.currentToast == null) {
            mInstance.currentToast = Toast.makeText(mInstance.mContext, text, Toast.LENGTH_LONG);
        } else {
            mInstance.currentToast.setText(text);
            mInstance.currentToast.setDuration(Toast.LENGTH_LONG);
        }
        mInstance.currentToast.show();
    }

    /** 取消 */
    public static void cancelToast() {
        if (mInstance.currentToast == null) {
        } else {
            mInstance.currentToast.cancel();
        }
    }
}
