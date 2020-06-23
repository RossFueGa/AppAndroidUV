package com.example.mykeepapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentDetalleApartado : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val vista = inflater.inflate(R.layout.fragment_detalle_apartado, container, false)

        var txtNombrePrestario = vista.findViewById<TextView>(R.id.txtNombrePrestario)
        var txtMatriculaPrestario = vista.findViewById<TextView>(R.id.txtMatriculaPrestario)
        var txtHoraFinPrestario = vista.findViewById<TextView>(R.id.txtHoraPrestario)
        var txtAulaPrestario = vista.findViewById<TextView>(R.id.txtAulaPrestario)
        var txtEdificioPrestario = vista.findViewById<TextView>(R.id.txtEdificioPrestario)
        var txtSerialPrestario = vista.findViewById<TextView>(R.id.txtSerial)
        var txtFechaPrestario = vista.findViewById<TextView>(R.id.txtFechaPrestario)
        val btnUpdateStatus = vista.findViewById<Button>(R.id.btnActualizarStatus)


        btnUpdateStatus.setOnClickListener {
            val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)

            txtNombrePrestario.text = prefs.getString("nombre","noValue")
            txtMatriculaPrestario.text = prefs.getString("matricula", "noValue")
            txtHoraFinPrestario.text = prefs.getString("horaApartado","noValue")
            txtAulaPrestario.text = prefs.getString("aulaApartado", "noValues")
            txtEdificioPrestario.text = prefs.getString("edificioApartado", "noValues")
            txtSerialPrestario.text = prefs.getString("serialEquipo", "noValue")
            txtFechaPrestario.text = prefs.getString("fechaApartado", "noValue")
        }



        // Inflate the layout for this fragment
        return vista
    }

}
