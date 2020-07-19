package com.example.mykeepapp.ui

import android.R.attr.fragment
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import com.example.mykeepapp.data.data.Api.ApiService
import com.example.mykeepapp.ui.models.Apartado
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

    var actualId : Long = 0L

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)

        val vista = inflater.inflate(R.layout.fragment_detalle_apartado, container, false)
        val btnMyconfirmarApartado = vista.findViewById<Button>(R.id.btnConfirmarApartado)
        val btnMyCancelarApartado = vista.findViewById<Button>(R.id.btnCancelarApartado)
        var myIdPrestamo = vista.findViewById<TextView>(R.id.idPrestamo)

        if(prefs.getLong("idDoneApartado", -9999) == -9999L){
            myIdPrestamo.setText("")
            Log.d("ID apartado en curso", prefs.getLong("idDoneApartado", -9999).toString())
        }else{
            myIdPrestamo.setText(prefs.getLong("idDoneApartado", -9999).toString())
            Log.d("Error de apartado ", prefs.getLong("idDoneApartado", -9999).toString())
        }



        btnMyconfirmarApartado.setOnClickListener {
            if(txtCodigoConfirmacionFormularioApartado.text.toString() == prefs.getInt("codigoConfirmacion" , 666).toString()){

                updateStatusDevice(prefs.getInt("idEquipo", -9999), prefs.getInt("idTipoEquipo",666),
                    prefs.getString("serialEquipo", "noValue").toString(), 1);

                updateStatusApart(prefs.getLong("idDoneApartado", -9999), 1);
                setData()

            }else{
                txtCodigoConfirmacionFormularioApartado.setError("Código no válido!")
            }
        }

        btnMyCancelarApartado.setOnClickListener {
            //btnConfirmarApartado.isEnabled = false;


            updateStatusDevice(prefs.getInt("idEquipo", -9999), prefs.getInt("idTipoEquipo",666),
                prefs.getString("serialEquipo", "noValue").toString(), 2);
            updateStatusApart(prefs.getLong("idDoneApartado", -9999), 2);



        }

        // Inflate the layout for this fragment
        return vista
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)
            actualId = prefs.getLong("idDoneApartado", -9999)
        }
    }

    override fun onStart() {
        super.onStart()
        userVisibleHint = true
    }

    fun updateStatusApart(idApartado: Long , status:Int){
        val prefs  = PreferenceManager.getDefaultSharedPreferences(activity)
        var myApartado = Apartado()
        var upStatus = ""
        when (status){
            1 -> upStatus = "EN CURSO"
            2 -> upStatus = "CANCELADO"
        }
        myApartado.idApartado = idApartado
        myApartado.matricula = prefs.getString("matricula", "noValueMatricula").toString()
        myApartado.grupo = prefs.getString("grupo", "noValueGrupo").toString()
        myApartado.fecha = prefs.getString("fechaApartado", "noValueFecha").toString()
        myApartado.horaInicio = prefs.getString("horaInicio", "noValueHoraInicio").toString()
        myApartado.horaFinal = prefs.getString("horaFinal", "noValueHoraFinal").toString()
        myApartado.idEquipo = prefs.getInt("idEquipo", -9999)
        myApartado.idLugar = prefs.getInt("idLugar", -9999)
        myApartado.codigoConfirmacion = prefs.getInt("codigoConfirmacion", -9999)
        myApartado.codigoDevolucion = prefs.getInt("codigoDevolucion", -9999)
        myApartado.estado = upStatus



        retrofitobj.updateApartado(myApartado).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show()
                    Log.d("ERROR", t.message)

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        Toast.makeText(activity, "Apartado en curso!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )


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
