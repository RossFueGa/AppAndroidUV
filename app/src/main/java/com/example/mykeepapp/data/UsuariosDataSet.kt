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

    val myUsuarios = MutableLiveData<List<Usuario>>()


    var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder().addCallAdapterFactory(
        RxJava2CallAdapterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(gson))
        .baseUrl("http://35.222.188.8:8080/ApiRestMiUV-V0.0.1/webresources/")
        .build();

    var retrofitobj = retrofit.create(ApiService::class.java)

    fun getUsuarios() : LiveData<List<Usuario>> {
        retrofitobj.getUsuariosRecords().enqueue(
            object : Callback<List<Usuario>> {
                override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                    Log.d("response data:", "getting data....")
                    Log.e(ContentValues.TAG,t.toString());
                }

                override fun onResponse(
                    call: Call<List<Usuario>>,
                    response: Response<List<Usuario>>
                ) {
                    if (response.isSuccessful){
                        Log.d("response data:", "getting data....")
                        myUsuarios.value = response.body()!!
                    }
                }

            }
        )
        return myUsuarios
    }



}