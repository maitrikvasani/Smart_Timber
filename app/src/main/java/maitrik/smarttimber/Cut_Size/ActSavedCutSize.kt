package maitrik.smarttimber.Cut_Size

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import maitrik.smarttimber.BaseActivity
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.CutSizeCFTMasterModel
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import java.text.DecimalFormat

class ActSavedCutSize : BaseActivity() {


    lateinit var rvItems : RecyclerView
    lateinit var tvTotalCFT : TextView
    lateinit var tvTotalCMT : TextView
    lateinit var tvTotalNOS : TextView
    lateinit var tvTotalAmount : TextView
    val strTAG = "ActSavedCutSize"
    var id:Int = 0
    var arrItems = ArrayList<CutSizeCFTMasterModel>()
    var arrSavedItems = ArrayList<CutSize>()
    var arrSubItemId = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.act_saved_cut_size)
        ViewGroup.inflate(this,R.layout.act_saved_cut_size,base_llSubMainContainer)
        initialize()
        getIntentData()
        clickEvent()
//        setData()
    }

    private fun clickEvent() {
        base_ivAdd.setOnClickListener {
            val intent = Intent(applicationContext,ActCutSizeCFT::class.java)
            intent.putExtra("MasterId",id)
            startActivity(intent)
        }
    }

    private fun getIntentData(){
        if (intent!=null){
            id=intent.getIntExtra("mid",0)
            var name=intent.getStringExtra("name")
            base_tvTitle.text = name
            var db= DBHandler(this)
            var list=db.getIdDetailOfCutSizeList(id,this)
            var subId = 0
            arrSubItemId = arrayListOf()

            for (i in 0 until list.size){
                if (!arrSubItemId.contains(list[i].subid)){
                    arrSubItemId.add(list[i].subid)
                }
            }

            var subList:ArrayList<CutSize> = arrayListOf()
            arrItems = arrayListOf()
            var amount = 0.0
            var nos = 0
            if (arrSubItemId.size > 0){
                for (i in 0 until arrSubItemId.size){
                    subList = db.getSubItemIdDetailOfCutSizeList(arrSubItemId[i],this)
                    App.showLog(strTAG,"Rate id = ${arrSubItemId[i]} &&  ${db.getRateCutSize(arrSubItemId[i])}")
                    amount += App.getCutSizeAmountInt(
                        App.getCutSizeTotalCFT(subList),
                        db.getRateCutSize(arrSubItemId[i]).toDouble()
                    )
                    val cutSizeSubItemList = db.getCutSizeHeightWidth(arrSubItemId[i])
                    App.showLog(strTAG,"sdf ${cutSizeSubItemList.height} & ${cutSizeSubItemList.width} & ${cutSizeSubItemList.rate}")
                    arrItems.add(CutSizeCFTMasterModel(cutSizeSubItemList.width,cutSizeSubItemList.height,subList,db.getRateCutSize(arrSubItemId[i]).toDouble()))
                }
            }
            val format = DecimalFormat("##,##,###")
            val formatAmount = format.format(amount)
            tvTotalAmount.text = formatAmount
            setData(arrItems)
            App.showLog(strTAG,"Size of ArrItems : ${arrItems.size}")

        }
    }

    private fun setData(arrItems: ArrayList<CutSizeCFTMasterModel>) {
        App.showLog(strTAG,"Go SetData Function")
        var totalCFT = 0.0
        var nos = 0
        App.showLog(strTAG,"Sized.... ${this.arrItems.size}")
            for (i in 0 until this.arrItems.size){
//                App.showLog(strTAG,"Sized sublist ${this.arrItems[i].arrItems.size}")
                for (j in 0..this.arrItems[i].arrItems.size-1){
                    totalCFT = totalCFT + arrItems[i].arrItems[j].cft!!
//                    App.showLog(strTAG,"Total CFt : $totalCFT")
                }
            }
            val decimalFormat = DecimalFormat("###.##")
            val formateTotalCFT = decimalFormat.format(totalCFT)
//            tvTotalCFT.text = "Total CFT : "+formateTotalCFT.toString()
        App.getTotalCFT(tvTotalCFT,tvTotalCMT,tvTotalNOS,tvTotalAmount,arrItems)
        rvItems.layoutManager = GridLayoutManager(this,3)
        rvItems.adapter = AdapterSavedCutSize(this, this.arrItems,tvTotalCFT,tvTotalCMT,tvTotalNOS,tvTotalAmount,id)
    }

    private fun initialize() {
        base_ivBack.visibility = View.VISIBLE
        base_tvTitle.visibility = View.VISIBLE
        base_Toolbar.visibility = View.VISIBLE
        base_ivAdd.visibility = View.VISIBLE
        rvItems = findViewById(R.id.actCutSizeSaved_rvItems)
        tvTotalCFT = findViewById(R.id.actCutSizeSaved_tvTotalCFT)
        tvTotalCMT = findViewById(R.id.actCutSizeSaved_tvTotalCMT)
        tvTotalNOS = findViewById(R.id.actCutSizeSaved_tvTotalNOS)
        tvTotalAmount = findViewById(R.id.actCutSizeSaved_tvTotalAmount)
    }

    override fun onResume() {
        arrSubItemId = arrayListOf()
       getIntentData()
        super.onResume()
    }
}


