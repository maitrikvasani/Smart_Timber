package maitrik.smarttimber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var etWidth : EditText
    lateinit var etHeight : EditText
    lateinit var etLength : EditText
    lateinit var tvCFT : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        etWidth = findViewById(R.id.ch_etWidth)
        etHeight = findViewById(R.id.ch_etHeight)
        etLength = findViewById(R.id.ch_etLength)
        tvCFT = findViewById(R.id.ch_tvCFT)
    }
}
