package maitrik.smarttimber.Cut_Size

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cutsize_list.*
import maitrik.smarttimber.Adapter.CutsizeDetail
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import java.text.DecimalFormat

class cutsize_list : AppCompatActivity() {
    var db=DBHandler(this@cutsize_list)
    var id:Int = 0
    lateinit var l:EditText
    lateinit var q:EditText
    var cft=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cutsize_list)
         l=findViewById<EditText>(R.id.et_lcs_length)
         q=findViewById<EditText>(R.id.et_lcs_qty)
        id=intent.getIntExtra("mid",0)
        var name=intent.getStringExtra("name")
        val actionBar=supportActionBar
        actionBar!!.title=name
       // Toast.makeText(this,"$id",Toast.LENGTH_SHORT).show()
        var db=DBHandler(this@cutsize_list)
        var list=db.getiddetail(id,this@cutsize_list)

       var a=CutsizeDetail(this,list,et_csl_width,et_csl_height,tv_cs_cft,tv_cs_cmt)
        lv_csdetail.adapter=a
        btn_lcs_cft.setOnClickListener {
            if (l.text.toString().isEmpty())
            {
                l.error="Enter Length"
            }
            else if (q.text.toString().isEmpty())
            {
                q.error="Enter Qty"
            }
            else{
            insertcutsize()
            l.setText("")
            q.setText("")
            l.requestFocus()
            }
        }
    }
    fun insertcutsize(){

        var list=db.getiddetail(id,this@cutsize_list)
        var w=0.0
        var h=0.0
        for (i in 0..list.size-1)
        {
            w=list[i].width
            h=list[i].height
        }
        cft=h*w*l.text.toString().toDouble()*q.text.toString().toInt()/144
        var f=DecimalFormat("###.##")
       cft= f.format(cft).toDouble()
        var c=CutSize(w,h,l.text.toString().toDouble(),q.text.toString().toInt(),cft,id)
       db.insertcutsize(c)
        list.add(c)
        var a=CutsizeDetail(this,list,et_csl_width,et_csl_height,tv_cs_cft,tv_cs_cmt)
        lv_csdetail.adapter=a
    }
}
