package com.hechuang.hepay.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.jdsjlzx.interfaces.OnLoadMoreListener
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.recyclerview.ProgressStyle
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.NoticeRecyclerAdapter
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.base.RvListener
import com.hechuang.hepay.bean.NoticeListBean
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.persenter.NoticePersenter
import com.hechuang.hepay.view.INoticeView
import kotlinx.android.synthetic.main.activity_notice.*

/**
 * Created by Android_xu on 2017/11/13.
 * 公告页面
 */
class NoticeActivity : BaseAppCompatActivity<NoticePersenter>(), INoticeView, OnLoadMoreListener, View.OnClickListener {
    //    @BindView(R.id.notice_back)
//    internal var mBack: ImageView? = null
//    @BindView(R.id.notice_recycler)
//    internal var mLRecyclerView: LRecyclerView? = null
    internal var page = 1
    private var id = ""
    internal var name = ""


    internal var adapter: NoticeRecyclerAdapter? = null
    internal var lRadapter: LRecyclerViewAdapter? = null

    override fun initlayout(): Int {
        return R.layout.activity_notice
    }

    override fun initPersenter() {
        mPersenter = NoticePersenter(this, this)
    }


    override fun initView() {
        id = intent.getStringExtra("id")
//        ButterKnife.bind(this)
        notice_back.setOnClickListener(this)
        mPersenter!!.getnoicelist(id, page.toString(), name)
        notice_recycler!!.layoutManager = LinearLayoutManager(this)
        notice_recycler!!.setPullRefreshEnabled(false)
        notice_recycler!!.setOnLoadMoreListener(this)
        //设置头部加载颜色
        notice_recycler!!.setHeaderViewColor(R.color.appbar, R.color.black_overlay, android.R.color.white)
        //设置底部加载颜色
        notice_recycler!!.setFooterViewColor(R.color.appbar, R.color.black_overlay, android.R.color.white)
        //设置底部加载文字提示
        notice_recycler!!.setFooterViewHint(UserData.recycler_loadinghint, UserData.recycler_nomorehint, UserData.recycler_nonetworkhint)
        notice_recycler!!.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader)
        notice_recycler!!.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.notice_back -> finish()
        }
    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {
        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String) {

    }

    override fun setnoticelistdata(mNoticelistbean: List<NoticeListBean>) {
        if (page == 1) {
            adapter = NoticeRecyclerAdapter(mNoticelistbean, this, RvListener { id, position ->
                val intent = Intent(this@NoticeActivity, NoticeInfoActivity::class.java)
                intent.putExtra("id", mNoticelistbean[position - 1].id)
                startActivity(intent)
            })
            lRadapter = LRecyclerViewAdapter(adapter)
            notice_recycler!!.adapter = lRadapter
            notice_recycler!!.setNoMore(false)
        } else {
            adapter!!.notifyDataSetChanged()
            lRadapter!!.notifyDataSetChanged()
        }
        notice_recycler!!.refreshComplete(0)
    }

    override fun setnotmore(msg: String) {
        notice_recycler!!.setNoMore(true)
    }

    override fun onLoadMore() {
        notice_recycler!!.setNoMore(false)
        page = page + 1
        mPersenter!!.getnoicelist("", page.toString(), name)
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }

    companion object {
        private val TAG = "NoticeActivity"
    }
}
