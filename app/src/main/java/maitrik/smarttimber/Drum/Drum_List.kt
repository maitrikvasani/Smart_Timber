package maitrik.smarttimber.Drum

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.print.PrintAttributes
import android.print.PrintManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.core.content.FileProvider
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.karumi.dexter.BuildConfig
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_drum__list.*
import maitrik.smarttimber.Adapter.CustomAdapterAllDrumList
import maitrik.smarttimber.Adapter.PdfDocumentAdapter
import maitrik.smarttimber.Adapter.tcft
import maitrik.smarttimber.Adapter.totalamnt
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import maitrik.smarttimber.Model.Drum
import maitrik.smarttimber.PDF.Common
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.RuntimeException
import java.security.Permissions
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Drum_List : AppCompatActivity() {
    val REQUEST_CODE_PERMISSIONS:Int = 2
   // private var localBackup: LocalBackup? = null
    var drumlistforsearch=ArrayList<Drum>()
    lateinit var lv: ListView
    lateinit var filter: Button
    lateinit var adapter: CustomAdapterAllDrumList
    internal lateinit var db: DBHandler
    var lstforpdf=ArrayList<Drum>()
   // lateinit var sv:SearchView
   lateinit var sv:androidx.appcompat.widget.SearchView
    var msharedpref: SharedPreferences?=null
    var dateshared: SharedPreferences?=null
    lateinit var state: Parcelable
    var no=-1
    var dateday = 0
    var datemounth = 0
    var dateyear = 0
    var datedayto = 0
    var datemounthto = 0
    var dateyearto = 0
    var dshared=0
    var fy=""
    var fm=""
    var fd=""
    var ty=""
    var tm=""
    val file_name:String="SmartTimber_Report.pdf"
    var td=""


    lateinit var context: Context
    lateinit var etfromdate: EditText
    lateinit var ettodate: EditText
    lateinit var dateok: ImageView
    lateinit var dateclear: ImageView
    lateinit var inputManager: InputMethodManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drum__list)
        inputManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        msharedpref=this.getSharedPreferences("My_Data", Context.MODE_PRIVATE)
        dateshared=this.getSharedPreferences("Date",Context.MODE_PRIVATE)
        context = this
        db = DBHandler(context)
        fy= dateshared!!.getString("fy","").toString()
        fm= dateshared!!.getString("fm","").toString()
        fd= dateshared!!.getString("fd","").toString()
        ty= dateshared!!.getString("ty","").toString()
        tm= dateshared!!.getString("tm","").toString()
        td= dateshared!!.getString("td","").toString()
        dshared= dateshared!!.getString("date","0")!!.toInt()
        /*Toast.makeText(this,"Date is : From $fd/$fm/$fy to $td/$tm/$ty",Toast.LENGTH_SHORT).show()*/
        /*etfromdate.setText("")*/
        filter=findViewById(R.id.btnfilter)
        dateok=findViewById(R.id.btndateok)
        dateclear=findViewById(R.id.btndateclr)
        etfromdate=findViewById(R.id.drumlistdate1)
        ettodate=findViewById(R.id.drumlistdate2)
        if (fd.isNotEmpty() && fm.isNotEmpty() && fy.isNotEmpty())
        {
            dateday = fd.toInt()
            datemounth = fm .toInt()
            dateyear = fy.toInt()
            etfromdate.setText("$fd/$fm/$fy")
        }
        if (td.isNotEmpty() && tm.isNotEmpty() && ty.isNotEmpty())
        {
            datedayto = td.toInt()
            datemounthto = tm .toInt()
            dateyearto = ty.toInt()
            ettodate.setText("$td/$tm/$ty")
        }
        sortingShared()
        etfromdate.setOnClickListener {
            showdatepickerdialog(etfromdate)
        }
        ettodate.setOnClickListener {
            showdatepickertodialog(ettodate)
        }
        dateok.setOnClickListener {

            if (etfromdate.text.isNotEmpty() && ettodate.text.isNotEmpty())
            {
                checkDateValidation(etfromdate.text.toString(),ettodate.text.toString())
            }
            else
            {
                Toast.makeText(this,"Select Date", Toast.LENGTH_SHORT).show()
            }
        }
        dateclear.setOnClickListener {
            var alertDialog = AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Are you sure want to clear date ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    etfromdate.setText("")
                    ettodate.setText("")
                    sortingShared()
                    /*Toast.makeText(this, "Clear", Toast.LENGTH_SHORT).show()*/
                    val editor=dateshared!!.edit()
                    editor.clear()
                    editor.apply()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }

        var msorting=msharedpref!!.getString("Sort","0")
        when(msorting)
        {
            "0"-> {
                no=0
                viewDrumList()
            }
            "1"->{
                no=1
                viewDrumListDESC()
            }
            "2"->{
                no=2
                viewDrumListbyDateDESC()
            }
            "3"->{
                no=3
                viewDrumListbyDateASC()
            }
            "4"->{
                no=4
                viewDrumListbyNameASC()
            }
            "5"->{
                no=5
                viewDrumListbyNameDESC()
            }
        }
        filter.setOnClickListener {
            val msorting=msharedpref!!.getString("Sort","0")
            when(msorting)
            {
                "0"-> {
                    no=0
                    viewDrumList()
                }
                "1"->{
                    no=1
                    viewDrumListDESC()
                }
                "2"->{
                    no=2
                    viewDrumListbyDateDESC()
                }
                "3"->{
                    no=3
                    viewDrumListbyDateASC()
                }
                "4"->{
                    no=4
                    viewDrumListbyNameASC()
                }
                "5"->{
                    no=5
                    viewDrumListbyNameDESC()
                }
            }
            search()
        }

        etsearchname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lv=findViewById(R.id.lstviewdrum)
                var filteredname=ArrayList<Drum>()
                if (!etsearchname.text.isEmpty()){
                    for (i in 0..drumlistforsearch.size-1)
                    {
                        if (drumlistforsearch.get(i).name!!.toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cdate.toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cno!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).vno!!.toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cmm.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cw.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cq.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).ckmm!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).ckw!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).ckq!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).mmm!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).mw!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).mq!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).sw!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).sh!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).sl!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).sq!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                        )
                            filteredname.add(drumlistforsearch[i])
                    }
                    adapter= CustomAdapterAllDrumList(this@Drum_List,filteredname,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
                    lv.adapter=adapter
                    totalcftforlist(filteredname)
                    lstforpdf=filteredname
                }
                else{
                    adapter= CustomAdapterAllDrumList(this@Drum_List,drumlistforsearch,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
                    lv.adapter=adapter
                    totalcftforlist(drumlistforsearch)
                    filter.performClick()
                    lstforpdf=drumlistforsearch
                }
            }
        })
    }

    private fun search() {
        etsearchname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lv=findViewById(R.id.lstviewdrum)
                var filteredname=ArrayList<Drum>()
                if (!etsearchname.text.isEmpty()){
                    for (i in 0..drumlistforsearch.size-1)
                    {
                        if (drumlistforsearch.get(i).name!!.toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cdate.toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cno!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).vno!!.toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cmm.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cw.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).cq.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).ckmm!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).ckw!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).ckq!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).mmm!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).mw!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).mq!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).sw!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).sh!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).sl!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                            || drumlistforsearch.get(i).sq!!.toString().toLowerCase().contains(p0.toString().toLowerCase())
                        )
                            filteredname.add(drumlistforsearch[i])
                    }
                    adapter= CustomAdapterAllDrumList(this@Drum_List,filteredname,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
                    lv.adapter=adapter
                    totalcftforlist(filteredname)
                    lstforpdf=filteredname
                }
                else{
                    adapter= CustomAdapterAllDrumList(this@Drum_List,drumlistforsearch,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
                    lv.adapter=adapter
                    totalcftforlist(drumlistforsearch)
                    lstforpdf=drumlistforsearch
                }
            }
        })
    }


    fun totalcftforlist(lst:ArrayList<Drum>)
    {
        var no=0
        var q=0
        tcft =0.0
        totalamnt =0
        for (i in 0..lst.size-1)
        {
            no=no+1
            tcft = tcft + lst.get(i).tcft!!
            totalamnt = totalamnt +lst.get(i).amnt!!
            q=q+ lst.get(i).cq
        }
        var t="Total Record: $no     "
        var s= String.format("%.2f", tcft).toDouble()
        var cft="Total CFT: $s      Total Amount: $totalamnt"
        var d="$t\n$cft"
        //tvtcft.text=d
        lstviewdrumtotalrecord.text="$no"
        lstviewdrumtotalchakar.text="$q"
        lstviewdrumtotalcft.text="$s"
        val ind= DecimalFormat("##,##,###")
        val famnt=ind.format(totalamnt)
        lstviewdrumtotalamnt.text=famnt
    }

    private fun viewDrumList()
    {
        if (ettodate.text.toString().isNotEmpty() && etfromdate.text.toString().isNotEmpty())
        {
            if (etsearchname.text.isNotEmpty())
            {
                drumlistforsearch=db.getdrumlistbyDateSearchwithChalanASC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
                var drumList:ArrayList<Drum> = db.getdrumlist(this)
                val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
                lstviewdrum.adapter=adapter
                totalcftforlist(drumList)
                lstforpdf=drumList
            }
            drumlistforsearch=db.getdrumlistbyDateSearchwithChalanASC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val drumList:ArrayList<Drum> = db.getdrumlistbyDateSearchwithChalanASC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
        else
        {
            if (etsearchname.text.isNotEmpty())
            {
                drumlistforsearch=db.getdrumlist(this)
                val drumList:ArrayList<Drum> = db.getdrumlist(this)
                val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
                lstviewdrum.adapter=adapter
                totalcftforlist(drumList)
                lstforpdf=drumList
            }
            drumlistforsearch=db.getdrumlist(this)
            val drumList:ArrayList<Drum> = db.getdrumlist(this)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
    }
    private fun viewDrumListDESC()
    {
        if (ettodate.text.toString().isNotEmpty() && etfromdate.text.toString().isNotEmpty())
        {
            drumlistforsearch=db.getdrumlistbyDateSearchwithChalanDESC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val drumList:ArrayList<Drum> = db.getdrumlistbyDateSearchwithChalanDESC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
        else
        {
            drumlistforsearch=db.getdrumlist(this)
            val drumList:ArrayList<Drum> = db.getdrumlistDESC(this)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
    }
    private fun viewDrumListbyDateASC()
    {
        if (ettodate.text.toString().isNotEmpty() && etfromdate.text.toString().isNotEmpty())
        {
            drumlistforsearch=db.getdrumlistbyDateSearchASC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val drumList:ArrayList<Drum> = db.getdrumlistbyDateSearchASC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
        else
        {
            drumlistforsearch=db.getdrumlistbyDateASC(this)
            val drumList:ArrayList<Drum> = db.getdrumlistbyDateASC(this)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
    }
    private fun viewDrumListbyDateDESC()
    {
        if (ettodate.text.toString().isNotEmpty() && etfromdate.text.toString().isNotEmpty())
        {
            drumlistforsearch=db.getdrumlistbyDateSearchDESC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val drumList:ArrayList<Drum> = db.getdrumlistbyDateSearchDESC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
        else
        {
            drumlistforsearch=db.getdrumlistbyDate(this)
            val drumList:ArrayList<Drum> = db.getdrumlistbyDate(this)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
    }
    private fun viewDrumListbyNameASC()
    {
        if (ettodate.text.toString().isNotEmpty() && etfromdate.text.toString().isNotEmpty())
        {
            drumlistforsearch=db.getdrumlistbyDateSearchwithNameASC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val drumList:ArrayList<Drum> = db.getdrumlistbyDateSearchwithNameASC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
        else
        {
            drumlistforsearch=db.getdrumlistbyNameASC(this)
            val drumList:ArrayList<Drum> = db.getdrumlistbyNameASC(this)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
    }
    private fun viewDrumListbyNameDESC()
    {
        if (ettodate.text.toString().isNotEmpty() && etfromdate.text.toString().isNotEmpty())
        {
            drumlistforsearch=db.getdrumlistbyDateSearchwithNameDESC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val drumList:ArrayList<Drum> = db.getdrumlistbyDateSearchwithNameDESC(this,dateyear,datemounth,dateday,dateyearto,datemounthto,datedayto)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
        else
        {
            drumlistforsearch=db.getdrumlistbyNameDESC(this)
            val drumList:ArrayList<Drum> = db.getdrumlistbyNameDESC(this)
            val adapter=CustomAdapterAllDrumList(this,drumList,totalcft,filter,lstviewdrum,lstviewdrumtotalrecord,lstviewdrumtotalchakar,lstviewdrumtotalcft,lstviewdrumtotalamnt,inputManager)
            lstviewdrum.adapter=adapter
            totalcftforlist(drumList)
            lstforpdf=drumList
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater
        menuInflater.inflate(R.menu.inchmmmenu,menu)
        sv=menu!!.findItem(R.id.menusearch).actionView as androidx.appcompat.widget.SearchView
        val sm=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.queryHint="Search"

        sv.setOnCloseListener(object : androidx.appcompat.widget.SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                filter.performClick()
                /*Toast.makeText(applicationContext,"onclose",Toast.LENGTH_SHORT).show()*/
                return false
            }
        })

        sv.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                etsearchname.setText(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId)
        {
            R.id.mconveter ->
            {
                val intent = Intent(this,Converter_INCHMM::class.java)
                startActivity(intent)
            }
            R.id.sort->
            {
                showsortdialog()
            }
            /* R.id.namellist->
             {
                 val i=Intent(this,ViewNameList::class.java)
                 startActivity(i)
             }*/
            R.id.printpdf->
            {
                pdf(1)
            }
            /* R.id.action_backup -> {
                 val outFileName =
                     Environment.getExternalStorageDirectory().toString() + File.separator + resources.getString(
                         R.string.app_name
                     ) + File.separator+"DBBackup"+File.separator
                 localBackup!!.performBackup(db, outFileName)
             }
             R.id.action_import ->
             {
                 val r=localBackup!!.performRestore(db)
                 if (r==1)
                 {
                     Toast.makeText(this,"Import Success",Toast.LENGTH_LONG).show()
                 }


               //  lv.deferNotifyDataSetChanged()

             }*/
            R.id.openpdf ->
            {
                pdf(0)
                //createPDFFile(Common.getAppPath(this@Drum_List)+file_name,2)
                //fopenpdf()
            }
            /* R.id.action_backup_Drive -> {
                 *//* isBackup = true
                 remoteBackup.connectToDrive(isBackup)*//*
                Toast.makeText(this,"Coming Soon..",Toast.LENGTH_SHORT).show()
            }
            R.id.action_import_Drive -> {
                *//* isBackup = false
                 remoteBackup.connectToDrive(isBackup)*//*
                Toast.makeText(this,"Coming Soon..",Toast.LENGTH_SHORT).show()
            }*/
            /*  R.id.action_delete_all -> {
                  //reinitialize the backup
                  val database = db.getWritableDatabase()
                  db.onUpgrade(database, db.getDatabaseVersion(),db.getDatabaseVersion())
                  sortingShared()
              }*/

        }
        return super.onOptionsItemSelected(item)
    }

    private fun openpdf() {
        maitrik.smarttimber.Adaper.Permission.Permissions.Permissions.verifyStoragePermissions(this)

        val path= Common.getAppPath(this@Drum_List)+ file_name
        val mimtype= MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(path))
        //  Toast.makeText(this,mimtype,Toast.LENGTH_LONG).show()
        val file= File(path)

      //  val uri=
           // FileProvider.getUriForFile(this@Drum_List, BuildConfig.APPLICATION_ID+".provider",file)
        val uri=FileProvider.getUriForFile(this@Drum_List,maitrik.smarttimber.BuildConfig.APPLICATION_ID+".provider",file)
        try {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri,mimtype)
            intent.flags= Intent.FLAG_GRANT_READ_URI_PERMISSION
            //intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            val activities: List<ResolveInfo> = packageManager.queryIntentActivities(intent, 0)
            val isIntentSafe: Boolean = activities.isNotEmpty()
            if (isIntentSafe) {
                startActivity(intent)
            }
            else
            {
                Toast.makeText(context,"Please Download PDF Viewer Application from PlayStore. ",
                    Toast.LENGTH_LONG).show()
            }
            startActivity(intent)
            /*val intent1=Intent.createChooser(intent,"Open With")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent1)
            }
            else
            {
                Toast.makeText(context,"Please Download PDF Viewer Application from PlayStore. ",Toast.LENGTH_LONG).show()
            }*/
        }catch (e: RuntimeException)
        {
            Toast.makeText(this,"Error=$e", Toast.LENGTH_LONG).show()
        }

        // startActivity(intent1)
    }


    private fun showsortdialog() {
        val sortoOptions = arrayOf("Chalan no 1-10","Chalan no 10-1","Date(Latest)","Date(Oldest)","Name(Ascending) A to Z","Name(Descending) Z to A")
        val mBuilder= AlertDialog.Builder(this)
        mBuilder.setTitle("Sort By")
        mBuilder.setIcon(R.drawable.ic_sort_black_24dp)
        mBuilder.setSingleChoiceItems(sortoOptions,no){
                dialog: DialogInterface?, which: Int ->
            if (which==0)
            {
                viewDrumList()
                no=0
                val editor=msharedpref!!.edit()
                editor.putString("Sort","0")
                editor.apply()
                Toast.makeText(this,sortoOptions[which], Toast.LENGTH_SHORT).show()
            }
            if (which==1)
            {
                viewDrumListDESC()
                no=1
                val editor=msharedpref!!.edit()
                editor.putString("Sort","1")
                editor.apply()
                Toast.makeText(this,sortoOptions[which], Toast.LENGTH_SHORT).show()
            }
            if (which==2)
            {
                viewDrumListbyDateDESC()
                no=2
                val editor=msharedpref!!.edit()
                editor.putString("Sort","2")
                editor.apply()
                Toast.makeText(this,sortoOptions[which], Toast.LENGTH_SHORT).show()
            }
            if(which==3)
            {
                viewDrumListbyDateASC()
                no=3
                val editor=msharedpref!!.edit()
                editor.putString("Sort","3")
                editor.apply()
                Toast.makeText(this,sortoOptions[which], Toast.LENGTH_SHORT).show()
            }
            if(which==4)
            {
                viewDrumListbyNameASC()
                no=4
                val editor=msharedpref!!.edit()
                editor.putString("Sort","4")
                editor.apply()
                Toast.makeText(this,sortoOptions[which], Toast.LENGTH_SHORT).show()
            }
            if(which==5)
            {
                viewDrumListbyNameDESC()
                no=5
                val editor=msharedpref!!.edit()
                editor.putString("Sort","5")
                editor.apply()
                Toast.makeText(this,sortoOptions[which], Toast.LENGTH_SHORT).show()
            }
            dialog!!.dismiss()
        }
        val mDialog=mBuilder.create()
        mDialog.show()
    }
    fun showdatepickerdialog(date: EditText) {
        var c = Calendar.getInstance()
        var y = c.get(Calendar.YEAR)
        var dm = c.get(Calendar.MONTH)
        var dt = c.get(Calendar.DAY_OF_MONTH)
        var dmm=""
        var moun=""
        var d=""
        var mm=dm+1
        if (mm<10)
        {
            dmm="$mm".padStart(2,'0')
        }
        else
        {
            dmm=mm.toString()
        }
        if (dt<10)
        {
            moun="$dt".padStart(2,'0')
        }
        else
        {
            moun=dt.toString()
        }
        d=moun
        dateday = d.toInt()
        datemounth = dmm.toInt()
        /*Toast.makeText(this,""+datemounth,Toast.LENGTH_LONG).show()
        Toast.makeText(this,""+datemounth,Toast.LENGTH_LONG).show()*/
        dateyear = y
        /*date.setText("$d/$dmm/$y")*/
        date.setOnClickListener {
            if (ettodate.text.isNotEmpty() && etfromdate.text.isNotEmpty())
            {
                fy= dateshared!!.getString("fy","").toString()
                fm= dateshared!!.getString("fm","").toString()
                fd= dateshared!!.getString("fd","").toString()
                d=fd.toString()
                dm=fm.toInt()-1
                y=fy.toInt()
                /* Toast.makeText(this,"$fd/$fm/$fy",Toast.LENGTH_SHORT).show()*/
            }
            val dialog =
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, myear: Int, mmonth: Int, mdayOfMonth: Int ->
                        var m=mmonth+1
                        var datezero=""
                        var mounthzero=""
                        if (mdayOfMonth<10)
                        {
                            datezero="$mdayOfMonth".padStart(2,'0')
                        }
                        else
                        {
                            datezero=mdayOfMonth.toString()
                        }
                        if (m<10)
                        {
                            mounthzero="$m".padStart(2,'0')
                        }
                        else
                        {
                            mounthzero=m.toString()
                        }
                        date.setText("" + datezero + "/" + mounthzero + "/" + myear)
                        /* var da="$datezero/$mounthzero/$myear"
                         Toast.makeText(this,"$da",Toast.LENGTH_SHORT).show()*/
                        dateday = datezero.toInt()
                        datemounth = mounthzero.toInt()
                        /*Toast.makeText(this,""+datemounth,Toast.LENGTH_LONG).show()*/
                        dateyear = myear
                        var e=dateshared!!.edit()
                        e.putString("fy",myear.toString())
                        e.putString("fm",mounthzero.toString())
                        e.putString("fd",datezero.toString())
                        e.apply()
                    },
                    y,
                    dm,
                    d.toInt()
                )
            dialog.show()
        }
    }
    fun showdatepickertodialog(date: EditText) {
        var c = Calendar.getInstance()
        var y = c.get(Calendar.YEAR)
        var dm = c.get(Calendar.MONTH)
        var dt = c.get(Calendar.DAY_OF_MONTH)
        var dmm=""
        var moun=""
        var d=""
        var mm=dm+1

        if (mm<10)
        {
            dmm="$mm".padStart(2,'0')

        }
        else
        {
            dmm=mm.toString()
        }

        if (dt<10)
        {
            moun="$dt".padStart(2,'0')
        }
        else
        {
            moun=dt.toString()
        }
        d=moun
        datedayto = d.toInt()
        datemounthto = dmm.toInt()
        /*Toast.makeText(this,""+datemounth,Toast.LENGTH_LONG).show()
        Toast.makeText(this,""+datemounth,Toast.LENGTH_LONG).show()*/
        dateyearto = y
        /*date.setText("$d/$dmm/$y")*/
        date.setOnClickListener {
            if (ettodate.text.isNotEmpty() && etfromdate.text.isNotEmpty())
            {
                ty= dateshared!!.getString("ty","").toString()
                tm= dateshared!!.getString("tm","").toString()
                td= dateshared!!.getString("td","").toString()
                d=td.toString()
                dm=tm.toInt()-1
                y=ty.toInt()
                /*Toast.makeText(this,"$d/$dm/$y",Toast.LENGTH_SHORT).show()*/
            }
            val dialog =
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, myear: Int, mmonth: Int, mdayOfMonth: Int ->
                        var m=mmonth+1
                        var datezero=""
                        var mounthzero=""
                        if (mdayOfMonth<10)
                        {
                            datezero="$mdayOfMonth".padStart(2,'0')
                        }
                        else
                        {
                            datezero=mdayOfMonth.toString()
                        }
                        if (m<10)
                        {
                            mounthzero="$m".padStart(2,'0')
                        }
                        else
                        {
                            mounthzero=m.toString()
                        }
                        date.setText("" + datezero + "/" + mounthzero + "/" + myear)
                        /*d="$myear/$mounthzero/$datezero"*/
                        datedayto = datezero.toInt()
                        datemounthto = mounthzero.toInt()
                        /*Toast.makeText(this,""+datemounth,Toast.LENGTH_LONG).show()*/
                        dateyearto = myear
                        var e=dateshared!!.edit()
                        e.putString("ty",myear.toString())
                        e.putString("tm",mounthzero.toString())
                        e.putString("td",datezero.toString())
                        /* Toast.makeText(this,"$datezero/$mounthzero/$myear",Toast.LENGTH_SHORT).show()*/
                        e.apply()
                    },
                    y,
                    dm,
                    d.toInt()
                )
            dialog.show()
        }
    }
    fun sortingShared()
    {
        val msorting=msharedpref!!.getString("Sort","0")
        when(msorting)
        {
            "0"-> {
                no=0
                viewDrumList()
            }
            "1"->{
                no=1
                viewDrumListDESC()
            }
            "2"->{
                no=2
                viewDrumListbyDateDESC()

            }
            "3"->{
                no=3
                viewDrumListbyDateASC()

            }
            "4"->{
                no=4
                viewDrumListbyNameASC()
            }
            "5"->{
                no=5
                viewDrumListbyNameDESC()
            }
        }
    }

    private fun checkDateValidation(fdate:String, tdate:String) {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        if (fdate.isNotEmpty() && tdate.isNotEmpty()) {
            if (formatter.parse(fdate).before(formatter.parse(tdate))) {
                if (dateday != 0 && datemounth != 0 && dateyear != 0 && datedayto != 0 && datemounthto != 0 && datedayto != 0 && dateyearto != 0) {
                    Toast.makeText(this, "$dateday/$datemounth/$dateyear to $datedayto/$datemounthto/$dateyearto", Toast.LENGTH_SHORT).show()
                }
                sortingShared()
            } else if (formatter.parse(fdate) == formatter.parse(tdate)) {
                if (dateday != 0 && datemounth != 0 && dateyear != 0 && datedayto != 0 && datemounthto != 0 && datedayto != 0 && dateyearto != 0) {
                    Toast.makeText(this, "$dateday/$datemounth/$dateyear to $datedayto/$datemounthto/$dateyearto", Toast.LENGTH_SHORT).show()
                }
                sortingShared()
            } else {
                ettodate.setText("")
                Toast.makeText(this, "From date should be less than or equal to TO date", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun pdf(i:Int){

        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener
            {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    createPDFFile(Common.getAppPath(this@Drum_List)+ file_name,i)
                }
                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(context,"Permission Allow for Print or Open PDF", Toast.LENGTH_LONG).show()
                }
            })
            .check()
    }
    fun createPDFFile(path:String,i:Int)
    {

        if (File(path).exists())
        {
            File(path).delete()
        }
        try {
            val doc= Document()
            PdfWriter.getInstance(doc, FileOutputStream(path))
            doc.open()
            doc.pageSize = PageSize.A4
            doc.addTitle("Report")
            doc.addAuthor("Maitrik")
            doc.addCreator("Smart Timber")
            val titlefont= Font(Font.FontFamily.UNDEFINED,20f, Font.NORMAL, BaseColor.BLACK)
            val normalbold= Font(Font.FontFamily.UNDEFINED,14f, Font.BOLD, BaseColor.BLACK)
            val normal= Font(Font.FontFamily.UNDEFINED,14f, Font.NORMAL, BaseColor.BLACK)
            val normalred= Font(Font.FontFamily.UNDEFINED,14f, Font.NORMAL, BaseColor.RED)

            doc.addHeader("report",addNewIem(doc,"Report", Element.ALIGN_CENTER,titlefont).toString())

            addLineSeperator(doc)
            addTitlePage(doc,normalbold)
            addLineSeperator(doc)

            var no=0
            var tcft=0.0
            var tamnt=0
            for (i in 0..lstforpdf.size-1)
            {
                val th= PdfPTable(floatArrayOf(3.80f,1f,1f))
                th.widthPercentage=80f
                insertCell(th," Name: "+lstforpdf[i].name, Element.ALIGN_LEFT,1,normal)
                noBorderCell(th,2)
                doc.add(th)

                val t1= PdfPTable(floatArrayOf(1.80f,2f,1f,1f))
                t1.widthPercentage=80f
                insertCell(t1,"Challan no: ${lstforpdf[i].cno}", Element.ALIGN_LEFT,1,normal)
                insertCell(t1,"Date: ${lstforpdf[i].cdate}", Element.ALIGN_LEFT,1,normal)
                noBorderCell(t1,2)
                doc.add(t1)

                val detail= PdfPTable(floatArrayOf(1f,1.5f,0.5f,0.80f,1f,1f))
                detail.widthPercentage=80f
                insertCell(detail,"ST", Element.ALIGN_CENTER,1, normal)
                insertCell(detail,"${lstforpdf[i].inchmm}", Element.ALIGN_CENTER,1, normal)
                insertCell(detail,"QTY",
                    Element.TITLE,1, Font(
                        Font(
                            Font.FontFamily.UNDEFINED,12f, Font.NORMAL,
                            BaseColor.BLACK)
                    )
                )
                insertCell(detail,"CFT", Element.ALIGN_CENTER,1, normal)
                noBorderCell(detail,2)

                insertCell(detail,"Chakkar", Element.ALIGN_LEFT,1,normal)
                val cw:Double=lstforpdf[i].cw+lstforpdf[i].cw
                val ckr = lstforpdf[i].cmm.toString() + "*" + cw
                insertCell(detail,ckr, Element.ALIGN_LEFT,1,normal)
                insertCell(detail,"${lstforpdf[i].cq}", Element.ALIGN_LEFT,1,normal)
                insertCellwithnopadding(detail,"${lstforpdf[i].ccft}", Element.ALIGN_RIGHT,1,normal)
                noBorderCell(detail,2)

                //For Chakkri
                if (lstforpdf[i].ckmm!=0) {
                    var ckri: String
                    if (lstforpdf[i].cdiq == 0) {
                        insertCell(detail,"Chakkri", Element.ALIGN_LEFT,1,normal)

                        ckri = lstforpdf[i].ckmm.toString() + "*" + lstforpdf[i].ckw.toString()

                        insertCell(detail,"$ckri", Element.ALIGN_LEFT,1,normal)
                        val cd = (lstforpdf[i].ckq)?.toInt()
                        insertCell(detail,"$cd", Element.ALIGN_LEFT,1,normal)

                    } else {

                        insertCell(detail,"Chaddi", Element.ALIGN_LEFT,1,normal)
                        ckri =
                            lstforpdf[i].ckmm.toString() + "*" + lstforpdf[i].ckw.toString() + "*" + lstforpdf[i].ckq.toString()

                        insertCell(detail,"$ckri", Element.ALIGN_LEFT,1,normal)
                        insertCell(detail,"${lstforpdf[i].cdiq}", Element.ALIGN_LEFT,1,normal)

                    }

                    insertCellwithnopadding(detail,"${lstforpdf[i].ckcft}",
                        Element.ALIGN_RIGHT,1,normal)
                    noBorderCell(detail,2)


                }

                if (lstforpdf[i].mmm!=0) {

                    insertCell(detail,"MCD", Element.ALIGN_LEFT,1,normal)
                    val mcd = lstforpdf[i].mmm.toString() + "*" + lstforpdf[i].mw.toString()

                    insertCell(detail, mcd, Element.ALIGN_LEFT,1,normal)

                    insertCell(detail, "${lstforpdf[i].mq}", Element.ALIGN_LEFT,1,normal)

                    insertCellwithnopadding(detail, "${lstforpdf[i].mcft}",
                        Element.ALIGN_RIGHT,1,normal)
                    noBorderCell(detail,2)
                }

                if (lstforpdf[i].sw!=0) {
                    insertCell(detail,"Sigmet", Element.ALIGN_LEFT,1,normal)
                    val sig = lstforpdf[i].sw.toString() + "*" + lstforpdf[i].sh.toString() + "*" + lstforpdf[i].sl.toString()
                    insertCell(detail,sig, Element.ALIGN_LEFT,1,normal)
                    insertCell(detail,"${lstforpdf[i].sq}", Element.ALIGN_LEFT,1,normal)
                    insertCellwithnopadding(detail,"${lstforpdf[i].scft}",
                        Element.ALIGN_RIGHT,1,normal)
                    noBorderCell(detail,2)
                }

                no += 1
                tcft += lstforpdf[i].tcft!!
                tamnt += lstforpdf[i].amnt!!
                insertCellrowspan(detail,"${lstforpdf[i].vno} ", Element.ALIGN_RIGHT,1,2,normalred)
                insertCellwithnopadding(detail,"Total ", Element.ALIGN_RIGHT,2,normal)
                insertCellwithnopadding(detail,"${lstforpdf[i].tcft}",
                    Element.ALIGN_RIGHT,1,normalbold)
                insertCellrowspan(detail,"Semi Total", Element.ALIGN_CENTER,1,2,normal)
                noBorderCell(detail,1)


                insertCell(detail,"Rate ", Element.ALIGN_RIGHT,2,normal)
                insertCellwithnopadding(detail,"${lstforpdf[i].rate}", Element.ALIGN_RIGHT,1,normal)
                noBorderCell(detail,1)

                insertCellwithnopadding(detail,"Amount ", Element.ALIGN_RIGHT,3,normal)
                insertCellwithnopadding(detail,"${lstforpdf[i].amnt}",
                    Element.ALIGN_RIGHT,1,normalbold)
                insertCell(detail,"$tamnt", Element.ALIGN_CENTER,1,normal)
                noBorderCell(detail,1)

                doc.add(detail)

                addLineSeperator(doc)
            }

            val t= PdfPTable(floatArrayOf(0.5f,1f,1f,0.50f))
            val dc= DecimalFormat("###.##")
            val ftcft=dc.format(tcft)
            t.widthPercentage=80f
            insertCell(t,"Record", Element.ALIGN_CENTER,1,normal)
            insertCell(t,"Total CFT", Element.ALIGN_CENTER,1,normal)
            insertCell(t,"Total Amount", Element.ALIGN_CENTER,1,normal)
            noBorderCell(t,1)

            insertCell(t,"$no", Element.ALIGN_CENTER,1,normalbold)

            insertCell(t,"$ftcft", Element.ALIGN_CENTER,1,normalbold)
            val f= DecimalFormat("##,##,###")
            val famnt=f.format(tamnt)
            insertCell(t,"$famnt", Element.ALIGN_CENTER,1,normalbold)
            noBorderCell(t,1)
            doc.add(t)
            doc.close()
            println("Done")
            doc.close()
            Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
            if (i==1) {
                printPDF()
            }
            else
            {
                openpdf()
            }
        }
        catch (e: FileNotFoundException)
        {
            e.printStackTrace()
        }
        catch (e: DocumentException)
        {
            e.printStackTrace()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
    }

    private fun addTitlePage(doc: Document, normalbold: Font) {
        if (etfromdate.text.toString().isNotEmpty() && ettodate.text.toString().isNotEmpty())
        {
            addNewIem(doc,"${etfromdate.text.toString()} To ${ettodate.text.toString()}",
                Element.ALIGN_CENTER,normalbold)
        }
    }

    private fun addNewIem(document: Document, text:String, align:Int, f: Font)  {
        val chunk= Chunk(text,f)
        val paragraph= Paragraph(chunk)
        paragraph.alignment=align
        document.add(paragraph)
    }
    private fun insertCellrowspan(table: PdfPTable, text: String, align: Int, colspan: Int, rowspan:Int, font: Font) {

        //create a new cell with the specified Text and Font
        val cell = PdfPCell(Phrase(text.trim { it <= ' ' }, font))
        //set the cell alignment
        cell.horizontalAlignment = align
        cell.verticalAlignment= Element.ALIGN_MIDDLE
        //cell.isUseAscender=true
        cell.paddingRight=4f
        cell.paddingBottom=5f
        cell.paddingLeft=4f
        //cell.setPadding(5f)
        //set the cell column span in case you want to merge two or more cells
        cell.colspan = colspan
        cell.rowspan=rowspan
        //in case there is no text and you wan to create an empty row
        if (text.trim { it <= ' ' }.equals("", ignoreCase = true)) {
            cell.minimumHeight = 10f
        }

        //add the call to the table
        table.addCell(cell)
    }
    private fun insertCell(table: PdfPTable, text: String, align: Int, colspan: Int, font: Font) {

        //create a new cell with the specified Text and Font
        val cell = PdfPCell(Phrase(text.trim { it <= ' ' }, font))
        //set the cell alignment
        cell.horizontalAlignment = align
        cell.verticalAlignment= Element.ALIGN_MIDDLE
        //cell.isUseAscender=true
        cell.paddingRight=4f
        cell.paddingBottom=5f
        cell.paddingLeft=4f
        //cell.setPadding(5f)
        //set the cell column span in case you want to merge two or more cells
        cell.colspan = colspan
        //in case there is no text and you wan to create an empty row
        if (text.trim { it <= ' ' }.equals("", ignoreCase = true)) {
            cell.minimumHeight = 10f
        }

        //add the call to the table
        table.addCell(cell)
    }
    private fun insertCellwithnopadding(table: PdfPTable, text: String, align: Int, colspan: Int, font: Font) {

        //create a new cell with the specified Text and Font
        val cell = PdfPCell(Phrase(text.trim { it <= ' ' }, font))
        //set the cell alignment
        cell.horizontalAlignment = align
        cell.verticalAlignment= Element.ALIGN_MIDDLE
        //cell.isUseAscender=true
        cell.paddingRight=4f
        cell.paddingBottom=5f
        /*cell.paddingLeft=4f*/
        //cell.setPadding(5f)
        //set the cell column span in case you want to merge two or more cells
        cell.colspan = colspan
        //in case there is no text and you wan to create an empty row
        if (text.trim { it <= ' ' }.equals("", ignoreCase = true)) {
            cell.minimumHeight = 10f
        }

        //add the call to the table
        table.addCell(cell)
    }
    private fun noBorderCell(table: PdfPTable, colspan: Int)
    {
        val cell = PdfPCell(Phrase(""))
        cell.colspan = colspan
        cell.border= Rectangle.NO_BORDER
        table.addCell(cell)
    }
    private fun addLineSeperator(document: Document) {

        var lineSeparator= LineSeparator()
        var color= BaseColor(0,0,0,0)
        lineSeparator.lineColor=color
        addLineSpace(document)
        document.add(Chunk(lineSeparator))
        addLineSpace(document)
    }
    private fun addLineSpace(document: Document) {
        document.add(Paragraph(""))
    }
    private fun printPDF() {
        val printManager=context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            var printDocumentAdapter= PdfDocumentAdapter(this@Drum_List,Common.getAppPath(this@Drum_List)+ file_name)
            printManager.print("Document",printDocumentAdapter, PrintAttributes.Builder().build())
        }catch (e:Exception)
        {
            Log.e("ST",""+e.message)
        }
    }
}
