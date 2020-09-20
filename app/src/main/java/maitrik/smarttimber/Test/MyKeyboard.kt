package maitrik.smarttimber.Test

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import maitrik.smarttimber.R

class MyKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {
    private var btn1: Button? = null
    private var btn2: Button? = null
    private var btn3: Button? = null
    private var btn4: Button? = null
    private var btn5: Button? = null
    private var btn6: Button? = null
    private var btn7: Button? = null
    private var btn8: Button? = null
    private var btn9: Button? = null
    private var btn0: Button? = null
    private var btnDel: Button? = null
    private var btnNext: Button? = null
    private var btnSava: Button? = null
    private var btnHalf: Button? = null
    private var btnPona: Button? = null
    private var btnPoint: Button? = null
    private val keyValues = SparseArray<String>()
    private var inputConnection: InputConnection? = null
    override fun removeAllViews() {
        super.removeAllViews()
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true)
        btn1 = findViewById<View>(R.id.ck_One) as Button
        btn1!!.setOnClickListener(this)
        btn2 = findViewById<View>(R.id.ck_Two) as Button
        btn2!!.setOnClickListener(this)
        btn3 = findViewById<View>(R.id.ck_Three) as Button
        btn3!!.setOnClickListener(this)
        btn4 = findViewById<View>(R.id.ck_Four) as Button
        btn4!!.setOnClickListener(this)
        btn5 = findViewById<View>(R.id.ck_Five) as Button
        btn5!!.setOnClickListener(this)
        btn6 = findViewById<View>(R.id.ck_Six) as Button
        btn6!!.setOnClickListener(this)
        btn7 = findViewById<View>(R.id.ck_Seven) as Button
        btn7!!.setOnClickListener(this)
        btn8 = findViewById<View>(R.id.ck_Eight) as Button
        btn8!!.setOnClickListener(this)
        btn9 = findViewById<View>(R.id.ck_Nine) as Button
        btn9!!.setOnClickListener(this)
        btn0 = findViewById<View>(R.id.ck_Zero) as Button
        btn0!!.setOnClickListener(this)
        btnSava = findViewById<View>(R.id.ck_Sava) as Button
        btnSava!!.setOnClickListener(this)
        btnHalf = findViewById<View>(R.id.ck_Half) as Button
        btnHalf!!.setOnClickListener(this)
        btnPona = findViewById<View>(R.id.ck_Pona) as Button
        btnPona!!.setOnClickListener(this)
        btnPoint = findViewById<View>(R.id.ck_Point) as Button
        btnPoint!!.setOnClickListener(this)
        btnNext = findViewById<View>(R.id.ck_Next) as Button
        btnNext!!.setOnClickListener(this)
        btnDel = findViewById<View>(R.id.ck_BackSPace) as Button
        btnDel!!.setOnClickListener(this)
        keyValues.put(R.id.ck_One, "1")
        keyValues.put(R.id.ck_Two, "2")
        keyValues.put(R.id.ck_Three, "3")
        keyValues.put(R.id.ck_Four, "4")
        keyValues.put(R.id.ck_Five, "5")
        keyValues.put(R.id.ck_Six, "6")
        keyValues.put(R.id.ck_Seven, "7")
        keyValues.put(R.id.ck_Eight, "8")
        keyValues.put(R.id.ck_Nine, "9")
        keyValues.put(R.id.ck_Zero, "0")
        keyValues.put(R.id.ck_Sava, ".25")
        keyValues.put(R.id.ck_Half, ".5")
        keyValues.put(R.id.ck_Pona, ".75")
        keyValues.put(R.id.ck_Point, ".")
        keyValues.put(R.id.ck_Next, "\n")
        keyValues.put(R.id.ck_BackSPace, "1")
    }

    override fun onClick(view: View) {
        if (inputConnection == null) return
        if (view.id == R.id.ck_BackSPace) {
            val selectedText = inputConnection!!.getSelectedText(0)
            if (TextUtils.isEmpty(selectedText)) {
                inputConnection!!.deleteSurroundingText(1, 0)
            } else {
                inputConnection!!.commitText("", 1)
            }
        } else {
            val value = keyValues[view.id]
            if (view.id == R.id.ck_Half) {
                Toast.makeText(context, "Half$value", Toast.LENGTH_SHORT).show()
                if (!value.contains(".5")) {
                    inputConnection!!.commitText(value, 1)
                }
            } else if (view.id == R.id.ck_Pona) {
                Toast.makeText(context, "Pona$value", Toast.LENGTH_SHORT).show()
                if (!value.contains(".75")) {
                    inputConnection!!.commitText(value, 1)
                }
            } else {
                inputConnection!!.commitText(value, 1)
            }
            //            value.contains(".25");
//            if (value.contains(".25")){
//                Log.e("From",value);
//                return;
//            }else{
//            inputConnection.commitText(value,1);
//            }
        }
    }

    fun setInputConnection(ic: InputConnection?) {
        inputConnection = ic
    }

    init {
        init(context, attrs)
    }
}