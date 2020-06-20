package com.example.mykeepapp.data.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mykeepapp.data.data.Api.ApiService
import com.example.mykeepapp.ui.models.Usuario
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UsuariosDataSet {

    val actualUser = MutableLiveData<Usuario>()


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


    fun getUserById(matricula : String) : LiveData<Usuario>{
        retrofitobj.getUsuarioById(matricula).enqueue(
            object : Callback<Usuario>{
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if(response.isSuccessful){
                        Log.d("response data:", "getting data....")
                        Log.d("response data:", response.body()?.matricula)
                        Log.d("response data:", response.body()?.contrasena)
                        actualUser.value = response.body()
                    }

                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Log.d("ERROR data:", "....")
                    Log.e(ContentValues.TAG,t.toString());
                }

            }
        )
        return actualUser;
    }




 }