package com.example.mykeepapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import com.example.mykeepapp.ui.models.Usuario
import kotlinx.android.synthetic.main.fragment_inicio.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentInicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)

        val vista = inflater.inflate(R.layout.fragment_inicio, container, false)



        var btnMyApartadoComputo = vista.findViewById<LinearLayout>(R.id.apartadoComputo)
        var btnMyApartadoProyector = vista.findViewById<LinearLayout>(R.id.apartadoProyectores)
        var btnMyApartadoBocina = vista.findViewById<LinearLayout>(R.id.apartadoBocinas)
        var btnApartadoAmplificador = vista.findViewById<LinearLayout>(R.id.apartadoAmplificador)
        val btnTituloApartado = vista.findViewById<TextView>(R.id.txtTituloApartado)

        btnTituloApartado.setOnClickListener {
            val myPrefs = prefs.getString("matricula", "noValue")
            Toast.makeText(activity, myPrefs, Toast.LENGTH_SHORT).show()

        }


        btnMyApartadoComputo.setOnClickListener {
            val intent = Intent(activity, FormularioApartado::class.java)
            startActivity(intent)
        }

        btnMyApartadoProyector.setOnClickListener {
            val intent = Intent(activity, TiposProyectores::class.java)
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
