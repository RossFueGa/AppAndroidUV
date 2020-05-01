package com.example.mykeepapp

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationButtoms : AppCompatActivity() {

    private var mNav: BottomNavigationView? = null
    private var mFrameL: FrameLayout? = null
    private var fragmentPerfil: FragmentPerfil? = null
    private var fragmentInicio: FragmentInicio? = null
    private var fragmentBuscar: FragmentBuscar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_buttoms)

        mFrameL = findViewById(R.id.main_frame)
        mNav = findViewById(R.id.main_nav)
        fragmentInicio = FragmentInicio()
        fragmentPerfil= FragmentPerfil()
        fragmentBuscar=FragmentBuscar()

    }
}
