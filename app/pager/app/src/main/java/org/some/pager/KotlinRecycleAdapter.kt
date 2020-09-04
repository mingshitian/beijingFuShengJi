package org.some.pager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KotlinRecycleAdapter(mContext: Context, private var list: List<SubBean>?) :
    RecyclerView.Adapter<KotlinRecycleAdapter.MyHolder>() {
    private var context: Context? = mContext
    private var itemClickListener: IKotlinItemClickListener? = null
    private var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_multi_column_list, parent, false)
        return MyHolder(view)
    }

    public fun setList(list: ArrayList<SubBean>?){
        this.list = list
        notifyDataSetChanged()
    }

    public fun setSelectedPosition(position: Int){
        currentPosition = position
        notifyDataSetChanged()
    }


//    android:foreground="@mipmap/ic_launcher"

    override fun getItemCount(): Int = list?.size!!

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.text.text = list!![position].name
        if (currentPosition == position){
            holder.parent.foreground = context?.getDrawable(R.mipmap.ic_launcher)
        } else{
            holder.parent.foreground = context?.getDrawable(R.drawable.ic_launcher_background)

        }


        // 点击事件
        holder.itemView.setOnClickListener {
            itemClickListener!!.onItemClickListener(position)
        }

    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // !! 断言
        var text: TextView = itemView.findViewById(R.id.item_text)
        var parent:RelativeLayout = itemView.findViewById(R.id.item_parent)


    }

    // 提供set方法
    fun setOnKotlinItemClickListener(itemClickListener: IKotlinItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //自定义接口
    interface IKotlinItemClickListener {
        fun onItemClickListener(position: Int)
    }

}