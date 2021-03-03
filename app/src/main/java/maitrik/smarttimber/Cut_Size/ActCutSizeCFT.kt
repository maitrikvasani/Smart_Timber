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
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_splash.*
import maitrik.smarttimber.Adapter.AdapterCutSize
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.CustomKeyboardView
import maitrik.smarttimber.TimberCalculator.components.keyboard.OnNextClickListener
import java.text.DecimalFormat
import kotlin.math.sign

class ActCutSizeCFT : AppCompatActivity() {

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
    var arrItems = ArrayList<CutSize>()
    var TAG = "CUTSIZE"
    var LENGTHDIF = "LENGTHDIFFERENCE"
    var LENGHT = "LENGTH"
    var QTY = "QTY"
    var HEIGHT = "HEIGHT"
    var WIDTH = "WIDTH"
    var lastFocus = "LastFocus"
    val decimalFormat = DecimalFormat()
    private lateinit var keyboard: CustomKeyboardView
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_cut_size_cft)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        delegate.applyDayNight()
        initialize()
        clickEvent()

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
//                    tvAmount.text = getTotalAmount(tvCFT.text.toString().toDouble(),p0.toString().toInt()).toString()
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

        tvEqual.setOnClickListener {
            if (etRate.text.isNotEmpty()){
            tvAmount.text = getTotalAmount(tvCFT.text.toString().toDouble(),etRate.text.toString().toDouble()).toString()
            }
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
            tvAmount.text = getTotalAmount(cft, etRate.text.toString().toDouble())
        }
//        tvAmount.text = fm
    }

    fun getTotalAmount(cft:Double,rate:Double):String{
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
//                savedialog()
                //  Toast.makeText(this,"Save",Toast.LENGTH_SHORT).show()
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

}
