package maitrik.smarttimber.Cut_Size

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.text.pdf.parser.Line
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.CutSizeCFTMasterModel
import maitrik.smarttimber.R

class ActSavedCutSize : AppCompatActivity() {


    lateinit var rvItems : RecyclerView
    lateinit var tvTotalCFT : TextView
    var arrItems = ArrayList<CutSizeCFTMasterModel>()
    val arrSavedItems = ArrayList<CutSize>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_saved_cut_size)
        initialize()
        getIntentData()
        setData()
    }

    private fun getIntentData(){
        var data  = intent.getSerializableExtra("SAVE") as ArrayList<CutSize>
        arrSavedItems.addAll(data)
        Log.d("save","size: ${arrSavedItems.size}")
//        arrItems.addAll(CutSizeCFTMasterModel(arrItems[0].width,arrItems[0].height,arrItems))
    }

    private fun setData() {
        arrItems.add(CutSizeCFTMasterModel(3.0,5.0,arrSavedItems))
        arrItems.add(CutSizeCFTMasterModel(3.0,5.0,arrSavedItems))
        arrItems.add(CutSizeCFTMasterModel(3.0,5.0,arrSavedItems))
        arrItems.add(CutSizeCFTMasterModel(3.0,5.0,arrSavedItems))
        arrItems.add(CutSizeCFTMasterModel(3.0,5.0,arrSavedItems))
        arrItems.add(CutSizeCFTMasterModel(3.0,5.0,arrSavedItems))
        arrItems.add(CutSizeCFTMasterModel(3.0,5.0,arrSavedItems))
        arrItems.add(CutSizeCFTMasterModel(3.0,5.0,arrSavedItems))
        arrItems.add(CutSizeCFTMasterModel(3.0,4.0,arrSavedItems))
        rvItems.layoutManager = GridLayoutManager(this,4)
        rvItems.adapter = AdapterSavedCutSize(this,arrItems,tvTotalCFT)
    }

    private fun initialize() {
        rvItems = findViewById(R.id.actCutSizeSaved_rvItems)
        tvTotalCFT = findViewById(R.id.actCutSizeSaved_tvTotalCFT)
    }
}


