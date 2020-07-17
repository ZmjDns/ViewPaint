package com.zmj.viewpaint.lesson15_view_summary

import android.os.Bundle
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.zmj.viewpaint.R
import com.zmj.viewpaint.lesson15_view_summary.fragment.LikeFragment
import com.zmj.viewpaint.lesson15_view_summary.fragment.RulerFragment
import com.zmj.viewpaint.lesson15_view_summary.fragment.SportFragment
import kotlinx.android.synthetic.main.act_summary.*

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/17
 * Description :
 */
class SummaryAct: AppCompatActivity() {

    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_summary)

        fragmentList.add(LikeFragment())
        fragmentList.add(RulerFragment())
        fragmentList.add(SportFragment())

        titleList.add("点赞")
        titleList.add("尺子")
        titleList.add("运动环")

        pagerAdapter = PagerAdapter(supportFragmentManager,fragmentList,titleList)

        pager.adapter = pagerAdapter
        tabs.setupWithViewPager(pager)
    }
}