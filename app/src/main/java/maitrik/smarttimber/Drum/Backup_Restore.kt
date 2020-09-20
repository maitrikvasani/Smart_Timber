package maitrik.smarttimber.Drum

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_backup__restore.*
import maitrik.smarttimber.Backup.LocalBackup
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import java.io.File

class Backup_Restore : AppCompatActivity() {
    private var localBackup: LocalBackup? = null
    internal lateinit var db: DBHandler
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup__restore)
        context = this
        localBackup= LocalBackup(this)
        db = DBHandler(context)
        btnabackup.setOnClickListener {
            val outFileName =
                Environment.getExternalStorageDirectory().toString() + File.separator + resources.getString(
                    R.string.app_name
                ) + File.separator+"DBBackup"+ File.separator
            localBackup!!.performBackup(db, outFileName,0)
        }
        btnrestore.setOnClickListener {
            val r=localBackup!!.performRestore(db)
            if (r==1)
            {
                Toast.makeText(this,"Import Success", Toast.LENGTH_LONG).show()
            }
        }

    }
}
