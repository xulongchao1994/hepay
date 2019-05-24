package com.hechuang.hepay.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.TextAdapter;
import com.hechuang.hepay.bean.Fastshop_titleBean;
import com.hechuang.hepay.bean.Union_top_classify_bean;

import java.util.ArrayList;
import java.util.List;


public class ViewLeft extends RelativeLayout implements ViewBaseAction {

    private ListView mListView;
    private String[] items;//显示字段
    private String[] itemsVaule;//隐藏id
    private List<Union_top_classify_bean.DataBean.ListBean> titlebeen = new ArrayList<>();
    private OnSelectListener mOnSelectListener;
    private TextAdapter adapter;
    private String mDistance;
    private String showText = "餐饮美食";
    private Context mContext;

    public String getShowText() {
        return showText;
    }

    public ViewLeft(Context context, List<Union_top_classify_bean.DataBean.ListBean> titlebeen) {
        super(context);
        this.titlebeen = titlebeen;
        items = new String[titlebeen.size()];
        itemsVaule = new String[titlebeen.size()];
        for (int i = 0; i < titlebeen.size(); i++) {
            items[i] = titlebeen.get(i).getName();
            itemsVaule[i] = titlebeen.get(i).getId();
        }
        init(context);
    }

    public ViewLeft(Context context, AttributeSet attrs, int defStyle, List<Fastshop_titleBean> titlebeen) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewLeft(Context context, AttributeSet attrs, List<Fastshop_titleBean> titlebeen) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
//		setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
        mListView = (ListView) findViewById(R.id.listView);
        adapter = new TextAdapter(context, items, R.drawable.adapter_allorder_img_false, R.drawable.choose_eara_item_selector);
        adapter.setTextSize(15);
        if (mDistance != null) {
            for (int i = 0; i < itemsVaule.length; i++) {
                if (itemsVaule[i].equals(mDistance)) {
                    adapter.setSelectedPositionNoNotify(i);
                    showText = items[i];
                    break;
                }
            }
        }
        mListView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {

            if (mOnSelectListener != null) {
//                    showText = items[position];
                mOnSelectListener.getValue(itemsVaule[position], items[position]);
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String distance, String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}
