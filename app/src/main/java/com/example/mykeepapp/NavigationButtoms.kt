package com.example.mykeepapp

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_navigation_buttoms.*
import kotlinx.android.synthetic.main.fragment_inicio.*

class NavigationButtoms : AppCompatActivity() {

    private val adapter by lazy { ViewAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_buttoms)
        pager.adapter = adapter
        val tabLayoutMediator = TabLayoutMediator(tab_layout, pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position){
                    0 -> {
                        tab.setIcon(R.drawable.baseline_home_black_24dp)
                    }
                    1 -> {
                        tab.setIcon(R.drawable.ic_attachment_black_24dp)
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

