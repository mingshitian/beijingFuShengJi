package org.some.pager


import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

// 1
class MoviesPagerAdapter(fragmentManager: FragmentManager, private val movies: List<DataBean>) :
    FragmentStatePagerAdapter(fragmentManager) {

  // 2   
  override fun getItem(position: Int): CustomFragment {
    val fragment = CustomFragment()
    fragment.setCenterText(movies[position].name)
    if (movies[position].sublist!=null)
    fragment.setSubList(movies[position].sublist)
    return fragment
  }

  // 3  
  override fun getCount(): Int {
    return movies.size
  }
}