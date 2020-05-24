package com.example.mykeepapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mykeepapp.ui.models.Usuario
import com.example.mykeepapp.R
import com.example.mykeepapp.data.data.Api.ApiService
import com.example.mykeepapp.viewmodel.MyViewModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {

    private lateinit var  viewModel : MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        aceptar_login.setOnClickListener {
            if (checkMatricula(txtMatriculaAlumnoLogin.text.toString())
                && checkPassword(passAlumnoLogin.text.toString()) ) {

                setUpModel(
                    txtMatriculaAlumnoLogin.text.toString().toUpperCase(),
                    passAlumnoLogin.text.toString())

                val intent = Intent(this, NavigationButtoms::class.java)
                startActivity(intent)
                finish()

            } else {

            }

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

    fun setUpModel(matricula : String, pass : String){
        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)

        val usuarioObserver = Observer<List<Usuario>>{
            for (user in it){
                if (user.matricula.equals(matricula) && user.contrasena.equals(pass)){
                    Toast.makeText(this, "Bienvedido ${user.nombre}", Toast.LENGTH_LONG).show()

                    break;
                }else{
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.getDataUserLive().observe(this, usuarioObserver)

    }



}
