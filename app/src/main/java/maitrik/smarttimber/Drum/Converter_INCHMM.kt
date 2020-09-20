package maitrik.smarttimber.Drum

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_converter__inchmm.*
import maitrik.smarttimber.R

class Converter_INCHMM : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter__inchmm)
        val im = arrayOf("INCH", "MM")
        var p = ""
        val sp = findViewById<Spinner>(R.id.spinchmm)
        var value1 = findViewById<EditText>(R.id.etvalue)
        var value2 = findViewById<EditText>(R.id.etvalue2)
        sp.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, im)
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Select Option", Toast.LENGTH_LONG).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                p = im[position]
            }
        }
        value1.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                btncovert.performClick()
                hideSoftKeyboard()
                true
            } else {
                false
            }
        }
        btncovert.setOnClickListener {
            if (value1.text.trim().isNotEmpty()) {
                var c = 0.0
                if (p == "INCH") {
                    c = value1.text.toString().toDouble() / 25.4

                } else {
                    c = value1.text.toString().toDouble() * 25.4
                }
                var s = String.format("%.2f", c)
                value2.setText("$s")
            } else {
                value1.error = "Enter value!!!"
            }
        }
    }
    fun hideSoftKeyboard()
    {
        val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
    }
    }

