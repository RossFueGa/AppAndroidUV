package com.example.mykeepapp.data.data.Api

import com.example.mykeepapp.ui.models.Apartado
import com.example.mykeepapp.ui.models.Equipo
import com.example.mykeepapp.ui.models.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /*----------------------------Usuarios-----------------------*/

    @GET("usuarios")
    fun getUsuariosRecords(): Call<List<Usuario>>

    @POST("usuarios")
    fun insertUsuario(@Body body: Usuario): Call<String>

    @GET("usuarios/{id}")
    fun getUsuarioById(@Path("id") id : String): Call<Usuario>

    @PUT("usuarios")
    fun updateUsuario(@Body body: Usuario) : Call<String>

    /*----------------------------Apartados-----------------------*/
    @POST("apartados")
    fun insertApartado(@Body body: Apartado ): Call<String>

    /*----------------------------Equipos-----------------------*/
    @GET("equipos")
    fun getEquipos() : Call<List<Equipo>>

    @PUT("equipos")
    fun updateEquipo(@Body body: Equipo) : Call<String>




}