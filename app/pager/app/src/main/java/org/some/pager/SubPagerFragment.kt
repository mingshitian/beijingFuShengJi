package org.some.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_first.*

class SubPagerFragment : Fragment() {

    private var myString :String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_subpager, container, false)
        val text :TextView = view.findViewById(R.id.center_text)
        text.text = myString
        return view


    }


    public fun setCenterText(string: String){
        myString = string
    }
}