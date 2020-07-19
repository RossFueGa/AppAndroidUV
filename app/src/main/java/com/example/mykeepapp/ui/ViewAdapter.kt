package com.example.mykeepapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewAdapter(fa: FragmentActivity ) : FragmentStateAdapter(fa) {
    //Variable de clase
    companion object{
        private const val ARG_OBJECT = "object"
    }
    /*
    * Cantidad de fragments a mostrar
    * */

    override fun getItemCount(): Int  = 4

    /*
    override fun getItemViewType(position: Int): Int {
        return POSITION_NONE
    }
    override fun getItemId(position: Int): Long {
        return POSITION_NONE.toLong();
    }*/




   override fun createFragment(position: Int): Fragment {
        var fra = Fragment()
        when(position){
            0->{fra = FragmentInicio()
            }
            1->{fra = FragmentDetalleApartado()
            }
            2->{fra = FragmentDevolverEquipo()
            }
            3->{fra = FragmentPerfil()
            }
            }
            return fra
        }

    }