package com.hechuang.hepay.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hechuang.hepay.util.Utils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class Welcome_Adapter extends PagerAdapter {
    List<ImageView> imageViews;
    List<String> imageIDList;
    private Context context;
    int[] bean ;

    public Welcome_Adapter(List<ImageView> imageViews, List<String> imageIDList, Context context, int[] bean) {
        this.imageViews = imageViews;
        this.imageIDList = imageIDList;
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    /**
     * 判断当前分页是不是view
     * 由于ViewPager里面的分页可以填入Fragment
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 清理内存
     * 从第一页滑动到第二页，此时第一页的内存应该释放
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));//释放滑动过后的前一页
    }

    /**
     * 初始化分页
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = imageViews.get(position);
//        imageView.setBackgroundColor(Color.parseColor(bean.get(position).getBgcolor()));
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(bean[position]).
        diskCacheStrategy(DiskCacheStrategy.ALL).override(1000, 1000).into(imageView);
//        imageView.setImageResource(imageIDList.get(position));
        ViewGroup.LayoutParams viewLayoutParams = new ViewGroup.LayoutParams
                (
                        Utils.dip2px(context, 170),
                        Utils.dip2px(context, 200)
                );
        container.addView(imageView, viewLayoutParams);//设置图片的宽高
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (viewPagerListener != null) {
//                    viewPagerListener.viewpagerlistener(position);
//                }
//            }
//        });
        return imageView;
    }

//    private ViewPagerListener viewPagerListener;
//
//    public interface ViewPagerListener {
//        void viewpagerlistener(int psition);
//    }
//
//    public void setOnViewPagerListener(ViewPagerListener viewPagerListener) {
//        this.viewPagerListener = viewPagerListener;
//    }
}
