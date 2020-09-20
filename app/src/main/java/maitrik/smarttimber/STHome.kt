package maitrik.smarttimber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sthome.*
import maitrik.smarttimber.Cut_Size.CutSizeCFT
import maitrik.smarttimber.Cut_Size.CutSizeHome
import maitrik.smarttimber.Drum.Backup_Restore
import maitrik.smarttimber.Drum.Home
import maitrik.smarttimber.Lagging.LaggingCFT
import maitrik.smarttimber.Round_Log.Roundlogcft

class STHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sthome)
        btn_home_drum.setOnClickListener {
            val i=Intent(this,Home::class.java)
            startActivity(i)
        }
        btn_home_roundlog.setOnClickListener {
            val i=Intent(this,Roundlogcft::class.java)
            startActivity(i)
        }
        btn_home_cutsize.setOnClickListener {
            val i=Intent(this,CutSizeHome::class.java)
            startActivity(i)
        }
        btn_home_backup_restore.setOnClickListener {
            val i=Intent(this,Backup_Restore::class.java)
            startActivity(i)
        }
        btn_home_lagging.setOnClickListener {
            startActivity(Intent(this,LaggingCFT::class.java))
        }
        btn_customKeyboard.setOnClickListener {
            startActivity(Intent(this,CustomKeyboard::class.java))
        }
    }
}
