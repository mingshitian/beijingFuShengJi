package org.some.pager


import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

// 1
class SubPagerAdapter(fragmentManager: FragmentManager, private val sublist: List<SubBean>?) :
    FragmentStatePagerAdapter(fragmentManager) {

  // 2   
  override fun getItem(position: Int): SubPagerFragment {
    val fragment = SubPagerFragment()
      sublist?.get(position)?.name?.let { fragment.setCenterText(it) }
    return fragment
  }

  // 3  
  override fun getCount(): Int {
//    for (dataBean in movies){
//      if (dataBean.sublist!=null)
//    }
      return sublist?.size ?: 0
  }

}