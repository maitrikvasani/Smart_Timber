package maitrik.smarttimber.Backup

import android.app.Activity
import android.app.AlertDialog
import android.os.Environment
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import maitrik.smarttimber.Adaper.Permission.Permissions
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import java.io.File




class LocalBackup(activity: Activity) {
     var activity:Activity = activity as Activity

    //ask to the user a name for the backup and perform it. The backup will be saved to a custom folder.

    fun performBackup(db: DBHandler, outFileName: String, i:Int ) {

        Permissions.Permissions.verifyStoragePermissions(activity)
        /*Permissions.verifyStoragePermissions(activity)**/

        val folder = File(
            Environment.DIRECTORY_DOWNLOADS.toString() + File.separator + activity.resources.getString(
                R.string.app_name
            )+File.separator+"DBBackup"
        )

        var success = true
        if (!folder.exists())
            success = folder.mkdirs()
        if (success) {
            if (i==1)
            {
                val b="backup"
                val out = "$outFileName$b.db"
                db.backup(out,0)
            }
            else
            {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Backup Name")
                .setIcon(R.drawable.ic_settings_backup_restore_black_24dp)
            val input = EditText(activity)
            input.hint="Enter File Name"
            input.setPadding(20,20,20,20)
            input.inputType = InputType.TYPE_CLASS_TEXT

            builder.setView(input)

            builder.setPositiveButton("Save") { dialog, which ->
               if( input.text.toString().isEmpty())
               {
                   input.error="Enter File Name"
                   input.isFocusable=true
                   Toast.makeText(activity,"File name Can't be empty. Try Again",Toast.LENGTH_SHORT).show()
               }
               else {
                   val m_Text = input.text.toString()
                   val out = "$outFileName$m_Text.db"
                   db.backup(out,1)
               }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
            }
        } else
            Toast.makeText(activity, "Unable to create directory. Retry", Toast.LENGTH_SHORT).show()
    }

    //ask to the user what backup to restore
    fun performRestore(db: DBHandler):Int {
        var t=0
        Permissions.Permissions.verifyStoragePermissions(activity)

        val folder = File(
            Environment.getExternalStorageDirectory().toString() + File.separator + activity.getResources().getString(
                R.string.app_name
            )+File.separator+"DBBackup"
        )
        if (folder.exists()) {

            val files = folder.listFiles()

            val arrayAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1)
            for (file in files!!)
                arrayAdapter.add(file.name)

            val builderSingle = AlertDialog.Builder(activity)
            builderSingle.setTitle("Restore:")
            builderSingle.setNegativeButton(
                "cancel"
            ) { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(
                arrayAdapter
            ) { dialog, which ->
                try {
                    db.importDB(files[which].path)
                    t=1
                } catch (e: Exception) {
                    Toast.makeText(activity, "Unable to restore. Retry", Toast.LENGTH_SHORT).show()
                }
            }
            builderSingle.show()
        } else
            Toast.makeText(
                activity,
                "Backup folder not present.\nDo a backup before a restore!",
                Toast.LENGTH_SHORT
            ).show()
        return t
    }

}