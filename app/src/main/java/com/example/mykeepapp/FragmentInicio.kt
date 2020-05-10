package com.example.mykeepapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_inicio.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentInicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_inicio, container, false)

        var btnMyApartadoComputo = vista.findViewById<LinearLayout>(R.id.apartadoComputo)
        var btnMyApartadoProyector = vista.findViewById<LinearLayout>(R.id.apartadoProyectores)
        var btnMyApartadoBocina = vista.findViewById<LinearLayout>(R.id.apartadoBocinas)
        var btnApartadoAmplificador = vista.findViewById<LinearLayout>(R.id.apartadoAmplificador)

        btnMyApartadoComputo.setOnClickListener {
            Toast.makeText(activity, "HOLA", Toast.LENGTH_LONG).show()
        }

        btnMyApartadoProyector.setOnClickListener {
            val intent = Intent(activity,TiposProyectores::class.java)
            startActivity(intent)
        }

        btnMyApartadoBocina.setOnClickListener {
            Toast.makeText(activity, "HOLA bocina", Toast.LENGTH_LONG).show()
        }

        btnApartadoAmplificador.setOnClickListener {
            Toast.makeText(activity, "HOLA amplificador", Toast.LENGTH_LONG).show()
        }

        // Inflate the layout for this fragment
        return vista

        
    }

}
