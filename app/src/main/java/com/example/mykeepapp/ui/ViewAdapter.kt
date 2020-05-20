package com.example.mykeepapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mykeepapp.ui.FragmentDetalleApartado
import com.example.mykeepapp.ui.FragmentInicio
import com.example.mykeepapp.ui.FragmentPerfil
import com.example.mykeepapp.ui.FragmentSinMensajes

class ViewAdapter(fa: FragmentActivity ) : FragmentStateAdapter(fa) {
    //Variable de vlase
    companion object{
        private const val ARG_OBJECT = "object"
    }
    /*
    * Cantidad de fragments a mostrar
    * */
    override fun getItemCount(): Int  = 4

    override fun createFragment(position: Int): Fragment {
        var fra = Fragment()
        when(position){
            0->{fra = FragmentInicio()
            }
            1->{fra = FragmentDetalleApartado()
            }
            2->{fra = FragmentSinMensajes()
            }
            3->{fra = FragmentPerfil()
            }
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