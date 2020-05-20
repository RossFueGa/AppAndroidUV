package com.example.mykeepapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.mykeepapp.R

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
            var intentRes = this.arguments?.getBundle("usuario")
            var user = intentRes?.getBundle("usuario")

            val intent = Intent(activity, FormularioApartado::class.java)
            intent.putExtra("equipo", user);
            startActivity(intent)
        }

        btnMyApartadoProyector.setOnClickListener {
            val intent = Intent(activity,
                TiposProyectores::class.java)
            startActivity(intent)
        }

        btnMyApartadoBocina.setOnClickListener {
            val intent = Intent(activity, FormularioApartado::class.java)
            startActivity(intent)
        }

        btnApartadoAmplificador.setOnClickListener {
            val intent = Intent(activity, FormularioApartado::class.java)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return vista

        
    }

}
