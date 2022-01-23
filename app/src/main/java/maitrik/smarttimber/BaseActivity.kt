package maitrik.smarttimber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {
    lateinit var base_Toolbar : Toolbar
    lateinit var base_ivBack : AppCompatImageView
    lateinit var base_ivAdd : AppCompatImageView
    lateinit var base_ivSave : AppCompatImageView
    lateinit var base_ivClose : AppCompatImageView
    lateinit var base_llSubMainContainer : LinearLayout
    lateinit var base_tvTitle : AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_base)
        baseInitialize()
        baseClickEvent()
    }

    private fun baseInitialize() {
        base_Toolbar = findViewById(R.id.base_toolbar)
        base_ivBack = findViewById(R.id.base_icBack)
        base_ivAdd = findViewById(R.id.base_icAdd)
        base_ivSave = findViewById(R.id.base_icSave)
        base_ivClose = findViewById(R.id.base_icClose)
        base_tvTitle = findViewById(R.id.base_tvTitle)
        base_llSubMainContainer = findViewById(R.id.base_llSubMainContainer)
    }

    private fun baseClickEvent() {
        base_ivBack.setOnClickListener {
            finish()
        }
    }
}