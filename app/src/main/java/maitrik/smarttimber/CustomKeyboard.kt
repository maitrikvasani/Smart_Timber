package maitrik.smarttimber

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

open class CustomKeyboard : AppCompatActivity(), View.OnClickListener {

    private var inputConnection: InputConnection? = null
    private val keyValues = SparseArray<String>()
    var temp = ArrayList<String>()
    lateinit var btnOne: Button
    lateinit var btnTwo: Button
    lateinit var btnThree: Button
    lateinit var btnFour: Button
    lateinit var btnFive: Button
    lateinit var btnSix: Button
    lateinit var btnSeven: Button
    lateinit var btnEight: Button
    lateinit var btnNine: Button
    lateinit var btnZero: Button
    lateinit var btnPoint: Button
    lateinit var btnSava: Button
    lateinit var btnHalf: Button
    lateinit var btnPona: Button
    lateinit var btnEqual: Button
    lateinit var btnNext: Button
    lateinit var btnBackSpace: Button
    lateinit var et: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_keyboard)
        init()
     //   listener()

    }

    companion object {
        var edit = true
    }

    private fun listener() {
        btnOne.setOnClickListener {
            clickFunction("1")
        }
        btnTwo.setOnClickListener {
            clickFunction("2")
        }
        btnThree.setOnClickListener {
            clickFunction("3")
        }
        btnFour.setOnClickListener {
            clickFunction("4")
        }
        btnFive.setOnClickListener {
            clickFunction("5")
        }
        btnSix.setOnClickListener {
            clickFunction("6")
        }
        btnSeven.setOnClickListener {
            clickFunction("7")
        }
        btnEight.setOnClickListener {
            clickFunction("8")
        }
        btnNine.setOnClickListener {
            clickFunction("9")
        }
        btnZero.setOnClickListener {
            clickFunction("0")
        }
        btnSava.setOnClickListener {
            clickFunction(".25")
        }
        btnHalf.setOnClickListener {
            clickFunction(".5")
        }
        btnPona.setOnClickListener {
            clickFunction(".75")
        }
        btnPoint.setOnClickListener {
            clickFunction(".")
        }
        btnBackSpace.setOnClickListener {
            removeDigit()
        }
    }

    private fun removeDigit() {
        temp.size
        temp.size - 1
        temp.removeAt(temp.size - 1)
        var tempString = ""
        for (i in 0 until temp.size) {
            tempString = "$tempString${temp[i]}"
        }
        et.setText(tempString)
    }

    private fun clickFunction(no: String) {
//        Toast.makeText(this, no,Toast.LENGTH_SHORT).show()
        if (edit) {
            temp.add(no)
            var tempString = ""
            for (i in 0 until temp.size) {
                Log.e("From", i.toString())
                tempString = "$tempString${temp[i]}"
                if (temp[i] == ".25" || temp[i] == ".5" || temp[i] == ".75") {
                    edit = false
                }
            }
            et.setText(tempString)
        }
    }

    private fun init() {
        btnOne = findViewById(R.id.ck_One)
        btnOne.setOnClickListener(this)
        btnTwo = findViewById(R.id.ck_Two)
        btnTwo.setOnClickListener(this)
        btnThree = findViewById(R.id.ck_Three)
        btnThree.setOnClickListener(this)
        btnFour = findViewById(R.id.ck_Four)
        btnFour.setOnClickListener(this)
        btnFive = findViewById(R.id.ck_Five)
        btnFive.setOnClickListener(this)
        btnSix = findViewById(R.id.ck_Six)
        btnSix.setOnClickListener(this)
        btnSeven = findViewById(R.id.ck_Seven)
        btnSeven.setOnClickListener(this)
        btnEight = findViewById(R.id.ck_Eight)
        btnEight.setOnClickListener(this)
        btnNine = findViewById(R.id.ck_Nine)
        btnNine.setOnClickListener(this)
        btnZero = findViewById(R.id.ck_Zero)
        btnZero.setOnClickListener(this)
        btnSava = findViewById(R.id.ck_Sava)
        btnSava.setOnClickListener(this)
        btnHalf = findViewById(R.id.ck_Half)
        btnHalf.setOnClickListener(this)
        btnPona = findViewById(R.id.ck_Pona)
        btnPona.setOnClickListener(this)
        btnPoint = findViewById(R.id.ck_Point)
        btnPoint.setOnClickListener(this)
        btnEqual = findViewById(R.id.ck_Equal)
        btnEqual.setOnClickListener(this)
        btnNext = findViewById(R.id.ck_Next)
        btnNext.setOnClickListener(this)
        btnBackSpace = findViewById(R.id.ck_BackSPace)
        btnBackSpace.setOnClickListener(this)

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
        if (inputConnection == null)
            return
        if (view.id == R.id.btnBackSpace) {
            var selectedText : CharSequence = inputConnection!!.getSelectedText(0)

            if (TextUtils.isEmpty(selectedText)) {
                inputConnection!!.deleteSurroundingText(1, 0)
            } else {
                inputConnection!!.commitText("",1)
            }
        }else {
            val value = keyValues[view.id]
            inputConnection!!.commitText(value, 1)
        }
    }

    fun setInputConnection(ic: InputConnection) {
        inputConnection = ic
    }




}
