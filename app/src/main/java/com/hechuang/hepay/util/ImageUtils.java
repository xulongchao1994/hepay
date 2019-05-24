package com.hechuang.hepay.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hechuang.hepay.api.PathConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    /**
     * 下载图片并保存到相册提示相册更新显示
     */
    public static void downloadimg(Context context, String url) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + PathConstant.IMG_DIR);
                if (!appDir.exists()) {
                    Boolean isSuccess = appDir.mkdirs();
                } else {
                }
                String fileName = PathConstant.APP_NAME + System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                try {
//                    DebugLog.i("开始保存");
                    FileOutputStream fos = new FileOutputStream(file);
                    //通过io流的方式来压缩保存图片
                    boolean isSuccess = resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    //把文件插入到系统图库
                    MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
                    //保存图片后发送广播通知更新数据库
                    Uri uri = Uri.fromFile(file);
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    if (isSuccess) {
                        MyToast.showMsg("图片下载完成");
                    } else {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 偏移效果
     *
     * @param origin 原图
     * @return 偏移后的bitmap
     */
    public static Bitmap skewBitmap(Bitmap origin, float x, float y) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.postSkew(-0.6f, -0.3f);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
}
