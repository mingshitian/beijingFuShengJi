package org.some.pager



import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var list: ArrayList<DataBean>? = null

    private var list2: ArrayList<SubBean>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    private fun initData() {
        list = ArrayList()
        list2 = ArrayList()
        val subBean1 = SubBean("我是子条目1")
        val subBean2 = SubBean("我是子条目2")
        val subBean3 = SubBean("我是子条目2")
        val subBean4 = SubBean("我是子条目2")
        val subBean5 = SubBean("我是子条目2")

        val subBean6 = SubBean("我是子条目2")

        list2!!.add(subBean1)
        list2!!.add(subBean2)
        list2!!.add(subBean3)

        list2!!.add(subBean4)
        list2!!.add(subBean5)

        list2!!.add(subBean6)

        for (i in 1..10) {
            val dataBean: DataBean = if (i == 3) {
                DataBean("我有子条目$i", list2)

            } else {
                DataBean("我是条目$i", null)
            }
            list!!.add(dataBean)
        }



    }

    private fun getBitmap(context: Context, vectorDrawableId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val vectorDrawable: Drawable? =
            context.getDrawable(vectorDrawableId)
        if (vectorDrawable != null) {
            bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
        }
        val canvas = bitmap?.let { Canvas(it) }
        if (canvas != null) {
            vectorDrawable?.setBounds(0, 0, canvas.width, canvas.height)
        }
        if (vectorDrawable != null) {
            if (canvas != null) {
                vectorDrawable.draw(canvas)
            }
        }
        return bitmap
    }

    private fun initView() {


//       val bitmap =  getBitmap(this,R.drawable.ic_launcher_background)
//        if (bitmap != null) {
//            iv_good.setImageBitmap(bitmap)
//        }

        pager_sliding.adapter = list?.let { MoviesPagerAdapter(supportFragmentManager, it) }
        pager_sliding.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }
        })
    }
}
