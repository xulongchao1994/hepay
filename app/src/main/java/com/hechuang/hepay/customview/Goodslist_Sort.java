package com.hechuang.hepay.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.TextAdapter;


public class Goodslist_Sort extends RelativeLayout implements ViewBaseAction {

    private ListView mListView;
    private final String[] items = new String[]{"综合排序", "价格从高到低", "价格从低到高"};//显示字段
    private final String[] itemsVaule = new String[]{"1", "2", "3"};//隐藏id
    private OnSelectListener mOnSelectListener;
    private TextAdapter adapter;
    private String mDistance;
    private String showText = "综合排序";
    private Context mContext;

    public String getShowText() {
        return showText;
    }

    public Goodslist_Sort(Context context) {
        super(context);
        init(context);
    }

    public Goodslist_Sort(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public Goodslist_Sort(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
//        setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_right));
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
                showText = items[position];
                mOnSelectListener.getValue(itemsVaule[position], items[position]);
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        public void getValue(String distance, String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}
