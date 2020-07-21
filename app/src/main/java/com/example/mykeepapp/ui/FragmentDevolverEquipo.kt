package com.example.mykeepapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import com.example.mykeepapp.data.data.Api.ApiService
import com.example.mykeepapp.ui.models.Apartado
import com.example.mykeepapp.ui.models.Equipo
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_formulario_apartado.*
import kotlinx.android.synthetic.main.fragment_sin_mensajes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class FragmentDevolverEquipo : Fragment() {

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
        val vista = inflater.inflate(R.layout.fragment_sin_mensajes, container, false)
        val btnGiveBackDevice = vista.findViewById<Button>(R.id.btnTerminarPrestamo)
        val txtCodeBack = vista.findViewById<TextInputEditText>(R.id.txtCodigoDevolucionFormularioApartado)

        btnGiveBackDevice.setOnClickListener {
            val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)

            if(txtCodeBack.text.toString() == prefs.getInt("codigoDevolucion", -9999).toString()){

                endApart(prefs.getLong("idDoneApartado", -9999))

                updateStatusDevice(prefs.getInt("idEquipo", -9999),
                    prefs.getInt("idTipoEquipo",-9999),
                    prefs.getString("serialEquipo", "noValueSerialEquipo").toString(),
                    1)




            }else{
                txtCodeBack.setError("Código no válido!")
            }


        }



        // Inflate the layout for this fragment
        return vista
    }


    fun goOut(){
        val intent = Intent(activity, Login::class.java)
        Toast.makeText(activity, "Cuídate mucho!", Toast.LENGTH_SHORT).show()
        startActivity(intent)
        activity?.finish()
    }

    fun updateStatusDevice(idEquipo:Int, idTipoEquipo : Int, serial : String, status: Int){
        var device = Equipo();
        var upStatus = ""
        when (status){
            1 -> upStatus = "DISPONIBLE"
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
                        Toast.makeText(activity, "Equipo devuelto", Toast.LENGTH_SHORT).show()

                    }
                }

            }
        )
    }

    fun endApart(idApartado: Long){
        val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)
        var myApartado = Apartado()
        myApartado.idApartado = idApartado
        myApartado.matricula = prefs.getString("matricula", "noValueMatricula").toString()
        myApartado.grupo = prefs.getString("grupo", "noValueGrupo").toString()
        myApartado.fecha = prefs.getString("fechaApartado", "noValueFecha").toString()
        myApartado.horaInicio = prefs.getString("horaInicio", "noValueHoraInicio").toString()
        myApartado.horaFinal = prefs.getString("horaFinal", "noValueHoraFinal").toString()
        myApartado.idEquipo = prefs.getInt("idEquipo", -9999)
        myApartado.idLugar = prefs.getInt("idLugar", -9999)
        myApartado.codigoConfirmacion = 0
        myApartado.codigoDevolucion = 0
        myApartado.estado = "COMPLETADO"

        retrofitobj.updateApartado(myApartado).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show()
                    Log.d("ERROR", t.message)

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        Toast.makeText(activity, "Regresado con éxito", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )


    }
}
