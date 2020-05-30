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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import com.example.mykeepapp.ui.models.Usuario
import com.example.mykeepapp.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_formulario_apartado.*
import java.text.SimpleDateFormat
import java.util.*

class FormularioApartado : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_apartado)
        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)
        txtDiaApartado.setText(prefs.getString("matricula", "noValue"))



        loadSpinner()
        goBack()
        setTimeFrom()
        setTime()
        setDate()
        btn_aceptar_apartado.setOnClickListener {
            checkGroup(txtGrupoFormularioApartado.text.toString())
            checkDates()
        }
    }


    fun checkGroup(grupo : String): Boolean{
        var isValid = false
        val arregloGrupos = arrayOf("201","202", "203",
                                                "401","402","403",
                                                "601","602","603",
                                                "701","801");
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

                txt_time.text = SimpleDateFormat("HH:mm").format(cal.time)
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

                txt_timeA.text = SimpleDateFormat("HH:mm").format(cal.time)
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
                txtDiaApartado.setText("" + dayOfMonth + "/" + (month+1) + "/" + year )
            }, year, month, day)

            showDate.show()


        }
    }



}
