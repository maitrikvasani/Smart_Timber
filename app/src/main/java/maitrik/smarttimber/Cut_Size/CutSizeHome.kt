package maitrik.smarttimber.Cut_Size

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cust_size_home.*
import maitrik.smarttimber.R

class CutSizeHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cust_size_home)
        btn_csh_cft.setOnClickListener{
            startActivity(Intent(this@CutSizeHome,CutSizeCFT::class.java))
        }
        btn_csh_viewalldata.setOnClickListener{
            startActivity(Intent(this@CutSizeHome,CutSizeList::class.java))
        }
        homeCutSize_btnCFT.setOnClickListener {
            startActivity(Intent(this,ActCutSizeCFT::class.java))
        }
    }
}
