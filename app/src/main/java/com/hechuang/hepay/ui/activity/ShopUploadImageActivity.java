package com.hechuang.hepay.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.Uploadimglistdata;
import com.hechuang.hepay.persenter.UploadImagePersenter;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.view.IUploadImageView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;

@SuppressLint("Registered")
public class ShopUploadImageActivity extends BaseAppCompatActivity<UploadImagePersenter> implements IUploadImageView, View.OnClickListener {

    private final int IMAGE_REQUEST_CODE = 321;
    private final int CAMERA_REQUEST_CODE = 310;
    private final int RESULT_REQUEST_CODE = 301;
    Uri imageUri;
    String photoPath;
    String bitmapToString;
    Bitmap bitmap;
//    @BindView(R.id.uploadiimg_img1)
//    ImageView mimage1;
//    @BindView(R.id.uploadiimg_img2)
//    ImageView mimage2;
//    @BindView(R.id.uploadiimg_img3)
//    ImageView mimage3;
//    @BindView(R.id.uploadiimg_img4)
//    ImageView mimage4;
//    @BindView(R.id.uploadiimg_img5)
//    ImageView mimage5;

    @Override
    protected int initlayout() {
        return R.layout.activity_uploadimg;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new UploadImagePersenter(this, this));
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

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

    }

    @Override
    public void onClick(View v) {
        Intent intent_gallery = new Intent(Intent.ACTION_PICK);
        intent_gallery.setType("image/*");
        startActivityForResult(intent_gallery, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                String[] filePathColumn = new String[]{MediaStore.Audio.Media.DATA};
                Cursor cursor = getContentResolver().query(imageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                photoPath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                cursor.close();
                crop(imageUri);
                break;
            case CAMERA_REQUEST_CODE:
                photoPath = Environment.getExternalStorageDirectory().toString() + "/image.jpg";
                crop(imageUri);
                break;
            case RESULT_REQUEST_CODE:
//                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(outputUri));

//                setImageBitmap(mimage1);

                break;
        }
    }

    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        File CropPhoto = new File(getExternalCacheDir(), "crop.jpg");
        try {
            if (CropPhoto.exists()) {
                CropPhoto.delete();
            }
            CropPhoto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        imageUri = Uri.fromFile(CropPhoto);
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // 图片格式
//        intent.putExtra("outputFormat", "PNG")
//        intent.putExtra("noFaceDetection", true)// 取消人脸识别
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);//同样的在onActivityResult中处理剪裁好的图片
    }


    /**
     * 压缩图片
     */
    private void setImageBitmap(ImageView imageview) {
        //获取imageview的宽和高
//        val targetWidth = imageview.getWidth()
//        val targetHeight = imageview.getHeight()

        //根据图片路径，获取bitmap的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true
//        BitmapFactory.decodeFile(photoPath, options)
//        val photoWidth = options.outWidth
//        val photoHeight = options.outHeight

        //获取缩放比例
//        var inSampleSize = 1
//        if (photoWidth > targetWidth || photoHeight > targetHeight) {
//            val widthRatio = Math.round(photoWidth.toFloat() / targetWidth)
//            val heightRatio = Math.round(photoHeight.toFloat() / targetHeight)
//            inSampleSize = Math.min(widthRatio, heightRatio)
//            Log.d("uploadimage", inSampleSize.toString() + "  " + widthRatio + "   " + heightRatio)
//        }
        //使用现在的options获取Bitmap
        options.inSampleSize = 4;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(photoPath, options);
        imageview.setImageBitmap(bitmap);
        bitmapToString(bitmap);
    }

    //把bitmap转换成字符串
    String bitmapToString(Bitmap bitmap) {
        String string;
        ByteArrayOutputStream btString = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, btString);
        byte[] bytes = btString.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
//        Log.d("uploadimage", string)
//        RequestBody body = new FormBody.Builder().add("file", string).build();
//        MyOkHttp.getInstance().post("http://192.168.1.150:8050/Api/Index/test01.php", body, object :MyOkHttp.RequestCallBack {
//            override fun success(data:String ?){
//                Log.d("uploadimg", data)
//            }
//
//            override fun fail(request:Request ?, e:java.lang.Exception ?){
//            }
//        },null)
        return string;
    }

    @Override
    public void getlistdata_ok(@NotNull ArrayList<Uploadimglistdata.DataBean.ListBean> data) {

    }

    @Override
    public void upimage_ok(@NotNull String msg) {
        MyToast.showMsg(msg);
    }

    @Override
    public void dtimgage_ok(@NotNull String msg) {

    }

//    @Override
//    public void getlistdata_ok(@NotNull String data) {
//
//    }
}
