package maitrik.smarttimber.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.core.os.persistableBundleOf
import kotlinx.android.synthetic.main.roundloglayout.view.*
import maitrik.smarttimber.Model.RoundLog
import maitrik.smarttimber.R
import maitrik.smarttimber.Round_Log.Roundlogcft

class CustomAdapterForRoundLog(activity: Activity, private var lstrl:ArrayList<RoundLog>):BaseAdapter() {
    private var inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.roundloglayout, null)
        view.tv_rl_length.text = "${lstrl[p0].rllenght}"
        view.tv_rl_perimeter.text="${lstrl[p0].rlperimeter}"
        view.tv_rl_qty.text="${lstrl[p0].rlqty}"
        view.tv_rl_cft.text="${lstrl[p0].rlcft}"
    return view
    }

    override fun getItem(p0: Int): Any {
return lstrl[p0]
    }

    override fun getItemId(p0: Int): Long {
        return lstrl[p0].rlid.toLong()
    }

    override fun getCount(): Int {
        return lstrl.size
    }
}