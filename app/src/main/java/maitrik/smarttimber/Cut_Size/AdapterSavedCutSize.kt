package maitrik.smarttimber.Cut_Size

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.CutSizeCFTMasterModel
import maitrik.smarttimber.R

class AdapterSavedCutSize(var context: Context,var arrItems : ArrayList<CutSizeCFTMasterModel>)
    :RecyclerView.Adapter<AdapterSavedCutSize.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_saved_cut_size,parent,false))
    }

    override fun getItemCount(): Int {
        return arrItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            holder.setIsRecyclable(false)
            tvWidth.text = arrItems[position].width.toString()
            tvHeight.text = arrItems[position].height.toString()
            rvItems.layoutManager = LinearLayoutManager(context)
            rvItems.adapter = AdapterChild(context,arrItems[position].arrItems)
        }
        holder.setIsRecyclable(false)
    }
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        var tvWidth = itemView.findViewById<TextView>(R.id.row_saved_cutSize_tvWidth)
        var tvHeight = itemView.findViewById<TextView>(R.id.row_saved_cutSize_tvHeight)
        var rvItems = itemView.findViewById<RecyclerView>(R.id.row_saved_cutSize_rvItems)
    }
}

class AdapterChild(
   var  context: Context,
   var arrItems: ArrayList<CutSize>
):RecyclerView.Adapter<AdapterChild.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_child_save_cutsize,parent,false))
    }

    override fun getItemCount(): Int {
        return arrItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            tvLength.text = arrItems[position].length.toString()
            tvQTY.text = arrItems[position].qty.toString()
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