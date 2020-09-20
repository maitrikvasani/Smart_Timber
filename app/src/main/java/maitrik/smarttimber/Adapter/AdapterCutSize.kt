package maitrik.smarttimber.Adapter

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.CustomKeyboardView
import java.text.DecimalFormat

class AdapterCutSize(
    var context: Context,
    var arrItems: ArrayList<CutSize>,
    var tvcft: TextView,
    var tvcmt: TextView,
    var keyboard: CustomKeyboardView,
    var tvNOS: TextView
):RecyclerView.Adapter<AdapterCutSize.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cutsizelayout,parent,false))
    }

    override fun getItemCount(): Int {
        return arrItems.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            Log.d("POS", "POS : $position")
            etLength.placeCursorEnd()
            etQty.placeCursorEnd()
//            val format  =DecimalFormat()
//            format.isDecimalSeparatorAlwaysShown = false
//            etLength.setText(format.format(arrItems[position].length))
            etLength.setText("${arrItems[position].length}")
            etLength.setSelection(etLength.text.length)
            etQty.setText("${arrItems[position].qty}")
            etQty.setSelection(etQty.text.length)
            keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL,etLength)
            keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL,etQty)
            c.text = arrItems[position].cft.toString()
            etLength.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0!!.isNotEmpty()){
                        arrItems[position].length = p0.toString().toDouble()
                        getCutSizeCFT(arrItems[position].width,arrItems[position].height,arrItems[position].length,arrItems[position].qty,position,c)
                        getCutsizeTotalCFT(tvcft,tvcmt)
                    }else{
                        arrItems[position].length = 0.0
                        getCutSizeCFT(arrItems[position].width,arrItems[position].height,arrItems[position].length,arrItems[position].qty,position,c)
                        getCutsizeTotalCFT(tvcft,tvcmt)
                    }
//                    if (etLength.text.isNotEmpty()){
//                        if (p0 != "" && p0 != " ") {
//                            arrItems[position].length = p0.toString().toDouble()
//                            getCutSizeCFT(arrItems[position].width,arrItems[position].height,arrItems[position].length,arrItems[position].qty,position,c)
//                            getCutsizeTotalCFT(tvcft,tvcmt)
//                        }else{
//                            arrItems[position].length = 0.0
//                            getCutSizeCFT(arrItems[position].width,arrItems[position].height,arrItems[position].length,arrItems[position].qty,position,c)
//                            getCutsizeTotalCFT(tvcft,tvcmt)
//                        }
//                    }else{
//                        etLength.setText("0.0")
//                        arrItems[position].length = 0.0
//                        getCutSizeCFT(arrItems[position].width,arrItems[position].height,arrItems[position].length,arrItems[position].qty,position,c)
//                        getCutsizeTotalCFT(tvcft,tvcmt)
//                    }
//                    arrItems[position].length = p0.toString().toDouble()
//                    getCutSizeCFT(arrItems[position].width,arrItems[position].height,arrItems[position].length,arrItems[position].qty,position,c)
//                    getCutsizeTotalCFT(tvcft,tvcmt)
                }

            })

            etQty.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0!!.isNotEmpty()){
                        arrItems[position].qty = p0.toString().toInt()
                        getCutSizeCFT(arrItems[position].width, arrItems[position].height, arrItems[position].length, arrItems[position].qty, position, c)
                        getCutsizeTotalCFT(tvcft, tvcmt)
                    }else{
                        arrItems[position].qty = 0
                        getCutSizeCFT(arrItems[position].width, arrItems[position].height, arrItems[position].length, arrItems[position].qty, position, c)
                        getCutsizeTotalCFT(tvcft, tvcmt)
                    }
//                    if (etQty.text.isNotEmpty()) {
//                        if (p0 != "" && p0 != " ") {
//                            arrItems[position].qty = p0.toString().toInt()
//                            getCutSizeCFT(arrItems[position].width, arrItems[position].height, arrItems[position].length, arrItems[position].qty, position, c)
//                            getCutsizeTotalCFT(tvcft, tvcmt)
//                        } else {
//                            arrItems[position].qty = 0
//                            getCutSizeCFT(arrItems[position].width, arrItems[position].height, arrItems[position].length, arrItems[position].qty, position, c)
//                            getCutsizeTotalCFT(tvcft, tvcmt)
//                        }
//                    }else{
//                        etQty.setText("0")
//                        arrItems[position].qty = 0
//                        getCutSizeCFT(arrItems[position].width, arrItems[position].height, arrItems[position].length, arrItems[position].qty, position, c)
//                        getCutsizeTotalCFT(tvcft, tvcmt)
//                    }

                }

            })

            arrItems[position].length = etLength.text.toString().toDouble()
            arrItems[position].qty = etQty.text.toString().toInt()
            arrItems[position].cft = c.text.toString().toDouble()
            getCutsizeTotalCFT(tvcft,tvcmt)
        }


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(view :View):RecyclerView.ViewHolder(view) {
//        var l=view.findViewById(R.id.et_csl_length) as TextView
//        var q=view.findViewById(R.id.et_csl_qty) as TextView
        var c=view.findViewById(R.id.tv_csl_cft)as TextView
        var etLength=view.findViewById(R.id.row_cutSize_etULength) as EditText
        var etQty=view.findViewById(R.id.row_cutSize_etUQty) as EditText
        var llUpdate = view.findViewById<LinearLayout>(R.id.row_cutSize_llUpdate) as LinearLayout

    }

    fun getCutsizeTotalCFT(tvCFT :TextView,tvCMT : TextView) {
        var cft = 0.0
        var cmt = 0.0
        var nos = 0
        for (i in 0 until arrItems.size) {
            cft = (cft + arrItems[i].cft!!)
            cmt = (cmt + arrItems[i].cmt!!)
            nos = (nos + arrItems[i].qty)
        }
        val f = DecimalFormat("##.##")
        val f1 = DecimalFormat("##.####")
        val fc = f.format(cft)
        val fm = f1.format(cmt)
        tvCFT.text = fc
        tvCMT.text = fm
        tvNOS.text = nos.toString()
    }

    fun getCutSizeCFT(w: Double, h: Double, l: Double, q: Int,position: Int,tvCFT : TextView) {
        val cft = w * h * l * q / 144
        val cmt = cft / 35.30
        val f = DecimalFormat("###.##")
        val fm = DecimalFormat("###.####")
        val fcft = f.format(cft).toDouble()
        val fcmt = fm.format(cmt).toDouble()
        arrItems[position].cft = fcft
        arrItems[position].cmt = fcmt
        tvCFT.text = arrItems[position].cft.toString()
        val c = CutSize(w, h, l, q, fcft, fcmt)
//        arrItems.add(c)
//        rvItems.layoutManager = LinearLayoutManager(this)
//        rvItems.adapter = AdapterCutSize(this,arrItems,tvCFT,tvCMT,keyboard)
//        rvItems.scrollToPosition(arrItems.size-1)
    }
    fun EditText.placeCursorEnd(){
        this.setSelection(this.text.length)
    }

}
