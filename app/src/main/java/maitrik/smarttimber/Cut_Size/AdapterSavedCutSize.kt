package maitrik.smarttimber.Cut_Size

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import maitrik.smarttimber.Adapter.tcft
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.CutSizeCFTMasterModel
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.CustomKeyboardView
import maitrik.smarttimber.TimberCalculator.components.keyboard.OnNextClickListener
import java.text.DecimalFormat

class AdapterSavedCutSize(var context: Context,var arrItems : ArrayList<CutSizeCFTMasterModel>,var tvTotalCFT :TextView
                          ,var tvTotalCMT :TextView,var tvTotalNOS :TextView,var tvTotalAmount :TextView,var id : Int)
    :RecyclerView.Adapter<AdapterSavedCutSize.ViewHolder>(){
    val strTAG = "AdapterSavedCutSize"
companion object{
    var onDeleteCLick: OnDeleteChangedAmount? = null
    fun onDeleteClickFun(listener: OnDeleteChangedAmount) {
        onDeleteCLick = listener
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_saved_cut_size,parent,false))
    }

    override fun getItemCount(): Int {
        return arrItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
//            llDetails.setOnLongClickListener {
//                Toast.makeText(context, "long Click$position  ", Toast.LENGTH_SHORT).show();
//                false;
//            }
            holder.setIsRecyclable(false)
            tvWidth.text = App.getFormatData(arrItems[position].width.toString())
            tvHeight.text = App.getFormatData(arrItems[position].height.toString())

            if (arrItems[position].rate == 0.0){
               llRate.visibility = View.GONE
               llAmount.visibility = View.GONE
            }else{
               tvRate.text =  App.checkPointZeroValidation(arrItems[position].rate.toString())
                llRate.visibility = View.VISIBLE
                llAmount.visibility = View.VISIBLE
            }
            rvItems.layoutManager = LinearLayoutManager(context)
            rvItems.adapter = AdapterChild(context,arrItems[position].arrItems,tvCFT,arrItems[position].rate.toDouble(),tvAmount,tvTotalCFT)
            ivEdit.setOnClickListener{
                App.showLog(strTAG,"SubId : ${arrItems[position].arrItems[0].subid}")
                val intent= Intent(context, ActCutSizeSubItemList::class.java)
                intent.putExtra("mid",id)
                intent.putExtra("subId",arrItems[position].arrItems[0].subid)
                context.startActivity(intent)
            }
            ivDelete.setOnClickListener {
                App.showLog(strTAG,"id : ${arrItems[position].arrItems[0].mid} ${arrItems[position].arrItems[0].subid}")
                var alertDialog = AlertDialog.Builder(context)
                    .setTitle("Warning")
                    .setMessage("Are you sure to Delete: ?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                        val db= DBHandler(context)
                        db.deleteCutSizeSubList(arrItems[position].arrItems[0].subid)
                        db.deleteCutSizeSubItemId(arrItems[position].arrItems[0].subid)
                        arrItems.removeAt(position)
                        notifyDataSetChanged()
                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()
//                        tvTotalAmount.text = App.getCutSizeAmount(App.getCutSizeTotalCFT(arrItems[position].arrItems),db.getRateCutSize(arrItems[position].arrItems[0].subid).toDouble())
                        App.getTotalCFT(tvTotalCFT,tvTotalCMT,tvTotalNOS,tvTotalAmount,arrItems)
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .show()
            }
        }
        holder.setIsRecyclable(false)
    }
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        var tvWidth: TextView = itemView.findViewById<TextView>(R.id.row_saved_cutSize_tvWidth)!!
        var tvHeight: TextView = itemView.findViewById<TextView>(R.id.row_saved_cutSize_tvHeight)
        var tvCFT: TextView = itemView.findViewById<TextView>(R.id.row_saved_cutSize_tvCFT)
        var tvRate: TextView = itemView.findViewById<TextView>(R.id.row_saved_cutSize_tvRate)
        var llRate: LinearLayout = itemView.findViewById<LinearLayout>(R.id.row_saved_cutSize_llRate)
        var tvAmount: TextView = itemView.findViewById<TextView>(R.id.row_saved_cutSize_tvAmount)
        var llAmount: LinearLayout = itemView.findViewById<LinearLayout>(R.id.row_saved_cutSize_llAmount)
        var rvItems: RecyclerView = itemView.findViewById<RecyclerView>(R.id.row_saved_cutSize_rvItems)
        var ivEdit: ImageView = itemView.findViewById<ImageView>(R.id.row_saved_cutSize_ivEdit)
        var ivDelete = itemView.findViewById<ImageView>(R.id.row_saved_cutSize_ivDelete)
        var llDetails = itemView.findViewById<LinearLayout>(R.id.row_saved_cutSize_llItems)
    }

    override fun getItemViewType(position: Int): Int {
//        App.showLog("AdapterSavedCutSize","POS :$position")
        return super.getItemViewType(position)
    }
}

class AdapterChild(
   var  context: Context,
   var arrItems: ArrayList<CutSize>,
   var tvCFT : TextView,
   var rate : Double,
   var tvAmount : TextView,
   var tvTotalCFT : TextView
):RecyclerView.Adapter<AdapterChild.ViewHolder>() {

    var cft = 0.0
    var totalCFT = 0.0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_child_save_cutsize,parent,false))
    }

    override fun getItemCount(): Int {
        return arrItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
//            var length = arrItems[position].length.toString()
            tvLength.text = App.getFormatData(arrItems[position].length.toString())
//            when {
//                length.contains(".75") -> {
//                    tvLength.text = length.replace(".75","|||")
//                }
//                length.contains(".5") -> {
//                    tvLength.text = length.replace(".5","||")
//                }
//                length.contains(".25") -> {
//                    tvLength.text = length.replace(".25","|")
//                }
//                length.contains(".0") -> {
//                    tvLength.text = length.replace(".0","")
//                }
//                //            tvLength.text = arrItems[position].length.toString()
//                //            Log.d("tvTotalCFT","tvTotalCFT:  ${fTotalcft.toString()}")
//                //            App.showLog("AdapterSavedCutSize","POS Sub Item :$position")
//                //            tvTotalCFT.text = fTotalcft.toString()
//            }
//            tvLength.text = arrItems[position].length.toString()
            tvQTY.text = arrItems[position].qty.toString()
            val df = DecimalFormat("###.##")
            cft = cft + arrItems[position].cft!!
            totalCFT = totalCFT + cft
            val fcft = df.format(cft)
            val fTotalcft = df.format(totalCFT)
//            Log.d("tvTotalCFT","tvTotalCFT:  ${fTotalcft.toString()}")
//            App.showLog("AdapterSavedCutSize","POS Sub Item :$position")
//            tvTotalCFT.text = fTotalcft.toString()
            tvCFT.text = fcft.toString()
            tvAmount.text = App.getCutSizeAmount(cft,rate.toDouble())
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
            var tvLength = itemView.findViewById<TextView>(R.id.row_child_saveCFT_tvLength)
            var tvQTY = itemView.findViewById<TextView>(R.id.row_child_saveCFT_tvQTY)
    }

}

interface  OnDeleteChangedAmount{
    fun onDelete(status :Boolean)
}