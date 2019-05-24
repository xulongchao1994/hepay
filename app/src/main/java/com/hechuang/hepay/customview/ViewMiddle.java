package com.hechuang.hepay.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.TextAdapter;

import java.util.ArrayList;
import java.util.LinkedList;


public class ViewMiddle extends LinearLayout implements ViewBaseAction {
    private static final String TAG = "ViewMiddle";
    private ListView regionListView;
    //    private ListView plateListView;
    private ArrayList<String> groups = new ArrayList<String>();
    private LinkedList<String> childrenItem = new LinkedList<String>();
    private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
    //    private TextAdapter plateListViewAdapter;
    private TextAdapter earaListViewAdapter;
    private OnSelectListener mOnSelectListener;
    private int tEaraPosition = 0;
    private int tBlockPosition = 0;
//    private String showString = "不限";

    public ViewMiddle(Context context, ArrayList<String> groups) {
        super(context);
        this.groups = groups;
        init(context);
    }

    public ViewMiddle(Context context, AttributeSet attrs, ArrayList<String> groups) {
        super(context, attrs);
        init(context);
    }

    public void setdata(ArrayList<String> groupdata) {
        if (groups != null) {
            groups.clear();
            earaListViewAdapter.notifyDataSetChanged();
            for (int i = 0; i < groupdata.size(); i++) {
                groups.add(groupdata.get(i));
            }
            earaListViewAdapter.notifyDataSetChanged();
        }
    }

    public void updateShowText(String showArea, String showBlock) {
        if (showArea == null || showBlock == null) {
            return;
        }
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).equals(showArea)) {
                earaListViewAdapter.setSelectedPosition(i);
                childrenItem.clear();
                if (i < children.size()) {
                    childrenItem.addAll(children.get(i));
                }
                tEaraPosition = i;
                break;
            }
        }
//        for (int j = 0; j < childrenItem.size(); j++) {
//            if (childrenItem.get(j).replace("不限", "").equals(showBlock.trim())) {
//                plateListViewAdapter.setSelectedPosition(j);
//                tBlockPosition = j;
//                break;
//            }
//        }
        setDefaultSelect();
    }

    String lift = "";

    private void init(Context context) {
        if (groups == null) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_region, this, true);
        regionListView = (ListView) findViewById(R.id.listView);
//        plateListView = (ListView) findViewById(R.id.listView2);
//        LinkedList<String> tItem = new LinkedList<String>();
//        tItem.add("不限");
//        tItem.add("100米以内");
//        tItem.add("500米以内");
//        tItem.add("1000米以内");
//        tItem.add("2000米以内");
//        for (int i = 0; i < groups.size(); i++) {
//            children.put(i, tItem);
//        }

        earaListViewAdapter = new TextAdapter(context, groups,
                R.drawable.choose_item_selected,
                R.drawable.choose_eara_item_selector);
        earaListViewAdapter.setTextSize(15);
        earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
        regionListView.setAdapter(earaListViewAdapter);
        earaListViewAdapter
                .setOnItemClickListener((view, position) -> {
                    lift = groups.get(position);
//                    if (position < children.size()) {
//                        childrenItem.clear();
//                        childrenItem.addAll(children.get(position));
//                        plateListViewAdapter.notifyDataSetChanged();
//                    }
                    if (mOnSelectListener != null) {
                        mOnSelectListener.getValue(lift);
                    }

                });
        if (tEaraPosition < children.size())
            childrenItem.addAll(children.get(tEaraPosition));
//        plateListViewAdapter = new TextAdapter(context, childrenItem,
//                R.drawable.adapter_allorder_img_false,
//                R.drawable.choose_plate_item_selector);
//        plateListViewAdapter.setTextSize(15);
//        plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
//        plateListView.setAdapter(plateListViewAdapter);
//        plateListViewAdapter
//                .setOnItemClickListener((view, position) -> {
//                    showString = childrenItem.get(position);
//                    if (mOnSelectListener != null) {
//                        mOnSelectListener.getValue(lift, showString);
//                    }
//
//                });
        if (tBlockPosition < childrenItem.size())
            lift = childrenItem.get(tBlockPosition);
        if (lift.contains("附近")) {
            lift = lift.replace("不限", "");
        }
        setDefaultSelect();

    }

    public void setDefaultSelect() {
        regionListView.setSelection(tBlockPosition);
//        plateListView.setSelection(tBlockPosition);
    }

    public String getShowText() {
        return lift;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String lift);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
