package maitrik.smarttimber.Cut_Size

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.act_base.*
import kotlinx.android.synthetic.main.activity_cutsize_list.*
import maitrik.smarttimber.Adapter.AdapterEditCutSize
import maitrik.smarttimber.BaseActivity
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.CutSizeSubItemModel
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.CustomKeyboardView
import maitrik.smarttimber.TimberCalculator.components.keyboard.OnNextClickListener
import java.text.DecimalFormat

class ActCutSizeSubItemList : BaseActivity() {
    var db=DBHandler(this@ActCutSizeSubItemList)
    var id:Int = 0
    var subId:Int = 0
    lateinit var etLength:EditText
    lateinit var etQty:EditText
    lateinit var etRate:EditText
    lateinit var etEditWidth:EditText
    lateinit var etEditHeight:EditText
    lateinit var tvHeight:TextView
    lateinit var tvWidth:TextView
    lateinit var tvTotalCFT:TextView
    lateinit var tvTotalCMT:TextView
    lateinit var ivEditDone:ImageView
    lateinit var ivEditCancel:ImageView
    lateinit var llEditHW:LinearLayout
    lateinit var amount:TextView
    lateinit var btnUpdateRate:Button
    var arrEditCutSizeList : ArrayList<CutSize> = arrayListOf()
    lateinit var ivEditHeightWidth:ImageView
    private lateinit var keyboard: CustomKeyboardView
    lateinit var model : CutSizeSubItemModel

    val strTAG = "ActCutSizeSubItemList"
    var cft=0.0
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewGroup.inflate(this,R.layout.activity_cutsize_list,base_llSubMainContainer)
        initialize()
        getData(subId)
        clickEvent()
    }

    private fun getData(id: Int) {
        val db = DBHandler(this@ActCutSizeSubItemList)
        arrEditCutSizeList = db.getSubItemIdDetailOfCutSizeList(id, this@ActCutSizeSubItemList)
        amount.text = App.getCutSizeAmount(App.getCutSizeTotalCFT(arrEditCutSizeList).toDouble(),db.getRateCutSize(subId).toDouble())
        model = db.getCutSizeHeightWidth(subId)
        App.showLog(strTAG,"Height ${model.height}  & Width ${model.width}")
        tvWidth.text = App.getFormatData(model.width.toString())
//        tvWidth.text = model.width.toString()
//        tvHeight.text = model.height.toString()
        tvHeight.text = App.getFormatData(model.height.toString())
//        etEditHeight.setText(App.getFormatData(model.height.toString()))
        etEditHeight.setText("${model.height}")
        etEditWidth.setText("${model.width}")
//        etEditWidth.setText(App.getFormatData(model.width.toString()))
        val adapter = AdapterEditCutSize(this, arrEditCutSizeList, et_csl_width, et_csl_height, tvTotalCFT, tvTotalCMT)
        lv_csdetail.adapter = adapter
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initialize() {
        base_ivBack.visibility = View.VISIBLE
        base_toolbar.visibility = View.VISIBLE
        base_tvTitle.visibility = View.VISIBLE
        base_tvTitle.text = "Edit"

        etLength=findViewById<EditText>(R.id.et_lcs_length)
        etQty=findViewById<EditText>(R.id.et_lcs_qty)
        etRate=findViewById<EditText>(R.id.et_lcs_rate)
        amount=findViewById<TextView>(R.id.tv_lcs_amount)
        tvWidth=findViewById<TextView>(R.id.et_csl_width)
        tvHeight=findViewById<TextView>(R.id.et_csl_height)
        tvTotalCFT=findViewById<TextView>(R.id.editCutSize_tvCFT)
        tvTotalCMT=findViewById<TextView>(R.id.editCutSize_tvCMT)


        etEditWidth=findViewById(R.id.et_editCutSize_width)
        etEditHeight=findViewById(R.id.et_editCutSize_height)
        ivEditDone=findViewById(R.id.cutsizelist_ivDone)
        ivEditCancel=findViewById(R.id.cutsizelist_ivCancel)
        llEditHW=findViewById(R.id.llEditCutSizeHW)
        btnUpdateRate=findViewById<Button>(R.id.btn_lcs_updateRate)
        ivEditHeightWidth=findViewById<ImageView>(R.id.cutsizelist_ivEdit)
        id=intent.getIntExtra("mid",0)
        subId=intent.getIntExtra("subId",0)
        etRate.setText(App.checkPointZeroValidation(db.getRateCutSize(subId).toString()))
        etRate.setSelection(etRate.length())
        App.showLog("From From","mId : $id && SubId : $subId")
        val name=intent.getStringExtra("name")
//        base_tvTitle.text = name
        keyboard = findViewById(R.id.editCutSize_customKeyboardView)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL,etEditHeight)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, etEditWidth)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, etLength)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, etQty)
        etLength.clearFocus()
        etLength.requestFocus()
//        etLength.setSelection(etLength.text.length)
        CustomKeyboardView.onNextClickFun(object : OnNextClickListener{
            override fun onClick(et: EditText) {
                super.onClick(et)
                et.setSelection(et.text.length)
                if (et.text.toString().isNotEmpty()){
                    if (!etQty.isFocused) {
                        et.focusSearch(View.FOCUS_FORWARD).let {
                            it.requestFocus()
                            return
                        }
                    } else {
                        btn_lcs_cft.performClick()
//                        btnCFT.performClick()
                    }
                }
            }
        })
    }

    private fun clickEvent() {
        ivEditHeightWidth.setOnClickListener {
//            val intent = Intent(applicationContext,ActCutSizeCFT::class.java)
//            intent.putExtra("MasterId",id)
//            startActivity(intent)
//            startActivity(Intent(applicationContext,ActCutSizeCFT::class.java))
        }
        etRate.setOnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode  == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE) )){
                if (etRate.text.toString().isNotEmpty()){
                    amount.text = App.getCutSizeAmount(App.getCutSizeTotalCFT(arrEditCutSizeList),etRate.text.toString().toDouble())
                    db.editCutSizeRate(subId,etRate.text.toString().toDouble())
                }else{
                    etRate.requestFocus()
                    etRate.error = "Enter Rate!!"
                }
            }
            return@setOnEditorActionListener false
        }

        btnUpdateRate.setOnClickListener{
            if (etRate.text.toString().isNotEmpty()){
                amount.text = App.getCutSizeAmount(App.getCutSizeTotalCFT(arrEditCutSizeList),etRate.text.toString().toDouble())
                db.editCutSizeRate(subId,etRate.text.toString().toDouble())
            }else{
                etRate.requestFocus()
                etRate.error = "Enter Rate!!"
            }
        }

        btn_lcs_cft.setOnClickListener {
            if (etLength.text.toString().isEmpty())
            {
                etLength.error="Enter Length"
            }
            else if (etQty.text.toString().isEmpty())
            {
                etQty.error="Enter Qty"
            }
            else{
                insertcutsize()
                etLength.setText("")
                etQty.setText("")
                etLength.requestFocus()
            }
        }

        ivEditCancel.setOnClickListener {
            llEditHW.visibility = View.GONE
        }

        ivEditDone.setOnClickListener {
            if (etEditHeight.text.isNotEmpty() && etEditWidth.text.isNotEmpty()){
                val cutSizeSubItemModel = CutSizeSubItemModel(etEditWidth.text.toString().toDouble(),etEditHeight.text.toString().toDouble(),subId.toDouble())
                App.showLog(strTAG,"Height ${cutSizeSubItemModel.height}  & Width ${cutSizeSubItemModel.width}")
                db.editCutSizeHeightWidth(subId,cutSizeSubItemModel)
                db.editCutSizeHeightWidthCUTSIZETABLE(cutSizeSubItemModel.width,cutSizeSubItemModel.height,subId)
                for (j in 0..arrEditCutSizeList.size-1){
                    val cft = cutSizeSubItemModel.width * cutSizeSubItemModel.height * arrEditCutSizeList[j].length  * arrEditCutSizeList[j].qty / 144
                    db.editCutSizeCFTCUTSIZETABLE(cft,arrEditCutSizeList[j].id)
                }
                val abc = db.getIdDetailOfCutSizeList(subId,this)
                for (i in 0..abc.size-1){
                    App.showLog("Width","${abc[i].width} && ${abc[i].height}")
                }
                val model = db.getCutSizeHeightWidth(subId)
                App.showLog(strTAG,"Height ${model.height}  & Width ${model.width}")
                window.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                llEditHW.visibility = View.GONE
                getData(subId)

//                hideSoftKeyboard()


            }else if (etEditWidth.text.isEmpty()){
                etEditWidth.error = "Enter Width"
            }else if (etEditHeight.text.isEmpty()){
                etEditHeight.error = "Enter Width"
            }
        }

        ivEditHeightWidth.setOnClickListener {
            etEditHeight.setText("${model.height}")
            etEditWidth.setText("${model.width}")
            llEditHW.visibility = View.VISIBLE
        }
    }

    private fun setData() {

    }


//    fun hideSoftKeyboard()
//    {
//        val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.SHOW_FORCED)
//    }


//    fun hideSoftKeyboard()
//    {
//        this.currentFocus?.let { view ->
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//            imm?.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//
////        val view = currentFocus
////        if (view != null){
////            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.SHOW_FORCED)
////        }
//    }

    fun insertcutsize(){

//        var list=db.getIdDetailOfCutSizeList(id,this@ActCutSizeSubItemList)
        var list = db.getSubItemIdDetailOfCutSizeList(subId,applicationContext)
        var w=0.0
        var h=0.0
        for (i in 0..list.size-1)
        {
            w=tvWidth.text.toString().toDouble()
            h=tvHeight.text.toString().toDouble()
//            h=list[i].height
        }
        cft=h*w*etLength.text.toString().toDouble()*etQty.text.toString().toInt()/144
        var f=DecimalFormat("###.##")
       cft= f.format(cft).toDouble()
//        App.showLog(strTAG,"mID : $id && sId : $subId")
        var c=CutSize(w,h,etLength.text.toString().toDouble(),etQty.text.toString().toInt(),cft,id,subId)
       db.insertcutsize(c)
        list.add(c)
        var a=AdapterEditCutSize(this,list,et_csl_width,et_csl_height,tvTotalCFT,tvTotalCMT)
        lv_csdetail.adapter=a
    }

    override fun onBackPressed() {
        if (keyboard.isExpanded) {
            keyboard.translateLayout()
        } else {
            super.onBackPressed()
        }
    }
}
