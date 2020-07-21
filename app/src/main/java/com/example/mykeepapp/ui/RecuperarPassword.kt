package com.example.mykeepapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.mykeepapp.R
import com.example.mykeepapp.data.data.Api.ApiService
import com.example.mykeepapp.ui.models.Usuario
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_recuperar_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RecuperarPassword : AppCompatActivity(), AdapterView.OnItemSelectedListener {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_password)

        loadSpinner()

        btnRecuperarPass.setOnClickListener {
            if(checkName(txtNombreAlumno.text.toString())
                && checkLastName1(txtApellidoPaternoAlumno.text.toString())
                && checkLastName2(txtApellidoMaternoAlumno.text.toString())
                && checkMatricula(txtMatriculaAlumno.text.toString())
                && checkPassword(passAlumno.text.toString())
                && checkGrupo(txtGrupoAlumno.text.toString())){
                updateUser()
            }
        }


    }


    fun updateUser(){
        val usuarioId = 2
        var user = Usuario()
        user.matricula = txtMatriculaAlumno.text.toString().toUpperCase()
        user.idTipoUsuario = usuarioId
        user.idCarrera = (spinnerCarrera.selectedItemPosition + 1)
        user.nombre = txtNombreAlumno.text.toString()
        user.apellidoPaterno = txtApellidoPaternoAlumno.text.toString()
        user.apellidoMaterno = txtApellidoMaternoAlumno.text.toString()
        user.contrasena = passAlumno.text.toString()
        user.grupo = txtGrupoAlumno.text.toString()

        retrofitobj.updateUsuario(user).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@RecuperarPassword, "ERROR", Toast.LENGTH_LONG).show()
                    Log.d("ERROR", t.message)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@RecuperarPassword, "Contraseña recuperada con éxito", Toast.LENGTH_LONG).show()

                    }
                }

            }
        )


    }

    fun checkGrupo(grupo:String):Boolean{
        var isValid = false
        if(grupo.length == 3){
            isValid = true;
        }
        return isValid;
    }

    fun  checkMatricula (matricula : String) : Boolean {
        var flag : Boolean = true;
        var numeros : Int = 0

        if(matricula.isNotEmpty()) {
            if(matricula.length == 9){
                if (matricula.get(0) == 'S' || matricula.get(0) == 's') {
                    var myChar = matricula.toCharArray()
                    for (pos in myChar.indices){
                        if (myChar.get(pos)== ' '){
                            flag = false;
                        }else if (myChar.get(pos) == '0' || myChar.get(pos) == '1' || myChar.get(pos) == '2' || myChar.get(pos) == '3'
                            || myChar.get(pos) == '4' || myChar.get(pos) == '5' || myChar.get(pos) == '6' || myChar.get(pos) == '7'
                            || myChar.get(pos) == '8' || myChar.get(pos) == '9'){
                            numeros ++;
                        }
                    }

                    if(numeros == 8 && (myChar.get(0) == 'S' || myChar.get(0) == 's')){

                    }else{
                        flag = false;
                    }

                } else {
                    flag = false;
                }
            }else{
                flag = false;
            }
        } else{
            flag = false;
        }

        if(flag == false){
            txtMatriculaAlumno.setError("Matrícula comienza por S y contiene 8 dígitos sin espacios")
        }

        return flag;
    }

    fun checkPassword(password : String) : Boolean{
        var isValid : Boolean = true
        if(password.isNotEmpty()) {

            if (password.length in 8..11) {
                var myChar = password.toCharArray()
                for (pos in myChar.indices) {
                    println(myChar.get(pos))

                    if (myChar.get(pos) == ' ') {
                        isValid = false;
                    }
                }
            } else {
                isValid = false;
            }
        }else{
            isValid = false;
        }
        if(isValid==false){
            passAlumno.setError("Contraseña debe incluir 8 caracteres sin espacios")
        }

        return isValid;
    }

    fun checkName(nombre : String) : Boolean{
        var isValid : Boolean = true;
        var testName = nombre.trimEnd()

        if(testName.isNotEmpty()){
            if(testName.length >= 3){
                var myChar = testName.toCharArray()
                for(pos in myChar.indices){
                    if(myChar.get(pos) == ' ' && myChar.get(pos +1) == ' '){
                        isValid = false;
                        break
                    }else if(myChar.get(pos) == '.' || myChar.get(pos) == ':' || myChar.get(pos) == '@'
                        || myChar.get(pos) == '>' || myChar.get(pos) == '<' || myChar.get(pos) == '-'
                        || myChar.get(pos) == '_' || myChar.get(pos) == '/' || myChar.get(pos) == '*'
                        || myChar.get(pos) == '+' || myChar.get(pos) == '?' || myChar.get(pos) == '¿'
                        || myChar.get(pos) == '!' || myChar.get(pos) == '¡' || myChar.get(pos) == '1'
                        || myChar.get(pos) == '2' || myChar.get(pos) == '3' || myChar.get(pos) == '4'
                        || myChar.get(pos) == '5' || myChar.get(pos) == '6' || myChar.get(pos) == '7'
                        || myChar.get(pos) == '8' || myChar.get(pos) == '9' || myChar.get(pos) == '0'
                        || myChar.get(pos) == '$' || myChar.get(pos) == '#' || myChar.get(pos) == '('
                        || myChar.get(pos) == ')' || myChar.get(pos) == '=' || myChar.get(pos) == '|'
                        || myChar.get(pos) == '°' || myChar.get(pos) == '.'){
                        isValid = false;
                        break
                    }
                }
            }else {
                isValid = false;
            }
        }else{
            isValid = false
        }

        if(isValid == false){
            txtNombreAlumno.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtNombreAlumno.setText(testName)
        return isValid;

    }

    fun checkLastName1(nombre : String) : Boolean{
        var isValid : Boolean = true;
        var testName = nombre.trimEnd()

        if(testName.isNotEmpty()){
            if(testName.length >= 3){
                var myChar = testName.toCharArray()
                for(pos in myChar.indices){
                    if(myChar.get(pos) == ' ' && myChar.get(pos +1) == ' '){
                        isValid = false;
                        break
                    }else if(myChar.get(pos) == '.' || myChar.get(pos) == ':' || myChar.get(pos) == '@'
                        || myChar.get(pos) == '>' || myChar.get(pos) == '<' || myChar.get(pos) == '-'
                        || myChar.get(pos) == '_' || myChar.get(pos) == '/' || myChar.get(pos) == '*'
                        || myChar.get(pos) == '+' || myChar.get(pos) == '?' || myChar.get(pos) == '¿'
                        || myChar.get(pos) == '!' || myChar.get(pos) == '¡' || myChar.get(pos) == '1'
                        || myChar.get(pos) == '2' || myChar.get(pos) == '3' || myChar.get(pos) == '4'
                        || myChar.get(pos) == '5' || myChar.get(pos) == '6' || myChar.get(pos) == '7'
                        || myChar.get(pos) == '8' || myChar.get(pos) == '9' || myChar.get(pos) == '0'
                        || myChar.get(pos) == '$' || myChar.get(pos) == '#' || myChar.get(pos) == '('
                        || myChar.get(pos) == ')' || myChar.get(pos) == '=' || myChar.get(pos) == '|'
                        || myChar.get(pos) == '°' || myChar.get(pos) == '.'){
                        isValid = false;
                        break
                    }
                }
            }else {
                isValid = false;
            }
        }else{
            isValid = false
        }

        if(isValid == false){
            txtApellidoPaternoAlumno.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtApellidoPaternoAlumno.setText(testName)
        return isValid;

    }

    fun checkLastName2(nombre : String) : Boolean{
        var isValid : Boolean = true;
        var testName = nombre.trimEnd()

        if(testName.isNotEmpty()){
            if(testName.length >= 3){
                var myChar = testName.toCharArray()
                for(pos in myChar.indices){
                    if(myChar.get(pos) == ' ' && myChar.get(pos +1) == ' '){
                        isValid = false;
                        break
                    }else if(myChar.get(pos) == '.' || myChar.get(pos) == ':' || myChar.get(pos) == '@'
                        || myChar.get(pos) == '>' || myChar.get(pos) == '<' || myChar.get(pos) == '-'
                        || myChar.get(pos) == '_' || myChar.get(pos) == '/' || myChar.get(pos) == '*'
                        || myChar.get(pos) == '+' || myChar.get(pos) == '?' || myChar.get(pos) == '¿'
                        || myChar.get(pos) == '!' || myChar.get(pos) == '¡' || myChar.get(pos) == '1'
                        || myChar.get(pos) == '2' || myChar.get(pos) == '3' || myChar.get(pos) == '4'
                        || myChar.get(pos) == '5' || myChar.get(pos) == '6' || myChar.get(pos) == '7'
                        || myChar.get(pos) == '8' || myChar.get(pos) == '9' || myChar.get(pos) == '0'
                        || myChar.get(pos) == '$' || myChar.get(pos) == '#' || myChar.get(pos) == '('
                        || myChar.get(pos) == ')' || myChar.get(pos) == '=' || myChar.get(pos) == '|'
                        || myChar.get(pos) == '°' || myChar.get(pos) == '.'){
                        isValid = false;
                        break
                    }
                }
            }else {
                isValid = false;
            }
        }else{
            isValid = false
        }

        if(isValid == false){
            txtApellidoMaternoAlumno.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtApellidoMaternoAlumno.setText(testName)
        return isValid;

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun loadSpinner(){
        val spinnerCarrera : Spinner = findViewById(R.id.spinnerCarrera)
        ArrayAdapter.createFromResource(
            this,
            R.array.carreras_array,
            android.R.layout.simple_spinner_item
        ).also{adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCarrera.adapter = adapter
        }


        val spinnerAula: Spinner = findViewById(R.id.spinnerAulaAlumno)
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

        val spinnerEdificio: Spinner = findViewById(R.id.spinnerEdificioAlumno)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val spinnerAula: Spinner = findViewById(R.id.spinnerAulaAlumno)
        spinnerAula.onItemSelectedListener = this


        val spinnerCarrera: Spinner = findViewById(R.id.spinnerCarrera)
        spinnerCarrera.onItemSelectedListener = this

        val spinnerEdificio: Spinner = findViewById(R.id.spinnerEdificioAlumno)
        spinnerEdificio.onItemSelectedListener = this
    }
}