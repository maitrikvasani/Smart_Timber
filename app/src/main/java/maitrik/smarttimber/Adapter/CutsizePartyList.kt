package maitrik.smarttimber.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.cutsizepartylist.view.*
import maitrik.smarttimber.Cut_Size.cutsize_list
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import java.text.DecimalFormat

class CutsizePartyList(private var activity: Activity,private var lstcs:ArrayList<CutSize>):BaseAdapter() {
    lateinit var name:TextView
    lateinit var cscft:TextView
    lateinit var cscmt:TextView
    private var inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.cutsizepartylist, null)
        name=view.findViewById(R.id.tv_cs_name)
        cscft=view.findViewById(R.id.tv_cspl_cft)
        cscmt=view.findViewById(R.id.tv_cspl_cmt)
        view.cs_dlt_partylist.setOnClickListener {
            var alertDialog = AlertDialog.Builder(activity)
                .setTitle("Warning")
                .setMessage("Are you sure to Delete: ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    val db=DBHandler(activity)
                    db.deletecsdetail(lstcs[p0].mid)
                    db.deletecsparty(lstcs[p0].mid)
                    lstcs.removeAt(p0)
                    notifyDataSetChanged()
                    Toast.makeText(activity, "Delete", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()

        }
        view.cs_view_partylist.setOnClickListener {
            var id=lstcs[p0].mid
            var name=lstcs[p0].pname
            //Toast.makeText(activity,"Click ${lstcs[p0].mid} and $i", Toast.LENGTH_SHORT).show()
            val intent= Intent(activity, cutsize_list::class.java)
            intent.putExtra("mid",id)
            intent.putExtra("name",name)
            activity.startActivity(intent)


        }
        view.cs_edit_partylist.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            //  var infla: LayoutInflater = layoutInflater
            val inflater=LayoutInflater.from(activity)
            var view:View=inflater.inflate(R.layout.cspartynnamelayout,null)
            var name=view.findViewById<EditText>(R.id.et_cs_name)
            name.setText("${lstcs[p0].pname}")
            builder.setView(view)
                .setTitle("Update")
                .setIcon(R.drawable.ic_edit_black_24dp)
                .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                    if (name.text.toString().isEmpty() )
                    {
                        name.requestFocus()
                    }
                    else {
                        val db = DBHandler(activity)
                        db.editpartyname(lstcs[p0].mid, name.text.toString())
                        lstcs[p0].pname = name.text.toString()
                        notifyDataSetChanged()
                    }
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->})
                .show()

        }
        name.text=lstcs[p0].pname
        val db=DBHandler(activity)
        val list:ArrayList<CutSize> = db.getiddetail(lstcs[p0].mid,activity)
        var c=0.0
        for (i in 0..list.size-1)
        {
            c=c+ list[i].cft!!
        }
        val m=c/35.30
        val f = DecimalFormat("###.##")
        val fm = DecimalFormat("###.####")
       val cft=f.format(c)
        val cmt=fm.format(m)
        cscft.text= cft
        cscmt.text= cmt
        return view
    }

    override fun getItem(p0: Int): Any {
     return lstcs[p0]
    }

    override fun getItemId(p0: Int): Long {
       return lstcs[p0].mid.toLong()
    }

    override fun getCount(): Int {
        return lstcs.size
    }


}