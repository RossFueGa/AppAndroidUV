package com.example.mykeepapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mykeepapp.R
import kotlinx.android.synthetic.main.activity_acuerdo_confidencialidad.*
import kotlinx.android.synthetic.main.activity_formulario_apartado.*

class AcuerdoConfidencialidad : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acuerdo_confidencialidad)
        goBack()
    }

    fun goBack(){
        goBackConfidencial.setOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 0){
                supportFragmentManager.popBackStack()
            }else{
                super.onBackPressed()
            }
        }
    }
}
