package com.example.mykeepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tipos_proyectores.*

class TiposProyectores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipos_proyectores)

        btnTipoHDMI.setOnClickListener {
            val intent = Intent(this,FormularioApartado::class.java)
            startActivity(intent)
        }

        btnTipoVGA.setOnClickListener {
            val intent = Intent(this,FormularioApartado::class.java)
            startActivity(intent)
        }

    }
}
