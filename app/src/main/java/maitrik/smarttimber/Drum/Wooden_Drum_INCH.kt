package maitrik.smarttimber.Drum

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_date_picker.view.*
import kotlinx.android.synthetic.main.activity_wooden__drum__inch.*
import maitrik.smarttimber.R
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.Model.Drum
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Wooden_Drum_INCH : AppCompatActivity() {
    var ickr = ""
    var ickri = ""
    var imcd = ""
    var isig = ""
    var itot = ""
    var tcft = 0.0
    var irate = ""
    var iamnt = ""
    var dateday = 0
    var datemounth = 0
    var dateyear = 0
    var date = ""
    val inchmm = "INCH"
    lateinit var ickrinch: EditText
    lateinit var ickrwidth: EditText
    lateinit var ickrqty: EditText
    lateinit var ickrcft: TextView
    lateinit var ickriinch: EditText
    lateinit var ickriwidth: EditText
    lateinit var ickriqty: EditText
    lateinit var ickricft: TextView
    lateinit var imcdinch: EditText
    lateinit var imcdwidth: EditText
    lateinit var imcdqty: EditText
    lateinit var imcft: TextView
    lateinit var isw: EditText
    lateinit var ish: EditText
    lateinit var isl: EditText
    lateinit var isq: EditText
    lateinit var iscft: TextView
    lateinit var ietrate: EditText
    lateinit var ietamnt: TextView
    lateinit var cdate: EditText
    lateinit var cno: EditText
    lateinit var vno: AutoCompleteTextView
    lateinit var db: DBHandler
    lateinit var name: AutoCompleteTextView
    var msharedpref: SharedPreferences?=null
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wooden__drum__inch)
            db = DBHandler(this)
            msharedpref=this.getSharedPreferences("Drum_INCH", Context.MODE_PRIVATE)
            ickrinch = findViewById<EditText>(R.id.etinchckr)
            ickrwidth = findViewById<EditText>(R.id.etinchckrwidth)
            ickrqty = findViewById<EditText>(R.id.etinchckrqty)
            ickrcft = findViewById<TextView>(R.id.txtinchckrcft)
            ickriinch = findViewById<EditText>(R.id.etinchckri)
            ickriwidth = findViewById<EditText>(R.id.etinchckriwidth)
            ickriqty = findViewById<EditText>(R.id.etinchckriqty)
            ickricft = findViewById<TextView>(R.id.txtinchckricft)
            imcdinch = findViewById<EditText>(R.id.etinchmcd)
            imcdwidth = findViewById<EditText>(R.id.etinchmcdwidth)
            imcdqty = findViewById<EditText>(R.id.etinchmcdqty)
            imcft = findViewById<TextView>(R.id.etinchmcdcft)
            isw = findViewById<EditText>(R.id.etinchsw)
            ish = findViewById<EditText>(R.id.etinchh)
            isl = findViewById<EditText>(R.id.etinchsl)
            isq = findViewById<EditText>(R.id.etinchsq)
            iscft = findViewById<TextView>(R.id.etinchscft)
            val iinchtcft = findViewById<TextView>(R.id.etinchtcft)
            val igetcft = findViewById<Button>(R.id.btninchgcft)
            ietrate = findViewById<EditText>(R.id.etinchrate)
            val ibtnrate = findViewById<Button>(R.id.btninchrate)
            val iv = findViewById<TextView>(R.id.txtinchview)
            ietamnt = findViewById<TextView>(R.id.txtinchtamnt)
            ietamnt = findViewById<TextView>(R.id.txtinchtamnt)
            ickrinch.setText(msharedpref!!.getString("ickrinch",""))
            ickrwidth.setText(msharedpref!!.getString("ickrwidth",""))
            ickrqty.setText(msharedpref!!.getString("ickrqty",""))
            ickriinch.setText(msharedpref!!.getString("ickriinch",""))
            ickriwidth.setText(msharedpref!!.getString("ickriwidth",""))
            ickriqty.setText(msharedpref!!.getString("ickriqty",""))
            imcdinch.setText(msharedpref!!.getString("imcdinch",""))
            imcdwidth.setText(msharedpref!!.getString("imcdwidth",""))
            imcdqty.setText(msharedpref!!.getString("imcdqty",""))
            isw.setText(msharedpref!!.getString("isw",""))
            ish.setText(msharedpref!!.getString("ish",""))
            isl.setText(msharedpref!!.getString("isl",""))
            isq.setText(msharedpref!!.getString("isq",""))
            ickrcft.text=msharedpref!!.getString("ckrcft","0.0")
            ickricft.text=msharedpref!!.getString("ckricft","0.0")
            imcft.text=msharedpref!!.getString("mcdcft","0.0")
            iscft.text=msharedpref!!.getString("sigcft","0.0")
            iinchtcft.text=msharedpref!!.getString("tcft","0.0")
            ietrate.setText(msharedpref!!.getString("rate","0"))
            ietamnt.text=msharedpref!!.getString("amnt","0")
            /*itot=msharedpref!!.getString("tcft","0.0")*/
            btninchsave.setOnClickListener {
                igetcft.performClick()
                ibtnrate.performClick()
                hideSoftKeyboard()
                if (tcft==0.0)
                {
                    Toast.makeText(this,"Calculate CFT!!", Toast.LENGTH_SHORT).show()
                }
                else {
                    showsavedialog()
                }
            }
            btninchviewall.setOnClickListener {
                hideSoftKeyboard()
                val intent = Intent(applicationContext, Drum_List::class.java)
                startActivity(intent)
            }
            ickrinch.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickrinch.text.toString().trim().isEmpty() || ickrinch.text.toString()==".") {
                        ickrinch.setText("0")
                    } else {
                        var w=ickrinch.text.toString().toDouble()
                        var v=w.roundToInt()
                        ickrinch.setText("$v")
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (ickrinch.text.toString().toInt()==0)
                    {
                        editor.putString("ickrinch","")
                    }
                    else
                    {
                        editor.putString("ickrinch",ickrinch.text.toString())
                    }
                    editor.apply()
                }
            }
            ickrwidth.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickrwidth.text.toString().trim().isEmpty() || ickrwidth.text.toString()==".") {
                        ickrwidth.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (ickrwidth.text.toString().toDouble()==0.0)
                    {
                        editor.putString("ickrwidth","")
                    }
                    else
                    {
                        editor.putString("ickrwidth",ickrwidth.text.toString())
                    }
                    editor.apply()
                }
            }
            ickrqty.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickrqty.text.toString().trim().isEmpty() || ickrqty.text.toString()==".") {
                        ickrqty.setText("0")
                    } else {
                        var w=ickrqty.text.toString().toDouble()
                        var v=w.roundToInt()
                        ickrqty.setText("$v")
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (ickrqty.text.toString().toInt()==0)
                    {
                        editor.putString("ickrqty","")
                    }
                    else
                    {
                        editor.putString("ickrqty",ickrqty.text.toString())
                    }
                    editor.apply()
                }
            }
            ickriinch.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickriinch.text.trim().isEmpty()|| ickriinch.text.toString()==".") {
                        ickriinch.setText("0")
                    } else {
                        var w=ickriinch.text.toString().toDouble()
                        var v=w.roundToInt()
                        ickriinch.setText("$v")
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (ickriinch.text.toString().toInt()==0)
                    {
                        editor.putString("ickriinch","")
                    }
                    else
                    {
                        editor.putString("ickriinch",ickriinch.text.toString())
                    }
                    editor.apply()
                }
            }
            ickriwidth.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickriwidth.text.trim().isEmpty()|| ickriwidth.text.toString()==".") {
                        ickriwidth.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (ickriwidth.text.toString().toDouble()==0.0)
                    {
                        editor.putString("ickriwidth","")
                    }
                    else
                    {
                        editor.putString("ickriwidth",ickriwidth.text.toString())
                    }
                    editor.apply()
                }
            }
            ickriqty.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickriqty.text.trim().isEmpty() || ickriqty.text.toString()==".") {
                        ickriqty.setText("0")
                    } else {
                        var w=ickriqty.text.toString().toDouble()
                        var v=w.roundToInt()
                        ickriqty.setText("$v")
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (ickriqty.text.toString().toInt()==0)
                    {
                        editor.putString("ickriqty","")
                    }
                    else
                    {
                        editor.putString("ickriqty",ickriqty.text.toString())
                    }
                    editor.apply()
                }

            }
            imcdinch.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (imcdinch.text.trim().isEmpty()|| imcdinch.text.toString()==".") {
                        imcdinch.setText("0")
                    } else {
                        var w=imcdinch.text.toString().toDouble()
                        var v=w.roundToInt()
                        imcdinch.setText("$v")
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (imcdinch.text.toString().toInt()==0)
                    {
                        editor.putString("imcdinch","")
                    }
                    else
                    {
                        editor.putString("imcdinch",imcdinch.text.toString())
                    }
                    editor.apply()
                }
            }
            imcdwidth.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (imcdwidth.text.trim().isEmpty()|| imcdwidth.text.toString()==".") {
                        imcdwidth.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (imcdwidth.text.toString().toDouble()==0.0)
                    {
                        editor.putString("imcdwidth","")
                    }
                    else
                    {
                        editor.putString("imcdwidth",imcdwidth.text.toString())
                    }
                    editor.apply()
                }
            }
            imcdqty.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (imcdqty.text.trim().isEmpty()|| imcdqty.text.toString()==".") {
                        imcdqty.setText("0")
                    } else {
                        var w=imcdqty.text.toString().toDouble()
                        var v=w.roundToInt()
                        imcdqty.setText("$v")
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (imcdqty.text.toString().toInt()==0)
                    {
                        editor.putString("imcdqty","")
                    }
                    else
                    {
                        editor.putString("imcdqty",imcdqty.text.toString())
                    }
                    editor.apply()
                }
            }
            isw.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (isw.text.trim().isEmpty()|| isw.text.toString()==".") {
                        isw.setText("0")
                    } else {
                        var w=isw.text.toString().toDouble()
                        var v=w.roundToInt()
                        isw.setText("$v")
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (isw.text.toString().toInt()==0)
                    {
                        editor.putString("isw","")
                    }
                    else
                    {
                        editor.putString("isw",isw.text.toString())
                    }
                    editor.apply()
                }
            }
            ish.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ish.text.trim().isEmpty()|| ish.text.toString()==".") {
                        ish.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (ish.text.toString().toDouble()==0.0)
                    {
                        editor.putString("ish","")
                    }
                    else
                    {
                        editor.putString("ish",ish.text.toString())
                    }
                    editor.apply()
                }
            }
            isl.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (isl.text.trim().isEmpty()|| isl.text.toString()==".") {
                        isl.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (isl.text.toString().toDouble()==0.0)
                    {
                        editor.putString("isl","")
                    }
                    else
                    {
                        editor.putString("isl",isl.text.toString())
                    }
                    editor.apply()
                }
            }
            isq.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (isq.text.trim().isEmpty()|| isq.text.toString()==".") {
                        isq.setText("0")
                    } else {
                        var w=isq.text.toString().toDouble()
                        var v=w.roundToInt()
                        isq.setText("$v")
                        igetcft.performClick()
                    }
                    val editor=msharedpref!!.edit()
                    if (isq.text.toString().toInt()==0)
                    {
                        editor.putString("isq","")
                    }
                    else
                    {
                        editor.putString("isq",isq.text.toString())
                    }
                    editor.apply()
                }
            }
            ietrate.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ietrate.text.trim().isEmpty()) {
                        ietrate.setText("0")
                    }
                    ibtnrate.performClick()
                    val editor=msharedpref!!.edit()
                    if (ietrate.text.toString().toInt()==0)
                    {
                        editor.putString("ietrate","")
                    }
                    else
                    {
                        editor.putString("ietrate",ietrate.text.toString())
                    }
                    editor.apply()
                }
            }
            ietrate.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_GO){
                    igetcft.performClick()
                    hideSoftKeyboard()
                    true
                } else {
                    false
                }
            }
            /*ickrinch.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickrinch.text.toString().trim().isEmpty()) {
                        ickrinch.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }*/
            /*ickrwidth.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickrwidth.text.toString().trim().isEmpty()) {
                        ickrwidth.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            ickrqty.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickrqty.text.toString().trim().isEmpty()) {
                        ickrqty.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            ickriinch.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickriinch.text.trim().isEmpty()) {
                        ickriinch.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            ickriwidth.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickriwidth.text.trim().isEmpty()) {
                        ickriwidth.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            ickriqty.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ickriqty.text.trim().isEmpty()) {
                        ickriqty.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }

            }
            imcdinch.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (imcdinch.text.trim().isEmpty()) {
                        imcdinch.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            imcdwidth.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (imcdwidth.text.trim().isEmpty()) {
                        imcdwidth.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            imcdqty.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (imcdqty.text.trim().isEmpty()) {
                        imcdqty.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            isw.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (isw.text.trim().isEmpty()) {
                        isw.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            ish.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ish.text.trim().isEmpty()) {
                        ish.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            isl.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (isl.text.trim().isEmpty()) {
                        isl.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }
            isq.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (isq.text.trim().isEmpty()) {
                        isq.setText("0")
                    } else {
                        igetcft.performClick()
                    }
                }
            }

            ietrate.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ietrate.text.trim().isEmpty()) {
                        ietrate.setText("0")
                    }
                    ibtnrate.performClick()

                }
            }*/
            ibtnrate.setOnClickListener {
                iv.text = "Total Amount: "
                ietamnt.text = gettotalamnt(itot.toString(), ietrate.text.toString()).toString()
            }
            igetcft.setOnClickListener {
                ickrcft.text =
                    getckrcft(ickrinch.text.toString(), ickrwidth.text.toString(), ickrqty.text.toString()).toString()
                ickricft.text =
                    getckricft(ickriinch.text.toString(), ickriwidth.text.toString(), ickriqty.text.toString()).toString()
                imcft.text =
                    getmcdcft(imcdinch.text.toString(), imcdwidth.text.toString(), imcdqty.text.toString()).toString()
                iscft.text = getsigmetcft(
                    isw.text.toString(),
                    ish.text.toString(),
                    isl.text.toString(),
                    isq.text.toString()
                ).toString()
                iinchtcft.text = "Total CFT: " + gettotalcft(
                    ickrcft.text.toString(),
                    ickricft.text.toString(),
                    imcft.text.toString(),
                    iscft.text.toString()
                ).toString()
                ibtnrate.performClick()
            }
        }

    private fun getckrcft(inch: String, width: String, qty: String): Double {
        var ckrcft = 0.00
        if (inch.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            ckrcft = String.format("%.2f", 0.00).toDouble()
        } else {
            val ckr =
                ((inch.toString().toDouble() * inch.toString().toDouble()) * (width.toString().toDouble() + width.toString().toDouble()) * qty.toString().toDouble() / 2200)
            ckrcft = String.format("%.2f", ckr).toDouble()
            val editor=msharedpref!!.edit()
            editor.putString("ckrcft","$ckrcft")
            editor.apply()
            val w = width.toDouble() + width.toDouble()
            ickr = "$inch*$w*$qty = $ckrcft"
        }
        return ckrcft
    }

    private fun getckricft(inch: String, width: String, qty: String): Double {
        var ckricft = 0.00
        if (inch.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            ckricft = String.format("%.2f", 0.00).toDouble()
        } else {
            val cft = ((inch.toDouble() * inch.toDouble()) * width.toDouble() * qty.toDouble() / 2200)
            ckricft = String.format("%.2f", cft).toDouble()
            val editor=msharedpref!!.edit()
            editor.putString("ckricft","$ckricft")
            editor.apply()
            ickri = "$inch*$width*$qty = $ckricft"
        }
        return ckricft
    }

    private fun getmcdcft(inch: String, width: String, qty: String): Double {
        var mcdcft = 0.00
        if (inch.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            mcdcft = String.format("%.2f", 0.00).toDouble()
        } else {
            val cft = ((inch.toDouble() * inch.toDouble()) * width.toDouble() * qty.toDouble() / 2200)
            mcdcft = String.format("%.2f", cft).toDouble()
            val editor=msharedpref!!.edit()
            editor.putString("mcdcft","$mcdcft")
            editor.apply()
            imcd = "$inch*$width*$qty = $mcdcft"
        }
        return mcdcft
    }

    private fun getsigmetcft(w: String, h: String, l: String, q: String): Double {
        var sigcft = 0.00
        if (w.trim().isEmpty() || h.trim().isEmpty() || l.trim().isEmpty() || q.trim().isEmpty()) {
            sigcft = String.format("%.2f", 0.00).toDouble()
        } else {
            val cft = w.toDouble() * h.toDouble() * l.toDouble() * q.toDouble() / 144
            sigcft = String.format("%.2f", cft).toDouble()
            val editor=msharedpref!!.edit()
            editor.putString("sigcft","$sigcft")
            editor.apply()
            isig = "$w*$h*$l*$q = $sigcft"
        }
        return sigcft
    }
    private fun gettotalamnt(tot: String, rate: String): Int {
        var a = 0
        var rt = 0
        rt = if (rate.trim().isEmpty()) {
            0
        } else {
            rate.toInt()
        }
        if (tot.trim().isNotEmpty()) {
            val ab = itot.toDouble() * rt.toDouble()
            a = ab.roundToInt()
            irate = rate.toString()
            iamnt = a.toString()
            val e=msharedpref!!.edit()
            e.putString("rate","$rate")
            e.putString("amnt","$iamnt")
            e.apply()
        } else {
            a = 0
            Toast.makeText(this, "Calculate CFT!!!", Toast.LENGTH_SHORT).show()
        }
        return a
    }

    private fun gettotalcft(ckr: String, ckri: String, mcd: String, sig: String): Double {
        var totalcft = 0.00
        totalcft = String.format("%.2f", ckr.toDouble() + ckri.toDouble() + mcd.toDouble() + sig.toDouble()).toDouble()
        tcft = totalcft
        itot = "$totalcft"
        val editor=msharedpref!!.edit()
        editor.putString("tcft","Total CFT: $totalcft")
        editor.apply()
        return totalcft
    }

    private fun getClear() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are You Sure  want to clear entry??")
        builder.setIcon(R.drawable.ic_clear_black)
            .setPositiveButton("Yes") { _, _ ->
                etinchckr.requestFocus()
                etinchckr.setText("")
                etinchckrwidth.setText("")
                etinchckrqty.setText("")
                txtinchckrcft.text = "0.0"
                etinchckriwidth.setText("")
                etinchckriqty.setText("")
                etinchckri.setText("")
                txtinchckricft.text = "0.0"
                etinchmcd.setText("")
                etinchmcdwidth.setText("")
                etinchmcdqty.setText("")
                etinchmcdcft.text = "0.0"
                etinchsw.setText("")
                etinchh.setText("")
                etinchsl.setText("")
                etinchsq.setText("")
                etinchscft.text = "0.0"
                etinchtcft.text = "Total CFT: 0.0"
                etinchrate.setText("")
                txtinchview.text = ""
                txtinchtamnt.text = ""
                val editor=msharedpref!!.edit()
                editor.clear()
                editor.apply()
            }
        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(this, "Not cleared...", Toast.LENGTH_LONG).show()
        }
        var dialog = builder.create()
        builder.show()
    }

    fun shareDrumDetials(
        ckr: String,
        ckri: String,
        mcd: String,
        sig: String,
        tot: String,
        rate: String,
        amnt: String
    ) {
        val msg = ArrayList<String>()
        val c = "Chakkar: $ckr"
        msg.add(c)
        if (ckri.isNotEmpty()) {
            val cki = "Chakkri : $ckri"
            msg.add(cki)
        }
        if (mcd.isNotEmpty()) {
            val m = "MCD      : $mcd"
            msg.add(m)
        }
        if (sig.isNotEmpty()) {
            val s = "Sigmet  : $sig"
            msg.add(s)
        }
        val totalcft = "Total CFT: $tot"
        msg.add(totalcft)
        if (rate == "" || rate.toInt() == 0) {
            var r = ""
        } else {
            val r = "Rate   :  $rate"
            val t = "Total Amount: $amnt"
            msg.add(r)
            msg.add(t)
        }
        var tmsg = ""
        for (i in 0..msg.size - 1) {
            tmsg += "${msg[i]} \n"
        }
        if (tot.trim().isNotEmpty() && tot.trim().toDouble()!=0.0) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "WOODEN DRUM CFT\n" +
                            tmsg
                )
                    .type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.sent_to)))
        } else {
            Toast.makeText(this, "Calculate CFT!!!!", Toast.LENGTH_LONG).show()
        }
        msg.clear()
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater
        menuInflater.inflate(R.menu.mymenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun hideSoftKeyboard()
    {
        val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.SHOW_FORCED)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                btninchgcft.performClick()
                shareDrumDetials(
                    ickr,
                    ickri,
                    imcd,
                    isig,
                    itot,
                    irate,
                    iamnt
                )
                /*Toast.makeText(this,"Share", Toast.LENGTH_SHORT).show()*/
            }
            R.id.clr -> {
                getClear()
                //Toast.makeText(this,"Clear", Toast.LENGTH_SHORT).show()
            }
            R.id.cconvert -> {
                val intent = Intent(this, Converter_INCHMM::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun showdatepickerdialog(date: EditText) {
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val dm = c.get(Calendar.MONTH)
        val dt = c.get(Calendar.DAY_OF_MONTH)
        var dmm = ""
        var moun = ""
        var d = ""
        val mm = dm + 1
        dmm = if (mm < 10) {
            "$mm".padStart(2, '0')

        } else {
            mm.toString()
        }

        moun = if (dt < 10) {
            "$dt".padStart(2, '0')
        } else {
            dt.toString()
        }
        d = moun
        dateday = d.toInt()
        datemounth = dmm.toInt()
        dateyear = y
        date.setText("$d/$dmm/$y")
        date.setOnClickListener {
            val dialog =
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, myear: Int, mmonth: Int, mdayOfMonth: Int ->
                        var m = mmonth + 1
                        var datezero = ""
                        var mounthzero = ""
                        datezero = if (mdayOfMonth < 10) {
                            "$mdayOfMonth".padStart(2, '0')
                        } else {
                            mdayOfMonth.toString()
                        }
                        mounthzero = if (m < 10) {
                            "$m".padStart(2, '0')
                        } else {
                            m.toString()
                        }
                        date.setText("" + datezero + "/" + mounthzero + "/" + myear)

                        dateday = datezero.toInt()
                        datemounth = mounthzero.toInt()
                        dateyear = myear
                    },
                    y,
                    dm,
                    d.toInt()
                )
            dialog.show()
        }
    }

    fun showsavedialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val view: View = inflater.inflate(R.layout.activity_date_picker, null)
        cdate = view.etddate
        /*cdate=view.etddate*/
        name = view.etdname
        autosuggestname(name)
        cno = view.etcno
        vno = view.etvno
        autosuggestvehivehicleno(vno)
        showdatepickerdialog(cdate)
        builder.setView(view)
            .setTitle("Save Entries")
            .setPositiveButton("SAVE", DialogInterface.OnClickListener { dialog, which ->
                savedata()
                hideSoftKeyboard()
                /* Toast.makeText(this,"name:$name,date:$date",Toast.LENGTH_LONG).show()*/
            })
            .setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which -> }
            )
        val dialog = builder.create()
        dialog.show()
    }

    fun savedata() {
        if (ickrinch.text.isEmpty()) {
            ickrinch.setText("0")
        }
        if (ickrwidth.text.isEmpty()) {
            ickrwidth.setText("0")
        }
        if (ickrqty.text.isEmpty()) {
            ickrqty.setText("0")
        }
        if (ickriinch.text.isEmpty()) {
            ickriinch.setText("0")
        }
        if (ickriwidth.text.isEmpty()) {
            ickriwidth.setText("0")
        }
        if (ickriqty.text.isEmpty()) {
            ickriqty.setText("0")
        }
        if (imcdinch.text.isEmpty()) {
            imcdinch.setText("0")
        }
        if (imcdwidth.text.isEmpty()) {
            imcdwidth.setText("0")
        }
        if (imcdqty.text.isEmpty()) {
            imcdqty.setText("0")
        }
        if (isw.text.isEmpty()) {
            isw.setText("0")
        }
        if (ish.text.isEmpty()) {
            ish.setText("0")
        }
        if (isl.text.isEmpty()) {
            isl.setText("0")
        }
        if (isq.text.isEmpty()) {
            isq.setText("0")
        }
        if (ietrate.text.trim().isEmpty()) {
            ietrate.setText("0")
            ietamnt.setText("0")
        }
        if (cno.text.trim().isEmpty()) {
            cno.setText("0")
        }
        val context = this
        val db = DBHandler(context)
        if (tcft == 0.0) {
            Toast.makeText(context, "Calculate CFT!!", Toast.LENGTH_LONG).show()
        } else {

            val drum = Drum(
                ickrinch.text.toString().toInt(),
                ickrwidth.text.toString().toDouble(),
                ickrqty.text.toString().toInt(),
                ickrcft.text.toString().toDouble(),
                ickriinch.text.toString().toInt(),
                ickriwidth.text.toString().toDouble(),
                ickriqty.text.toString().toDouble(),
                ickricft.text.toString().toDouble(),
                0,
                imcdinch.text.toString().toInt(),
                imcdwidth.text.toString().toDouble(),
                imcdqty.text.toString().toInt(),
                imcft.text.toString().toDouble(),
                isw.text.toString().toInt(),
                ish.text.toString().toDouble(),
                isl.text.toString().toDouble(),
                isq.text.toString().toInt(),
                iscft.text.toString().toDouble(),
                tcft.toDouble(),
                ietrate.text.toString().toInt(),
                ietamnt.text.toString().toInt(),
                dateday,
                datemounth,
                dateyear,
                cdate.text.toString(),
                inchmm,
                name.text.toString(),
                cno.text.toString().toInt(),
                vno.text.toString()
            )
            db.insertdrum(drum)
        }
    }

    fun autosuggestname(name: AutoCompleteTextView) {
        val n=db.getname(this)
        val namelist=ArrayList<String>()
        for (i in 0..n.size-1)
        {
            n.get(i).name?.let { namelist.add(it) }
        }
        val b=namelist.distinct()
        val adapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,b)
        name.setAdapter(adapter)
    }
    fun autosuggestvehivehicleno(name: AutoCompleteTextView) {
        val n=db.getvno(this)
        val namelist=ArrayList<String>()
        for (i in 0..n.size-1)
        {
            n.get(i).vno?.let { namelist.add(it) }
        }
        val b=namelist.distinct()
        val adapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,b)
        name.setAdapter(adapter)
    }


}
