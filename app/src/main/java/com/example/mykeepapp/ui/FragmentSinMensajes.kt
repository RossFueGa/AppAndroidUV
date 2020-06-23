package com.example.mykeepapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import kotlinx.android.synthetic.main.fragment_sin_mensajes.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentSinMensajes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_sin_mensajes, container, false)
        val btnUpdateStatus = vista.findViewById<Button>(R.id.btnTerminarPrestamo)

        btnUpdateStatus.setOnClickListener {
            val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)
            txtMensajeDevolverEquipo.text = prefs.getString("serialEquipo", "Cuando")

        }



        // Inflate the layout for this fragment
        return vista
    }

}
