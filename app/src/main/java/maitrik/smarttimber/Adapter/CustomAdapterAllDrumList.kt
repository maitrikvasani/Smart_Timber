package maitrik.smarttimber.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_drum__list.*
import kotlinx.android.synthetic.main.drum_update_layout.view.*
import kotlinx.android.synthetic.main.drumlist_layout.view.*
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import maitrik.smarttimber.Model.Drum

import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

var mckr = ""
var mckri = ""
var mmcd = ""
var msig = ""
var mtot = ""
var mrate=""
var mamnt=""
var mcdi=""
var drumlist= arrayOf("")
var p= ArrayList<Int>()
var p1=0
var m=""
var tcft=0.0
var total=0.0
var totalamnt=0
var inchmm=""
var dateday = 0
var datemounth = 0
var dateyear = 0
val inch="INCH"
val mm="MM"
var cno=0
var vno=""

lateinit var txtckri:TextView
class CustomAdapterAllDrumList(
    internal var activity: Activity,
    internal var lstdrum: ArrayList<Drum>,
    internal var tvtcft:TextView,
    internal var filter:Button,
    internal var lv:ListView,
    internal var tr:TextView,
    internal var tc:TextView,
    internal var ttcft:TextView,
    internal var tamnt:TextView,
    internal var im:InputMethodManager
) : BaseAdapter() {

    internal var inflater: LayoutInflater

    init {
        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
            view = inflater.inflate(R.layout.drumlist_layout, null)
        totalcftfun()

        var d="Date: ${lstdrum[position].dateday}/${lstdrum[position].datemounth}/${lstdrum[position].dateyear}"
        var date="Date: ${lstdrum[position].cdate}"
       /* var date="Date: ${lstdrum[position].dateday}/${lstdrum[position].datemounth}/${lstdrum[position].dateyear}"*/
        var name="${lstdrum[position].name}"
        view.drumlistname.setText("${lstdrum[position].name}")
        view.drumlistdate.setText("${lstdrum[position].cdate}")
        /*view.drumlistdate.setText("Date: ${lstdrum[position].dateday}/${lstdrum[position].datemounth}/${lstdrum[position].dateyear}")*/
        view.drumlistcno.setText("${lstdrum[position].cno}")
        if (lstdrum[position].vno=="STOCK")
        {
            view.drumlistvno.setTextColor(Color.RED)
        }
            view.drumlistvno.setText("${lstdrum[position].vno}")

        var cw:Double=lstdrum[position].cw.toDouble()+lstdrum[position].cw.toDouble()
        var ckr=""
        var ckri=""
        var mcd=""
        var sig=""
        var totalcft=""
            if (lstdrum[position].cmm == 0) {
                view.drumlistckr.visibility = View.GONE
                ckr=""
            } else {
                var c="${lstdrum[position].cmm}".padStart(4,' ')
                var cw="$cw".padStart(4,' ')
                var cq="${lstdrum[position].cq}".padStart(3,' ')
                    ckr =
                        lstdrum[position].cmm.toString() + "*" + cw + "*" + lstdrum[position].cq + "=" + lstdrum[position].ccft
                /*ckr=c +"*"+cw+"*"+cq+"="+lstdrum[position].ccft*/
                view.drumlistckr.text = ckr
            }
            if (lstdrum[position].ckmm == 0) {
                view.drumlistckri.visibility = View.GONE
                ckri=""
            } else {
                if (lstdrum[position].cdiq == 0) {
                   /* var c="${lstdrum[position].ckmm}".padStart(4,' ')
                    var cw="${lstdrum[position].ckw}".padStart(4,' ')
                    var cq="${lstdrum[position].ckq}".padStart(3,' ')*/
                    var cd= (lstdrum[position].ckq)?.toInt()
                     ckri = lstdrum[position].ckmm.toString() + "*" + lstdrum[position].ckw.toString() + "*" + cd + "=" + lstdrum[position].ckcft.toString()
                    /*ckri=c +"*"+cw+"*"+cq+"="+lstdrum[position].ckcft*/
                    view.drumlistckri.text = ckri
                } else {

                     ckri =
                        lstdrum[position].ckmm.toString() + "*" + lstdrum[position].ckw.toString() + "*" + lstdrum[position].ckq.toString() + "*" + lstdrum[position].cdiq.toString() + "=" + lstdrum[position].ckcft.toString()

                    view.drumlistckri.text = ckri
                }
            }
            if (lstdrum[position].mmm == 0) {
                view.drumlistmcd.visibility = View.GONE
                mcd=""
            } else {
                 mcd =
                    lstdrum[position].mmm.toString() + "*" + lstdrum[position].mw.toString() + "*" + lstdrum[position].mq.toString() + "=" + lstdrum[position].mcft.toString()
                view.drumlistmcd.text = mcd
            }
            if (lstdrum[position].sw == 0) {
                view.drumlistsigmet.visibility = View.GONE
                sig=""
            } else {
                 sig =
                    lstdrum[position].sw.toString() + "*" + lstdrum[position].sh.toString() + "*" + lstdrum[position].sl.toString() + "*" + lstdrum[position].sq.toString() + "=" + lstdrum[position].scft.toString()
                view.drumlistsigmet.text = sig
            }
            var tcft = ""
            if (lstdrum[position].rate == 0) {
                tcft ="CFT: "+lstdrum[position].tcft.toString()
                totalcft=tcft
            } else {
                tcft ="CFT: "+lstdrum[position].tcft + "*" + lstdrum[position].rate + "=" + lstdrum[position].amnt
                totalcft=tcft
            }
            view.drumlist.text = tcft
        /*}*/
        view.imgdlt.setOnClickListener {
            var alertDialog = AlertDialog.Builder(activity)
                .setTitle("Warning")
                .setMessage("Are you sure to Delete: $ckr ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    val id = lstdrum[position].id
                    val db = DBHandler(activity)
                    db.deleteDrum(id)
                    notifyDataSetChanged()
                    lstdrum.removeAt(position)
                    totalcftfun()
                    lv.setSelection(position-1)
                    Toast.makeText(activity, "Delete", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()


        }
        view.imgshare.setOnClickListener {

           shareDrumDetials(name,lstdrum[position].cno.toString(),date,ckr,ckri,mcd,sig,lstdrum[position].tcft.toString(),lstdrum[position].rate.toString(),lstdrum[position].amnt.toString(),lstdrum[position].vno.toString())
           /* Toast.makeText(activity,"Share!!!",Toast.LENGTH_SHORT).show()*/
        }
        view.imgedit.setOnClickListener {
           /* Toast.makeText(activity, "Edit", Toast.LENGTH_LONG).show()*/
            val inflater=LayoutInflater.from(activity)
            val view=inflater.inflate(R.layout.drum_update_layout,null)
            val builder=AlertDialog.Builder(activity)
            txtckri=view.findViewById(R.id.txtmmckri)
            var lckri= view.findViewById<LinearLayout>(R.id.llmmckri)
            var lchdi= view.findViewById<LinearLayout>(R.id.llmmcdi)
            var ckrmm = view.findViewById<EditText>(R.id.etmmckr)
            var ckrwidth = view.findViewById<EditText>(R.id.etmmckrwidth)
            var ckrqty =  view.findViewById<EditText>(R.id.etmmckrqty)
            var ckrcft =  view.findViewById<TextView>(R.id.txtmmckrcft)
            var ckrimm =  view.findViewById<EditText>(R.id.etmmckri)
            var ckriwidth = view. findViewById<EditText>(R.id.etmmckriwidth)
            var ckriqty =  view.findViewById<EditText>(R.id.etmmckriqty)
            var ckricft =  view.findViewById<TextView>(R.id.txtmmckricft)
            var mcdmm =  view.findViewById<EditText>(R.id.etmmmcd)
            var mcdwidth = view. findViewById<EditText>(R.id.etmmmcdwidth)
            var mcdqty =  view.findViewById<EditText>(R.id.etmmmcdqty)
            var mcft =  view.findViewById<TextView>(R.id.txtmmmcdcft)
            var sw = view. findViewById<EditText>(R.id.etmmswidth)
            var sh =  view.findViewById<EditText>(R.id.etmmsheight)
            var sl =  view.findViewById<EditText>(R.id.etmmslength)
            var sq =  view.findViewById<EditText>(R.id.etmmsqty)
            var scft = view. findViewById<TextView>(R.id.txtmmscft)
            var cdimmw=view.findViewById<EditText>(R.id.etcmmcwidth)
            var cdimmh=view.findViewById<EditText>(R.id.etsmmcheight)
            var cdimml=view.findViewById<EditText>(R.id.etsmmclenght)
            var cdimmq=view.findViewById<EditText>(R.id.etsmmcqty)
            /*var cdimmc=view.findViewById<TextView>(R.id.etcmmccft)*/
            var mmtcft = view.findViewById<TextView>(R.id.txtmmtcft)
            var getcft=view.findViewById<Button>(R.id.btnmmgcft)
            var rate=view.findViewById<EditText>(R.id.etmmrate)
            var btnrate=view.findViewById<Button>(R.id.btnmmrate)
            var amnt=view.findViewById<TextView>(R.id.txttamnt)
            var etdate=view.findViewById<EditText>(R.id.etdate)
            var name=view.findViewById<AutoCompleteTextView>(R.id.etname)
           var  cno=view.findViewById<EditText>(R.id.etcno)
           var  vno=view.findViewById<AutoCompleteTextView>(R.id.etvno)
            autosuggestname(name)
            autosuggestvehivehicleno(vno)
            if (lstdrum[position].inchmm=="MM"){
                inchmm= mm
            if (lstdrum[position].cdiq==0)
            {
                lckri.visibility=View.VISIBLE
                lchdi.visibility=View.GONE
                txtckri.setText("Chakkri")
            }
            else if(lstdrum[position].cdiq!==0)
            {
                lchdi.visibility=View.VISIBLE
                lckri.visibility=View.GONE
                txtckri.setText("Chaddi")

            }
            else
            {
                lckri.visibility=View.VISIBLE
                lchdi.visibility=View.GONE
                txtckri.setText("Chakkri")
            }
            txtckri.setOnClickListener {
                var item = arrayOf("Chakkri", "Chaddi")
                var c=0
                if (txtckri.text=="Chakkri")
                {
                    c=0
                }
                else
                {
                    c=1
                }
                var builder = AlertDialog.Builder(activity)
                builder.setTitle("Choose an option..")
                builder.setSingleChoiceItems(item,c) { dialog: DialogInterface?, which: Int ->
                    if (item[which] == "Chakkri") {
                        txtckri.text="Chakkri"
                        Toast.makeText(activity, "Chakkri", Toast.LENGTH_SHORT).show()
                        lckri.visibility= View.VISIBLE
                        lchdi.visibility= View.GONE
                        ckricft.text=getckricft(ckrimm.text.toString(),ckriwidth.text.toString(),ckriqty.text.toString()).toString()

                        mmtcft.text=gettotalcft(ckrcft.text.toString(),ckricft.text.toString(),mcft.text.toString(),scft.text.toString()).toString()
                        ckrimm.requestFocus()
                        cdimmw.setText("0")
                        cdimmh.setText("0")
                        cdimml.setText("0")
                        cdimmq.setText("0")
                    }
                    else
                    {
                        txtckri.text="Chaddi"
                        Toast.makeText(activity, "Chaddi", Toast.LENGTH_SHORT).show()
                        lchdi.visibility= View.VISIBLE
                        lckri.visibility= View.GONE
                        ckricft.text=getchaddicft(cdimmw.text.toString(),cdimmh.text.toString(),cdimml.text.toString(),cdimmq.text.toString()).toString()
                        mmtcft.text=gettotalcft(ckrcft.text.toString(),ckricft.text.toString(),mcft.text.toString(),scft.text.toString()).toString()
                        cdimmw.requestFocus()
                        ckrimm.setText("0")
                        ckriwidth.setText("0")
                        ckriqty.setText("0")
                    }
                    dialog!!.dismiss()
                }
                var dialog = builder.create()
                dialog.show()
            }
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
                        btnrate.performClick()
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
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (ckrimm.text.toString().toInt()==0)
                         {
                             editor.putString("ckrimm","")
                         }
                         else
                         {
                             editor.putString("ckrimm",ckrimm.text.toString())
                         }
                         editor.apply()*/
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
                        getcft.performClick()
                        btnrate.performClick()
                        /*val editor=msharedpref!!.edit()
                        if (ckriwidth.text.toString().toDouble()==0.0)
                        {
                            editor.putString("ckriwidth","")
                        }
                        else
                        {
                            editor.putString("ckriwidth",ckriwidth.text.toString())
                        }
                        editor.apply()*/
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
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (ckriqty.text.toString().toInt()==0)
                         {
                             editor.putString("ckriqty","")
                         }
                         else
                         {
                             editor.putString("ckriqty",ckriqty.text.toString())
                         }
                         editor.apply()*/
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (mcdmm.text.toString().toInt()==0)
                         {
                             editor.putString("mcdmm","")
                         }
                         else
                         {
                             editor.putString("mcdmm",mcdmm.text.toString())
                         }
                         editor.apply()*/
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
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (mcdwidth.text.toString().toDouble()==0.0)
                         {
                             editor.putString("mcdwidth","")
                         }
                         else
                         {
                             editor.putString("mcdwidth",mcdwidth.text.toString())
                         }
                         editor.apply()*/
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
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (mcdqty.text.toString().toInt()==0)
                         {
                             editor.putString("mcdqty","")
                         }
                         else
                         {
                             editor.putString("mcdqty",mcdqty.text.toString())
                         }
                         editor.apply()*/
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
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (sq.text.toString().toInt()==0)
                         {
                             editor.putString("sq","")
                         }
                         else
                         {
                             editor.putString("sq",sq.text.toString())
                         }
                         editor.apply()*/
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
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (sw.text.toString().toInt()==0)
                         {
                             editor.putString("sw","")
                         }
                         else
                         {
                             editor.putString("sw",sw.text.toString())
                         }
                         editor.apply()*/
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
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (sh.text.toString().toDouble()==0.0)
                         {
                             editor.putString("sh","")
                         }
                         else
                         {
                             editor.putString("sh",sh.text.toString())
                         }
                         editor.apply()*/
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
                        mmtcft.text = "Total CFT: " + gettotalcft(
                                ckrcft.text.toString(),
                                ckricft.text.toString(),
                                mcft.text.toString(),
                                scft.text.toString()
                        ).toString()
                        getcft.performClick()
                        btnrate.performClick()
                        /* val editor=msharedpref!!.edit()
                         if (sl.text.toString().toDouble()==0.0)
                         {
                             editor.putString("sl","")
                         }
                         else
                         {
                             editor.putString("sl",sl.text.toString())
                         }
                         editor.apply()*/
                    }
                }
                rate.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        if (rate.text.toString().trim().isNotEmpty()) {
                            view.btnmmgcft.performClick()
                            amnt.text = gettotalamnt(mtot, rate.text.toString()).toString()
                            getcft.performClick()
                            btnrate.performClick()
                        } else {
                            rate.setText("0")
                        }
                        /* val editor=msharedpref!!.edit()
                         if (rate.text.toString().toInt()==0)
                         {
                             editor.putString("rate","")
                         }
                         else
                         {
                             editor.putString("rate",rate.text.toString())
                         }
                         editor.apply()*/
                    }
                }
                rate.setOnEditorActionListener { v, actionId, event ->
                    if(actionId == EditorInfo.IME_ACTION_GO){
                        btnrate.performClick()
                        view?.hideKeyboard(im)
                        true
                    } else {
                        false
                    }
                }
            getcft.setOnClickListener {
                ckrcft.text = getckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
                if (txtckri.text == "Chakkri") {

                    ckricft.text =
                        getckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
                } else {
                    ckricft.text = getchaddicft(
                        cdimmw.text.toString(),
                        cdimmh.text.toString(),
                        cdimml.text.toString(),
                        cdimmq.text.toString()
                    ).toString()
                }
                mcft.text = getmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
                scft.text =
                    getsigmetcft(sw.text.toString(), sh.text.toString(), sl.text.toString(), sq.text.toString()).toString()
                mmtcft.text =gettotalcft(
                    ckrcft.text.toString(),
                    ckricft.text.toString(),
                    mcft.text.toString(),
                    scft.text.toString()
                ).toString()
            }
            btnrate.setOnClickListener { amnt.text = gettotalamnt(mtot, rate.text.toString()).toString()
                /**//*shareDrumDetials(mckr.toString(),mckri.toString(),mmcd.toString(),msig.toString(),mtot.toString(),mrate.toString(),mamnt.toString())*/
                m = """Chakkar:  ${mckr}
               |$mckri
               | MCD    :  $mmcd
               | Sigmet :  $msig
               | Total CFT:$mtot
               | Rate   :  $mrate
               | Total Amount:$mamnt
        """.trimMargin()
            }
            inchmm= mm}
            //inch cft
            else
            {
                ckrmm.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        if (ckrmm.text.toString().trim().isEmpty() || ckrmm.text.toString()==".") {
                            ckrmm.setText("0")
                        }
                        var w=ckrmm.text.toString().toDouble()
                        var v=w.roundToInt()
                        ckrmm.setText("$v")
                        getcft.performClick()
                        btnrate.performClick()
                    }
                }
                ckrwidth.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        var i=""
                        if (ckrwidth.text.toString().trim().isEmpty() || ckrwidth.text.toString()==".") {
                            ckrwidth.setText("0")
                        }
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
                    }
                }
                ckriwidth.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        if (ckriwidth.text.toString().trim().isEmpty() || ckriwidth.text.toString()==".") {
                            ckriwidth.setText("0")
                        }
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
                    }
                }
                mcdwidth.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        if (mcdwidth.text.toString().trim().isEmpty() || mcdwidth.text.toString()==".") {
                            mcdwidth.setText("0")
                        }
                        btnrate.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        getcft.performClick()
                        btnrate.performClick()
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
                        btnrate.performClick()
                        btnrate.performClick()

                    }
                }
                sh.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        if (sh.text.toString().trim().isEmpty() || sh.text.toString()==".") {
                            sh.setText("0")
                        }
                        getcft.performClick()
                        btnrate.performClick()
                    }
                }
                sl.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        if (sl.text.toString().trim().isEmpty() || sl.text.toString()==".") {
                            sl.setText("0")
                        }
                        getcft.performClick()
                        btnrate.performClick()
                    }
                }
                rate.setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        if (rate.text.toString().trim().isNotEmpty()) {
                            amnt.text = gettotalamnt(mtot, rate.text.toString()).toString()
                            btnrate.performClick()
                        } else {
                            rate.setText("0")
                        }
                    }
                }
                rate.setOnEditorActionListener { v, actionId, event ->
                    if(actionId == EditorInfo.IME_ACTION_GO){
                        btnrate.performClick()
                        view?.hideKeyboard(im)
                        true
                    } else {
                        false
                    }
                }
                getcft.setOnClickListener {
                    ckrcft.text = getinchckrcft(ckrmm.text.toString(), ckrwidth.text.toString(), ckrqty.text.toString()).toString()
                    ckricft.text = geinchtckricft(ckrimm.text.toString(), ckriwidth.text.toString(), ckriqty.text.toString()).toString()
                    mcft.text = getinchmcdcft(mcdmm.text.toString(), mcdwidth.text.toString(), mcdqty.text.toString()).toString()
                    scft.text =getinchsigmetcft(sw.text.toString(), sh.text.toString(), sl.text.toString(), sq.text.toString()).toString()
                    mmtcft.text =getinchtotalcft(ckrcft.text.toString(), ckricft.text.toString(), mcft.text.toString(), scft.text.toString()
                    ).toString()
                }
                btnrate.setOnClickListener {
                    amnt.text = getinchtotalamnt(mtot, rate.text.toString()).toString()
                    m = """Chakkar:  ${mckr}
               |$mckri
               | MCD    :  $mmcd
               | Sigmet :  $msig
               | Total CFT:$mtot
               | Rate   :  $mrate
               | Total Amount:$mamnt
        """.trimMargin()
                }
                inchmm= inch
            }
            ckrmm.setText("${lstdrum[position].cmm}")
            ckrwidth.setText("${lstdrum[position].cw}")
            ckrqty.setText("${lstdrum[position].cq}")
            ckrcft.setText("${lstdrum[position].ccft}")
            ckrimm.setText("${lstdrum[position].ckmm}")
            ckriwidth.setText("${lstdrum[position].ckw}")
            ckriqty.setText("${lstdrum[position].ckq}")

            if (lstdrum[position].cdiq!=0)
            {
                cdimmw.setText("${lstdrum[position].ckmm}")
                cdimmh.setText("${lstdrum[position].ckw}")
                cdimml.setText("${lstdrum[position].ckq}")
            }
            ckricft.setText("${lstdrum[position].ckcft}")
            cdimmq.setText("${lstdrum[position].cdiq}")
            mcdmm.setText("${lstdrum[position].mmm}")
            mcdwidth.setText("${lstdrum[position].mw}")
            mcdqty.setText("${lstdrum[position].mq}")
            mcft.setText("${lstdrum[position].mcft}")
            sw.setText("${lstdrum[position].sw}")
            sh.setText("${lstdrum[position].sh}")
            sl.setText("${lstdrum[position].sl}")
            sq.setText("${lstdrum[position].sq}")
            scft.setText("${lstdrum[position].scft}")
            mmtcft.setText("${lstdrum[position].tcft}")
            rate.setText("${lstdrum[position].rate}")
            amnt.setText("${lstdrum[position].amnt}")
            etdate.setText("${lstdrum[position].cdate}")
            /*etdate.setText("${lstdrum[position].dateday}/${lstdrum[position].datemounth}/${lstdrum[position].dateyear}")*/
            dateday=lstdrum[position].dateday
            datemounth=lstdrum[position].datemounth
            dateyear=lstdrum[position].dateyear
            mtot=lstdrum[position].tcft.toString()
            cno.setText("${lstdrum[position].cno}")
            vno.setText("${lstdrum[position].vno}")
            showdatepickerdialog(etdate)
            /*Toast.makeText(activity,"$dateday/$datemounth/$dateyear",Toast.LENGTH_LONG).show()*/
            name.setText("${lstdrum[position].name}")
               /*builder .setTitle("Update Data")
                   .setIcon(R.drawable.abc_edit_text_material)*/
               builder.setView(view)
                .setPositiveButton("Update",DialogInterface.OnClickListener { dialog, which ->
                    getcft.performClick()
                    btnrate.performClick()
                   /* Toast.makeText(activity,""+position,Toast.LENGTH_LONG).show()*/
                    val db = DBHandler(activity)
                    if (view.etcno.text.trim().isEmpty())
                    {
                        view.etcno.setText("0")
                    }
                    if (txtckri.text.toString()=="Chakkri")
                    {
                        val drum= Drum(
                            ckrmm.text.toString().toInt(),
                            ckrwidth.text.toString().toDouble(),
                            ckrqty.text.toString().toInt(),
                            ckrcft.text.toString().toDouble(),
                            ckrimm.text.toString().toInt(),
                            ckriwidth.text.toString().toDouble(),
                            ckriqty.text.toString().toDouble(),
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
                            total.toDouble(),
                            rate.text.toString().toInt(),
                            amnt.text.toString().toInt(),
                            dateday,datemounth,dateyear,etdate.text.toString(),inchmm,name.text.toString(),cno.text.toString().toInt(),vno.text.toString())
                        db.updatedrum(lstdrum[position].id,drum)
                    }
                    else{
                    val drum=Drum(
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
                        total.toDouble(),
                        rate.text.toString().toInt(),
                        amnt.text.toString().toInt(),
                        dateday,datemounth,dateyear,etdate.text.toString(),inchmm,name.text.toString(),cno.text.toString().toInt(),vno.text.toString())
                    db.updatedrum(lstdrum[position].id,drum)}

                    lstdrum[position].cmm=ckrmm.text.toString().toInt()
                    lstdrum[position].cw=ckrwidth.text.toString().toDouble()
                    lstdrum[position].cq=ckrqty.text.toString().toInt()
                    lstdrum[position].ccft=ckrcft.text.toString().toDouble()
                    lstdrum[position].ckmm=ckrimm.text.toString().toInt()
                    lstdrum[position].ckw=ckriwidth.text.toString().toDouble()
                    lstdrum[position].ckq=ckriqty.text.toString().toDouble()
                    if (lstdrum[position].cdiq!=0)
                    {
                        lstdrum[position].ckmm=cdimmw.text.toString().toInt()
                        lstdrum[position].ckw=cdimmh.text.toString().toDouble()
                        lstdrum[position].ckq=cdimml.text.toString().toDouble()
                        lstdrum[position].cdiq=cdimmq.text.toString().toInt()
                    }
                    lstdrum[position].ckcft=ckricft.text.toString().toDouble()
                    lstdrum[position].mmm=mcdmm.text.toString().toInt()
                    lstdrum[position].mw=mcdwidth.text.toString().toDouble()
                    lstdrum[position].mq=mcdqty.text.toString().toInt()
                    lstdrum[position].mcft=mcft.text.toString().toDouble()
                    lstdrum[position].sw=sw.text.toString().toInt()
                    lstdrum[position].sh=sh.text.toString().toDouble()
                    lstdrum[position].sl=sl.text.toString().toDouble()
                    lstdrum[position].sq=sq.text.toString().toInt()
                    lstdrum[position].scft=scft.text.toString().toDouble()
                    lstdrum[position].tcft= total
                    lstdrum[position].rate=rate.text.toString().toInt()
                    lstdrum[position].amnt=amnt.text.toString().toInt()
                    lstdrum[position].dateday=dateday
                    lstdrum[position].datemounth=datemounth
                    lstdrum[position].dateyear=dateyear
                    lstdrum[position].inchmm=inchmm
                    lstdrum[position].cdate=etdate.text.toString()
                    lstdrum[position].name=name.text.toString()
                    lstdrum[position].cno=cno.text.toString().toInt()
                    lstdrum[position].vno=vno.text.toString()
                    /*Toast.makeText(activity,""+p1,Toast.LENGTH_LONG).show()*/
                    /*filter.performClick()*/
                    notifyDataSetChanged()
                    lv.setSelection(position)
                    totalcftfun()
                })
                   .setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->  })
                .show()
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return lstdrum[position]
    }

    override fun getItemId(position: Int): Long {
        return lstdrum[position].id.toLong()
    }

    override fun getCount(): Int {
        return lstdrum.size
    }
    private fun gettotalamnt(tot: String, rate: String): Int {
        var a = 0
        var rt = 0
        if (rate.toString().trim().isEmpty()) {
            rt = 0
        } else {
            rt = rate.toInt()
        }
        if (tot.trim().isNotEmpty()) {
            var ab = tot.toDouble() * rt.toDouble()
            a = ab.roundToInt()
            mrate = rate.toString()
            mamnt = a.toString()
        }/* else {
            a = 0
            Toast.makeText(activity, "Correct do right operation!!!", Toast.LENGTH_SHORT).show()
        }*/
        return a
    }

    private fun getckrcft(mm: String, width: String, qty: String): Double {
        var ckrcft = 0.00
        if (mm.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            ckrcft = String.format("%.2f", 0.00).toDouble()
        } else {
            var ckr =
                ((mm.toString().toDouble() / 25.4) * (mm.toString().toDouble() / 25.4) * (width.toString().toDouble() + width.toString().toDouble()) * qty.toString().toDouble() / 2200)
            ckrcft = String.format("%.2f", ckr).toDouble()
            var w = width.toDouble() + width.toDouble()
            mckr = "$mm*$w*$qty = $ckrcft"
        }
        return ckrcft
    }

    private fun getckricft(mm: String, width: String, qty: String): Double {
        var ckricft = 0.00
        if (mm.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            ckricft = String.format("%.2f", 0.00).toDouble()
        } else {
            var cft = ((mm.toDouble() / 25.4 * mm.toDouble() / 25.4) * width.toDouble() * qty.toDouble() / 2200)
            ckricft = String.format("%.2f", cft).toDouble()
            mckri = "Chakkri:  $mm*$width*$qty = $ckricft"
        }
        return ckricft

    }

    private fun getchaddicft(w: String, h: String, l: String, q: String): Double {
        var sigcft = 0.00
        if (w.trim().isEmpty() || h.trim().isEmpty() || l.trim().isEmpty() || q.trim().isEmpty()) {
            sigcft = String.format("%.2f", 0.00).toDouble()
        } else {
            var cft = w.toDouble() * h.toDouble() * l.toDouble() * q.toDouble() / 144
            sigcft = String.format("%.2f", cft).toDouble()
            mckri = "Chaddi:  $w*$h*$l*$q = $sigcft"
        }
        return sigcft
    }

    private fun getmcdcft(mm: String, width: String, qty: String): Double {
        var mcdcft = 0.00
        if (mm.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            mcdcft = String.format("%.2f", 0.00).toDouble()
        } else {
            var cft = ((mm.toDouble() / 25.4) * (mm.toDouble() / 25.4) * width.toDouble() * qty.toDouble() / 2200)
            mcdcft = String.format("%.2f", cft).toDouble()
            mmcd = "$mm*$width*$qty = $mcdcft"
        }
        return mcdcft
    }

    private fun getsigmetcft(w: String, h: String, l: String, q: String): Double {
        var sigcft = 0.00
        if (w.trim().isEmpty() || h.trim().isEmpty() || l.trim().isEmpty() || q.trim().isEmpty()) {
            sigcft = String.format("%.2f", 0.00).toDouble()
        } else {
            var cft = w.toDouble() * h.toDouble() * l.toDouble() * q.toDouble() / 144
            sigcft = String.format("%.2f", cft).toDouble()
            msig = "$w*$h*$l*$q = $sigcft"
        }
        return sigcft
    }

    private fun gettotalcft(ckr: String, ckri: String, mcd: String, sig: String): Double {
        var totalcft = 0.00
        totalcft = String.format("%.2f", ckr.toDouble() + ckri.toDouble() + mcd.toDouble() + sig.toDouble()).toDouble()
        mtot = "$totalcft"
        tcft=totalcft
        total=totalcft
        return totalcft
    }

    private fun getinchckrcft(inch: String, width: String, qty: String): Double {
        var ckrcft = 0.00
        if (inch.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            ckrcft = String.format("%.2f", 0.00).toDouble()
        } else {
            var ckr =
                ((inch.toString().toDouble() * inch.toString().toDouble()) * (width.toString().toDouble() + width.toString().toDouble()) * qty.toString().toDouble() / 2200)
            ckrcft = String.format("%.2f", ckr).toDouble()
            var w = width.toDouble() + width.toDouble()
            mckr = "$inch*$w*$qty = $ckrcft"
        }
        return ckrcft
    }
    private fun geinchtckricft(inch: String, width: String, qty: String): Double {
        var ckricft = 0.00
        if (inch.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            ckricft = String.format("%.2f", 0.00).toDouble()
        } else {
            var cft = ((inch.toDouble() * inch.toDouble()) * width.toDouble() * qty.toDouble() / 2200)
            ckricft = String.format("%.2f", cft).toDouble()
            mckri = "$inch*$width*$qty = $ckricft"
        }
        return ckricft
    }

    private fun getinchmcdcft(inch: String, width: String, qty: String): Double {
        var mcdcft = 0.00
        if (inch.trim().isEmpty() || width.trim().isEmpty() || qty.trim().isEmpty()) {
            mcdcft = String.format("%.2f", 0.00).toDouble()
        } else {
            var cft = ((inch.toDouble() * inch.toDouble()) * width.toDouble() * qty.toDouble() / 2200)
            mcdcft = String.format("%.2f", cft).toDouble()
            mmcd = "$inch*$width*$qty = $mcdcft"
        }
        return mcdcft
    }

    private fun getinchsigmetcft(w: String, h: String, l: String, q: String): Double {
        var sigcft = 0.00
        if (w.trim().isEmpty() || h.trim().isEmpty() || l.trim().isEmpty() || q.trim().isEmpty()) {
            sigcft = String.format("%.2f", 0.00).toDouble()
        } else {
            var cft = w.toDouble() * h.toDouble() * l.toDouble() * q.toDouble() / 144
            sigcft = String.format("%.2f", cft).toDouble()
            msig = "$w*$h*$l*$q = $sigcft"
        }
        return sigcft
    }
    private fun getinchtotalcft(ckr: String, ckri: String, mcd: String, sig: String): Double {
        var totalcft = 0.00
        totalcft = String.format("%.2f", ckr.toDouble() + ckri.toDouble() + mcd.toDouble() + sig.toDouble()).toDouble()
        tcft=totalcft
        mtot = "$totalcft"
        total =totalcft
        return totalcft
    }
    private fun getinchtotalamnt(tot: String, rate: String): Int {
        var a = 0
        var rt = 0
        if (rate.toString().trim().isEmpty()) {
            rt = 0
        } else {
            rt = rate.toInt()
        }
        if (tot.trim().isNotEmpty()) {
            var ab = mtot.toDouble() * rt.toDouble()
            a = ab.roundToInt()
            mrate = rate.toString()
            mamnt = a.toString()
        }
        return a
    }

    fun showdatepickerdialog(date:EditText)
    {
        val c = Calendar.getInstance()
        var y=c.get(Calendar.YEAR)
        var dm=0
        var dt=c.get(Calendar.DAY_OF_MONTH)
        /*Toast.makeText(activity,"$dateday/$datemounth/$dateyear",Toast.LENGTH_LONG).show()*/
       /* dm=dm+1*/

        var d=""
        d= dateday.toString()
        dm=datemounth-1
        y=dateyear
        /*date.setText("$d/$dm/$y")*/
        date.setOnClickListener {
            d= dateday.toString()
            dm=datemounth-1
            y=dateyear
            val dialog =
                DatePickerDialog(
                    activity,
                    DatePickerDialog.OnDateSetListener { view, myear: Int, mmonth: Int, mdayOfMonth: Int ->
                        var m = mmonth + 1
                        var datezero = ""
                        var mounthzero = ""
                        if (mdayOfMonth < 10) {
                            datezero = "$mdayOfMonth".padStart(2, '0')
                        } else {
                            datezero = mdayOfMonth.toString()
                        }
                        if (m < 10) {
                            mounthzero = "$m".padStart(2, '0')
                        } else {
                            mounthzero = m.toString()
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
    fun totalcftfun()
    {
        /*val db = DBHandler(activity)
        var data=db.getdrumlist(activity)*/
        var data=lstdrum
        var no=0
        var q=0
        tcft=0.0
        totalamnt=0
        for (i in 0..data.size-1)
        {
            no=no+1
            tcft=tcft+ data.get(i).tcft!!
            totalamnt= totalamnt+data.get(i).amnt!!
            q=q+data.get(i).cq
        }
        var t="Total Record: $no            Total no of Quantity: $q"
        var s= String.format("%.2f", tcft).toDouble()
        var cft="Total CFT: $s      Total Amount: $totalamnt"
        var d="$t\n$cft"
        tvtcft.text=d
        tr.text="$no"
        tc.text="$q"
        ttcft.text="$s"
        val ind=DecimalFormat("##,##,###")
        val famnt=ind.format(totalamnt)
        tamnt.text=famnt
        /*tvtcft.setText(d)*/


    }
    private fun shareDrumDetials(
        name:String,
        cno:String,
        date:String,
        ckr: String,
        ckri: String,
        mcd: String,
        sig: String,
        tot: String,
        rate: String,
        amnt: String,
        vno:String
    ) {
        var msg=ArrayList<String>()
        var txt=""
        var n=""
        msg.add(date)
        if (name == "")
        {
            n=""
        }
        else
        {
            n="Name: $name"
            msg.add(n)
        }
        var no=""
        if ((cno == "") || (cno.toInt()==0) )
        {
            no=""
        }
        else
        {
            no="Challan no: $cno"
            msg.add(no)
        }
        var ck=""
        if (ckr=="")
        {
            ck=""
        }
        else
        {
            ck="Chakkar:  $ckr"
            msg.add(ck)

        }
        var cki=""
        if (ckri=="")
        {
            cki=""
        }
        else
        {
            cki="Chakkri :  $ckri"
            msg.add(cki)
        }
        var m=""
        if (mcd=="")
        {
            m=""
        }
        else
        {
            m="MCD     :  $mcd"
            msg.add(m)
        }
        var s=""
        if (sig=="")
        {
            s=""
        }
        else
        {
            s="Sigmet :  $sig"
            msg.add(s)
        }
        var r=""
        var t=""
        var totalcft="Total CFT: $tot"
        msg.add(totalcft)
        if ((rate=="") ||(rate.toInt()==0))
        {
            r=""
        }
        else
        {
            r="Rate   :  $rate"
            t="Total Amount: $amnt"
            msg.add(r)
            msg.add(t)
        }
        var v=""
        if (vno=="")
        {
            v=""
        }
        else
        {
            v="Vehicle no: $vno"
            msg.add(v)
        }

        for (i in 0..msg.size-1)
        {
            txt += "${msg[i]} \n"
        }
        msg.clear()
        if (tot.trim().isNotEmpty()) {
            var sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "WOODEN DRUM CFT\n" +
                            "$txt"
                )
                    .type = "text/plain"
            }
            activity.startActivity(Intent.createChooser(sendIntent, "Choose app"))
        } else {
            Toast.makeText(activity, "Calculate CFT!!!!", Toast.LENGTH_SHORT).show()
        }

    }
    fun View.hideKeyboard(inputMethodManager: InputMethodManager) {
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

     fun autosuggestname(name: AutoCompleteTextView) {
        val db = DBHandler(activity)
        val n=db.getname(activity)
        val namelist=ArrayList<String>()
        for (i in 0..n.size-1)
        {
            n.get(i).name?.let { namelist.add(it) }
           // n.get(i).name?.let { namelist.add(it) }
        }
        val b=namelist.distinct()
        val adapter= ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,b)
        name.setAdapter(adapter)
    }
    fun autosuggestvehivehicleno(name: AutoCompleteTextView) {
        val db = DBHandler(activity)
        val n=db.getvno(activity)
        val namelist=ArrayList<String>()
        for (i in 0..n.size-1)
        {
            n.get(i).vno?.let { namelist.add(it) }
           // n.get(i).vno?.let { namelist.add(it) }
        }
        val b=namelist.distinct()
        val adapter= ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,b)
        name.setAdapter(adapter)
    }


}