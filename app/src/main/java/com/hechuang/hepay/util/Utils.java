package com.hechuang.hepay.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.ui.activity.WebActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Android_xu on 2018/1/26.
 */

public class Utils {

    /**
     * 判断sd卡中是否有地址数据库
     */
    public boolean hasDb(Context context, String name) {
        boolean result = false;
        String filePath = Environment.getExternalStorageDirectory() + ApiFactory.DATABASE_DIRE;// 数据库在SDCard中存放的路径
        // String fileName = "ecm_region.db";// 数据库名称
        String fileName = name;// 数据库名称
        // 判断数据库是否存在SDCard中
        if (new File(Environment.getExternalStorageDirectory() + ApiFactory.DATABASE_DIRE + name).exists()) {// 数据库已存在
            result = true;
        } else {// 数据库不存在存在
            boolean state = new Utils().copyFileFromAssets(context, filePath, fileName);
            // 数据库复制成功
            // 数据库复制失败
            result = state == true;
        }
        return result;
    }

    /**
     * 将地址数据库写入到sd卡中
     */
    public boolean copyFileFromAssets(Context context, String filepath, String fileName) {
        boolean result = false;
        try {
            File tmpFile = new File(filepath);
            if (!tmpFile.exists()) {
                tmpFile.mkdirs();
            }
            if (!new File(filepath + fileName).exists()) {
                // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
                InputStream is = context.getAssets().open(fileName);
                // 输出流
                OutputStream os = new FileOutputStream(filepath + fileName);
                // 文件写入
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                // 关闭文件流
                os.flush();
                os.close();
                is.close();
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取地图城市信息
     */
    public ArrayList<Map<String, String>> selectMapCityData(String pid, SQLiteDatabase db) {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String sql = "select * from area where parent_id=(?)";
        Cursor cursor = db.rawQuery(sql, new String[]{pid});
        Map<String, String> map = null;
        while (cursor.moveToNext()) {
            String region_name = cursor.getString(cursor.getColumnIndex("region_name"));
            String region_id = cursor.getString(cursor.getColumnIndex("region_id"));
            map = new HashMap<String, String>();
            map.put("name", region_name);
            map.put("id", region_id);
            list.add(map);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 旋转图片
     *
     * @param angle  旋转角度
     * @param bitmap 要处理的Bitmap
     * @return 处理后的Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return resizedBitmap;
    }

    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    public static boolean checkPackInfo(Context mContext, String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
        }
        return packageInfo != null;
    }

    private static final String TAG = "Utils";

    public static void startapp(Context mContext, String packname) {
        PackageManager packageManager = mContext.getPackageManager();
//        Log.d(TAG, "startapp: " + checkPackInfo(mContext, packname));
        String username = "";
        if (UserData.islogin) {
            username = UserData.username;
        } else {
            username = "";
        }
        Log.d(TAG, "startapp: " + username);
        if (checkPackInfo(mContext, packname)) {
            Intent intent = packageManager.getLaunchIntentForPackage(packname);
            intent.putExtra("name", username);
            mContext.startActivity(intent);
        } else {
            MyToast.showMsg("没有安装" + packname);
        }

    }


    //判断某一个类是否存在任务栈里面
    public static boolean isExistMainActivity(Context mContext, Class<?> activity) {
        Intent intent = new Intent(mContext, activity);
        ComponentName cmpName = intent.resolveActivity(mContext.getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);  //获取从栈顶开始往下查找的10个activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }

    /**
     * 获取地址信息
     */
    public ArrayList<String> selectCityDataOfArea(String pid, SQLiteDatabase db) {
        ArrayList<String> list = new ArrayList<String>();
        String sql = "select * from area where parent_id= (?)";
        Cursor cursor = db.rawQuery(sql, new String[]{pid});
        while (cursor.moveToNext()) {
            String region_name = cursor.getString(cursor.getColumnIndex("region_name"));
            list.add(region_name);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 获取地址id信息-new
     */
    public String selectCityIdOfArea(String name, String parentid, SQLiteDatabase db) {
        String result = "";
        String sql = "select region_id from area where region_name= (?) and parent_id=(?) ";
        Cursor cursor = db.rawQuery(sql, new String[]{name, parentid});
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex("region_id"));
        }
        cursor.close();
        return result;
    }

    /**
     * 跳转到webactivity
     */
    public static void startwebactivity(Context mContext, String weburl) {
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("web_url", weburl);
        mContext.startActivity(intent);
    }

    /**
     * dip与px之间转换
     *
     * @param dipValue
     * @return
     */

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 判断字符串是否含有大写字母，小写字母，数字
     *
     * @param pwd
     * @return
     */
    public static boolean Pwdisok(String pwd) {
//        boolean isok = false;
        StringBuffer upperstr = new StringBuffer();
        StringBuffer lowerstr = new StringBuffer();
        if (pwd.length() >= 8) {
            if (pwd.length() <= 30) {
                for (int i = 0; i < pwd.length(); i++) {
                    char c = pwd.charAt(i);
                    if (Character.isUpperCase(c)) {
                        upperstr.append(c);
                    } else if (Character.isLowerCase(c)) {
                        lowerstr.append(c);
                    }
                }
                if (upperstr.length() > 0 && lowerstr.length() > 0) {
                    Pattern pattern = Pattern.compile(".*\\d+.*");
                    Matcher isNum = pattern.matcher(pwd);
                    if (isNum.matches()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 适合播放声音短，文件小
     * 可以同时播放多种音频
     * 消耗资源较小
     */
    public static void playSound(Context context, int rawId) {
        SoundPool soundPool;
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频的数量
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        }
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        soundPool.load(context, rawId, 1);
        soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(1, 1, 1, 0, 0, 1));
        //第一个参数id，即传入池中的顺序，第二个和第三个参数为左右声道，第四个参数为优先级，第五个是否循环播放，0不循环，-1循环
        //最后一个参数播放比率，范围0.5到2，通常为1表示正常播放
//        soundPool.play(1, 1, 1, 0, 0, 1);
        //回收Pool中的资源
//        soundPool.release();

    }


    /**
     * 获取版本号
     *
     * @param context
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * 获取屏幕宽度
     */
    public static int getwindow_w(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static Boolean ishasurl(String host) {
        boolean istrue = false;
        for (int i = 0; i < UserData.bai_url.length; i++) {
            if (host.contains(UserData.bai_url[i])) {
                return true;
            } else {
                istrue = false;
            }
        }
        return istrue;
    }


    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    public static   int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = getScreenHeight(anchorView.getContext());
        final int screenWidth = getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
