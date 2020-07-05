package com.example.mykeepapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import com.example.mykeepapp.data.data.Api.ApiService
import com.example.mykeepapp.ui.models.Equipo
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_detalle_apartado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class FragmentDetalleApartado : Fragment() {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder().addCallAdapterFactory(
        RxJava2CallAdapterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(gson))
        .baseUrl("http://35.222.188.8:8080/ApiRestMyKepp-1.0/webresources/")
        .build();

    var retrofitobj = retrofit.create(ApiService::class.java)

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)



        val vista = inflater.inflate(R.layout.fragment_detalle_apartado, container, false)
        val btnMyconfirmarApartado = vista.findViewById<Button>(R.id.btnConfirmarApartado)
        val btnMyCancelarApartado = vista.findViewById<Button>(R.id.btnCancelarApartado)
        val myIdPrestamo = vista.findViewById<TextView>(R.id.idPrestamo)

        myIdPrestamo.setText(prefs.getLong("idDoneApartado", -9999).toString())

        btnMyconfirmarApartado.setOnClickListener {
            if(txtCodigoConfirmacionFormularioApartado.text.toString() == prefs.getInt("codigoConfirmacion" , 666).toString()){

                updateStatusDevice(prefs.getInt("idEquipo", 666), prefs.getInt("idTipoEquipo",666),
                    prefs.getString("serialEquipo", "noValue").toString(), 1)

            }else{
                txtCodigoConfirmacionFormularioApartado.setError("Código no válido!")
            }
        }

        btnMyCancelarApartado.setOnClickListener {
            //btnConfirmarApartado.isEnabled = false;

            updateStatusDevice(prefs.getInt("idEquipo", 666), prefs.getInt("idTipoEquipo",666),
                prefs.getString("serialEquipo", "noValue").toString(), 2)

        }

        // Inflate the layout for this fragment
        return vista
    }

    fun updateStatusDevice(idEquipo:Int, idTipoEquipo : Int, serial : String, status: Int){
        var device = Equipo();
        var upStatus = ""
        when (status){
            1 -> upStatus = "PRESTADO"
            2 -> upStatus = "DISPONIBLE"
        }

        device.idEquipo = idEquipo
        device.idTipoEquipo = idTipoEquipo
        device.serial = serial
        device.estado = upStatus

        retrofitobj.updateEquipo(device).enqueue(
            object  : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        Toast.makeText(activity, "Completado", Toast.LENGTH_SHORT).show()
                        setData()
                    }
                }

            }
        )
    }

    fun setData(){
        val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)
        txtNombrePrestario.text = prefs.getString("nombre","noValue")
        txtMatriculaPrestario.text = prefs.getString("matricula", "noValue")
        txtHoraPrestario.text = prefs.getString("horaApartado","noValue")
        txtAulaPrestario.text = prefs.getString("aulaApartado", "noValues")
        txtEdificioPrestario.text = prefs.getString("edificioApartado", "noValues")
        txtSerial.text = prefs.getString("serialEquipo", "noValue")
        txtFechaPrestario.text = prefs.getString("fechaApartado", "noValue")
    }



}
