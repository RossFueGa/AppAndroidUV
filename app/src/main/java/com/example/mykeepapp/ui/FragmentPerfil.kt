package com.example.mykeepapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentPerfil : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_perfil, container, false)
        val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)

        var myTxtNombreUsuario = vista.findViewById<TextView>(R.id.txtNombrePerfil)
        var myTxtTipoUsuario = vista.findViewById<TextView>(R.id.txtTipoUsuarioPerfil)

        var btnCerrarSesion = vista.findViewById<LinearLayout>(R.id.cerrarSesionPerfil)


        val nombreUsuario = prefs.getString("nombre", "noValue")
        val apellidosUsuario = prefs.getString("apPaterno", "noValue") + " "+  prefs.getString("apMaterno", "noValue")

        val tipoUsuario = prefs.getString("tipoUsuario", "noValue")



        myTxtNombreUsuario.text = nombreUsuario + " " + apellidosUsuario
        myTxtTipoUsuario.text = getStatus(tipoUsuario.toString())

        btnCerrarSesion.setOnClickListener {
            Toast.makeText(activity, "Cuídate mucho!", Toast.LENGTH_SHORT).show()
            activity?.finish()
        }


        // Inflate the layout for this fragment
        return vista;
    }



    fun getStatus(idSatus : String) : String{
        var myStatus = String()
        when(idSatus){
            "1" -> myStatus = "Académico"
            "2" -> myStatus = "Estudiante"
        }
        return myStatus;
    }



}
