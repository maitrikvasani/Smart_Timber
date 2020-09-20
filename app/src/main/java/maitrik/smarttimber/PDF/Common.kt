package maitrik.smarttimber.PDF
import android.content.Context
import android.os.Environment
import maitrik.smarttimber.R
import java.io.File

object Common {
    fun getAppPath(context:Context):String
        {
            val dir=File(
                Environment.getExternalStorageDirectory().toString()
                +File.separator
                +context.resources.getString(R.string.app_name)
                    +File.separator
                    +"PDF"
                +File.separator)
            if (!dir.exists())
            {
                dir.mkdirs()
            }
            return dir.path+File.separator
        }
}