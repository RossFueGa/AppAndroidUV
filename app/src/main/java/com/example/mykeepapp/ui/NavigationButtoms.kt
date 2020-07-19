package com.example.mykeepapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.mykeepapp.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_navigation_buttoms.*


class NavigationButtoms : AppCompatActivity() {



    private val adapter by lazy { ViewAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_buttoms)



        pager.adapter = adapter



        val tabLayoutMediator = TabLayoutMediator(tab_layout, pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.setIcon(R.drawable.baseline_home_black_24dp)
                    }
                    1 -> {
                        tab.setIcon(R.drawable.ic_attachment_black_24dp)
                     //   (pager.adapter as ViewAdapter).notifyDataSetChanged()
                    }
                    2 -> {
                        tab.setIcon(R.drawable.ic_message_black_24dp)
                    }
                    3 -> {
                        tab.setIcon(R.drawable.baseline_person_outline_black_24dp)
                    }
                }
            })
        tabLayoutMediator.attach()
    }


}

