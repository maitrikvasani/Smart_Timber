package maitrik.smarttimber.Drum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import maitrik.smarttimber.R

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btninchcft.setOnClickListener {
            val intent= Intent(this,Wooden_Drum_INCH::class.java)
            startActivity(intent)
        }
        btnmmgcft.setOnClickListener {
            val intent= Intent(this,Wooden_Drum_MM::class.java)
            startActivity(intent)
        }
        btnaddparty.setOnClickListener {
            val intent= Intent(this,Converter_INCHMM::class.java)
            startActivity(intent)
        }
        btnviewdata.setOnClickListener {
            val intent= Intent(this,Drum_List::class.java)
            startActivity(intent)
        }
        btncreateorder.setOnClickListener {
            val intent= Intent(this,Drum_Order::class.java)
            startActivity(intent)
        }
        btnaboutus.setOnClickListener {
            val intent= Intent(this,About_Us::class.java)
            startActivity(intent)
        }
        btnbackup.setOnClickListener {
            val intent= Intent(this,Backup_Restore::class.java)
            startActivity(intent)
        }
    }
}
