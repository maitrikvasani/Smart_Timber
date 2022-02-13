package maitrik.smarttimber.Cut_Size

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.cspartynnamelayout.view.*
import maitrik.smarttimber.Adapter.AdapterCutSize
import maitrik.smarttimber.BaseActivity
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.CutSizeSubItemModel
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.CustomKeyboardView
import maitrik.smarttimber.TimberCalculator.components.keyboard.OnNextClickListener
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sign

class ActCutSizeCFT : BaseActivity() {

    lateinit var  sharedPref : SharedPreferences
    lateinit var etWidth : EditText
    lateinit var etHeigth : EditText
    lateinit var etLengthDifference : EditText
    lateinit var etLength : EditText
    lateinit var etQty : EditText
    lateinit var btnCFT : Button
    lateinit var tvCFT : TextView
    lateinit var tvCMT : TextView
    lateinit var tvNOS : TextView
    lateinit var rvItems : RecyclerView
    lateinit var etRate : EditText
    lateinit var tvAmount : TextView
    lateinit var ivAdd : ImageView
    lateinit var tvEqual : TextView
    lateinit var btnEqual : Button
    var masterId = -1
    val strTAG = "ActCutSizeCFT"
    var arrItems = ArrayList<CutSize>()
    var TAG = "CUTSIZE"
    var LENGTHDIF = "LENGTHDIFFERENCE"
    var LENGHT = "LENGTH"
    var QTY = "QTY"
    var RATE = "RATE"
    var HEIGHT = "HEIGHT"
    var WIDTH = "WIDTH"
    var lastFocus = "LastFocus"
    val decimalFormat = DecimalFormat()
    private lateinit var keyboard: CustomKeyboardView
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.act_cut_size_cft)
        ViewGroup.inflate(this,R.layout.act_cut_size_cft,base_llSubMainContainer)
        var db = DBHandler(applicationContext)
        initialize()
        clickEvent()
        getIntentData()
            arrItems = readData()
        for (i in 0 until arrItems.size){
            Log.d("From","QTY ${arrItems[i].width}")
        }
        getSharedData()

        if (arrItems.size > 0){

//            setRecyclerViewLayout(rvItems,this)
            rvItems.layoutManager = LinearLayoutManager(this)
            rvItems.adapter = AdapterCutSize(this, arrItems, tvCFT, tvCMT, keyboard, tvNOS)
            rvItems.scrollToPosition(arrItems.size-1)
            etWidth.setText("${arrItems[0].width}")
            etHeigth.setText("${arrItems[0].height}")
            getCutsizeTotalCFT()
        }

        etWidth.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isNotBlank()){
                    editList(p0, WIDTH)
                }
            }

        })

        etHeigth.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isNotEmpty()){
                    editList(p0,HEIGHT)
                }
            }

        })

//        etRate.addTextChangedListener(object : TextWatcher{
//            override fun afterTextChanged(p0: Editable?) {
//                if (p0!!.isNotEmpty()){
//                    tvAmount.text = getCutSizeAmount(tvCFT.text.toString().toDouble(),p0.toString().toInt()).toString()
//                }
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//        })
    }

    private fun getIntentData() {
        if (intent != null){
           masterId =  intent.getIntExtra("MasterId",-1)
            App.showLog(strTAG,"MasterId : $masterId")
        }


    }

    private fun editList(p0: CharSequence, type: String) {
        if (type == WIDTH){
            for (i in 0 until arrItems.size){
                arrItems[i].width = p0.toString().toDouble()
                arrItems[i].cft = getCFT(arrItems[i].width,arrItems[i].height,arrItems[i].length,arrItems[i].qty)
                arrItems[i].cmt = getCMT(arrItems[i].cft!!)
                rvItems.layoutManager = LinearLayoutManager(this)
                rvItems.adapter = AdapterCutSize(this, arrItems, tvCFT, tvCMT, keyboard, tvNOS)
                rvItems.scrollToPosition(arrItems.size-1)
                getCutsizeTotalCFT()
            }
        }else{
            for (i in 0 until arrItems.size){
                arrItems[i].height = p0.toString().toDouble()
                arrItems[i].cft = getCFT(arrItems[i].width,arrItems[i].height,arrItems[i].length,arrItems[i].qty)
                arrItems[i].cmt = getCMT(arrItems[i].cft!!)
                rvItems.layoutManager = LinearLayoutManager(this)
                rvItems.adapter = AdapterCutSize(this, arrItems, tvCFT, tvCMT, keyboard, tvNOS)
                rvItems.scrollToPosition(arrItems.size-1)
                getCutsizeTotalCFT()
            }
        }

    }



    private fun getSharedData() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        etWidth.setText(sharedPref.getString(WIDTH,""))
        etWidth.setSelection(etWidth.text.length)
        etWidth.placeCursorEnd()
        
        etHeigth.setText(sharedPref.getString(HEIGHT,""))
        etHeigth.setSelection(etHeigth.text.length)
        etHeigth.placeCursorEnd()
        
        etLengthDifference.setText(sharedPref.getString(LENGTHDIF,""))
        etLengthDifference.placeCursorEnd()
        
        etLength.setText(sharedPref.getString(LENGHT,""))
        etLength.placeCursorEnd()

        etQty.setText(sharedPref.getString(QTY,""))
        etQty.placeCursorEnd()

        etRate.setText(sharedPref.getString(RATE,""))
        etRate.placeCursorEnd()

        val lastFocus =sharedPref.getString(lastFocus,"W")
        if (lastFocus == "W"){
           etWidth.requestFocus()
        }else if (lastFocus == "H"){
            etHeigth.requestFocus()
        }else if (lastFocus == "L"){
            etLength.requestFocus()
        }else if (lastFocus == "Q"){
            etQty.requestFocus()
        }else{
            etWidth.requestFocus()
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initialize() {
        decimalFormat.isDecimalSeparatorAlwaysShown = false
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        etWidth = findViewById(R.id.cutSize_etWidth)
        etHeigth = findViewById(R.id.cutSize_etHeight)
        etLengthDifference = findViewById(R.id.cutSize_etLengthDifference)
        etLength = findViewById(R.id.cutSize_etLength)
        etQty = findViewById(R.id.cutSize_etQty)
        btnCFT = findViewById(R.id.cutSize_btnCft)
        tvCFT = findViewById(R.id.cutSize_tvCft)
        tvCMT = findViewById(R.id.cutSize_tvCmt)
        tvNOS = findViewById(R.id.cutSize_tvNOS)
        rvItems = findViewById(R.id.cutSize_rvItems)
        etRate = findViewById(R.id.cutSize_etRate)
        tvAmount = findViewById(R.id.cutSize_tvAmount)
        keyboard = findViewById(R.id.cutSize_customKeyboardView)
        ivAdd = findViewById(R.id.cutSize_ivAdd)
        tvEqual = findViewById(R.id.cutSize_tvEqual)
        btnEqual = findViewById(R.id.cutSize_btnEqual)
        base_ivBack.visibility = View.VISIBLE
        base_ivSave.visibility = View.VISIBLE
        base_ivClose.visibility = View.VISIBLE
        base_tvTitle.visibility = View.VISIBLE
        base_tvTitle.text = "Cut Size CFT"



        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, etHeigth)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, etWidth)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, etLengthDifference)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, etLength)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, etQty)

        etWidth.requestFocus()
        etWidth.setSelection(etWidth.text.length)
        CustomKeyboardView.onNextClickFun(object : OnNextClickListener {
            override fun onClick(et: EditText) {
                super.onClick(et)
                et.setSelection(et.text.length)

                if (et == etLengthDifference){
                    etLength.requestFocus()
                    return
                }

                // For editText value is empty so it is working next button
                if (et == etLengthDifference){
                    if (!etQty.isFocused) {
                        et.focusSearch(View.FOCUS_FORWARD).let {
                            it.requestFocus()
                            return
                        }
                    }
                }
                // For without enter value can't move forward
                if (et.text.toString().isNotEmpty()) {
                    if (!etQty.isFocused) {
                        et.focusSearch(View.FOCUS_FORWARD).let {
                            it.requestFocus()
                            return
                        }
                    } else {
                        btnCFT.performClick()
                    }
                }
            }

        })

//        keyboard.translateLayout()


    }

    private fun readData() : ArrayList<CutSize> {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json = sharedPref.getString(TAG,"")
        if (!json.isNullOrEmpty()){
            Log.e("From","Json ${json.toString()}")
            var type= object : TypeToken<ArrayList<CutSize>>(){}.type
            arrItems=  gson.fromJson(json,type)
        }
        return arrItems
    }
    companion object{
        var click : Boolean = true
        fun EditText.placeCursorEnd(){
            this.setSelection(this.text.length)
        }
    }


    private fun clickEvent() {
        btnCFT.setOnClickListener {
            getCutSizeCFT(
                etWidth.text.toString().toDouble(),
                etHeigth.text.toString().toDouble(),
                etLength.text.toString().toDouble(),
                etQty.text.toString().toInt()
            )
            getCutsizeTotalCFT()
            if (etLengthDifference.text.toString().isEmpty()) {
                etLength.setText("")
                etQty.setText("")
                etLength.requestFocus()
            } else {
                etLength.setText("${etLength.text.toString().toDouble() + etLengthDifference.text.toString().toDouble()}")
                etQty.setText("")
                etQty.requestFocus()
            }
        }

        ivAdd.setOnClickListener {
            val intent = Intent(this,ActSavedCutSize::class.java)
            intent.putExtra("SAVE",arrItems)
            startActivity(intent)
        }

        btnEqual.setOnClickListener {
            if (etRate.text.isNotEmpty()){
            tvAmount.text = getCutSizeAmount(tvCFT.text.toString().toDouble(),etRate.text.toString().toDouble()).toString()
            }
        }

        etRate.setOnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode  == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE) )){
                if (etRate.text.toString().isNotEmpty()){
                    tvAmount.text = App.getCutSizeAmount(App.getCutSizeTotalCFT(arrItems),etRate.text.toString().toDouble())
                }else{
                    etRate.requestFocus()
                    etRate.error = "Enter Rate!!"
                }
            }
            return@setOnEditorActionListener false
        }

        base_ivSave.setOnClickListener {
            savedialog()
        }
        base_ivBack.setOnClickListener {
            onBackPressed()
        }
        base_ivClose.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setIcon(R.drawable.ic_clear_red_24dp)
                .setTitle("Clear all the entries ?")
                .setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        arrItems.clear()
                        etWidth.setText("")
                        etHeigth.setText("")
                        etLengthDifference.setText("")
                        etLength.setText("")
                        etQty.setText("")
                        etRate.setText("")
                        etWidth.requestFocus()
                        val edit = sharedPref.edit()
                        edit.clear()
                        edit.apply()
                        rvItems.layoutManager = LinearLayoutManager(this)
                        rvItems.adapter = AdapterCutSize(
                            this,
                            arrItems,
                            tvCFT,
                            tvCMT,
                            keyboard,
                            tvNOS
                        )
                        getCutsizeTotalCFT()
                    })
                .setNegativeButton(
                    "No",
                    DialogInterface.OnClickListener { dialogInterface, i -> })
            builder.show()
        }
    }


    fun getCFT(w: Double, h: Double, l: Double, q: Int):Double{
        val cft =  w * h * l * q / 144
        val f = DecimalFormat("###.##")
        f.isDecimalSeparatorAlwaysShown = false
        val fcft = f.format(cft).toDouble()
        return  fcft
    }

    fun getCMT(cft : Double):Double{
        val cmt = cft / 35.30
        val fm = DecimalFormat("###.####")
        fm.isDecimalSeparatorAlwaysShown = false
        val fcmt = fm.format(cmt).toDouble()
        return fcmt
    }

    fun getCutSizeCFT(w: Double, h: Double, l: Double, q: Int) {
        val cft = w * h * l * q / 144
        val cmt = cft / 35.30
//        val format = DecimalFormat()
//        format.isDecimalSeparatorAlwaysShown = false
        val f = DecimalFormat("###.##")
        val fm = DecimalFormat("###.####")
        val fcft = f.format(cft).toDouble()
        val fcmt = fm.format(cmt).toDouble()
        val c = CutSize(w, h, l, q, fcft, fcmt)
        arrItems.add(c)
        for (i in 0 until arrItems.size){
            Log.d("From","QTY ${arrItems[i].width}")
        }
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = AdapterCutSize(this,arrItems,tvCFT,tvCMT,keyboard,tvNOS)
        rvItems.scrollToPosition(arrItems.size-1)
    }
    fun getCutsizeTotalCFT() {
        var cft = 0.0
        var cmt = 0.0
        var nos = 0
        var amount = 0
        for (i in 0 until arrItems.size) {
            cft = (cft + arrItems[i].cft!!)
            cmt = (cmt + arrItems[i].cmt!!)
            nos = (nos + arrItems[i].qty)
        }
        val f = DecimalFormat("##.##")
        val f1 = DecimalFormat("##.####")
        val famount = DecimalFormat("##,##,###")
        f.isDecimalSeparatorAlwaysShown = false
        f1.isDecimalSeparatorAlwaysShown = false
        val fc = f.format(cft)
        val fm = f1.format(cmt)
//        amount = (cft*etRate.text.toString().toInt()).toInt()
        val fa = famount.format(amount)
        tvCFT.text = fc
        tvCMT.text = fm
        tvNOS.text = nos.toString()
        if (etRate.text.isNotEmpty()) {
            tvAmount.text = getCutSizeAmount(cft, etRate.text.toString().toDouble())
        }
//        tvAmount.text = fm
    }

    fun getCutSizeAmount(cft:Double,rate:Double):String{
        val formate = DecimalFormat("##,##,###")
        val amount = cft*rate
        return formate.format(amount)
    }

    override fun onBackPressed() {
        if (keyboard.isExpanded) {
            keyboard.translateLayout()
        } else {
            super.onBackPressed()
        }
        saveSharedData()
    }

    override fun onPause() {
        super.onPause()
        saveSharedData()
    }

    private fun saveSharedData() {
        val sharedPref :  SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val edit = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(arrItems)
        edit.putString(TAG,json)
        if (etWidth.text.toString().isNotEmpty()){
            edit.putString(WIDTH,etWidth.text.toString())
        }
        if (etHeigth.text.toString().isNotEmpty()){
            edit.putString(HEIGHT,etHeigth.text.toString())
        }
        if (etLengthDifference.text.toString().isNotEmpty()){
            edit.putString(LENGTHDIF,etLengthDifference.text.toString())
        }
        if (etLength.text.toString().isNotEmpty()){
            edit.putString(LENGHT,etLength.text.toString())
        }
        if (etQty.text.toString().isNotEmpty()){
            edit.putString(QTY,etQty.text.toString())
        }


            edit.putString(RATE,etRate.text.toString())

        if (etWidth.isFocused){
            edit.putString(lastFocus,"W")
        }
        if (etHeigth.isFocused){
            edit.putString(lastFocus,"H")
        }
        if (etLength.isFocused){
            edit.putString(lastFocus,"L")
        }
        if (etQty.isFocused){
            edit.putString(lastFocus,"Q")
        }
        edit.commit()
        edit.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater
        menuInflater.inflate(maitrik.smarttimber.R.menu.cutsize, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.cssave -> {
                savedialog()
                  Toast.makeText(this,"Save", Toast.LENGTH_SHORT).show()
            }
            R.id.csclr -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setIcon(R.drawable.ic_clear_red_24dp)
                    .setTitle("Clear all the entries ?")
                    .setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            arrItems.clear()
                            etWidth.setText("")
                            etHeigth.setText("")
                            etLengthDifference.setText("")
                            etLength.setText("")
                            etQty.setText("")
                            etWidth.requestFocus()
                            val edit = sharedPref.edit()
                            edit.clear()
                            edit.apply()
                            rvItems.layoutManager = LinearLayoutManager(this)
                            rvItems.adapter = AdapterCutSize(
                                this,
                                arrItems,
                                tvCFT,
                                tvCMT,
                                keyboard,
                                tvNOS
                            )
                            getCutsizeTotalCFT()
                        })
                    .setNegativeButton(
                        "No",
                        DialogInterface.OnClickListener { dialogInterface, i -> })
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setRecyclerViewLayout(rv : RecyclerView, context: Context)
    {
        val layoutManager = LinearLayoutManager(context)
//        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rv.layoutManager = layoutManager
    }

    fun savedialog() {
        if (tvCFT.text == "") {
            Toast.makeText(this, "Please Calculate CFT!!!", Toast.LENGTH_SHORT).show()
        } else if (masterId != -1){
            val db = DBHandler(applicationContext)
            var cutSizeSubItemModel = CutSizeSubItemModel(etWidth.text.toString().toDouble(),etHeigth.text.toString().toDouble(),0.0)
            if (etRate.text.toString().isEmpty()){
                db.insertCutSizeMasterItems(masterId,0.0,cutSizeSubItemModel)
            }else{
                db.insertCutSizeMasterItems(masterId,etRate.text.toString().toDouble(),cutSizeSubItemModel)
            }

            val lastSubId = db.getLastSubItemId()
            App.showLog(strTAG,"SubItemID : $lastSubId")
            insertcutsizecft(masterId,lastSubId)
            finish()
        }else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val inflater: LayoutInflater = layoutInflater
            val view: View = inflater.inflate(R.layout.cspartynnamelayout, null)
            var name = view.et_cs_name
            builder.setView(view)
                .setTitle("Save Record")
                .setIcon(R.drawable.ic_save_white_24dp)
                .setPositiveButton("SAVE") { _, _ ->
                    if (name.text.toString().isEmpty()) {
                        name.error = "Enter Name"
                        name.requestFocus()
                        Toast.makeText(this, "Please Enter Name!!", Toast.LENGTH_SHORT).show()
                    } else {
                        insertcutsizemaster(name.text.toString())
                        val db = DBHandler(applicationContext)
                        val lastid = getlastid()
                        var cutSizeSubItemModel = CutSizeSubItemModel(etWidth.text.toString().toDouble(),etHeigth.text.toString().toDouble(),0.0)
                        if (etRate.text.toString().isEmpty()){
                            db.insertCutSizeMasterItems(lastid,0.0,cutSizeSubItemModel)
                        }else{
                            App.showLog("mmm","rate ${etRate.text.toString()}")
                            db.insertCutSizeMasterItems(lastid,etRate.text.toString().toDouble(),cutSizeSubItemModel)
                        }

                        var lastSubId = db.getLastSubItemId()
                        App.showLog(strTAG,"SubItemID : $lastSubId")
                        insertcutsizecft(lastid,lastSubId)
                        val intent= Intent(this, ActSavedCutSize::class.java)
                        intent.putExtra("mid",lastid)
                        intent.putExtra("name",name.text.toString())
//                        activity.startActivity(intent)
//                        var intent = Intent(this, CutSizeList::class.java)
                        startActivity(intent)
                    }
                }
                .setNegativeButton(
                    "NO"
                ) { _, _ ->

                }
            val dialog = builder.create()
            dialog.show()
        }
    }

    fun insertcutsizemaster(name: String) {
        val context = this
        val db = DBHandler(context)
        val date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MM-yyyy")
        val formatDate = df.format(date)
        App.showLog(strTAG,"Date : $formatDate")
        val cutSize = CutSize(name,formatDate)
        db.insertcutsizemaster(cutSize)
    }

    fun getlastid(): Int {
        val context = this
        val db = DBHandler(context)
        val i = db.getlastid()
//        Toast.makeText(context, "$i", Toast.LENGTH_SHORT).show()
        return i
    }

    fun insertcutsizecft(masterId:Int,lastSubId:Int) {
        val context = this
        App.showLog(strTAG,"SubItem id :$lastSubId")
        val db = DBHandler(context)
//        val lastid = getlastid()
        for (i in 0..arrItems.size - 1) {
            val cs = CutSize(
                arrItems[i].width,
                arrItems[i].height,
                arrItems[i].length,
                arrItems[i].qty,
                arrItems[i].cft,
                masterId,
                lastSubId
            )
            db.insertcutsize(cs)
        }
    }

}
