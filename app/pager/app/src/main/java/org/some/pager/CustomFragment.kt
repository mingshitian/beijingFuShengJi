package org.some.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_login_first.*

class CustomFragment : Fragment() {

    private var mystring :String = ""
    private var mylist:List<SubBean>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login_first, container, false)
        val text :TextView = view.findViewById(R.id.center_text)
        val topSliding :RecyclerView = view.findViewById(R.id.top_sliding)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        topSliding.layoutManager = layoutManager
        topSliding.itemAnimator = DefaultItemAnimator()


//        top_sliding.adapter

        if (mylist == null)
            text.text = mystring

        if (mylist != null) {
            val viewPager: ViewPager = view.findViewById(R.id.sub_pager)
            viewPager.adapter = SubPagerAdapter(childFragmentManager, mylist)
            val adapter = activity?.let { KotlinRecycleAdapter(it,mylist ) }
            topSliding.adapter = adapter
            viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    adapter?.setSelectedPosition(position)
                }

            })


            adapter?.setOnKotlinItemClickListener(object : KotlinRecycleAdapter.IKotlinItemClickListener {
                override fun onItemClickListener(position: Int) {
                    viewPager.setCurrentItem(position,true)
                }
            })
        }
        return view


    }


    fun setCenterText(string: String){
        mystring = string
    }

    fun setSubList(list:List<SubBean>?){
        mylist = list
    }
}