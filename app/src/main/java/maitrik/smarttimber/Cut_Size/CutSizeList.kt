package maitrik.smarttimber.Cut_Size

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cut_size_list.*
import maitrik.smarttimber.Adapter.CutsizePartyList
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R

class CutSizeList : AppCompatActivity() {
    var cslist=ArrayList<CutSize>()
    internal lateinit var db: DBHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cut_size_list)
        db=DBHandler(this)
        cslist=db.getcutsizelist(this)
       val adapter=CutsizePartyList(this,cslist)
        lv_cs.adapter=adapter

    }
}
