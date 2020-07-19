package com.example.mykeepapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import kotlinx.android.synthetic.main.activity_tipos_proyectores.*

class TiposProyectores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipos_proyectores)

        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)
        val editor  = prefs.edit()

        btnTipoHDMI.setOnClickListener {
            editor.putInt("Device", 1 )
            editor.apply()
            val intent = Intent(this,
                FormularioApartado::class.java)
            startActivity(intent)
        }

        btnTipoVGA.setOnClickListener {
            editor.putInt("Device", 2 )
            editor.apply()
            val intent = Intent(this,
                FormularioApartado::class.java)
            startActivity(intent)
        }

        btnRegresarTipoProyector.setOnClickListener {
            finish()
        }

    }
}
