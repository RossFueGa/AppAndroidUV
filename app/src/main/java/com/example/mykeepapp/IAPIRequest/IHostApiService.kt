package com.example.mykeepapp.IAPIRequest

import com.example.mykeepapp.HTTPBodyPojos.Usuario
import com.example.mykeepapp.HTTPPojosResponse.ResponseUsuario
import retrofit2.Call
import retrofit2.http.*

interface IHostApiService {

    /*----------------------------Usuarios-----------------------*/

    @GET("Usuarios")
    fun getUsuariosRecords(): Call<ArrayList<ResponseUsuario> >

    @POST("Usuarios")
    fun insertUsuario(@Body body: Usuario): Call<String>

    @GET("Usuarios/{id}")
    fun getUsuarioById(@Path("id") id : String): Call<ResponseUsuario>

    @PUT("Usuarios")
    fun updateUsuario(@Body body: Usuario) : Call<String>

    @DELETE("Usuarios/{id}")
    fun deleteUsuario(@Path("id") id : String ): Call<String>

}