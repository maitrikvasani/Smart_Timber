package maitrik.smarttimber.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.cutsizedetaillayout.view.*
import kotlinx.android.synthetic.main.drumlist_layout.view.*
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import java.text.DecimalFormat

class CutsizeDetail(internal var activity: Activity, private var lstcs:ArrayList<CutSize>,var w:TextView, var h:TextView, var tvcft:TextView,var tvcmt:TextView):BaseAdapter() {

    private var inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.cutsizedetaillayout, null)
       /*var  id=findViewById<TextView>(R.id.tv_id)
        var  m=findViewById<TextView>(R.id.master)*/
       /* id.text="${lstcs[p0].id}"
        m.text="${lstcs[p0].mid}"*/
        w.setText("${lstcs[p0].width}")
        h.setText("${lstcs[p0].height}")
//        view.et_csl_length.setText("${lstcs[p0].length}")
//        view.et_csl_qty.setText("${lstcs[p0].qty}")
        view.cft.text = "${lstcs[p0].cft}"
        getCutsizeTotalCFT()
        view.csedit.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
          //  var infla: LayoutInflater = layoutInflater
            val inflater=LayoutInflater.from(activity)
            var view:View=inflater.inflate(R.layout.cutsizeupdatelayout,null)
            var l=view.findViewById<EditText>(R.id.et_u_length)
            var q=view.findViewById<EditText>(R.id.et_u_qty)
            l.setText("${lstcs[p0].length}")
            q.setText("${lstcs[p0].qty}")
            builder.setView(view)
                .setTitle("Update")
                .setIcon(R.drawable.ic_edit_black_24dp)
                .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                    if (l.text.toString().isEmpty() )
                    {
                        l.requestFocus()
                    }
                    else if(q.text.toString().isEmpty())
                    {
                        q.requestFocus()
                    }
                    else{
                    val db = DBHandler(activity)
                    var cft=getCutSizeCFT(lstcs[p0].width,lstcs[p0].height,l.text.toString().toDouble(),q.text.toString().toInt())
                  val cs=CutSize(l.text.toString().toDouble(),q.text.toString().toInt(),cft)
                    db.editcsdetail(lstcs[p0].id,cs)
                        notifyDataSetChanged()
                        lstcs[p0].length=l.text.toString().toDouble()
                        lstcs[p0].qty=q.text.toString().toInt()
                        lstcs[p0].cft=cft
                        getCutsizeTotalCFT()
                    }
                })
                .setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->  })
                .show()
        }
        view.csdelete.setOnClickListener {
            var alertDialog = AlertDialog.Builder(activity)
                .setTitle("Warning")
                .setMessage("Are you sure to Delete ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    var db = DBHandler(activity)
                    var id = lstcs[p0].id
                    var i = db.deletecsdetail(id)
                    notifyDataSetChanged()
                    lstcs.removeAt(p0)
                    getCutsizeTotalCFT()
                    if (i == true) {
                        Toast.makeText(activity, "Delete Successfully", Toast.LENGTH_LONG).show()
                    }
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->})
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()


        }
        return view
    }

    override fun getItem(p0: Int): Any {
        return lstcs[p0]
    }

    override fun getItemId(p0: Int): Long {
        return lstcs[p0].id.toLong()
    }

    override fun getCount(): Int {

        return lstcs.size
    }
    fun getCutsizeTotalCFT()
    {
        var cft=0.0
        var cmt=0.0
        for (i in 0 until lstcs.size)
        {
            cft=(cft+ lstcs[i].cft!!)

        }
        val f= DecimalFormat("##.##")
        val f1= DecimalFormat("##.####")
        val fc=f.format(cft)
        cmt=cft/35.30
        val fm=f1.format(cmt)
        tvcft.text= fc
        tvcmt.text= fm
    }
    fun editcsdetail(id:Int)
    {
        var db=DBHandler(activity)
        var l=CutSize()
        val c=CutSize()

       // db.editcsdetail(id,cs)
    }
    fun deletecsdetail(id:Int)
    {

    }
    fun getCutSizeCFT(w: Double, h: Double, l: Double, q: Int):Double {
        val cft = w * h * l * q/144
        val cmt = cft/35.40
        val f = DecimalFormat("###.##")
        val fm = DecimalFormat("###.####")
        val fcft = f.format(cft).toDouble()
        val fcmt=fm.format(cmt).toDouble()
        val c=CutSize(w,h,l,q,fcft,fcmt)
       return fcft
    }

}