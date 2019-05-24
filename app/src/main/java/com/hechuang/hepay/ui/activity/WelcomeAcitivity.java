package com.hechuang.hepay.ui.activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.Welcome_Adapter;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.bean.Welcome_list_Bean;
import com.hechuang.hepay.persenter.WelcomePersenter;
import com.hechuang.hepay.util.Eyes;
import com.hechuang.hepay.view.IWelcomeView;
import com.hechuang.hepay.wxapi.WXEntryActivity;
import com.octopus.round_progressbar.RoundProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * 欢迎
 * Created by Android_xu on 2018/1/9.
 */
public class WelcomeAcitivity extends BaseAppCompatActivity<WelcomePersenter> implements IWelcomeView, View.OnClickListener {
    public static WelcomeAcitivity mWelcomeAcitivity;
    @BindView(R.id.welcome_fisth)
    ImageView img_fisth;
    @BindView(R.id.welcome_img_layout)
    RelativeLayout img_layout;
    @BindView(R.id.welcome_img)
    ImageView img;
    @BindView(R.id.welcome_progress)
    RoundProgressBar mRoundProgressBar;
    @BindView(R.id.welcome_viewpager_layout)
    RelativeLayout viewpager_layout;
    @BindView(R.id.welcome_viewpager)
    ViewPager viewpager;
    @BindView(R.id.viewpager_dian)
    LinearLayout group;
    int isfirst = 0;
    int currentItem;
    List<String> imageIDList;
    int[] imgages = new int[]{R.drawable.welcome_one, R.drawable.welcome_two};
    List<Integer> inageintlsit;
    List<ImageView> imageViews;
    Welcome_Adapter adapter;
    //    private ImageView[] tips;//装点点的ImageView数组
    Welcome_list_Bean welcome_bean = null;
    private static final String TAG = "WelcomeAcitivity";

    @Override
    public void showloading() {
        getMLoading().show();
    }

    @Override
    public void dissmissloading() {
        getMLoading().dismiss();
    }

    @Override
    public void getdataerror(String msg) {
//        MyToast.showMsg(msg);
        viewpager_layout.setVisibility(View.GONE);
        img_layout.setVisibility(View.GONE);
//        mRoundProgressBar.setVisibility(View.GONE);
        img_fisth.setVisibility(View.VISIBLE);
        Glide.with(this).
                load(R.drawable.main_icon).
                override(700, 800).
                into(img_fisth);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            UserData.zhuan_username = (String) bundle.get("name");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeAcitivity.this, MainActivity.class));
                finish();
            }
        }, 2000L);
    }

    @Override
    protected int initlayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new WelcomePersenter(this, this));
    }

    @Override
    protected void initView() {
        Eyes.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.white));
        mWelcomeAcitivity = this;
        ButterKnife.bind(this);
        Eyes.translucentStatusBar(this);
        startActivity(new Intent(WelcomeAcitivity.this, MainActivity.class));
        finish();
        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean islogin = sp.getBoolean("islogin", false);
        UserData.username = sp.getString("name", "");
        UserData.tokenid = sp.getString("token_id", "");
        isfirst = sp.getInt("first", 0);
        img.setOnClickListener(this);
        if (islogin) {
            UserData.islogin = true;
        } else {
            UserData.islogin = false;
        }
//        Log.d(TAG, "initView: " + isfirst);
        if (isfirst < 10) {
            isfirst = isfirst + 1;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("first", isfirst);
        editor.commit();
        if (isfirst <= 1) {
            viewpager.setVisibility(View.VISIBLE);
            initpager(imgages);
        } else {
            viewpager_layout.setVisibility(View.GONE);
            img_layout.setVisibility(View.GONE);
            img_fisth.setVisibility(View.VISIBLE);
            Glide.with(this).
                    load(R.drawable.main_icon).
                    override(700, 800).
                    into(img_fisth);
            getMPersenter().loginapp(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome_img:
                break;
        }
    }

    private void initimage(final Welcome_list_Bean bean) {
        Glide.with(WelcomeAcitivity.this).
                load(bean.getImgurl()).
                override(640, 960).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                error(R.mipmap.ic_launcher).
                into(img);
//        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        img.setBackgroundColor(Color.parseColor(bean.getBgcolor()));
        new getImageCacheAsyncTask(this).execute(bean.getImgurl());
        mRoundProgressBar.setOnClickListener(v -> {
            mRoundProgressBar.clearAnimation();
            finish();
            startActivity(new Intent(WelcomeAcitivity.this, MainActivity.class));
        });
    }

    /**
     * 设置图片滑动
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initpager(final int[] imginteger) {
//        imageIDList = new ArrayList<>();
//        for (int i = 0; i < im.size(); i++) {
//            imageIDList.add(beans.get(i).getImgurl());
//        }
        imageViews = new ArrayList<>();
        for (int i = 0; i < imginteger.length; i++) {
            ImageView imgs = new ImageView(this);
            imageViews.add(imgs);
        }
//        tips = new ImageView[imageViews.size()];
//
//        for (int i = 0; i < tips.length; i++) {
//            ImageView imageView = new ImageView(this);
//            tips[i] = imageView;
//            if (i == 0) {
//                Glide.with(this)
//                        .load(R.drawable.welcome_round_ed)
//                        .into(tips[i]);
//            } else {
//                Glide.with(this)
//                        .load(R.drawable.welcome_round)
//                        .into(tips[i]);
//            }
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(40,
//                    40));
//            layoutParams.leftMargin = 5;
//            layoutParams.rightMargin = 5;
//            group.addView(imageView, layoutParams);
//        }


        //设置Adapter
        adapter = new Welcome_Adapter(imageViews, imageIDList, this, imginteger);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                setImageBackground(position % imageViews.size());
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float startY;
            float endX;
            float endY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        endY = event.getY();
                        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//获取屏幕的宽度
                        Point size = new Point();
                        windowManager.getDefaultDisplay().getSize(size);
                        int width = size.x;
//首先要确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我这里的判断距离是屏幕宽度的4分之一（这里可以适当控制）
//                        Log.e(TAG, "onTouch: " + currentItem + "\n" + (imageViews.size() - 1) + "\n" + (startX - endX) + "\n" + UserRelated.screenWidth + "\n" + startX + "\n" + endX);
                        if (startX - endX == 0 || startX - endX < 5 && startX - endX > 0) {
//                            switch (beans.get(currentItem).getMold()) {
//                                case "2":
//                                    finish();
//                                    Intent intent = new Intent(WelcomeActivity.this, GoodsInfoActivity.class);
//                                    intent.putExtra("welcome", "1");
//                                    intent.putExtra("proid", beans.get(currentItem).getId());
//                                    startActivity(intent);
//                                    break;
//                                case "1":
//                                    finish();
//                                    Intent intent2 = new Intent(WelcomeActivity.this, StoreListActivity.class);
//                                    intent2.putExtra("welcome", "1");
//                                    intent2.putExtra("supid", beans.get(currentItem).getId());
//                                    startActivity(intent2);
//                                    break;
//                                case "3":
//                            finish();
                            if (currentItem == imageViews.size() - 1) {
//                                ARouter.getInstance().build("/activity/hepay").navigation();
                                startActivity(new Intent(WelcomeAcitivity.this, MainActivity.class));
                            }
//                            MyToast.showMsg(String.valueOf(currentItem));
//                            Utils.startwebactivity(WelcomeAcitivity.this, beans.get(currentItem).getUrl());
//                                    break;
//                                default:
////                                    MyToast.showMsg("sdfadf");
//                                    break;
//                            }
                        } else if (currentItem == (imageViews.size() - 1) && startX - endX > 0 && startX - endX >= (width / 5)) {
                            finish();
//                            ARouter.getInstance().build("/activity/hepay").navigation();
                            startActivity(new Intent(WelcomeAcitivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                        }
                        break;
                }
                return false;
            }
        });
        viewpager.setCurrentItem(0);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
//        viewPager.setCurrentItem((mImageViews.length) * 100);
    }

//    /**
//     * 设置选中的tip的背景
//     *
//     * @param selectItems
//     */
//    private void setImageBackground(int selectItems) {
//        for (int i = 0; i < tips.length; i++) {
//            if (i == selectItems) {
//                Glide.with(this)
//                        .load(R.drawable.welcome_round_ed)
//                        .override(300, 300)
//                        .into(tips[i]);
//            } else {
//                Glide.with(this)
//                        .load(R.drawable.welcome_round)
//                        .override(300, 300)
//                        .into(tips[i]);
//            }
//        }
//    }

    @Override
    public void setwelcome_img(Welcome_list_Bean bean) {
        welcome_bean = new Welcome_list_Bean(bean.getImgurl(), bean.getUrl(), bean.getMold(), bean.getId(), bean.getBgcolor());
        img_fisth.setVisibility(View.GONE);
        viewpager_layout.setVisibility(View.GONE);
        img_layout.setVisibility(View.VISIBLE);
        mRoundProgressBar.startAnimation(7000, new LinearInterpolator(), new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                startActivity(new Intent(WelcomeAcitivity.this, MainActivity.class));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        initimage(bean);
    }

    @Override
    public void setwelcome_viewpager(List<Welcome_list_Bean> beans) {
        viewpager_layout.setVisibility(View.VISIBLE);
        img_layout.setVisibility(View.GONE);
        img_fisth.setVisibility(View.GONE);
        initpager(imgages);
    }

    @Override
    public void login_success(String msg) {
        startActivity(new Intent(WelcomeAcitivity.this, MainActivity.class));
        if (!UserData.username.equals("")) {
            JPushInterface.setAlias(WelcomeAcitivity.this, 0, UserData.username);
        }
    }

    @Override
    public void login_error(String msg) {
        startActivity(new Intent(WelcomeAcitivity.this, MainActivity.class));
    }

    private class getImageCacheAsyncTask extends AsyncTask<String, Void, File> {
        private final Context context;

        public getImageCacheAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(String... params) {
            String imgUrl = params[0];
            try {
                return Glide.with(context)
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            if (result == null) {
                return;
            }
            //此path就是对应文件的缓存路径
            String path = result.getPath();
//            Log.e("path", path);
            Bitmap bmp = BitmapFactory.decodeFile(path);
            img.setImageBitmap(bmp);

        }
    }
}
