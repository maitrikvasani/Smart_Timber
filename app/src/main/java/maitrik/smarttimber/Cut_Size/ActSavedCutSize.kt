package maitrik.smarttimber.Cut_Size

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.text.pdf.parser.Line
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.CutSizeCFTMasterModel
import maitrik.smarttimber.R

class ActSavedCutSize : AppCompatActivity() {


    lateinit var rvItems : RecyclerView
    var arrItems = ArrayList<CutSizeCFTMasterModel>()
    val arrSavedItems = ArrayList<CutSize>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_saved_cut_size)
        initialize()
        setData()
        getIntentData()
    }

    private fun getIntentData(){
        var data  = intent.getSerializableExtra("SAVE") as ArrayList<CutSize>
        arrSavedItems.addAll(data)
//        arrItems.addAll(CutSizeCFTMasterModel(arrItems[0].width,arrItems[0].height,arrItems))
    }

    private fun setData() {
        rvItems.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvItems.adapter = AdapterSavedCutSize(this,arrItems)
    }

    private fun initialize() {
        rvItems = findViewById(R.id.actCutSizeSaved_rvItems)
    }
}


