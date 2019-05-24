package com.hechuang.hepay.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 */
public class KeyBoardUtils {
    /**
     * 关闭软键盘
     *
     * @param view 焦点view
     */
    public static void closeKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view
                .getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(
                    view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 打开软键盘
     *
     * @param view 焦点view
     */
    public static void openKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view
                .getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
