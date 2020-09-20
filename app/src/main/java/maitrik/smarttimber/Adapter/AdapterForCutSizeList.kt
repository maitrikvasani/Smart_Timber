package maitrik.smarttimber.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_roundlogcft.*
import kotlinx.android.synthetic.main.cutsizelayout.view.*
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.R
import java.text.DecimalFormat

class AdapterForCutSizeList(private var activity: Activity,private var lstcs:ArrayList<CutSize>):BaseAdapter() {
    lateinit var l:EditText
    lateinit var q:EditText
    lateinit var c:TextView
    private var inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.cutsizelayout, null)
//        l=view.findViewById(R.id.et_csl_length)
//        q=view.findViewById(R.id.et_csl_qty)
        c=view.findViewById(R.id.tv_csl_cft)
        l.setText("${lstcs[p0].pname}")
        q.setText("${lstcs[p0].mid}")
        c.text="${lstcs[p0].mid}"
       // Toast.makeText(activity,""+lstcs[p0].mid,Toast.LENGTH_SHORT).show()
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
      //  tvcft.text= fc
       // tvcmt.text= fm

    }
}