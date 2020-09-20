package maitrik.smarttimber.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.itextpdf.text.pdf.parser.Line
import kotlinx.android.synthetic.main.activity_cut_size_cft.*
import kotlinx.android.synthetic.main.activity_roundlogcft.*
import kotlinx.android.synthetic.main.cutsizelayout.view.*
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.CustomKeyboardView
import java.text.DecimalFormat

    class AdapterForCutSize(internal var activity: Activity,private var lstcs:ArrayList<CutSize>,var cscft:Button,var tvcft:TextView,var tvcmt:TextView,var cs_lv:ListView , var keyboard : CustomKeyboardView):BaseAdapter() {
    lateinit var l:TextView
    lateinit var q:TextView
    lateinit var c:TextView
    lateinit var etLength : EditText
    lateinit var etQty : EditText
    lateinit var llUpldate : LinearLayout
    lateinit var btnUpdate : Button
    private var inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.cutsizelayout, null)
//        l=view.findViewById(R.id.et_csl_length)
//        q=view.findViewById(R.id.et_csl_qty)
        c=view.findViewById(R.id.tv_csl_cft)
        etLength=view.findViewById(R.id.row_cutSize_etULength)
        etQty=view.findViewById(R.id.row_cutSize_etUQty)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL,etLength)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL,etQty)
        llUpldate=view.findViewById(R.id.row_cutSize_llUpdate)
        l.text = "${lstcs[p0].length}"
        q.text = "${lstcs[p0].qty}"
        c.text="${lstcs[p0].cft}"

        cs_lv.setOnItemClickListener { adapterView, view, i, l ->
            llUpldate.visibility= View.VISIBLE
            etLength.setText("${lstcs[i].length}")
            etQty.setText("${lstcs[i].qty}")

        }
        /*cs_lv.setOnItemClickListener{ adapterView, view, i, l ->
            val inflater=LayoutInflater.from(activity)
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            var view:View=inflater.inflate(R.layout.cutsizeupdatelayout,null)
            var l=view.findViewById<EditText>(R.id.et_u_length)
            var q=view.findViewById<EditText>(R.id.et_u_qty)

            keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL,l)
            keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL,q)

            l.setText("${lstcs[i].length}")
            q.setText("${lstcs[i].qty}")
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

                        val cft=getCutSizeCFT(lstcs[i].width,lstcs[i].height,l.text.toString().toDouble(),q.text.toString().toInt())
                        cs_lv.deferNotifyDataSetChanged()
                        lstcs[i].length=l.text.toString().toDouble()
                        lstcs[i].qty=q.text.toString().toInt()
                        lstcs[i].cft=cft[0]
                        getCutsizeTotalCFT()
                    }
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->  })
                .setNeutralButton("Delete",DialogInterface.OnClickListener { dialog, which ->
                    lstcs.removeAt(i)
                    notifyDataSetChanged()
                    getCutsizeTotalCFT()
                    l.requestFocus()
                })
                .show()
        }*/
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
    fun getCutSizeCFT(w: Double, h: Double, l: Double, q: Int):ArrayList<Double> {
        val cft = w * h * l * q/144
        val cmt = cft/35.30
        val f = DecimalFormat("###.##")
        val fm = DecimalFormat("###.####")
        val fcft = f.format(cft).toDouble()
        val fcmt=fm.format(cmt).toDouble()
        val c=CutSize(w,h,l,q,fcft,fcmt)
        val l=ArrayList<Double>()
        l.add(fcft)
        l.add(fcmt)
        return l

    }
    fun getCutsizeTotalCFT()
    {
        var cft=0.0
        var cmt=0.0
        for (i in 0 until lstcs.size)
        {
            cft=(cft+ lstcs[i].cft!!)
            cmt=(cmt+ lstcs[i].cmt!!)
            c.text= lstcs[i].cft.toString()
        }
        val f=DecimalFormat("##.##")
        val f1=DecimalFormat("##.####")
        val fc=f.format(cft)
        val fm=f1.format(cmt)
        tvcft.text= fc
        tvcmt.text= fm

    }
}