package com.example.mykeepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_alumno_academico.*
import kotlinx.android.synthetic.main.activity_alumno_academico.btn_gato_estudiante
import kotlinx.android.synthetic.main.activity_alumno_academico.btn_gato_profesor
import kotlinx.android.synthetic.main.activity_tipos_proyectores.*

class AlumnoAcademico : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumno_academico)

        btn_gato_estudiante.setOnClickListener {
            val intent = Intent(this,RegistroAlumno::class.java)
            startActivity(intent)
        }

        btn_gato_profesor.setOnClickListener {
            val intent = Intent(this, RegistroAcademico::class.java)
            startActivity(intent)
        }

        txt_regresar.setOnClickListener {
            finish()
        }

    }
}
