package com.example.mykeepapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mykeepapp.R
import com.example.mykeepapp.ui.models.Usuario
import kotlinx.android.synthetic.main.activity_bienvenida.*

class Bienvenida : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        btn_inicio_sesion.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        txt_registro.setOnClickListener {
            val intent = Intent(this, AlumnoAcademico::class.java)
            startActivity(intent)
        }


    }

}
