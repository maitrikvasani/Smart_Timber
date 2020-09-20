package maitrik.smarttimber.Drum

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_date_picker.*
import maitrik.smarttimber.R
import maitrik.smarttimber.Model.DBHandler

import java.util.*

class DatePicker : AppCompatActivity() {
    var dateday = 0
    var datemounth = 0
    var dateyear = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)
       showdatepickerdialog()
    }
    fun showdatepickerdialog()
    {
        val c = Calendar.getInstance()
        val y=c.get(Calendar.YEAR)
        var dmm=c.get(Calendar.MONTH)
        val date=c.get(Calendar.DAY_OF_MONTH)
        dmm=dmm+1
        var dm=0
        var n=""
        var d=0
        if (dmm < 10)
        {
            var m="$dmm".padStart(2,'0')
            dm=m.toInt()
        }
        if (date < 10)
        {
            var m="$date".padStart(2,'0')
            d=m.toInt()
        }
        dateday=d
        datemounth=dm
        dateyear=y

        etddate.setText("$d/$dm/$y")
        etddate.setOnClickListener {
            val dialog=
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, myear:Int, mmonth:Int, mdayOfMonth:Int ->
                    etddate.setText(""+ mdayOfMonth +"/"+ mmonth +"/"+ myear)
                    dateday=mdayOfMonth
                    datemounth=mmonth
                    dateyear=myear
                },y,dm,d)
            dialog.show() }
    }

}
