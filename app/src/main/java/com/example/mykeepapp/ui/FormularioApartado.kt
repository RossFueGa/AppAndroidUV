package com.example.mykeepapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import com.example.mykeepapp.data.data.Api.ApiService
import com.example.mykeepapp.ui.models.Apartado
import com.example.mykeepapp.ui.models.Equipo
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_formulario_apartado.*
import kotlinx.android.synthetic.main.fragment_detalle_apartado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class FormularioApartado : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    val codesRange : IntRange = 123..2630

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


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_apartado)


        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)

        if(prefs.getInt("tipoUsuario", -9999) == 2){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtGrupoFormularioApartado.focusable = View.NOT_FOCUSABLE
                txtGrupoFormularioApartado.setText(prefs.getString("grupo", "abc"))
            }
        }


        loadSpinner()
        goBack()
        setTimeFrom()
        setTime()
        setDate()

        btn_aceptar_apartado.setOnClickListener {


            if(
            checkGroup(txtGrupoFormularioApartado.text.toString()) &&
            checkDates() && checkDateToApart(txtDiaApartado.text.toString())
            && checkTimeToApart(txt_time.text.toString(), txt_timeA.text.toString())){

                getDevice()

            }
        }

    }

    fun checkDateToApart(date: String) : Boolean{
        var isValid = false;
        //Actual date from calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)

        //Data from user's selected date
        var myDates = date.split("-")
        var myYear = Integer.valueOf(myDates.get(0))
        var myMonth = Integer.valueOf(myDates.get(1))
        var myDay = Integer.valueOf(myDates.get(2))



        if(myYear == year && myMonth == month && (myDay >= day && myDay <= day+3 ) ){
            isValid = true

        }else{
            txtDiaApartado.setError("Fecha no válida!")
        }




        return  isValid;
    }

    fun checkTimeToApart(horaInicio: String, horaFinal:String):Boolean{
        var isValid = false

        var myStartTime = horaInicio.split(":")
        var myFinalTime = horaFinal.split(":")

        var myHourStart  = myStartTime.get(0)
        var myMinutesStart = myStartTime.get(1)
        var startTime = myHourStart + myMinutesStart

        var myHourFinal = myFinalTime.get(0)
        var myMinutesFinal = myFinalTime.get(1)
        var finalTime = myHourFinal + myMinutesFinal


        if(Integer.parseInt(startTime) < Integer.parseInt(finalTime) && Integer.parseInt(startTime) >= 700 && Integer.parseInt(startTime) <= 2099 && Integer.parseInt(finalTime) <= 2100 && Integer.parseInt(finalTime) >=701){
            isValid = true;
            Toast.makeText(this@FormularioApartado, "Hora válida", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(this@FormularioApartado, "Error de hora!", Toast.LENGTH_SHORT).show()
        }
        return isValid;
    }

    fun getDeviceToApart():Int{
        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)
        return prefs.getInt("Device", 666)
    }



    fun getDevice(){
        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)

        val editor  = prefs.edit()

        editor.putString("horaApartado", txt_time.text.toString() + "-" + txt_timeA.text.toString())

        retrofitobj.getEquipos().enqueue(
            object : Callback<List<Equipo>> {
                override fun onFailure(call: Call<List<Equipo>>, t: Throwable) {
                    Log.d("ERROR", "NO DATA")
                }

                override fun onResponse(
                    call: Call<List<Equipo>>,
                    response: Response<List<Equipo>>
                ) {
                    if(response.isSuccessful){
                        for(indice in response.body()?.indices!!){
                            if(response.body()!!.get(indice).estado.equals("DISPONIBLE") && response.body()!!.get(indice).idTipoEquipo == getDeviceToApart()){
                                doApart(response.body()!!.get(indice).idEquipo)
                                updateStatusDevice(response.body()!!.get(indice).idEquipo, response.body()!!.get(indice).idTipoEquipo, response.body()!!.get(indice).serial)
                                editor.putString("serialEquipo", response.body()!!.get(indice).serial)
                                editor.putInt("idEquipo", response.body()!!.get(indice).idEquipo)
                                editor.putInt("idTipoEquipo", response.body()!!.get(indice).idTipoEquipo)
                                editor.apply()
                                break;
                            }
                        }
                    }

                }
            }
        )
    }

    fun updateStatusDevice(idEquipo:Int, idTipoEquipo : Int, serial : String){
        var device = Equipo();
        device.idEquipo = idEquipo
        device.idTipoEquipo = idTipoEquipo
        device.serial = serial
        device.estado = "PENDIENTE"

        retrofitobj.updateEquipo(device).enqueue(
            object  : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){

                    }
                }

            }
        )
    }

    fun doApart(idEquipo: Int){
        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)
        val editor  = prefs.edit()



        var myApartado = Apartado()
        myApartado.matricula = prefs.getString("matricula", "noValue").toString()
        myApartado.grupo = txtGrupoFormularioApartado.text.toString()
        myApartado.fecha = txtDiaApartado.text.toString()
        myApartado.horaInicio = txt_time.text.toString()
        myApartado.horaFinal = txt_timeA.text.toString()
        myApartado.idEquipo = idEquipo
        myApartado.idLugar = getPlace(spinnerAulaApartado.selectedItem.toString(), spinnerEdificioApartado.selectedItem.toString())
        myApartado.codigoConfirmacion = getRandomCode()
        myApartado.codigoDevolucion = getRandomCode()
        myApartado.estado = "PENDIENTE"


        editor.putString("aulaApartado", spinnerAulaApartado.selectedItem.toString())
        editor.putString("edificioApartado", spinnerEdificioApartado.selectedItem.toString())
        editor.putString("fechaApartado", myApartado.fecha)
        editor.putString("horaInicio", myApartado.horaInicio)
        editor.putString("horaFinal", myApartado.horaFinal)
        editor.putInt("idLugar", myApartado.idLugar);



        retrofitobj.insertApartado(myApartado).enqueue(
            object : Callback<Long> {
                override fun onFailure(call: Call<Long>, t: Throwable) {
                    Toast.makeText(this@FormularioApartado, "ERROR", Toast.LENGTH_LONG).show()
                    Log.d("ERROR", t.message)

                }

                override fun onResponse(call: Call<Long>, response: Response<Long>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@FormularioApartado, "Apartado con éxito", Toast.LENGTH_LONG).show()
                        editor.putInt("codigoConfirmacion", myApartado.codigoConfirmacion)
                        editor.putInt("codigoDevolucion", myApartado.codigoDevolucion)
                        editor.putLong("idDoneApartado" , response.body()!!)
                        Log.d("idApartado", response.body()!!.toString())
                        editor.apply()
                    }
                }
            }
        )

    }


    fun getRandomCode() : Int{
        val randomNumber = Random()
        return randomNumber.nextInt((codesRange.last - codesRange.first) + codesRange.first)
    }

    fun getPlace(aula : String , edificio : String) : Int{
        val placesList = ArrayList<Pair<String, String>>()
        placesList.add(0,Pair("1","1"))
        placesList.add(1,Pair("2","1"))
        placesList.add(2,Pair("3","1"))
        placesList.add(3,Pair("4","1"))
        placesList.add(4,Pair("5","1"))
        placesList.add(5,Pair("6","1"))
        placesList.add(6,Pair("7","1"))
        placesList.add(7,Pair("8","1"))
        placesList.add(8,Pair("9","1"))
        placesList.add(9,Pair("10","1"))
        placesList.add(10,Pair("11","1"))
        placesList.add(11,Pair("12","1"))
        placesList.add(12,Pair("13","1"))
        placesList.add(13,Pair("14","1"))
        placesList.add(14,Pair("15","1"))
        placesList.add(15,Pair("16","1"))
        placesList.add(16,Pair("1","2"))
        placesList.add(17,Pair("2","2"))
        placesList.add(18,Pair("3","2"))
        placesList.add(19,Pair("4","2"))
        placesList.add(20,Pair("5","2"))
        placesList.add(21,Pair("6","2"))
        placesList.add(22,Pair("7","2"))
        placesList.add(23,Pair("8","2"))
        placesList.add(24,Pair("9","2"))
        placesList.add(25,Pair("10","2"))
        placesList.add(26,Pair("11","2"))
        placesList.add(27,Pair("12","2"))
        placesList.add(28,Pair("13","2"))
        placesList.add(29,Pair("14","2"))
        placesList.add(30,Pair("15","2"))
        placesList.add(31,Pair("16","2"))

        return placesList.indexOf(Pair(aula, edificio))+1

    }

    fun checkGroup(grupo : String): Boolean{
        var isValid = false
        val arregloGrupos = arrayOf("101", "102", "103", "201","202", "203",
                                                "301", "302", "303", "401","402", "403",
                                                "501", "502", "503", "601","602", "603",
                                                "701", "702", "703", "801", "802", "803");
        if (grupo.length == 3){
            for (i  in arregloGrupos.indices){
                if( txtGrupoFormularioApartado.text.toString() == arregloGrupos.get(i) ){
                    isValid = true

                }
            }
            if(isValid == false){
                txtGrupoFormularioApartado.setError("Grupo no válido")
            }
        }else{
            txtGrupoFormularioApartado.setError("Grupo no válido")

        }

    return isValid
    }

    fun checkDates() : Boolean{
        var isValid = false
        if(txt_time.text.toString() != "" && txt_timeA.text.toString() != "" && txtDiaApartado.text.toString() != ""){
           isValid = true


        }else{
            Toast.makeText(this,"Debe llenar todos los campos!", Toast.LENGTH_SHORT).show()
        }


        return isValid;
    }

    fun goBack(){
        goBackFormApartado.setOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 0){
                supportFragmentManager.popBackStack()
            }else{
                super.onBackPressed()
            }
        }
    }

    fun loadSpinner(){
        val spinnerAula: Spinner = findViewById(R.id.spinnerAulaApartado)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.aulas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerAula.adapter = adapter
        }

        val spinnerEdificio: Spinner = findViewById(R.id.spinnerEdificioApartado)
        ArrayAdapter.createFromResource(
            this,
            R.array.edificios_array,
            android.R.layout.simple_spinner_item
        ).also{adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerEdificio.adapter = adapter
        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val spinnerAula: Spinner = findViewById(R.id.spinnerAulaApartado)
        spinnerAula.onItemSelectedListener = this

        val spinnerEdificio: Spinner = findViewById(R.id.spinnerEdificioApartado)
        spinnerEdificio.onItemSelectedListener = this
    }

    fun setTimeFrom(){
        pickTimeDe.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                txt_time.text = SimpleDateFormat("HH:mm:ss").format(cal.time)
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }

    fun setTime(){
        pickTimeA.setOnClickListener {
            val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                //ingresar la hora en el tex_time

                txt_timeA.text = SimpleDateFormat("HH:mm:ss").format(cal.time)

            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setDate(){

        btnDiaApartado.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val showDate = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{   view, year, month, dayOfMonth ->
                txtDiaApartado.setText("" + year + "-" + (month+1) + "-" + dayOfMonth   )
                }, year, month, day)
            showDate.show()


        }
    }



}
