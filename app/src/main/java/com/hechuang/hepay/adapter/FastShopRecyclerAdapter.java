package com.hechuang.hepay.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hechuang.hepay.R;
import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.bean.FastShopBase;
import com.hechuang.hepay.bean.FastShopShoreTagsBean;
import com.hechuang.hepay.bean.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Request;

/**
 * 联盟商家列表适配器
 */

public class FastShopRecyclerAdapter extends RecyclerView.Adapter<FastShopRecyclerAdapter.MyFastShopViewHolder> {
    private static final String TAG = "FastShopRecyclerAdapter";
    private Context mContext;
    private LayoutInflater mInflater;
    private List<FastShopBase> bean;
    private AlertDialog alertDialog;
    private String lng = "";
    private String lat = "";
    private String z_lng = "";
    private String z_lat = "";
    public Activity activity;
    private String[] maps = {};

    public void FastShopRecyclerAdapter_getactivity(Activity activity) {
        this.activity = activity;

    }

    public FastShopRecyclerAdapter(Context mContext, List<FastShopBase> bean) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.bean = bean;
    }

    @Override
    public MyFastShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyFastShopViewHolder(mInflater.inflate(R.layout.adapter_fastshopitme, parent, false));
    }

    @Override
    public void onBindViewHolder(MyFastShopViewHolder holder, int position) {
        final FastShopBase shopBase = bean.get(position);
        Glide.with(mContext).load(shopBase.getShopphoto()).override(200, 200).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.icon);
        holder.shopname.setText(shopBase.getShopname());
        holder.shopcontent.setText(shopBase.getCategoryanme());
        List<FastShopShoreTagsBean> listShopTagsBean = shopBase.getShoreTags();
        String[] shoreTags = new String[listShopTagsBean.size()];
        if (listShopTagsBean != null && listShopTagsBean.size() > 0) {
            for (int i = 0; i < listShopTagsBean.size(); i++) {
                shoreTags[i] = listShopTagsBean.get(i).getName();
            }
        }
//        switch (listShopTagsBean.size()) {
//            case 1:
//                holder.shoreTags1.setText(shoreTags[0]);
//                holder.shoreTags2.setText("");
//                holder.shoreTags3.setText("");
//                holder.shoreTags4.setText("");
//                holder.shoreTags5.setText("");
//                break;
//            case 2:
//                holder.shoreTags1.setText(shoreTags[0]);
//                holder.shoreTags2.setText(shoreTags[1]);
//                holder.shoreTags3.setText("");
//                holder.shoreTags4.setText("");
//                holder.shoreTags5.setText("");
//                break;
//            case 3:
//                holder.shoreTags1.setText(shoreTags[0]);
//                holder.shoreTags2.setText(shoreTags[1]);
//                holder.shoreTags3.setText(shoreTags[2]);
//                holder.shoreTags4.setText("");
//                holder.shoreTags5.setText("");
//                break;
//            case 4:
//                holder.shoreTags1.setText(shoreTags[0]);
//                holder.shoreTags2.setText(shoreTags[1]);
//                holder.shoreTags3.setText(shoreTags[2]);
//                holder.shoreTags4.setText(shoreTags[3]);
//                holder.shoreTags5.setText("");
//                break;
//            case 5:
//                holder.shoreTags1.setText(shoreTags[0]);
//                holder.shoreTags2.setText(shoreTags[1]);
//                holder.shoreTags3.setText(shoreTags[2]);
//                holder.shoreTags4.setText(shoreTags[3]);
//                holder.shoreTags5.setText(shoreTags[4]);
//                break;
//            default:
//                holder.shoreTags1.setText("");
//                holder.shoreTags2.setText("");
//                holder.shoreTags3.setText("");
//                holder.shoreTags4.setText("");
//                holder.shoreTags5.setText("");
//                break;
//        }
        holder.addres.setText(shopBase.getCity() + shopBase.getCounty() + shopBase.getAddress());
        switch (shopBase.getShareaccounths()) {
            case "0":
                holder.shopping_img.setVisibility(View.GONE);
                break;
            case "1":
                holder.shopping_img.setVisibility(View.VISIBLE);
                break;
            default:
                holder.shopping_img.setVisibility(View.GONE);
                break;
        }
        holder.shorejli.setText(shopBase.getDistance());

//        );
    }

    @Override
    public int getItemCount() {
        return bean == null ? 0 : bean.size();
    }

    class MyFastShopViewHolder extends RecyclerView.ViewHolder {
        //        RelativeLayout title_layout;
//        TextView title;
        ImageView icon;
        TextView shopname;
        TextView shopcontent;
        TextView addres;
        //        TextView time;
//        ImageView location;
////        ImageView phone;
//        TextView shoreTags1;
//        TextView shoreTags2;
//        TextView shoreTags3;
//        TextView shoreTags4;
//        TextView shoreTags5;
        TextView shorejli;
        ImageView shopping_img;
        LinearLayout shopname_layout;

        public MyFastShopViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.adapter_fastshop_icon);
            shopname = (TextView) itemView.findViewById(R.id.adapter_fastshop_shopname);
            shopcontent = (TextView) itemView.findViewById(R.id.adapter_fastshop_shopcontent);
            addres = (TextView) itemView.findViewById(R.id.adapter_fastshop_addres);
//            time = (TextView) itemView.findViewById(R.id.adapter_fastshop_time);
//            location = (ImageView) itemView.findViewById(R.id.adapter_fastshop_location);
//            phone = (ImageView) itemView.findViewById(R.id.adapter_fastshop_phone);
//            shoreTags1 = (TextView) itemView.findViewById(R.id.adapter_fastshop_shoreTags1);
//            shoreTags2 = (TextView) itemView.findViewById(R.id.adapter_fastshop_shoreTags2);
//            shoreTags3 = (TextView) itemView.findViewById(R.id.adapter_fastshop_shoreTags3);
//            shoreTags4 = (TextView) itemView.findViewById(R.id.adapter_fastshop_shoreTags4);
//            shoreTags5 = (TextView) itemView.findViewById(R.id.adapter_fastshop_shoreTags5);
            shorejli = (TextView) itemView.findViewById(R.id.adapter_fastshop_juli);
//            shopping_img = (ImageView) itemView.findViewById(R.id.adapter_fastshop_shopping_img);
            shopname_layout = (LinearLayout) itemView.findViewById(R.id.adapter_fastshop_shopname_laout);
        }

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 地理位置转为坐标系
     *
     * @return location :location[0] = lontitude  location[1]=latitude
     */
    public void address_maps(String city, String discrict) {
        String url = ApiFactory.ADDERS_MAPS + "&address=" + discrict + "&city=" + city + "&ak=" + UserData.baidu_ak;
        MyOkHttp.getInstance().get(url, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
//                Log.e("maps", "坐标转换后的数据 " + data);
                String data1 = data.replace("renderOption&&renderOption({", "{");
                String data2 = data1.replace("})", "}");
                try {
                    JSONObject json = new JSONObject(data2);
                    String status = String.valueOf(json.get("status"));
                    if (status.equals("0")) {
                        JSONObject result = json.getJSONObject("result");
                        JSONObject item = result.getJSONObject("location");
                        lng = String.valueOf(item.get("lng"));
                        lat = String.valueOf(item.get("lat"));
                        baidu_gaode(lng, lat);
                    } else {

                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void fail(Request request, Exception e) {

            }
        }, null);
    }

    /***
     * 百度转高德
     */
    public void baidu_gaode(String lontitude, String latitude) {
        String url = ApiFactory.BAIDU_GAODE + "locations=" + lontitude + "," + latitude + "&coordsys=" + "baidu" + "&output=" + "json" + "&key=" + UserData.gaode_ak;
        MyOkHttp.getInstance().get(url, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                try {
                    JSONObject json = new JSONObject(data);
                    String status = String.valueOf(json.get("status"));
                    String info = String.valueOf(json.get("info"));
                    String infocode = String.valueOf(json.get("infocode"));
                    String locations = String.valueOf(json.get("locations"));
                    String[] maps = locations.split(",");
                    z_lat = maps[0];//lat
                    z_lng = maps[1];//lng
                } catch (JSONException e) {

                }
            }

            @Override
            public void fail(Request request, Exception e) {

            }
        }, null);
    }


}
