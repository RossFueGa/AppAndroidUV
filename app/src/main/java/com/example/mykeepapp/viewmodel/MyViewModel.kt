package com.example.mykeepapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mykeepapp.data.data.UsuariosDataSet
import com.example.mykeepapp.ui.models.Usuario


class MyViewModel:  ViewModel(){

    val userRepo = UsuariosDataSet()

    fun getDataUserLive(): LiveData<List<Usuario>>{
        return userRepo.getUsuarios()
    }

}