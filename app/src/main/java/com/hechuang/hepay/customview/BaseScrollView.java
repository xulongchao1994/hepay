package com.hechuang.hepay.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


/**
 */

public class BaseScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener;

    public interface ScrollViewListener {
        void onScrollChanged(BaseScrollView scrollView, int x, int y, int oldX, int oldy);
    }

    public BaseScrollView(Context context) {
        super(context);
    }

    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldy) {
//        View view = (View) getChildAt(getChildCount() - 1);
//        int d = view.getBottom();
//        d -= (getHeight() + getScrollY());
//        int c = getScrollY();
//        if (d == 0) {
//        } else if (c == 0) {
//        } else
        super.onScrollChanged(l, t, oldl, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldy);
        }
    }

}

