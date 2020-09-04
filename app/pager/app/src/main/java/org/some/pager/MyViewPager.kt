package org.some.pager

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class MyViewPager : ViewPager {
    constructor(context: Context) : super(context) {}
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }


    override fun canScrollHorizontally(direction: Int): Boolean {
        return true
    }

    fun setOnPagerSlidingListener(listener:OnPagerSlidingListener){
       this.addOnPageChangeListener( object : OnPageChangeListener{
           override fun onPageScrollStateChanged(state: Int) {
           }

           override fun onPageScrolled(
               position: Int,
               positionOffset: Float,
               positionOffsetPixels: Int
           ) {
           }

           override fun onPageSelected(position: Int) {
               listener.onPageSelected(position,false,null,0)
           }

       })
    }



    interface OnPagerSlidingListener {
        fun onPageSelected(
            position: Int, isSubBean: Boolean, sublist: ArrayList<SubBean>?,
            subPosition:Int)
    }
}