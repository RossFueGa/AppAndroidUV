package com.example.mykeepapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.activity_alumno_academico.*

class ViewAdapter(fa: FragmentActivity ) : FragmentStateAdapter(fa) {
    //Variable de vlase
    companion object{
        private const val ARG_OBJECT = "object"
    }
    /*
    * Cantidad de fragments a mostrar
    * */
    override fun getItemCount(): Int  = 5

    override fun createFragment(position: Int): Fragment {
        var fra = Fragment()
        when(position){
            0->{fra = FragmentInicio()}
            1->{fra = FragmentBuscar()}
            2->{fra = FragmentDetalleApartado()}
            3->{fra = FragmentSinMensajes()}
            4->{fra = FragmentPerfil()}
            }
            return fra
        }
        /* Cuando se tiene consistencia en el tipo de fragments se puede usar así
        val fragment = FragmentInicio()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
        */
    }