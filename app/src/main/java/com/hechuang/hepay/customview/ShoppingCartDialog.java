package com.hechuang.hepay.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.api.PathConstant;
import com.hechuang.hepay.base.BaseAdapter;
import com.hechuang.hepay.base.BaseDialog;
import com.hechuang.hepay.base.BaseViewHolder;
import com.hechuang.hepay.bean.BusinessShoppingBean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.ui.activity.LoginActivity;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ${GongWenbo} on 2018/5/22 0022.
 */
public class ShoppingCartDialog extends BaseDialog implements BaseAdapter.BaseAdapterListener<BusinessShoppingBean.DataBean.ListBean> {
    public static final String CART_GOODS = "cartGoods";
    private static final long TIME = 300;       // 动画的时间
    @BindView(R.id.rv_cart_goods)
    RecyclerView mRvCartGoods;
    List<BusinessShoppingBean.DataBean.ListBean> list = new ArrayList<>();
    @BindView(R.id.tv_shopping_cart_count)
    TextView mTvShoppingCartCount;
    @BindView(R.id.ll_shopping_cart)
    LinearLayout mLlShoppingCart;
    @BindView(R.id.business_allprice)
    TextView shop_allprice;
    private BaseAdapter mAdapter;
    private int allCount;
    private CartGoodsDialogListener mCartGoodsDialogListener;
    private int mHeightPixels;
    private String shopid;

    @Override
    protected void init() {
        initData();
        initAdapter();
        initScreen();
        // 打开的动画
        openAnim();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<BusinessShoppingBean.DataBean.ListBean> cartGoodsList = (List<BusinessShoppingBean.DataBean.ListBean>) bundle.getSerializable(CART_GOODS);
            // 将没有个数的商品移除，只是为了模拟数据，网络数据看情况
            allCount = bundle.getInt("count");
            shop_allprice.setText(bundle.getString("allprice"));
            for (int i = 0; i < cartGoodsList.size(); i++) {
                BusinessShoppingBean.DataBean.ListBean shopGoodsBean = cartGoodsList.get(i);
                int count = Integer.valueOf(shopGoodsBean.getCommodity().getProNum());
                if (count != 0) {
                    list.add(shopGoodsBean);
                }
            }
            shopid = bundle.getString("shopid");
        }
        // 如果个数大于0，那么要显示商品数
        if (allCount > 0) {
            mTvShoppingCartCount.setVisibility(View.VISIBLE);
            mTvShoppingCartCount.setText(String.valueOf(allCount));
        }
    }

    private void initScreen() {
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 获取屏幕的高度
        mHeightPixels = dm.heightPixels;
    }

    public void openAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLlShoppingCart, "translationY", mHeightPixels, 0);
        objectAnimator.setDuration(TIME);
        objectAnimator.start();
    }

    public void closeAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLlShoppingCart, "translationY", 0, mHeightPixels);
        objectAnimator.setDuration(TIME);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                dismiss();
            }
        });
    }

    private void initAdapter() {
        // 如果商品个数大于指定数时,高度写死,其他wrap_content
        if (list.size() >= 4) {
            ViewGroup.LayoutParams lp = mRvCartGoods.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = Utils.dip2px(mActivity, 200);
            mRvCartGoods.setLayoutParams(lp);
        }
        mAdapter = new BaseAdapter(list, R.layout.item_cart_goods, this);
        mRvCartGoods.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvCartGoods.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_business_goods_cart;
    }

    @Override
    public float setAlpha() {
        return 0.3f;
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @OnClick({R.id.view_shadow, R.id.ll_cart_goods_clear, R.id.tv_shopping_cart_pay, R.id.business_shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 点击阴影消失
            case R.id.view_shadow:
                closeAnim();
                break;
            // 清空购物车
            case R.id.ll_cart_goods_clear:
                if (allCount > 0) {
                    clearCartGoodsDialog();
                } else {
                    MyToast.showMsg("购物车中空空如也");
                }
                break;
            // 去支付
            case R.id.tv_shopping_cart_pay:
                if (mCartGoodsDialogListener != null) {
                    mCartGoodsDialogListener.gopay();
                }
//                pay();
                break;
            case R.id.business_shopping:
                dismiss();
                break;

        }
    }

//    public void pay() {
//        if (allCount > 0) {
//
//        } else {
//            MyToast.showMsg("购物车中空空如也");
//        }
//    }

    ClearShoppingCartDialog mdialog = null;

    public void clearCartGoodsDialog() {
        mdialog = new ClearShoppingCartDialog();
        mdialog.show(getFragmentManager(), "shoppingCart");
        mdialog.setShoppingCartDialogListener(new ClearShoppingCartDialog.ShoppingCartDialogListener() {
            @Override
            public void clear() {
                clearshopping();

            }
        });
    }

    private void clearshopping() {
        RequestBody body = new FormBody.Builder()
                .add("supid", shopid)
                .add("agenid", UserData.username)
                .add("token", UserData.tokenid)
                .build();
        MyOkHttp.getInstance().post(ApiFactory.HOST + "Api/Unshop/empty.php", body, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject datas = jsonObject.getJSONObject("data");
                    String status = String.valueOf(datas.get("status"));
                    String msg = String.valueOf(datas.get("msg"));
                    if (status.equals("1")) {
                        list.clear();
                        mAdapter.notifyDataSetChanged();
                        if (mCartGoodsDialogListener != null) {
                            mCartGoodsDialogListener.clear();
                            allCount = 0;
                            mTvShoppingCartCount.setVisibility(View.GONE);
                        }
                        mdialog.dismiss();
                    } else {
                        MyToast.showMsg(msg);
                    }
                } catch (JSONException e) {
                    MyToast.showMsg(e.getMessage());
                }
            }

            @Override
            public void fail(Request request, Exception e) {
                MyToast.showMsg(e.getMessage());
            }
        }, null);
    }

    private static final String TAG = "ShoppingCartDialog";

    @Override
    public void convert(final BaseViewHolder holder, final BusinessShoppingBean.DataBean.ListBean shopGoodsBean) {
        // 商品名
        holder.setTitle(R.id.tv_cart_goods_title, shopGoodsBean.getCommodity().getProname());
        holder.setTitle(R.id.tv_cart_goods_stylename, shopGoodsBean.getCommodity().getStylename());
        // 添加
        holder.getView(R.id.iv_cart_goods_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getView(R.id.iv_cart_goods_add).setClickable(false);
                RequestBody body = new FormBody.Builder()
                        .add("supplierid", shopid)
                        .add("proid", shopGoodsBean.getCommodity().getProid())
                        .add("styleid", shopGoodsBean.getCommodity().getStyleid())
                        .add("agenid", UserData.username)
                        .add("token", UserData.tokenid)
                        .build();
                MyOkHttp.getInstance().post(PathConstant.BUSINESS_ADDSHOPPING, body, new MyOkHttp.RequestCallBack() {
                    @Override
                    public void success(String data) {
                        try {
                            JSONObject object = new JSONObject(data);
                            JSONObject jsonObject = object.getJSONObject("data");
                            String msg = String.valueOf(jsonObject.get("msg"));
                            String status = String.valueOf(jsonObject.get("status"));
                            String count = String.valueOf(jsonObject.get("count"));
                            String num = String.valueOf(jsonObject.get("num"));
                            if (status.equals("1")) {
                                addgoods(holder, count);
                            } else {
                                if (msg.contains("登录")) {
                                    startActivity(new Intent(mActivity, LoginActivity.class));
                                } else {
                                    MyToast.showMsg(msg
                                    );
                                }
                            }
                        } catch (JSONException e) {
                            MyToast.showMsg(e.getMessage());
                        }
                    }

                    @Override
                    public void fail(Request request, Exception e) {

                    }
                }, null);

            }
        });
        // 减少
        holder.getView(R.id.iv_cart_goods_reduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getView(R.id.iv_cart_goods_reduce).setClickable(false);
                RequestBody body = new FormBody.Builder()
                        .add("supplierid", shopid)
                        .add("proid", shopGoodsBean.getCommodity().getProid())
                        .add("styleid", shopGoodsBean.getCommodity().getStyleid())
                        .add("agenid", UserData.username)
                        .add("token", UserData.tokenid)
                        .build();
                MyOkHttp.getInstance().post(PathConstant.BUSINESS_REDUCESHOPPING, body, new MyOkHttp.RequestCallBack() {
                    @Override
                    public void success(String data) {
                        try {
                            JSONObject object = new JSONObject(data);
                            JSONObject jsonObject = object.getJSONObject("data");
                            String msg = String.valueOf(jsonObject.get("msg"));
                            String status = String.valueOf(jsonObject.get("status"));
                            String count = String.valueOf(jsonObject.get("count"));
                            String unm = String.valueOf(jsonObject.get("num"));
                            if (status.equals("1")) {
                                reducegoods(holder, count, unm);
                            } else {
                                if (msg.contains("登录")) {
                                    startActivity(new Intent(mActivity, LoginActivity.class));
                                } else {
                                    MyToast.showMsg(msg
                                    );
                                }
                            }
                        } catch (JSONException e) {
                            MyToast.showMsg(e.getMessage());
                        }
                    }

                    @Override
                    public void fail(Request request, Exception e) {

                    }
                }, null);


            }
        });
        // 数量
        holder.setTitle(R.id.tv_cart_goods_count, String.valueOf(shopGoodsBean.getCommodity().getProNum()));
        holder.setTitle(R.id.tv_cart_goods_price, String.valueOf(shopGoodsBean.getCommodity().getPrice()));
    }

    private void reducegoods(BaseViewHolder holder, String allprice, String num) {
        BusinessShoppingBean.DataBean.ListBean shopGoodsBean = list.get(holder.getLayoutPosition());
        int count = Integer.valueOf(shopGoodsBean.getCommodity().getProNum());
        count--;
        allCount = Integer.valueOf(num);
        allCount--;
        if (count == 0) {
            int layoutPosition = holder.getLayoutPosition();
            list.remove(layoutPosition);
            mAdapter.notifyItemRemoved(layoutPosition);
        } else {
            holder.setTitle(R.id.tv_cart_goods_count, String.valueOf(count));
        }
        shopGoodsBean.getCommodity().setProNum(String.valueOf(count));
        // 防止点击过快
        if (allCount <= 0) {
            allCount = 0;
            mTvShoppingCartCount.setVisibility(View.GONE);
            shop_allprice.setText("共 ¥ 0.0");
        }
        shop_allprice.setText("共 ¥" + allprice);
        mTvShoppingCartCount.setText(String.valueOf(allCount));
        if (mCartGoodsDialogListener != null) {
            mCartGoodsDialogListener.reduce(allCount, shopGoodsBean.getCommodity().getProid(), shopGoodsBean.getCommodity().getProNum(), allprice);
        }
        holder.getView(R.id.iv_cart_goods_reduce).setClickable(true);
    }

    private void addgoods(BaseViewHolder holder, String allprice) {
        try {
            BusinessShoppingBean.DataBean.ListBean shopGoodsBean = list.get(holder.getLayoutPosition());
            int count = Integer.valueOf(shopGoodsBean.getCommodity().getProNum());
            count++;
            allCount++;
            shopGoodsBean.getCommodity().setProNum(String.valueOf(count));
            holder.setTitle(R.id.tv_cart_goods_count, String.valueOf(count));
            mTvShoppingCartCount.setText(String.valueOf(allCount));
            if (mCartGoodsDialogListener != null) {
                mCartGoodsDialogListener.add(allCount, shopGoodsBean.getCommodity().getProid(), shopGoodsBean.getCommodity().getProNum(), allprice);
            }
            shop_allprice.setText("共 ¥" + allprice);
        } catch (Exception e) {

        }
        holder.getView(R.id.iv_cart_goods_add).setClickable(true);
    }

    public void setCartGoodsDialogListener(CartGoodsDialogListener cartGoodsDialogListener) {
        mCartGoodsDialogListener = cartGoodsDialogListener;
    }

    public interface CartGoodsDialogListener {

        void add(int allCount, String proid, String num, String allprice);

        void reduce(int allCount, String proid, String num, String allprice);

        void clear();

        void gopay();
    }

}
