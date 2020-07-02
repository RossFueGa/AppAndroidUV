package com.example.mykeepapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R


/**
 * A simple [Fragment] subclass.
 */
class FragmentInicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor  = prefs.edit()

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
            editor.putInt("Device", 3 )
            editor.apply()
            val intent = Intent(activity, FormularioApartado::class.java)

            startActivity(intent)
        }

        btnMyApartadoProyector.setOnClickListener {
            editor.putInt("Device", 2 )
            editor.apply()
            val intent = Intent(activity, TiposProyectores::class.java)
            startActivity(intent)
        }

        btnMyApartadoBocina.setOnClickListener {
            editor.putInt("Device", 4 )
            editor.apply()
            val intent = Intent(activity, FormularioApartado::class.java)
            startActivity(intent)
        }

        btnApartadoAmplificador.setOnClickListener {
            editor.putInt("Device", 5 )
            editor.apply()
            val intent = Intent(activity, FormularioApartado::class.java)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return vista

        
    }


}
