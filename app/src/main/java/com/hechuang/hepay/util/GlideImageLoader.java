package com.hechuang.hepay.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hechuang.hepay.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Android_xu on 2018/3/5.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(path).error(R.drawable.icon_error).override(1000, 300).into(imageView);
    }
}
