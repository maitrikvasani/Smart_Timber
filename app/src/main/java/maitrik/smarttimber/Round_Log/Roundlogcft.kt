package maitrik.smarttimber.Round_Log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_roundlogcft.*
import maitrik.smarttimber.Adapter.CustomAdapterForRoundLog
import maitrik.smarttimber.Model.RoundLog
import maitrik.smarttimber.R
import java.text.DecimalFormat
import java.text.Format

class Roundlogcft : AppCompatActivity() {
lateinit var rll:EditText
    lateinit var rlp:EditText
    lateinit var rlq:EditText
    var lstrl=ArrayList<RoundLog>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roundlogcft)
        rll=findViewById(R.id.et_rl_length)
        rlp=findViewById(R.id.et_rl_perimeter)
        rlq=findViewById(R.id.et_rl_qty)

        btn_rl_cft.setOnClickListener {
            if (rll.text.isEmpty())
            {
                rll.error="Please enter Length"
            }
            else if ( rlp.text.isEmpty())
                {
                    rlp.error="Please enter Inch"
                }
            else if (rlq.text.isEmpty())
            {
                rlq.error="Please enter Qty"
            }
            else{
            getRoundLogCFT(rll.text.toString(),rlp.text.toString(),rlq.text.toString())
                getRoundLogTotalCFT()
            }
        }
        et_rl_qty.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                btn_rl_cft.performClick()
                et_rl_perimeter.setText("")
                et_rl_length.setText("")
                et_rl_qty.setText("")
                et_rl_length.requestFocus()
                true
            } else {
                false
            }
        }

    }
    fun getRoundLogTotalCFT()
    { var c=0.0
        var m=0.0
        for (i in 0 until lstrl.size)
        {
             c= (c+ lstrl[i].rlcft)
            m= (m+ lstrl[i].rlcmt)
        }
        val f=DecimalFormat("##.##")
        val f1=DecimalFormat("##.####")
        val fc=f.format(c)
        val fm=f1.format(m)
        tv_rl_cft.text="$fc"
        tv_rl_cmt.text="$fm"
    }
    fun getRoundLogCFT(l:String,p:String,q:String)
    {
        val cft=l.toDouble()*p.toDouble()*p.toDouble()*q.toInt()/2304
        val cmt=cft/35.30
        val f=DecimalFormat("##.##")
        val f1=DecimalFormat("##.####")
        val fcft=f.format(cft)
        val fcmt=f1.format(cmt)
        val rl=RoundLog(rll.text.toString().toDouble(),rlp.text.toString().toDouble(),rlq.text.toString().toInt(),fcft.toDouble(),fcmt.toDouble())
        lstrl.add(rl)
        val adapter=CustomAdapterForRoundLog(this,lstrl)
        lv_rl.adapter= adapter
        Toast.makeText(this@Roundlogcft, "RoundLog : $fcft\nCMT: $fcmt",Toast.LENGTH_LONG).show()
    }
}
