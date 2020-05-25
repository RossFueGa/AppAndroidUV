package com.example.mykeepapp.data.data.Api

import com.example.mykeepapp.ui.models.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /*----------------------------Usuarios-----------------------*/

    @GET("Usuarios")
    fun getUsuariosRecords(): Call<List<Usuario>>

    @POST("Usuarios")
    fun insertUsuario(@Body body: Usuario): Call<String>

    @GET("Usuarios/{id}")
    fun getUsuarioById(@Path("id") id : String): Call<Usuario>

    @PUT("Usuarios")
    fun updateUsuario(@Body body: Usuario) : Call<String>

    @DELETE("Usuarios/{id}")
    fun deleteUsuario(@Path("id") id : String ): Call<String>

}