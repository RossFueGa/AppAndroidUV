package com.example.mykeepapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.mykeepapp.R
import com.example.mykeepapp.ui.models.Usuario
import com.example.mykeepapp.viewmodel.MyViewModel
import com.google.common.hash.Hashing
import kotlinx.android.synthetic.main.activity_login.*
import java.nio.charset.StandardCharsets


class Login : AppCompatActivity() {

    private lateinit var  viewModel : MyViewModel

    var user = Usuario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txtMatriculaAlumnoLogin.setText("S17004079")
        passAlumnoLogin.setText("sopita12")
        val prefs  = PreferenceManager.getDefaultSharedPreferences(this)




       aceptar_login.setOnClickListener {
            if (checkMatricula(txtMatriculaAlumnoLogin.text.toString())
                    && checkPassword(passAlumnoLogin.text.toString()) &&
                    setUpModel(
                    txtMatriculaAlumnoLogin.text.toString().toUpperCase(),
                    encryptPassword(passAlumnoLogin.text.toString()))) {

                val editor  = prefs.edit()
                editor.putString("matricula", user.matricula)
                editor.apply()

                Toast.makeText(this, "Bienvenido ${user.nombre}", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, NavigationButtoms::class.java)
                intent.putExtra("saludo", user.matricula)
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

    fun encryptPassword(pass : String) : String{
        var sha256hex = Hashing.sha256()
            .hashString(pass, StandardCharsets.UTF_8)
            .toString()
        return sha256hex
    }

    fun setUpModel(matricula : String, pass : String) : Boolean{


        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        var isValid = false

        val oneUser = Observer<Usuario>{
            if(it.matricula.equals(matricula) && it.contrasena.equals(pass)){
                isValid = true;
                Log.d("ENCONTRADO: ", "${it.matricula}")
                user = it

            }else{

            }
        }

        viewModel.getOneUserDataLive(matricula).observe(this, oneUser)
        Log.d("VALOR: ", "${isValid}")
    return isValid;
    }

}
