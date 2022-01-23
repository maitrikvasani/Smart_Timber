package maitrik.smarttimber.Cut_Size

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import maitrik.smarttimber.Adapter.total
import maitrik.smarttimber.Adapter.totalamnt
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.CutSizeCFTMasterModel
import java.text.DecimalFormat

class App {
    companion object {
        var isNumber: Boolean = false
        val strTAG = "App"

        fun showLog(from: String, msg: String) {
            Log.d("From :$from", msg)
        }

        fun checkPointZeroValidation(txt : String) : String{
            if (txt.contains(".0")){
                return txt.replace(".0","")
            }
            return txt
        }

        fun getFormatData(txtString: String): String {
            when {
                txtString.contains(".75") -> {
                    return txtString.replace(".75", "|||")
                }
                txtString.contains(".5") -> {
                    return txtString.replace(".5", "||")
                }
                txtString.contains(".25") -> {
                    return txtString.replace(".25", "|")
                }
                txtString.contains(".0") -> {
                    return txtString.replace(".0", "")
                }
            }
            return txtString
        }

        fun getCutSizeAmount(cft:Double,rate:Double):String{
            val format = DecimalFormat("##,##,###")
            val amount = cft*rate
            return format.format(amount)
        }

        fun getCutSizeAmountInt(cft:Double,rate:Double):Double{
            val format = DecimalFormat("#######")
            val rate = cft*rate.toDouble()
            return format.format(rate).toDouble()
        }

        fun getCutSizeTotalCFT(arrList:ArrayList<CutSize>):Double{
           var total = 0.0
            if (arrList.size > 0){
                for (i in 0 until arrList.size){
                    total = (total + arrList[i].cft!!)
                }
            }
            return  total
        }

        fun getCutSizeTotalAmount(rate:ArrayList<CutSize>):String{
            var total = 0
            val format = DecimalFormat("##,##,###")
            val amount = total * total
            return format.format(amount)
        }

        @SuppressLint("SetTextI18n")
        fun getTotalCFT(tvTotalCFT:TextView,tvTotalCMT: TextView,tvTotalNOS: TextView,tvTotalAmount: TextView,arrItems:ArrayList<CutSizeCFTMasterModel>){
            var totalCFT = 0.0
            var totalCMT = 0.0
            var totalNOS = 0
            var totalAmount = 0.0
            for (i in 0 until arrItems.size){
                totalAmount += getCutSizeAmountInt(getCutSizeTotalCFT(arrItems[i].arrItems),arrItems[i].rate)
                for (j in 0 until arrItems[i].arrItems.size){
                    totalCFT += arrItems[i].arrItems[j].cft!!
                    totalNOS += arrItems[i].arrItems[j].qty
                }
            }
            totalCMT = totalCFT/35.30
            val decimalFormat = DecimalFormat("###.##")
            val cmtFormat = DecimalFormat("###.####")
            val amountFormat = DecimalFormat("##,##,###")
            cmtFormat.isDecimalSeparatorAlwaysShown = false
            tvTotalCMT.text = cmtFormat.format(totalCMT)
            val formatTotalCFT = decimalFormat.format(totalCFT)
            tvTotalNOS.text = totalNOS.toString()
            tvTotalCFT.text = formatTotalCFT.toString()
            tvTotalAmount.text = amountFormat.format(totalAmount)
        }
//
//        fun hideSoftKeyboard()
//        {
//            val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.SHOW_FORCED)
//        }
    }
}