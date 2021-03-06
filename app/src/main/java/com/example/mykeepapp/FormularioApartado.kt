package com.example.mykeepapp

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_formulario_apartado.*
import java.text.SimpleDateFormat
import java.util.*

class FormularioApartado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_apartado)

        pickTimeDe.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                //ingresar la hora en el tex_time

                txt_time.text = SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        pickTimeA.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                //ingresar la hora en el tex_time

                txt_timeA.text = SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }



    }
}
