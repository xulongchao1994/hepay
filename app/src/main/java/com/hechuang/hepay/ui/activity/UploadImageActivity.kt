package com.hechuang.hepay.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.UpLoadImageAdapter
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.bean.Uploadimglistdata
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.customview.OnRecyclerItemClickListener
import com.hechuang.hepay.customview.RedPacketPopupWindow
import com.hechuang.hepay.persenter.UploadImagePersenter
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.IUploadImageView
import kotlinx.android.synthetic.main.activity_uploadimg.*
import kotlinx.android.synthetic.main.view_uploadimage.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class UploadImageActivity : BaseAppCompatActivity<UploadImagePersenter>(), IUploadImageView, View.OnClickListener {
    val IMAGE_REQUEST_CODE = 321
    val CAMERA_REQUEST_CODE = 311
    val RESULT_REQUEST_CODE = 301
    var imageUri: Uri? = null
    var photoPath: String? = null
    var bitmap: Bitmap? = null
    var imagelist: List<Bitmap>? = null
    var jiancaiuri: Uri? = null
    var datalist = ArrayList<Uploadimglistdata.DataBean.ListBean>()
    var adapter: UpLoadImageAdapter? = null
    var tempFile: File? = null
    var popupWindow: PopupWindow? = null
    var sp: SharedPreferences? = null
    private var mItemTouchHelper: ItemTouchHelper? = null

    private fun showpopup() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.view_uploadimage, null)
        popupWindow = RedPacketPopupWindow(this)
        popupWindow!!.contentView = view
        view.uploadimage_photograph.setOnClickListener(View.OnClickListener {
            //相机
            if (Build.VERSION.SDK_INT > 23) {
                val callperssion = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (callperssion != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 111)
                    return@OnClickListener
                } else {
                    shengqingxiangjiquanxian()
                }
            } else {
                openphotograph()
            }
            popupWindow!!.dismiss()
        })
        view.uploadimage_album.setOnClickListener(View.OnClickListener {
            //相册
            val intent_gallery = Intent(Intent.ACTION_PICK)
            intent_gallery.type = "image/*"
            startActivityForResult(intent_gallery, IMAGE_REQUEST_CODE)
            if (popupWindow != null && popupWindow!!.isShowing) popupWindow!!.dismiss()
        })
        view.uploadimage_cancel.setOnClickListener(View.OnClickListener {
            //取消
            if (popupWindow != null && popupWindow!!.isShowing) popupWindow!!.dismiss()
        })
        view.uploadimage_layout.setOnClickListener(View.OnClickListener {
            //取消
            if (popupWindow != null && popupWindow!!.isShowing) popupWindow!!.dismiss()
        })
        popupWindow!!.showAsDropDown(uploadimg_bianji)
    }

    /**
     * 内存权限判断完成后判断相机权限
     */
    private fun shengqingxiangjiquanxian() {
        if (Build.VERSION.SDK_INT > 23) {
            var callperssion = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (callperssion != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 222)
                return
            } else {
                openphotograph()
            }
        } else {
            openphotograph()
        }
    }

    /**
     * 上传图片成功
     */
    override fun upimage_ok(msg: String) {
        MyToast.showMsg(msg)
        if (uploadimg_bianji.visibility == View.GONE) {
            uploadimg_bianji.visibility = View.VISIBLE
        }
        mPersenter!!.getimagelist()
    }

    /**
     * 删除图片成功
     */
    override fun dtimgage_ok(msg: String) {
        MyToast.showMsg(msg)
        mPersenter!!.getimagelist()
        uploadimg_bianji.text = "编辑"
        uploadimg_quxiao.visibility = View.GONE
    }

    /**
     * 获取数据成功加载列表
     */
    override fun getlistdata_ok(data: ArrayList<Uploadimglistdata.DataBean.ListBean>) {
//        Log.d("uploadimage", data.size.toString())
        if (data == null || data.size < 1) {
            uploadimg_bianji.visibility = View.GONE
        }
        if (data == null || data.size < 5) {
            val addlost = Uploadimglistdata.DataBean.ListBean("-1", "jiahao", false, false)
            data.add(addlost)
        }

        datalist = data
        adapter = UpLoadImageAdapter(datalist, this, null)
        uploadimg_recycler.adapter = adapter

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.uploadimg_back -> finish()
            R.id.uploadimg_bianji -> {
                val text_str = uploadimg_bianji.text.toString()
                when (text_str) {
                    "编辑" -> {
                        var jiahao_p = 0
                        for (i in 0 until datalist.size) {
                            if (datalist.get(i).id != "-1") {
                                datalist.get(i).isIsshow_dt = true
                            } else {
                                jiahao_p = i
                            }
                        }
                        if (jiahao_p > 0) {
                            datalist.removeAt(jiahao_p)
//                            adapter!!.gaibianimg()
                        }
                        if (adapter != null) {
                            adapter!!.gaibianimg()
                        }
                        uploadimg_bianji.text = "删除"
                        uploadimg_quxiao.visibility = View.VISIBLE
                    }
                    "删除" -> {
                        var shuliang: Int? = 0//用户判断用户选择图片的数量
                        //先执行删除操作然后在改变状态
                        var id_json = JSONObject()
                        var id_array = JSONArray()
                        for (i in 0 until datalist.size) {
                            if (datalist.get(i).isIsdelete) {
                                id_array.put(datalist.get(i).id)
                                shuliang = shuliang!! + 1
                            }
                        }
                        if (shuliang!! > 0) {
                            id_json.put("json", id_array)
                            val json_str = id_json.toString()
                            AlertDialog.Builder(this).setTitle("禾田付").setMessage("是否确认删除图片")
                                    .setPositiveButton("确定") { dialog, which ->
                                        mPersenter!!.deleteimages(json_str)
                                    }.setNegativeButton("取消", null).show()
                        } else {
                            for (i in 0 until datalist.size) {
//                                if (datalist.get(i).id != "-1") {
                                datalist.get(i).isIsshow_dt = false
//                                } else {
//                                    datalist.removeAt(i)
////                                    datalist.get(i).url = "jiahao"
//                                }
                            }
                            if (datalist.size < 5) {
                                val addlost = Uploadimglistdata.DataBean.ListBean("-1", "jiahao", false, false)
                                datalist.add(addlost)
                            }
                            if (adapter != null) {
                                adapter!!.gaibianimg()
                            }
                            uploadimg_bianji.text = "编辑"
                            uploadimg_quxiao.visibility = View.GONE
                        }
                    }
                }
            }
            R.id.uploadimg_quxiao -> {
                for (i in 0 until datalist.size) {
                    datalist.get(i).isIsshow_dt = false
                }
                if (datalist.size < 5) {
                    val addlost = Uploadimglistdata.DataBean.ListBean("-1", "jiahao", false, false)
                    datalist.add(addlost)
                }
                adapter!!.gaibianimg()
                uploadimg_bianji.text = "编辑"
                uploadimg_quxiao.visibility = View.GONE
            }
        }
    }

    override fun initlayout(): Int {
        return R.layout.activity_uploadimg
    }

    override fun initPersenter() {
        mPersenter = UploadImagePersenter(this, this)
    }

    /**
     * 加载视图
     */
    override fun initView() {
        imagelist = ArrayList()
        val layoutp = GridLayoutManager(this, 2)
        uploadimg_recycler.layoutManager = layoutp!!
        mPersenter!!.getimagelist()
        uploadimg_back.setOnClickListener(this)
        uploadimg_bianji.setOnClickListener(this)
        uploadimg_quxiao.setOnClickListener(this)
        uploadimg_recycler.addOnItemTouchListener(object : OnRecyclerItemClickListener(uploadimg_recycler) {
            override fun onItemClick(vh: RecyclerView.ViewHolder) {
                if (datalist.get(vh.layoutPosition).url == "jiahao") {
                    showpopup()
                } else {
                    datalist.get(vh.layoutPosition).isIsdelete = !datalist.get(vh.layoutPosition).isIsdelete
                    adapter!!.gaibianimg()
                }
            }

            override fun onItemLongClick(vh: RecyclerView.ViewHolder) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
                if (datalist.get(vh.layoutPosition).url != "jiahao" && datalist.get(vh.layoutPosition).url != "xiaoshi")
                    if (vh.layoutPosition != 0) {
                        mItemTouchHelper!!.startDrag(vh)
                        //获取系统震动服务
//                    val vib = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator//震动70毫秒
//                    vib.vibrate(70)

                    }
            }
        })
        mItemTouchHelper = ItemTouchHelper(
                object : ItemTouchHelper.Callback() {
                    /**
                     * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
                     * @param recyclerView
                     * @param viewHolder
                     * @return
                     */
                    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                        if (recyclerView.layoutManager is GridLayoutManager) {
                            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                            val swipeFlags = 0
                            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
                        } else {
                            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                            val swipeFlags = 0
                            //                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
                        }
                    }

                    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        //得到当拖拽的viewHolder的Position
                        val fromPosition = viewHolder.adapterPosition
                        //拿到当前拖拽到的item的viewHolder
                        val toPosition = target.adapterPosition
                        if (fromPosition < toPosition) {
                            for (i in fromPosition until toPosition) {
                                Collections.swap(datalist, i, i + 1)
                            }
                        } else {
                            for (i in fromPosition downTo toPosition + 1) {
                                Collections.swap(datalist, i, i - 1)
                            }
                        }
                        adapter!!.notifyItemMoved(fromPosition, toPosition)
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        //                int position = viewHolder.getAdapterPosition();
                        //                myAdapter.notifyItemRemoved(position);
                        //                datas.remove(position);
                    }

                    /**
                     * 重写拖拽可用
                     * @return
                     */
                    override fun isLongPressDragEnabled(): Boolean {
                        return false
                    }

                    /**
                     * 长按选中Item的时候开始调用
                     *
                     * @param viewHolder
                     * @param actionState
                     */
                    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                            viewHolder!!.itemView.setBackgroundColor(Color.LTGRAY)
                        }
                        if (viewHolder != null) {
//                            Log.d("uploadimage", "开始........." + viewHolder!!.layoutPosition)
                        }
                        super.onSelectedChanged(viewHolder, actionState)
                    }

                    /**
                     * 手指松开的时候还原
                     * @param recyclerView
                     * @param viewHolder
                     */
                    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {

//                        Log.d("uploadimage", "结束........." + viewHolder.layoutPosition)
                        super.clearView(recyclerView, viewHolder)
                        viewHolder.itemView.setBackgroundColor(0)
                    }
                })

        mItemTouchHelper!!.attachToRecyclerView(uploadimg_recycler)


    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    /**
     * 获取数据错误
     */
    override fun getdataerror(msg: String?) {
        if (msg == "请先登录") {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            MyToast.showMsg("登录失效，请重新登录")
            sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
            val editor = sp!!.edit()
            editor.putString("token_id", "")
            editor.putString("urserid", "")
            editor.putString("usertype", "")
            editor.putBoolean("islogin", false)
            UserData.islogin = false
            editor.commit()
            UserData.sessionid = ""
            UserData.tokenid = ""
            UserData.username = ""
            uploadimg_bianji.visibility = View.GONE
            finish()

            WebActivity.mWebActivity.finish()
        } else {
            MyToast.showMsg(msg)
        }
    }

    /**
     * 打开相机
     */
    private fun openphotograph() {
        if (popupWindow != null && popupWindow!!.isShowing) popupWindow!!.dismiss()
        //用于保存调用相机拍照后所生成的文件
        tempFile = File(Environment.getExternalStorageDirectory().path, System.currentTimeMillis().toString() + ".jpg")
        //跳转到调用系统相机
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            val contentUri = FileProvider.getUriForFile(this, "com.hechuang.hepay.fileProvider", tempFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
//            Log.e("dasd", contentUri.toString())
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile))
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    /**
     * 剪切图片
     */
    private fun crop(uri: Uri) {
        val CropPhoto = File(externalCacheDir, "crop.jpg")
        try {
            if (CropPhoto.exists()) {
                CropPhoto.delete()
            }
            CropPhoto.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        jiancaiuri = Uri.fromFile(CropPhoto)
//        Log.d("uploadimage", uri.toString())
//        Log.d("uploadimage", jiancaiuri.toString())
        // 裁剪图片意图
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 4)
        intent.putExtra("aspectY", 3)
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 600)
        intent.putExtra("outputY", 450)
        // 图片格式
        intent.putExtra("return-data", false)// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, jiancaiuri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true)
        startActivityForResult(intent, RESULT_REQUEST_CODE)//同样的在onActivityResult中处理剪裁好的图片
    }

    //把bitmap转换成字符串上传到服务器
    fun bitmapToString(bitmap: Bitmap) {
        val string: String?
        val btString = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, btString)
        val bytes = btString.toByteArray()
        string = Base64.encodeToString(bytes, Base64.DEFAULT)
        mPersenter!!.uploadimg(string)
    }

    /**
     * 拍照或者从相册返回后从此方法处理
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE) {//相册
//            Log.d("uploadimage", "image")
            if (data != null) {
                try {
                    imageUri = data!!.getData()
                    //获取照片路径
                    if (imageUri != null) {
                        val filePathColumn: Array<String> = arrayOf(MediaStore.Audio.Media.DATA)
                        val cursor = contentResolver.query(imageUri, filePathColumn, null, null, null)
                        cursor!!.moveToFirst()
                        photoPath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]))
                        cursor.close()
                        crop(this.imageUri!!)
                    }
                } catch (e: Exception) {

                }
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {//相机
//            Log.d("uploadimage", "camear")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(this, "com.hechuang.hepay.fileProvider", tempFile!!)
//                Log.d("dasd", imageUri!!.toString())
                crop(imageUri!!)
            } else {
                crop(Uri.fromFile(tempFile))
            }
        } else if (requestCode == RESULT_REQUEST_CODE) {
//            Log.d("uploadimage", "RESULT")
            try {
                val bitmaps: Bitmap
                bitmaps = BitmapFactory.decodeStream(contentResolver.openInputStream(jiancaiuri))
                bitmapToString(bitmaps)
            } catch (e: IllegalStateException) {
//                Log.e("uploadimage", e.message)
            }
//            uploadiimg_img5.setImageBitmap(bitmaps)
            //请求服务器的接口，上传到服务器
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            111 -> {//读取内存权限
                for (s in permissions) {
                    when (s) {
                        "android.permission.READ_EXTERNAL_STORAGE" -> {
                            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                                shengqingxiangjiquanxian()
                            } else {
                                MyToast.showMsg("读写权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                            }
                        }
                        "android.permission.WRITE_EXTERNAL_STORAGE" -> {
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                shengqingxiangjiquanxian()
                            } else {
                                MyToast.showMsg("读写权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                            }
                        }
                    }
                }
            }
            222 -> {//获取相机权限
                for (s in permissions) {
                    when (s) {
                        "android.permission.CAMERA" -> {
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                openphotograph()
                            } else {
                                MyToast.showMsg("读写权限已禁止，部分功能暂时无法使用，建议在权限管理中打开")
                            }
                        }
                    }
                }
            }
        }
    }

}

