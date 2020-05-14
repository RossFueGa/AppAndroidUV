package com.example.mykeepapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mykeepapp.HTTPBodyPojos.Usuario
import com.example.mykeepapp.IAPIRequest.IHostApiService
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_registro_academico.*
import kotlinx.android.synthetic.main.activity_registro_alumno.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RegistroAcademico : AppCompatActivity() {
    var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder().addCallAdapterFactory(
        RxJava2CallAdapterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(gson))
        .baseUrl("http://35.222.188.8:8080/ApiRestMiUV-V0.0.1/webresources/")
        .build();

    var retrofitobj = retrofit.create(IHostApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_academico)

        btnAceptarRegistroAcademico.setOnClickListener {
           if(checkName(txtNombreAcademico.text.toString())
               && checkLastName1(txtApellidoPaternoAcademico.text.toString())
               && checkLastName2((txtApellidoMaternoAcademico.text.toString()))
               && checkMatricula(txtMatriculaAcademico.text.toString())
               && checkPassword(passAcademico.text.toString())){

                insertOne()

            }else{
                Toast.makeText(this, "Error en registro", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun insertOne(){
        val usuarioId = "1"
        var user = Usuario()
        user.matricula = txtMatriculaAcademico.text.toString()
        user.idTipoUsuario = usuarioId
        user.idCarrera = "1"
        user.nombre = txtNombreAcademico.text.toString()
        user.apellidoPaterno = txtApellidoPaternoAcademico.text.toString()
        user.apellidoMaterno = txtApellidoMaternoAcademico.text.toString()
        user.contrasena = passAcademico.text.toString()

        retrofitobj.insertUsuario(user).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@RegistroAcademico, "ERROR", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful()){
                        Toast.makeText(this@RegistroAcademico, "Added", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )



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
            txtMatriculaAcademico.setError("Matrícula comienza por S y contiene 8 dígitos sin espacios")
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
            passAcademico.setError("Contraseña debe incluir 8 caracteres sin espacios")
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
                        || myChar.get(pos) == '!' || myChar.get(pos) == '¡'){
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
            txtNombreAcademico.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtNombreAcademico.setText(testName)
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
                        || myChar.get(pos) == '!' || myChar.get(pos) == '¡'){
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
            txtApellidoPaternoAcademico.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtApellidoPaternoAcademico.setText(testName)
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
                        || myChar.get(pos) == '!' || myChar.get(pos) == '¡'){
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
            txtApellidoMaternoAcademico.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtApellidoMaternoAcademico.setText(testName)
        return isValid;

    }


}
