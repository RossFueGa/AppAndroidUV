package com.example.mykeepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        aceptar_login.setOnClickListener {
            val intent = Intent(this,NavigationButtoms::class.java)
            startActivity(intent)
        }

    }
}
