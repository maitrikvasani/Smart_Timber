package maitrik.smarttimber.Cut_Size

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.android.synthetic.main.activity_cut_size_list.*
import maitrik.smarttimber.Adapter.AdapterCutSizePartyList
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R

class CutSizeList : AppCompatActivity() {
    var cslist=ArrayList<CutSize>()
    private lateinit var tvNoDataFound : AppCompatTextView
    private lateinit var lvNameList : ListView
    internal lateinit var db: DBHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cut_size_list)
        init()
        db=DBHandler(this)
        cslist=db.getcutsizelist(this)
        if (cslist.size == 0){
            tvNoDataFound.visibility = View.VISIBLE
            lvNameList.visibility = View.GONE
        }else{
            tvNoDataFound.visibility = View.GONE
            lvNameList.visibility = View.VISIBLE
            val adapter= AdapterCutSizePartyList(this,cslist,lvNameList,tvNoDataFound)
            lv_cs.adapter=adapter
        }


    }

    private fun init() {
        tvNoDataFound = findViewById(R.id.lv_tvNoDataFound)
        lvNameList = findViewById(R.id.lv_cs)
    }

    override fun onResume() {
        db=DBHandler(this)
        cslist=db.getcutsizelist(this)
        val adapter= AdapterCutSizePartyList(this,cslist,lvNameList,tvNoDataFound)
        lvNameList.adapter=adapter
        super.onResume()
    }
}
