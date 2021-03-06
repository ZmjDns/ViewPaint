package com.zmj.viewpaint.lesson15_view_summary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zmj.viewpaint.R
import kotlinx.android.synthetic.main.act_sport_view.view.*

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/17
 * Description :
 */
class SportFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.act_sport_view,container,false)
        view.viewMove.startAnimal()
        view.viewSport.startAnimal()
        return view
    }
}