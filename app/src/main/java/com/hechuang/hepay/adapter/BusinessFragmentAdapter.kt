package com.hechuang.hepay.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

class BusinessFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    lateinit var mFragments: ArrayList<Fragment>
    lateinit var mTitle: ArrayList<String>
    var currentfragment: Fragment? = null

    constructor(fm: FragmentManager, fragments: ArrayList<out Fragment>, title: ArrayList<String>) : this(fm) {
        mFragments = ArrayList<Fragment>(fragments)
        mTitle = title
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        this.currentfragment = `object` as Fragment
        super.setPrimaryItem(container, position, `object`)
    }
}
