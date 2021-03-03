package maitrik.smarttimber.Drum

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.activity_wooden__drum__mm.*
import maitrik.smarttimber.R
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.Model.Drum
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Wooden_Drum_MM : AppCompatActivity() {

    var string = ""
    var mckr = ""
    var mckri = ""
    var mmcd = ""
    var msig = ""
    var mtot = ""
    var mrate = ""
    var mamnt = ""
    var mcdi = ""
    var drumlist = arrayOf("")
    var drum = ArrayList<String>()
    var m = ""
    var tcft = 0.0
    var listdrum: List<Drum> = ArrayList<Drum>()
    lateinit var db: DBHandler
    var dateday = 0
    var datemounth = 0
    var dateyear = 0
    var date = ""
    val mminch = "MM"
    var d = ""
    var no=-1
    var n=ArrayList<String>()

    lateinit var ckrmm: EditText
    lateinit var ckrwidth: EditText
    lateinit var ckrqty: EditText
    lateinit var ckrcft: TextView
    lateinit var ckrimm: EditText
    lateinit var ckriwidth: EditText
    lateinit var ckriqty: EditText
    lateinit var ckricft: TextView
    lateinit var mcdmm: EditText
    lateinit var mcdwidth: EditText
    lateinit var mcdqty: EditText
    lateinit var mcft: TextView
    lateinit var sw: EditText
    lateinit var sh: EditText
    lateinit var sl: EditText
    lateinit var sq: EditText
    lateinit var scft: TextView
    lateinit var mmtcft: TextView
    lateinit var rate: EditText
    lateinit var amnt: TextView
    lateinit var txtckri: TextView
    lateinit var cdimmw: EditText
    lateinit var cdimmh: EditText
    lateinit var cdimml: EditText
    lateinit var cdimmq: EditText
    lateinit var cdate: EditText
    lateinit var name: AutoCompleteTextView
    lateinit var cno: EditText
    lateinit var vno: AutoCompleteTextView
    var msharedpref: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wooden__drum__mm)
        db = DBHandler(this)
        msharedpref=this.getSharedPreferences("Drum_MM", Context.MODE_PRIVATE)
        txtckri = findViewById(R.id.txtmmckri)
        val lckri = findViewById<LinearLayout>(R.id.llmmckri)
        val lchdi = findViewById<LinearLayout>(R.id.llmmcdi)
        ckrmm = findViewById(R.id.etmmckr)
        ckrwidth = findViewById(R.id.etmmckrwidth)
        ckrqty = findViewById(R.id.etmmckrqty)
        ckrcft = findViewById(R.id.txtmmckrcft)
        ckrimm = findViewById(R.id.etmmckri)
        ckriwidth = findViewById(R.id.etmmckriwidth)
        ckriqty = findViewById(R.id.etmmckriqty)
        ckricft = findViewById(R.id.txtmmckricft)
        mcdmm = findViewById(R.id.etmmmcd)
        mcdwidth = findViewById(R.id.etmmmcdwidth)
        mcdqty = findViewById(R.id.etmmmcdqty)
        mcft = findViewById(R.id.txtmmmcdcft)
        sw = findViewById(R.id.etmmswidth)
        sh = findViewById(R.id.etmmsheight)
        sl = findViewById(R.id.etmmslength)
        sq = findViewById(R.id.etmmsqty)
        scft = findViewById(R.id.txtmmscft)
        cdimmw = findViewById(R.id.etcmmcwidth)
        cdimmh = findViewById(R.id.etsmmcheight)
        cdimml = findViewById(R.id.etsmmclenght)
        cdimmq = findViewById(R.id.etsmmcqty)
        mmtcft = findViewById(R.id.txtmmtcft)
        val getcft = findViewById<Button>(R.id.btnmmgcft)
        rate = findViewById(R.id.etmmrate)
        val btnrate = findViewById<Button>(R.id.btnmmrate)
        val vi = findViewById<TextView>(R.id.txtview)
        amnt = findViewById(R.id.txttamnt)

        var ca = 0
        ckrmm.setText(msharedpref!!.getString("ckrmm",""))
        ckrwidth.setText(msharedpref!!.getString("ckrwidth",""))
        ckrqty.setText(msharedpref!!.getString("ckrqty",""))
        ckrimm.setText(msharedpref!!.getString("ckrimm",""))
        ckriwidth.setText(msharedpref!!.getString("ckriwidth",""))
        ckriqty.setText(msharedpref!!.getString("ckriqty",""))
        cdimmw.setText(msharedpref!!.getString("cdimmw",""))
        cdimmh.setText(msharedpref!!.getString("cdimmh",""))
        cdimml.setText(msharedpref!!.getString("cdimml",""))
        cdimmq.setText(msharedpref!!.getString("cdimmq",""))
        mcdmm.setText(msharedpref!!.getString("mcdmm",""))
        mcdwidth.setText(msharedpref!!.getString("mcdwidth",""))
        mcdqty.setText(msharedpref!!.getString("mcdqty",""))
        sq.setText(msharedpref!!.getString("sq",""))
        sw.setText(msharedpref!!.getString("sw",""))
        sh.setText(msharedpref!!.getString("sh",""))
        sl.setText(msharedpref!!.getString("sl",""))
        rate.setText(msharedpref!!.getString("rate",""))
        ckrcft.text=msharedpref!!.getString("ckrcft","0.0")
        ckricft.text=msharedpref!!.getString("ckricft","0.0")
        mcft.text=msharedpref!!.getString("mcdcft","0.0")
        scft.text=msharedpref!!.getString("scft","0.0")
        mmtcft.text=msharedpref!!.getString("tcft","0.0")
        rate.setText(msharedpref!!.getString("rate","0"))
        amnt.text=msharedpref!!.getString("amnt","0")
        mtot= msharedpref!!.getString("tcft","0.0").toString()
        val s=msharedpref!!.getString("CS","")
        when(s) {
            "1" ->
            {
                no=0
            }
            "2"->
            {
                no=1
            }
            "3"->
            {
                no=2
            }
        }
        val c=msharedpref!!.getString("c","Chakkri")
        when(c)
        {
            "Chakkri"->
            {
                ca=0
                txtckri.text="Chakkri"
                lckri.visibility = View.VISIBLE
                lchdi.visibility = View.GONE
                getcft.performClick()
                val e=msharedpref!!.edit()
                e.putString("cdimmw","")
                e.putString("cdimmh","")
                e.putString("cdimml","")
                e.putString("cdimmq","")
                e.putString("ckricft","0.0")
                e.apply()

            }
            "Chaddi"->
            {
                ca=1
                txtckri.text="Chaddi"
                lchdi.visibility = View.VISIBLE
                lckri.visibility = View.GONE
                getcft.performClick()
                val e=msharedpref!!.edit()
                e.putString("ckrimm","")
                e.putString("ckriwidth","")
                e.putString("ckriqty","")
                e.putString("ckricft","0.0")
                e.apply()
            }
        }
        btnviewall.setOnClickListener {
            hideSoftKeyboard()
            val intent = Intent(applicationContext, Drum_List::class.java)
            startActivity(intent)
        }
        etmmrate.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                btnrate.performClick()
                hideSoftKeyboard()
                true
            } else {
                false
            }
        }
        txtckri.setOnClickListener {
            var item = arrayOf("Chakkri", "Chaddi")
            val editor=msharedpref!!.edit()
            var c = 0
            if (txtckri.text == "Chakkri") {
                c = 0
                editor.putString("c","Chakkri")
            } else {
                c = 1
                editor.putString("c","Chaddi")
            }
            editor.apply()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose an option..")
            builder.setSingleChoiceItems(item, c) { dialog: DialogInterface?, which: Int ->
                if (item[which] == "Chakkri") {
                    txtckri.text = "Chakkri"
                    val editor=msharedpref!!.edit()
                    editor.putString("c","Chakkri")
                    editor.apply()
                    Toast.makeText(this, "Chakkri", Toast.LENGTH_LONG).show()
                    lckri.visibility = View.VISIBLE
                    lchdi.visibility = View.GONE
                    ckricft.text = getckricft(
                        ckrimm.text.toString(),
                        ckriwidth.text.toString(),
                        ckriqty.text.toString()
                    ).toString()
                    getcft.performClick()
                    var e=msharedpref!!.edit()
                    e.putString("ckricft",ckricft.text.toString())
                    e.apply()
                    ckrimm.requestFocus()
                    ckrimm.setText("")
                    ckriwidth.setText("")
                    ckriqty.setText("")
                    cdimmw.setText("0")
                    cdimmh.setText("0")
                    cdimml.setText("0")
                    cdimmq.setText("0")
                } else {
                    txtckri.text = "Chaddi"
                    val editor=msharedpref!!.edit()
                    editor.putString("c","Chaddi")
                    editor.apply()
                    Toast.makeText(this, "Chaddi", Toast.LENGTH_LONG).show()
                    lchdi.visibility = View.VISIBLE
                    lckri.visibility = View.GONE
                    ckricft.text = getchaddicft(
                        cdimmw.text.toString(),
                        cdimmh.text.toString(),
                        cdimml.text.toString(),
                        cdimmq.text.toString()
                    ).toString()
                    getcft.performClick()
                    val e=msharedpref!!.edit()
                    e.putString("ckricft",ckricft.text.toString())
                    e.apply()
                    cdimmw.requestFocus()
                    cdimmw.setText("")
                    cdimmh.setText("")
                    cdimml.setText("")
                    cdimmq.setText("")
                    ckrimm.setText("0")
                    ckriwidth.setText("0")
                    ckriqty.setText("0")
                }
                dialog!!.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
        /*ckrmm.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckrmm.text.toString().trim().isEmpty()) {
                    ckrmm.setText("0")
                }
                var w=ckrmm.text.toString().toInt()

                ckrcft.text =
                    getckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
                getcft.performClick()
            }
        }
        ckrwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckrwidth.text.toString().trim().isEmpty()) {
                    ckrwidth.setText("0")
                }
                ckrcft.text =
                    getckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
                getcft.performClick()
            }
        }
        ckrqty.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckrqty.text.toString().trim().isEmpty()) {
                    ckrqty.setText("0")
                }
                ckrcft.text =
                    getckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
                getcft.performClick()
            }
        }
        ckrimm.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckrimm.text.toString().trim().isEmpty()) {
                    ckrimm.setText("0")
                }
                ckricft.text =
                    getckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
               getcft.performClick()
            }
        }
        ckriwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckriwidth.text.toString().trim().isEmpty()) {
                    ckriwidth.setText("0")
                }
                ckricft.text =
                    getckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
                getcft.performClick()
            }
        }
        ckriqty.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckriqty.text.toString().trim().isEmpty()) {
                    ckriqty.setText("0")
                }
                ckricft.text =
                    getckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
                getcft.performClick()
            }
        }
        cdimmw.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (cdimmw.text.toString().trim().isEmpty()) {
                    cdimmw.setText("0")
                }
                ckricft.text = getchaddicft(
                    cdimmw.text.toString(),
                    cdimmh.text.toString(),
                    cdimml.text.toString(),
                    cdimmq.text.toString()
                ).toString()
               getcft.performClick()
            }
        }
        cdimmh.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (cdimmh.text.toString().trim().isEmpty()) {
                    cdimmh.setText("0")
                }
                ckricft.text = getchaddicft(
                    cdimmw.text.toString(),
                    cdimmh.text.toString(),
                    cdimml.text.toString(),
                    cdimmq.text.toString()
                ).toString()
                getcft.performClick()
            }
        }
        cdimml.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (cdimml.text.toString().trim().isEmpty()) {
                    cdimml.setText("0")
                }
                ckricft.text = getchaddicft(
                    cdimmw.text.toString(),
                    cdimmh.text.toString(),
                    cdimml.text.toString(),
                    cdimmq.text.toString()
                ).toString()
               getcft.performClick()
            }
        }
        cdimmq.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (cdimmq.text.toString().trim().isEmpty()) {
                    cdimmq.setText("0")
                }
                ckricft.text = getchaddicft(
                    cdimmw.text.toString(),
                    cdimmh.text.toString(),
                    cdimml.text.toString(),
                    cdimmq.text.toString()
                ).toString()
               getcft.performClick()
            }
        }
        mcdmm.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (mcdmm.text.toString().trim().isEmpty()) {
                    mcdmm.setText("0")
                }
                mcft.text =
                    getmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
               getcft.performClick()
            }
        }
        mcdwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (mcdwidth.text.toString().trim().isEmpty()) {
                    mcdwidth.setText("0")
                }
                mcft.text =
                    getmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
                getcft.performClick()
            }
        }
        mcdqty.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (mcdqty.text.toString().trim().isEmpty()) {
                    mcdqty.setText("0")
                }
                mcft.text =
                    getmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
               getcft.performClick()
            }
        }
        sq.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (sq.text.toString().trim().isEmpty()) {
                    sq.setText("0")
                }
                scft.text = getsigmetcft(
                    sw.text.toString(),
                    sh.text.toString(),
                    sl.text.toString(),
                    sq.text.toString()
                ).toString()
               getcft.performClick()
            }
        }
        sw.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (sw.text.toString().trim().isEmpty()) {
                    sw.setText("0")
                }
                scft.text = getsigmetcft(
                    sw.text.toString(),
                    sh.text.toString(),
                    sl.text.toString(),
                    sq.text.toString()
                ).toString()
               getcft.performClick()
            }
        }
        sh.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (sh.text.toString().trim().isEmpty()) {
                    sh.setText("0")
                }
                scft.text = getsigmetcft(
                    sw.text.toString(),
                    sh.text.toString(),
                    sl.text.toString(),
                    sq.text.toString()
                ).toString()
                getcft.performClick()
            }
        }
        sl.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (sl.text.toString().trim().isEmpty()) {
                    sl.setText("0")
                }
                scft.text = getsigmetcft(
                    sw.text.toString(),
                    sh.text.toString(),
                    sl.text.toString(),
                    sq.text.toString()
                ).toString()
               getcft.performClick()
            }
        }
        rate.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (rate.text.toString().trim().isNotEmpty()) {
                    vi.text = "Total Amount: "
                    amnt.text = gettotalamnt(mtot, rate.text.toString()).toString()
                    btnrate.performClick()
                } else {
                    rate.setText("0")
                }
            }
        }*/
        ckrmm.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckrmm.text.toString().trim().isEmpty() || ckrmm.text.toString()==".") {
                    ckrmm.setText("0")
                }
                var w=ckrmm.text.toString().toDouble()
                var v=w.roundToInt()
                ckrmm.setText("$v")
                ckrcft.text =
                    getckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
                getcft.performClick()
                val editor=msharedpref!!.edit()
                if (ckrmm.text.toString().toInt()==0)
                {
                    editor.putString("ckrmm","")
                }
                else
                {
                    editor.putString("ckrmm",ckrmm.text.toString())}
                editor.apply()
            }
        }
        ckrwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                var i=""
                if (ckrwidth.text.toString().trim().isEmpty() || ckrwidth.text.toString()==".") {
                    ckrwidth.setText("0")

                }
                ckrcft.text =
                    getckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
                getcft.performClick()
                val editor=msharedpref!!.edit()
                if (ckrwidth.text.toString().toDouble()==0.0)
                {
                    editor.putString("ckrwidth","")
                }
                else
                {
                    editor.putString("ckrwidth",ckrwidth.text.toString())
                }
                editor.apply()
            }
        }
        ckrqty.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckrqty.text.toString().trim().isEmpty() || ckrqty.text.toString()==".") {
                    ckrqty.setText("0")
                }
                var w=ckrqty.text.toString().toDouble()
                var v=w.roundToInt()
                ckrqty.setText("$v")
                ckrcft.text =
                    getckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (ckrqty.text.toString().toInt()==0)
                {
                    editor.putString("ckrqty","")
                }
                else
                {
                    editor.putString("ckrqty",ckrqty.text.toString())
                }
                editor.apply()
            }
        }
        ckrimm.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckrimm.text.toString().trim().isEmpty() || ckrimm.text.toString()==".") {
                    ckrimm.setText("0")
                }
                var w=ckrimm.text.toString().toDouble()
                var v=w.roundToInt()
                ckrimm.setText("$v")
                ckricft.text =
                    getckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (ckrimm.text.toString().toInt()==0)
                {
                    editor.putString("ckrimm","")
                }
                else
                {
                    editor.putString("ckrimm",ckrimm.text.toString())
                }
                editor.apply()
            }
        }
        ckriwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckriwidth.text.toString().trim().isEmpty() || ckriwidth.text.toString()==".") {
                    ckriwidth.setText("0")
                }
                ckricft.text =
                    getckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (ckriwidth.text.toString().toDouble()==0.0)
                {
                    editor.putString("ckriwidth","")
                }
                else
                {
                    editor.putString("ckriwidth",ckriwidth.text.toString())
                }
                editor.apply()
            }
        }
        ckriqty.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (ckriqty.text.toString().trim().isEmpty() || ckriqty.text.toString()==".") {
                    ckriqty.setText("0")
                }
                var w=ckriqty.text.toString().toDouble()
                var v=w.roundToInt()
                ckriqty.setText("$v")
                ckricft.text =
                    getckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (ckriqty.text.toString().toInt()==0)
                {
                    editor.putString("ckriqty","")
                }
                else
                {
                    editor.putString("ckriqty",ckriqty.text.toString())
                }
                editor.apply()
            }
        }
        cdimmw.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (cdimmw.text.toString().trim().isEmpty() || cdimmw.text.toString()==".") {
                    cdimmw.setText("0")
                }
                var w=cdimmw.text.toString().toDouble()
                var v=w.roundToInt()
                cdimmw.setText("$v")
                ckricft.text = getchaddicft(
                    cdimmw.text.toString(),
                    cdimmh.text.toString(),
                    cdimml.text.toString(),
                    cdimmq.text.toString()
                ).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (cdimmw.text.toString().toInt()==0)
                {
                    editor.putString("cdimmw","")
                }
                else
                {
                    editor.putString("cdimmw",cdimmw.text.toString())
                }
                editor.apply()
            }
        }
        cdimmh.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (cdimmh.text.toString().trim().isEmpty() || cdimmh.text.toString()==".") {
                    cdimmh.setText("0")
                }
                ckricft.text = getchaddicft(
                    cdimmw.text.toString(),
                    cdimmh.text.toString(),
                    cdimml.text.toString(),
                    cdimmq.text.toString()
                ).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (cdimmh.text.toString().toDouble()==0.0)
                {
                    editor.putString("cdimmh","")
                }
                else
                {
                    editor.putString("cdimmh",cdimmh.text.toString())
                }
                editor.apply()
            }
        }
        cdimml.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (cdimml.text.toString().trim().isEmpty() || cdimml.text.toString()==".") {
                    cdimml.setText("0")
                }
                ckricft.text = getchaddicft(
                    cdimmw.text.toString(),
                    cdimmh.text.toString(),
                    cdimml.text.toString(),
                    cdimmq.text.toString()
                ).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (cdimml.text.toString().toDouble()==0.0)
                {
                    editor.putString("cdimml","")
                }
                else
                {
                    editor.putString("cdimml",cdimml.text.toString())
                }
                editor.apply()
            }
        }
        cdimmq.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (cdimmq.text.toString().trim().isEmpty() || cdimmq.text.toString()==".") {
                    cdimmq.setText("0")
                }
                var w=cdimmq.text.toString().toDouble()
                var v=w.roundToInt()
                cdimmq.setText("$v")
                ckricft.text = getchaddicft(
                    cdimmw.text.toString(),
                    cdimmh.text.toString(),
                    cdimml.text.toString(),
                    cdimmq.text.toString()
                ).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (cdimmq.text.toString().toInt()==0)
                {
                    editor.putString("cdimmq","")
                }
                else
                {
                    editor.putString("cdimmq",cdimmq.text.toString())
                }
                editor.apply()
            }
        }
        mcdmm.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (mcdmm.text.toString().trim().isEmpty() || mcdmm.text.toString()==".") {
                    mcdmm.setText("0")
                }
                var w=mcdmm.text.toString().toDouble()
                var v=w.roundToInt()
                mcdmm.setText("$v")
                mcft.text =
                    getmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (mcdmm.text.toString().toInt()==0)
                {
                    editor.putString("mcdmm","")
                }
                else
                {
                    editor.putString("mcdmm",mcdmm.text.toString())
                }
                editor.apply()
            }
        }
        mcdwidth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (mcdwidth.text.toString().trim().isEmpty() || mcdwidth.text.toString()==".") {
                    mcdwidth.setText("0")
                }
                mcft.text =
                    getmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (mcdwidth.text.toString().toDouble()==0.0)
                {
                    editor.putString("mcdwidth","")
                }
                else
                {
                    editor.putString("mcdwidth",mcdwidth.text.toString())
                }
                editor.apply()
            }
        }
        mcdqty.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (mcdqty.text.toString().trim().isEmpty() || mcdqty.text.toString()==".") {
                    mcdqty.setText("0")
                }
                var w=mcdqty.text.toString().toDouble()
                var v=w.roundToInt()
                mcdqty.setText("$v")
                mcft.text =
                    getmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (mcdqty.text.toString().toInt()==0)
                {
                    editor.putString("mcdqty","")
                }
                else
                {
                    editor.putString("mcdqty",mcdqty.text.toString())
                }
                editor.apply()
            }
        }
        sq.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (sq.text.toString().trim().isEmpty() || sq.text.toString()==".") {
                    sq.setText("0")
                }
                var w=sq.text.toString().toDouble()
                var v=w.roundToInt()
                sq.setText("$v")
                scft.text = getsigmetcft(
                    sw.text.toString(),
                    sh.text.toString(),
                    sl.text.toString(),
                    sq.text.toString()
                ).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (sq.text.toString().toInt()==0)
                {
                    editor.putString("sq","")
                }
                else
                {
                    editor.putString("sq",sq.text.toString())
                }
                editor.apply()
            }
        }
        sw.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (sw.text.toString().trim().isEmpty() || sw.text.toString()==".") {
                    sw.setText("0")
                }
                var w=sw.text.toString().toDouble()
                var v=w.roundToInt()
                sw.setText("$v")
                scft.text = getsigmetcft(
                    sw.text.toString(),
                    sh.text.toString(),
                    sl.text.toString(),
                    sq.text.toString()
                ).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (sw.text.toString().toInt()==0)
                {
                    editor.putString("sw","")
                }
                else
                {
                    editor.putString("sw",sw.text.toString())
                }
                editor.apply()
            }
        }
        sh.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (sh.text.toString().trim().isEmpty() || sh.text.toString()==".") {
                    sh.setText("0")
                }
                scft.text = getsigmetcft(
                    sw.text.toString(),
                    sh.text.toString(),
                    sl.text.toString(),
                    sq.text.toString()
                ).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
                val editor=msharedpref!!.edit()
                if (sh.text.toString().toDouble()==0.0)
                {
                    editor.putString("sh","")
                }
                else
                {
                    editor.putString("sh",sh.text.toString())
                }
                editor.apply()
            }
        }
        sl.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (sl.text.toString().trim().isEmpty() || sl.text.toString()==".") {
                    sl.setText("0")
                }
                scft.text = getsigmetcft(
                    sw.text.toString(),
                    sh.text.toString(),
                    sl.text.toString(),
                    sq.text.toString()
                ).toString()
                mmtcft.text = "Total CFT: " + gettotalcft(ckrcft.text.toString(), ckricft.text.toString(), mcft.text.toString(), scft.text.toString()).toString()
                val editor=msharedpref!!.edit()
                if (sl.text.toString().toDouble()==0.0)
                {
                    editor.putString("sl","")
                }
                else
                {
                    editor.putString("sl",sl.text.toString())
                }
                editor.apply()
            }
        }
        rate.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (rate.text.toString().trim().isNotEmpty()) {
                    vi.text = "Total Amount: "
                    amnt.text = gettotalamnt(mtot, rate.text.toString()).toString()
                    btnrate.performClick()
                } else {
                    rate.setText("0")
                }
                val editor=msharedpref!!.edit()
                if (rate.text.toString().toInt()==0)
                {
                    editor.putString("rate","")
                }
                else
                {
                    editor.putString("rate",rate.text.toString())
                }
                editor.apply()
            }
        }
        getcft.setOnClickListener {
            ckrcft.text = getckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
            if (txtckri.text == "Chakkri") {
                ckricft.text = getckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
            } else {
                ckricft.text = getchaddicft(cdimmw.text.toString(), cdimmh.text.toString(), cdimml.text.toString(), cdimmq.text.toString()).toString()
            }
            mcft.text = getmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
            scft.text = getsigmetcft(sw.text.toString(), sh.text.toString(), sl.text.toString(), sq.text.toString()).toString()
            mmtcft.text = "Total CFT: " + gettotalcft(ckrcft.text.toString(), ckricft.text.toString(), mcft.text.toString(), scft.text.toString()).toString()
            btnrate.performClick()
        }
        btnrate.setOnClickListener {
            vi.text = "Total Amount: "
            amnt.text = gettotalamnt(mtot, rate.text.toString()).toString()

            /**//*shareDrumDetials(mckr.toString(),mckri.toString(),mmcd.toString(),msig.toString(),mtot.toString(),mrate.toString(),mamnt.toString())*/
            m = """Chakkar:  ${mckr}
               |$mckri
               | MCD    :  $mmcd
               | Sigmet :  $msig
               | Total CFT:$mtot
               | Rate   :  $mrate
               | Total Amount:$mamnt
        """.trimMargin()
            drum.add(m)
        }
        btnsv.setOnClickListener {
            getcft.performClick()
            btnrate.performClick()
            hideSoftKeyboard()
            if (tcft==0.0)
            {
                Toast.makeText(this,"Calculate CFT!!", Toast.LENGTH_SHORT).show()
            }
            else {
                showsavedialog()
            }
        }

    }

    private fun autosuggestname(name:AutoCompleteTextView) {
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

    private fun gettotalamnt(tot: String, rate: String): Int {
        val a: Int
        val rt: Int = if (rate.trim().isEmpty()) {
            0
        } else {
            rate.toInt()
        }


        if (tot.trim().isNotEmpty()) {
            val ab = mtot.toDouble() * rt.toDouble()
            a = ab.roundToInt()
            mrate = rate
            mamnt = a.toString()
            val editor=msharedpref!!.edit()
            editor.putString("rate", rate)
            editor.putString("amnt",amnt.text.toString())
            editor.apply()
        } else {
            a = 0
            Toast.makeText(this, "Calculate CFT!!", Toast.LENGTH_SHORT).show()
        }
        return a
    }

    private fun getckrcft(mm: String, width: String, qty: String): Double {
        val ckrcft: Double
        if (mm.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            ckrcft = String.format("%.2f", 0.00).toDouble()
        } else {
            val ckr =
                ((mm.toDouble() / 25.4) * (mm.toDouble() / 25.4) * (width.toDouble() + width.toDouble()) * qty.toDouble() / 2200)
            ckrcft = String.format("%.2f", ckr).toDouble()
            val w = width.toDouble() + width.toDouble()
            val editor=msharedpref!!.edit()
            editor.putString("ckrcft","$ckrcft")
            editor.apply()
            mckr = "$mm*$w*$qty = $ckrcft"
        }
        return ckrcft
    }

    private fun getckricft(mm: String, width: String, qty: String): Double {
        var ckricft = 0.00
        if (mm.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            ckricft = String.format("%.2f", 0.00).toDouble()
        } else {
            val cft = ((mm.toDouble() / 25.4 * mm.toDouble() / 25.4) * width.toDouble() * qty.toDouble() / 2200)
            ckricft = String.format("%.2f", cft).toDouble()
            val editor=msharedpref!!.edit()
            editor.putString("ckricft","$ckricft")
            editor.apply()
            mckri = if (ckricft == 0.0) {
                ""
            } else {
                "Chakkri : $mm*$width*$qty = $ckricft"
            }
        }
        return ckricft

    }

    private fun getchaddicft(w: String, h: String, l: String, q: String): Double {
        var cdicft = 0.00
        if (w.trim().isEmpty() || h.trim().isEmpty() || l.trim().isEmpty() || q.trim().isEmpty()) {
            cdicft = String.format("%.2f", 0.00).toDouble()
        } else {
            val cft = w.toDouble() * h.toDouble() * l.toDouble() * q.toDouble() / 144
            cdicft = String.format("%.2f", cft).toDouble()
            val editor=msharedpref!!.edit()
            editor.putString("ckricft","$cdicft")
            editor.apply()
            mckri = if (cdicft == 0.0) {
                ""
            } else {
                "Chaddi  : $w*$h*$l*$q = $cdicft"
            }
        }
        return cdicft
    }

    private fun getmcdcft(mm: String, width: String, qty: String): Double {
        var mcdcft = 0.00
        if (mm.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            mcdcft = String.format("%.2f", 0.00).toDouble()
        } else {
            val cft = ((mm.toDouble() / 25.4) * (mm.toDouble() / 25.4) * width.toDouble() * qty.toDouble() / 2200)
            mcdcft = String.format("%.2f", cft).toDouble()
            val editor=msharedpref!!.edit()
            editor.putString("mcdcft","$mcdcft")
            editor.apply()
            mmcd = if (mcdcft == 0.0) {
                ""
            } else {
                "$mm*$width*$qty = $mcdcft"
            }
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
            editor.putString("scft","$sigcft")
            editor.apply()
            msig = if (sigcft == 0.0) {
                ""
            } else {
                "$w*$h*$l*$q = $sigcft"
            }
        }
        return sigcft
    }

    private fun gettotalcft(ckr: String, ckri: String, mcd: String, sig: String): Double {
        var totalcft = 0.00
        totalcft = String.format("%.2f", ckr.toDouble() + ckri.toDouble() + mcd.toDouble() + sig.toDouble()).toDouble()
        val editor=msharedpref!!.edit()
        editor.putString("tcft","Total CFT: $totalcft")
        editor.apply()
        mtot = "$totalcft"
        tcft = totalcft
        return totalcft
    }

    private fun getClear() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are You Sure  want to clear entry??")
        builder.setIcon(R.drawable.ic_clear_black)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                etmmckr.requestFocus()
                etmmckr.setText("")
                etmmckrwidth.setText("")
                etmmckrqty.setText("")
                txtmmckrcft.setText("0.0")
                etmmckri.setText("")
                etmmckriwidth.setText("")
                etmmckriqty.setText("")
                etcmmcwidth.setText("0")
                etsmmcheight.setText("0")
                etsmmclenght.setText("0")
                etsmmcqty.setText("0")
                txtmmckricft.setText("0.0")
                etmmmcd.setText("")
                etmmmcdwidth.setText("")
                etmmmcdqty.setText("")
                txtmmmcdcft.setText("0.0")
                etmmswidth.setText("")
                etmmsheight.setText("")
                etmmslength.setText("")
                etmmsqty.setText("")
                txtmmscft.setText("0.0")
                txtmmtcft.setText("Total CFT: 0.0")
                etmmrate.setText("")
                txttamnt.setText("")
                txtview.setText("")
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

    private fun shareDrumDetials(
        ckr: String,
        ckri: String,
        mcd: String,
        sig: String,
        tot: String,
        rate: String,
        amnt: String
    ) {
        val msg = ArrayList<String>()
        var tmsg = ""
        val c = "Chakkar: $ckr"
        msg.add(c)
        if (ckri.isNotEmpty()) {
            /* var cki="Chakkri : $ckri"*/
            msg.add(ckri)
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
            startActivity(Intent.createChooser(sendIntent,resources.getText(R.string.sent_to)))
        } else {

            Toast.makeText(this, "Calculate CFT!!!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater
        menuInflater.inflate(R.menu.mymenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                btnmmgcft.performClick()
                shareDrumDetials(
                    mckr,
                    mckri,
                    mmcd,
                    msig,
                    mtot,
                    mrate,
                    mamnt
                )
            }
            R.id.clr -> {
                getClear()
            }
            R.id.cconvert -> {
                val intent = Intent(this, Converter_INCHMM::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
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
                        /*d="$myear/$mounthzero/$datezero"*/
                        dateday = datezero.toInt()
                        datemounth = mounthzero.toInt()
                        /*Toast.makeText(this,""+datemounth,Toast.LENGTH_LONG).show()*/
                        dateyear = myear
                    },
                    y,
                    dm,
                    d.toInt()
                )
            dialog.show()
        }
    }
    fun savedata() {
        if (ckrmm.text.trim().isEmpty()) {
            ckrmm.setText("0")
        }
        if (ckrwidth.text.trim().isEmpty()) {
            ckrwidth.setText("0")
        }
        if (ckrqty.text.trim().isEmpty()) {
            ckrqty.setText("0")
        }
        if (ckrimm.text.trim().isEmpty()) {
            ckrimm.setText("0")
        }
        if (ckriwidth.text.trim().isEmpty()) {
            ckriwidth.setText("0")
        }
        if (ckriqty.text.trim().isEmpty()) {
            ckriqty.setText("0")
        }
        if (mcdmm.text.trim().isEmpty()) {
            mcdmm.setText("0")
        }
        if (mcdwidth.text.trim().isEmpty()) {
            mcdwidth.setText("0")
        }
        if (mcdqty.text.trim().isEmpty()) {
            mcdqty.setText("0")
        }
        if (sw.text.trim().isEmpty()) {
            sw.setText("0")
        }
        if (sh.text.trim().isEmpty()) {
            sh.setText("0")
        }
        if (sq.text.trim().isEmpty()) {
            sq.setText("0")
        }
        if (sl.text.trim().isEmpty()) {
            sl.setText("0")
        }
        if (rate.text.trim().isEmpty()) {
            rate.setText("0")
            amnt.setText("0")
        }


        var context = this
        var db = DBHandler(context)
        if (tcft == 0.0) {
            Toast.makeText(context, "Calculate CFT!", Toast.LENGTH_LONG).show()
        } else {
            if (cno.text.trim().isEmpty()) {
                cno.setText("0")
            }
            if (txtckri.text == "Chakkri") {
                var drum = Drum(
                    ckrmm.text.toString().toInt(),
                    ckrwidth.text.toString().toDouble(),
                    ckrqty.text.toString().toInt(),
                    ckrcft.text.toString().toDouble(),
                    ckrimm.text.toString().toInt(),
                    ckriwidth.text.toString().toDouble(),
                    ckriqty.text.toString().toDouble(),
                    ckricft.text.toString().toDouble(),
                    0,
                    mcdmm.text.toString().toInt(),
                    mcdwidth.text.toString().toDouble(),
                    mcdqty.text.toString().toInt(),
                    mcft.text.toString().toDouble(),
                    sw.text.toString().toInt(),
                    sh.text.toString().toDouble(),
                    sl.text.toString().toDouble(),
                    sq.text.toString().toInt(),
                    scft.text.toString().toDouble(),
                    tcft.toDouble(),
                    rate.text.toString().toInt(),
                    amnt.text.toString().toInt(),
                    dateday,
                    datemounth,
                    dateyear,
                    cdate.text.toString(),
                    mminch,
                    name.text.toString(),
                    cno.text.toString().toInt(),
                    vno.text.toString()
                )
                db.insertdrum(drum)
            } else {
                var drum = Drum(
                    ckrmm.text.toString().toInt(),
                    ckrwidth.text.toString().toDouble(),
                    ckrqty.text.toString().toInt(),
                    ckrcft.text.toString().toDouble(),
                    cdimmw.text.toString().toInt(),
                    cdimmh.text.toString().toDouble(),
                    cdimml.text.toString().toDouble(),
                    ckricft.text.toString().toDouble(),
                    cdimmq.text.toString().toInt(),
                    mcdmm.text.toString().toInt(),
                    mcdwidth.text.toString().toDouble(),
                    mcdqty.text.toString().toInt(),
                    mcft.text.toString().toDouble(),
                    sw.text.toString().toInt(),
                    sh.text.toString().toDouble(),
                    sl.text.toString().toDouble(),
                    sq.text.toString().toInt(),
                    scft.text.toString().toDouble(),
                    tcft.toDouble(),
                    rate.text.toString().toInt(),
                    amnt.text.toString().toInt(),
                    dateday,
                    datemounth,
                    dateyear,
                    cdate.text.toString(),
                    mminch,
                    name.text.toString(),
                    cno.text.toString().toInt(),
                    vno.text.toString()
                )
                db.insertdrum(drum)
            }
        }
    }

    fun hideSoftKeyboard()
    {
        val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
    }

    fun showsavedialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val view: View = inflater.inflate(R.layout.activity_date_picker, null)
        cdate = view.etddate
        name = view.etdname
        autosuggestname(name)
        cno = view.etcno
        vno = view.etvno
        autosuggestvehivehicleno(vno)
        showdatepickerdialog(cdate)
        builder.setView(view)
            .setTitle("Save Entries")
            .setPositiveButton("SAVE") { _, _ ->
                savedata()
                hideSoftKeyboard()
            }
            .setNegativeButton("NO"
            ) { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }
    fun autosuggestvehivehicleno(name: AutoCompleteTextView) {
        var n=db.getvno(this)
        var namelist=ArrayList<String>()
        //0..n.size-1
        for (i in 0..n.size-1)
        {
            n.get(i).vno?.let { namelist.add(it) }
        }
        var b=namelist.distinct()
        var adapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,b)
        name.setAdapter(adapter)
    }

}
