package com.example.mykeepapp.ui

import android.content.Intent
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
        var btnAcuerdoConfidencialidad = vista.findViewById<LinearLayout>(R.id.btnAcuerdoConfidencialidad)
        var myBtnEditarPerfil = vista.findViewById<LinearLayout>(R.id.btnEditarPerfil)


        val nombreUsuario = prefs.getString("nombre", "noValue")
        val apellidosUsuario = prefs.getString("apPaterno", "noValue") + " "+  prefs.getString("apMaterno", "noValue")

        val tipoUsuario = prefs.getInt("tipoUsuario", 666)



        myTxtNombreUsuario.text = nombreUsuario + " " + apellidosUsuario
        myTxtTipoUsuario.text = getStatus(tipoUsuario)

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(activity, Login::class.java)
            Toast.makeText(activity, "Cuídate mucho!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            activity?.finish()
        }

        btnAcuerdoConfidencialidad.setOnClickListener {
            val intent = Intent(activity, AcuerdoConfidencialidad::class.java)
            startActivity(intent)
        }

        myBtnEditarPerfil.setOnClickListener {
            val intent = Intent(activity, EditarPerfil::class.java)
            startActivity(intent)
        }



        // Inflate the layout for this fragment
        return vista;
    }



    fun getStatus(idSatus : Int) : String{
        var myStatus = String()
        when(idSatus){
            1 -> myStatus = "Académico"
            2 -> myStatus = "Estudiante"
        }
        return myStatus;
    }



}
