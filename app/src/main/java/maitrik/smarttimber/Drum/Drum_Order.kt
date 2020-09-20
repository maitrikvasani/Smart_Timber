package maitrik.smarttimber.Drum

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_drum__order.*
import maitrik.smarttimber.R

class Drum_Order : AppCompatActivity() {
    var msharedpref: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drum__order)
        var btn = findViewById<Button>(R.id.btnsendorder)
        msharedpref=this.getSharedPreferences("Drum_Order", Context.MODE_PRIVATE)
        etordername.setText(msharedpref!!.getString("oname",""))
        etorderckr.setText(msharedpref!!.getString("ockr",""))
        etorderckrwidth.setText(msharedpref!!.getString("ockrwidth",""))
        etorderckri.setText(msharedpref!!.getString("ockri",""))
        etorderckriwidth.setText(msharedpref!!.getString("ockriwidth",""))
        etorderchdiwidth.setText(msharedpref!!.getString("ocdiwidth",""))
        etorderckrikhacha.setText(msharedpref!!.getString("ockrikhacha",""))
        etordermcd.setText(msharedpref!!.getString("omcd",""))
        etordermcdwidth.setText(msharedpref!!.getString("omcdwidth",""))
        etordersigw.setText(msharedpref!!.getString("osw",""))
        etordersigh.setText(msharedpref!!.getString("osh",""))
        etordersigl.setText(msharedpref!!.getString("osl",""))
        etordersigq.setText(msharedpref!!.getString("osq",""))
        etorderchole.setText(msharedpref!!.getString("ohole",""))
        etorderpin.setText(msharedpref!!.getString("opin",""))
        etordercable.setText(msharedpref!!.getString("ocable",""))
        etorderhub.setText(msharedpref!!.getString("ohub",""))
        etordertierod1.setText(msharedpref!!.getString("ot1",""))
        etordertierod2.setText(msharedpref!!.getString("ot2",""))
        etordernailcircle.setText(msharedpref!!.getString("ons",""))
        etordernos.setText(msharedpref!!.getString("onos",""))
        txtcable.setOnClickListener {
            var item = arrayOf("Cable", "Double Cable")
            var c=0
            if (txtcable.text.toString().trim()=="Cable")
            {
                c=0
            }
            else
            {
                c=1
            }
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Choose an option..")
            builder.setSingleChoiceItems(item, c) { dialog: DialogInterface?, which: Int ->
                if (item[which] == "Cable")
                {
                    txtcable.text="Cable"
                    Toast.makeText(this,"Cable", Toast.LENGTH_SHORT).show()
                }
                else{
                    txtcable.text="Double\nCable"
                    Toast.makeText(this,"Double Cable", Toast.LENGTH_SHORT).show()
                }
                dialog!!.dismiss()
            }
            var dialog = builder.create()
            dialog.show()
        }
        etordername.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("oname",etordername.text.toString())
                editor.apply()
            }
        }
        etorderckr.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ockr",etorderckr.text.toString())
                editor.apply()
            }
        }
        etorderckrwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ockrwidth",etorderckrwidth.text.toString())
                editor.apply()
            }
        }
        etorderckri.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ockri",etorderckri.text.toString())
                editor.apply()
            }
        }
        etorderckriwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ockriwidth",etorderckriwidth.text.toString())
                editor.apply()
            }
        }
        etorderchdiwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ocdiwidth",etorderchdiwidth.text.toString())
                editor.apply()
            }
        }
        etorderckrikhacha.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ockrikhacha",etorderckrikhacha.text.toString())
                editor.apply()
            }
        }
        etordermcd.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("omcd",etordermcd.text.toString())
                editor.apply()
            }
        }
        etordermcdwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("omcdwidth",etordermcdwidth.text.toString())
                editor.apply()
            }
        }
        etordersigw.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("osw",etordersigw.text.toString())
                editor.apply()
            }
        }
        etordersigh.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("osh",etordersigh.text.toString())
                editor.apply()
            }
        }
        etordersigl.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("osl",etordersigl.text.toString())
                editor.apply()
            }
        }
        etordersigq.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("osq",etordersigq.text.toString())
                editor.apply()
            }
        }
        etorderchole.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ohole",etorderchole.text.toString())
                editor.apply()
            }
        }
        etorderpin.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("opin",etorderpin.text.toString())
                editor.apply()
            }
        }
        etordercable.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ocable",etordercable.text.toString())
                editor.apply()
            }
        }
        etorderhub.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ohub",etorderhub.text.toString())
                editor.apply()
            }
        }
        etordertierod1.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ot1",etordertierod1.text.toString())
                editor.apply()
            }
        }
        etordertierod2.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ot2",etordertierod2.text.toString())
                editor.apply()
            }
        }
        etordernailcircle.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("ons",etordernailcircle.text.toString())
                editor.apply()
            }
        }
        etordernos.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
            {
                val editor=msharedpref!!.edit()
                editor.putString("onos",etordernos.text.toString())
                editor.apply()
            }
        }
        txtckri.setOnClickListener {
            var item = arrayOf("Chakkri", "Chaddi" )
            var c = 0
            if (txtckri.text == "Chakkri") {
                c = 0
            } else {
                c = 1
            }
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Choose an option..")
            builder.setSingleChoiceItems(item, c){ dialog: DialogInterface?, which: Int ->
                if (item[which]=="Chakkri")
                {
                    txtckri.text="Chakkri"
                    etorderchdiwidth.visibility= View.GONE
                    etorderckriwidth.visibility= View.VISIBLE
                    etorderckri.requestFocus()
                    Toast.makeText(this, "Chakkri", Toast.LENGTH_LONG).show()
                }
                else
                {
                    txtckri.text="Chaddi"
                    etorderckri.requestFocus()
                    etorderchdiwidth.visibility= View.VISIBLE
                    etorderckriwidth.visibility= View.GONE
                    Toast.makeText(this, "Chaddi", Toast.LENGTH_LONG).show()
                }
                dialog!!.dismiss()
            }
            var dialog = builder.create()
            dialog.show()
        }
        btn.setOnClickListener {
            if (etorderckr.text.toString().trim().isEmpty())
            {
                etorderckr.error="Enter Chakkar Flangue"
                etorderckr.requestFocus()
            }
            else
            {
                getorder(
                    etordername.text.toString(),
                    etorderckr.text.toString(),
                    etorderckrwidth.text.toString(),
                    etorderckri.text.toString(),
                    etorderckriwidth.text.toString(),
                    etorderchdiwidth.text.toString(),
                    etorderckrikhacha.text.toString(),
                    etordermcd.text.toString(),
                    etordermcdwidth.text.toString(),
                    etordersigw.text.toString(),
                    etordersigh.text.toString(),
                    etordersigl.text.toString(),
                    etordersigq.text.toString(),
                    etorderchole.text.toString(),
                    etorderpin.text.toString(),
                    etordercable.text.toString(),
                    etorderhub.text.toString(),
                    etordertierod1.text.toString(),
                    etordertierod2.text.toString(),
                    etordernailcircle.text.toString(),
                    etordernos.text.toString()
                )
            }
            if (etorderckrwidth.text.toString().trim().isEmpty())
            {
                etorderckrwidth.error="Enter Chakkar Width"
                etorderckrwidth.requestFocus()
            }
            else
            {
                getorder(
                    etordername.text.toString(),
                    etorderckr.text.toString(),
                    etorderckrwidth.text.toString(),
                    etorderckri.text.toString(),
                    etorderckriwidth.text.toString(),
                    etorderchdiwidth.text.toString(),
                    etorderckrikhacha.text.toString(),
                    etordermcd.text.toString(),
                    etordermcdwidth.text.toString(),
                    etordersigw.text.toString(),
                    etordersigh.text.toString(),
                    etordersigl.text.toString(),
                    etordersigq.text.toString(),
                    etorderchole.text.toString(),
                    etorderpin.text.toString(),
                    etordercable.text.toString(),
                    etorderhub.text.toString(),
                    etordertierod1.text.toString(),
                    etordertierod2.text.toString(),
                    etordernailcircle.text.toString(),
                    etordernos.text.toString()
                )
            }
            if (etordernos.text.toString().trim().isEmpty())
            {
                etordernos.error="Enter NOS"
                etordernos.requestFocus()
            }
            else
            {
                getorder(
                    etordername.text.toString(),
                    etorderckr.text.toString(),
                    etorderckrwidth.text.toString(),
                    etorderckri.text.toString(),
                    etorderckriwidth.text.toString(),
                    etorderchdiwidth.text.toString(),
                    etorderckrikhacha.text.toString(),
                    etordermcd.text.toString(),
                    etordermcdwidth.text.toString(),
                    etordersigw.text.toString(),
                    etordersigh.text.toString(),
                    etordersigl.text.toString(),
                    etordersigq.text.toString(),
                    etorderchole.text.toString(),
                    etorderpin.text.toString(),
                    etordercable.text.toString(),
                    etorderhub.text.toString(),
                    etordertierod1.text.toString(),
                    etordertierod2.text.toString(),
                    etordernailcircle.text.toString(),
                    etordernos.text.toString()
                )
            }


        }

    }
    fun getorder(
        name: String,
        ckr: String,
        ckrw: String,
        ckri: String,
        ckriw: String,
        chdi:String,
        ckrikhacha:String,
        mcd: String,
        mcdw: String,
        sw: String,
        sh: String,
        sl: String,
        sq: String,
        ch: String,
        pin: String,
        cable: String,
        hub: String,
        tie1: String,
        tie2: String,
        nc: String,
        nos: String
    ): Any {
        var msg=ArrayList<String>()
        var n = "Name: $name"
        msg.add(n)
        var ckrv=""
        if (ckr.isNotEmpty())
        {
            ckrv = "Chakkar: $ckr ($ckrw*$ckrw)"
            msg.add(ckrv)
        }
        var ckriv=""
        if (ckri.isNotEmpty()) {
            if (txtckri.text == "Chakkri") {
                if (ckrikhacha.isNotEmpty()) {
                    ckriv = "${txtckri.text}: $ckri ($ckriw) Khacha $ckrikhacha U"
                    msg.add(ckriv)
                } else {
                    ckriv = "${txtckri.text}: $ckri ($ckriw)"
                    msg.add(ckriv)
                }
            } else
                if (ckrikhacha.isNotEmpty())
                {
                    ckriv = "${txtckri.text}: $ckri ($chdi) Khacha $ckrikhacha U"
                    msg.add(ckriv)
                }else
                {
                    ckriv = "${txtckri.text}: $ckri ($chdi)"
                    msg.add(ckriv)
                }
        }
        var mcdv=""
        if (mcd.isNotEmpty())
        {
            mcdv = "MCD: $mcd ($mcdw)"
            msg.add(mcdv)
        }
        var sigv=""
        if(sw.isNotEmpty())
        {
            sigv = "Sigmet: $sw*$sh*$sl*$sq"
            msg.add(sigv)
        }
        var holev=""
        if (ch.isNotEmpty())
        {
            holev = "Center Hole: $ch"
            msg.add(holev)
        }
        var pinv=""
        if (pin.isNotEmpty())
        {
            pinv = "Pin: $pin"
            msg.add(pinv)
        }

        var cablev=""
        if (cable.isNotEmpty())
        {
            if (txtcable.text=="Cable")
            {
                cablev = "Cable: $cable"
                msg.add(cablev)
            }
            else{
                cablev = "Double Cable: $cable+$cable"
                msg.add(cablev)}
        }

        var hubv=""
        if (hub.isNotEmpty())
        {
            hubv = "Hub: $hub*$hub"
            msg.add(hubv)
        }
        var tie=""
        if (tie1.isNotEmpty())
        {
            tie = "Tie Road: $tie1*$tie2"
            msg.add(tie)
        }
        var ncv=""
        if (nc.isNotEmpty())
        {
            ncv = "Nail Circle: $nc"
            msg.add(ncv)
        }
        var nosv=""
        if (nos.isNotEmpty())
        {
            nosv = "NOS: $nos"
            msg.add(nosv)
        }
        var tmsg=""
        for (i in 0..msg.size-1)
        {
            tmsg += "${msg[i]} \n"
        }
        var m = """|$n
            |$ckrv
            |$ckriv
            |$mcdv
            |$sigv
            |$holev
            |$pinv
            |$cablev
            |$hubv
            |$tie
            |$ncv
            |$nosv
        """.trimMargin()
        /* Toast.makeText(this, m, Toast.LENGTH_LONG).show()*/
        var sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, "WOODEN DRUM CFT\n" +
                        "$tmsg"
            )
                .type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.sent_to)))
        msg.clear()
        return m
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.mymenuclr -> {
                getClear()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getClear() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are You Sure  want to clear entry??")
        builder.setIcon(R.drawable.ic_clear_black)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                etordername.setText("")
                etorderckr.setText("")
                etorderckrwidth.setText("")
                etorderckri.setText("")
                etorderckriwidth.setText("")
                etorderchdiwidth.setText("")
                etorderckrikhacha.setText("")
                etordermcd.setText("")
                etordermcdwidth.setText("")
                etordersigw.setText("")
                etordersigh.setText("")
                etordersigl.setText("")
                etordersigq.setText("")
                etorderchole.setText("")
                etorderpin.setText("")
                etordercable.setText("")
                etorderhub.setText("")
                etordertierod1.setText("")
                etordertierod2.setText("")
                etordernailcircle.setText("")
                etordernos.setText("")
                val editor=msharedpref!!.edit()
                editor.clear()
                editor.apply()
            })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(this, "Not cleared...", Toast.LENGTH_LONG).show()
        })
        var dialog = builder.create()
        builder.show()
    }
}
