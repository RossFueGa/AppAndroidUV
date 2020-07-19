package com.example.mykeepapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import com.example.mykeepapp.data.data.Api.ApiService
import com.example.mykeepapp.ui.models.Usuario
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_editar_perfil.*
import kotlinx.android.synthetic.main.activity_formulario_apartado.*
import kotlinx.android.synthetic.main.activity_registro_alumno.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class EditarPerfil : AppCompatActivity() {


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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)
        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)

        val nombreUsuario = prefs.getString("nombre", "noValue")
        val apellidosUsuario = prefs.getString("apPaterno", "noValue") + " "+  prefs.getString("apMaterno", "noValue")
        val tipoUsuario = prefs.getInt("tipoUsuario", 666)

        txtNametoEdit.text = nombreUsuario + " " + apellidosUsuario
        txtMatriculaToEdit.text = getStatus(tipoUsuario) + " " + prefs.getString("matricula", "noValueMatricula")

        if(tipoUsuario == 1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtEditGrupo.visibility = View.INVISIBLE
            }
        }

        if(tipoUsuario == 2 && isDateToUpdateGroup()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtEditGrupo.focusable = View.FOCUSABLE
            }
        }else{
            txtEditGrupo.focusable = View.NOT_FOCUSABLE
            txtEditGrupo.setText(prefs.getString("grupo", "noValueForGroup").toString())
        }







        btnUpdateUser.setOnClickListener {
            if( checkName(txtEditNombre.text.toString())
                && checkLastName1(txtEditApellidoPaterno.text.toString())
                && checkLastName2(txtApellidoMaterno.text.toString())){

                updateUser()

            }else{
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            }
        }



    }


    fun updateUser(){
        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)
        var user = Usuario()

        user.matricula = prefs.getString("matricula", "noValueMatricula").toString()
        user.idTipoUsuario = prefs.getInt("tipoUsuario", -9999)
        user.idCarrera = prefs.getInt("idCarrera", -9999)
        user.nombre = txtEditNombre.text.toString()
        user.apellidoPaterno = txtEditApellidoPaterno.text.toString()
        user.apellidoMaterno = txtApellidoMaterno.text.toString()
        user.contrasena = prefs.getString("rawPassword", "NoPasswordValue").toString()
        user.grupo = txtEditGrupo.text.toString()




        retrofitobj.updateUsuario(user).enqueue(
            object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("ERROR AL UPDATE", t.toString())
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@EditarPerfil, "Éxito, vuelve a iniciar sesión para ver los cambios", Toast.LENGTH_SHORT).show()
                        val editor  = prefs.edit()
                        editor.putString("grupo", user.grupo)
                        editor.apply()

                    }
                }

            }
        )


    }

    fun isDateToUpdateGroup() : Boolean{
        var isValid = false
        //Actual date from calendar
        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH) + 1

        when(month){
            2 -> isValid = true
            9 -> isValid = true
        }

        return isValid;

    }

    fun checkGroup(grupo : String): Boolean{
        var isValid = false
        val arregloGrupos = arrayOf("101", "102", "103", "201","202", "203",
            "301", "302", "303", "401","402", "403",
            "501", "502", "503", "601","602", "603",
            "701", "702", "703", "801", "802", "803");
        if (grupo.length == 3){
            for (i  in arregloGrupos.indices){
                if( txtEditGrupo.text.toString() == arregloGrupos.get(i) ){
                    isValid = true

                }
            }
            if(isValid == false){
                txtEditGrupo.setError("Grupo no válido")
            }
        }else{
            txtEditGrupo.setError("Grupo no válido")

        }

        return isValid
    }

    private  fun getStatus(idSatus : Int) : String{
        var myStatus = String()
        when(idSatus){
            1 -> myStatus = "Académico"
            2 -> myStatus = "Estudiante"
        }
        return myStatus;
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
            txtEditNombre.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtEditNombre.setText(testName)
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
            txtEditApellidoPaterno.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtEditApellidoPaterno.setText(testName)
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
            txtApellidoMaterno.setError("No debe contener dos espacios en blanco seguidos ni caracteres especiales")
        }
        txtApellidoMaterno.setText(testName)
        return isValid;

    }
}
