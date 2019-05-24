package com.hechuang.hepay.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.github.jdsjlzx.interfaces.OnLoadMoreListener
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.recyclerview.ProgressStyle
import com.hechuang.hepay.R
import com.hechuang.hepay.adapter.NewsAdapter
import com.hechuang.hepay.base.BaseAppCompatActivity
import com.hechuang.hepay.base.RvListener
import com.hechuang.hepay.bean.NewsBean
import com.hechuang.hepay.bean.UserData
import com.hechuang.hepay.persenter.NewPersenter
import com.hechuang.hepay.util.MyToast
import com.hechuang.hepay.view.INewsView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.view_news_title.*
import okhttp3.FormBody

/**
 * Created by Android_xu on 2017/9/27.
 * 消息页面
 */
class NewsActivity : BaseAppCompatActivity<NewPersenter>(), INewsView, OnLoadMoreListener {
    //    @BindView(R.id.news_back)
//    internal var mBack: ImageView? = null
//    @BindView(R.id.news_LRecyclerView)
//    internal var mLRecyclerView: LRecyclerView? = null
    internal var page = 1


    internal var apapter: NewsAdapter? = null

    override fun initlayout(): Int {
        return R.layout.activity_news
    }

    override fun initPersenter() {
        mPersenter = NewPersenter(this, this)
    }


    override fun initView() {
//        ButterKnife.bind(this)
        page = 1
        mPersenter!!.getmsg(page)
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        news_LRecyclerView!!.layoutManager = mLayoutManager
        news_LRecyclerView!!.setPullRefreshEnabled(false)
        news_LRecyclerView!!.setOnLoadMoreListener(this)
        //        //设置头部加载颜色
        //        news_LRecyclerView.setHeaderViewColor(R.color.fragment_textcolor, R.color.black_overlay, android.R.color.white);
        //设置底部加载颜色
        news_LRecyclerView!!.setFooterViewColor(R.color.appbar, R.color.black_overlay, R.color.messager)
        //设置底部加载文字提示
        news_LRecyclerView!!.setFooterViewHint(UserData.recycler_loadinghint, UserData.recycler_nomorehint, UserData.recycler_nonetworkhint)
        news_LRecyclerView!!.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader)
        val body = FormBody.Builder().build()
        news_back.setOnClickListener { finish() }
    }

//    @OnClick(R.id.news_back)
//    fun onClick(v: View) {
//        when (v.id) {
//            R.id.news_back -> finish()
//        }
//    }

    override fun showloading() {
        mLoading!!.show()
    }

    override fun dissmissloading() {

        mLoading!!.dismiss()
    }

    override fun getdataerror(msg: String) {

    }

    override fun setnewlistview(newsBeanList: List<NewsBean>) {
        if (page == 1) {
            apapter = NewsAdapter(newsBeanList, this, RvListener { id, position ->
                val intent1 = Intent(this@NewsActivity, NewsInfoActivity::class.java)
                intent1.putExtra("newsid", newsBeanList[position - 1].id)
                startActivity(intent1)
            })
            val LRAdapter = LRecyclerViewAdapter(apapter)
            news_LRecyclerView!!.adapter = LRAdapter
        } else {
            apapter!!.notifyDataSetChanged()
        }
        news_LRecyclerView!!.refreshComplete(0)
    }

    override fun listerror(msg: String) {
        if (page != 1) {
            news_LRecyclerView!!.setNoMore(true)
            news_LRecyclerView!!.refreshComplete(0)
        } else {
            MyToast.showMsg(msg)
        }
    }

    override fun onResume() {
        super.onResume()
        page = 1
        mPersenter!!.getmsg(page)
    }

    override fun onLoadMore() {
        page = page + 1
        mPersenter!!.getmsg(page)
    }
}
