package com.example.mykeepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mykeepapp.HTTPBodyPojos.Usuario
import com.example.mykeepapp.HTTPPojosResponse.ResponseUsuario
import com.example.mykeepapp.IAPIRequest.IHostApiService
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registro_academico.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {

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
        setContentView(R.layout.activity_login)

        aceptar_login.setOnClickListener {

            if (checkMatricula(txtMatriculaAlumnoLogin.text.toString())
                && checkPassword(passAlumnoLogin.text.toString()) ) {
                Toast.makeText(this@Login,"Validando...", Toast.LENGTH_LONG).show()

                validateLogin(
                    txtMatriculaAlumnoLogin.text.toString(),
                    passAlumnoLogin.text.toString())

            } else { }

        }

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
            txtMatriculaAlumnoLogin.setError("Matrícula comienza por S y contiene 8 dígitos sin espacios")
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
            passAlumnoLogin.setError("Contraseña debe incluir 8 caracteres sin espacios")
        }

        return isValid;
    }

    fun validateLogin(matricula : String, pass : String) {
        retrofitobj.getUsuarioById(matricula).enqueue(
            object : Callback<ResponseUsuario>{
                override fun onFailure(call: Call<ResponseUsuario>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(
                    call: Call<ResponseUsuario>,
                    response: Response<ResponseUsuario>){
                    if(response.body()?.contrasena.equals(pass)){
                        val intent = Intent(this@Login,NavigationButtoms::class.java)
                        startActivity(intent)
                        Toast.makeText(this@Login, "Bienvenido ${response.body()?.nombre}" , Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@Login, "No existes" , Toast.LENGTH_LONG).show()
                    }


                    }
                }
        )

    }


}
