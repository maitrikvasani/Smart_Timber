package maitrik.smarttimber.Cut_Size

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_cut_size_cft.*
import kotlinx.android.synthetic.main.cspartynnamelayout.view.*
import maitrik.smarttimber.Adapter.AdapterCutSize
import maitrik.smarttimber.Adapter.AdapterForCutSize
import maitrik.smarttimber.Model.CutSize
import maitrik.smarttimber.Model.DBHandler
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.CustomKeyboardView
import maitrik.smarttimber.TimberCalculator.components.keyboard.OnNextClickListener
import maitrik.smarttimber.TimberCalculator.components.keyboard.controllers.DefaultKeyboardController
import maitrik.smarttimber.TimberCalculator.components.keyboard.controllers.KeyboardController
import maitrik.smarttimber.TimberCalculator.components.keyboard.controllers.OnClickSpecialButton
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.text.DecimalFormat


class CutSizeCFT : AppCompatActivity(), OnNextClickListener {
    lateinit var l: EditText
    lateinit var h: EditText
    lateinit var w: EditText
    lateinit var q: EditText
    lateinit var d: EditText
    lateinit var cscft: Button
    lateinit var save: Button
    lateinit var tvcft: TextView
    lateinit var tvcmt: TextView
    lateinit var scrollView: ScrollView
    var lstcs = ArrayList<CutSize>()

    private lateinit var keyboard: CustomKeyboardView
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_cut_size_cft_ck)
        scrollView = findViewById(R.id.scrollView)
        h = findViewById(R.id.et_cs_height)
        l = findViewById(R.id.et_cs_length)
        w = findViewById(R.id.et_cs_width)
        q = findViewById(R.id.et_cs_qty)
        d = findViewById(R.id.et_cs_ld)
        tvcft = findViewById(R.id.tv_cs_cft)
        tvcmt = findViewById(R.id.tv_cs_cmt)
        cscft = findViewById(R.id.btn_cs_cft)
        save = findViewById(R.id.btn_cs_savedata)



        keyboard = findViewById(R.id.customKeyboardView)


        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, h)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, w)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, l)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, d)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, q)

        w.requestFocus()

        DefaultKeyboardController.onCheck(object : OnClickSpecialButton{
            override fun checkIsNumber(inputText: KeyboardController.SpecialKey) {
                if (inputText == KeyboardController.SpecialKey.PONA){

                }
            }

        })




        CustomKeyboardView.onNextClickFun(object : OnNextClickListener {
            override fun onClick(et: EditText) {
                super.onClick(et)
                // For editText value is empty so it is working next button
                if (et == d){
                    if (!q.isFocused) {
                        et.focusSearch(View.FOCUS_FORWARD).let {
                            it.requestFocus()
                            return
                        }
                    }
                }
                // For without enter value can't move forward
                if (et.text.toString().isNotEmpty()) {
                    if (!q.isFocused) {
                        et.focusSearch(View.FOCUS_FORWARD).let {
                            it.requestFocus()
                            return
                        }
                    } else {
                        cscft.performClick()
                    }
                }
            }

        })

        keyboard.translateLayout()
        Log.e("TAG", "CK $: ${keyboard.height}")
        Log.e("TAG", "CK $: ${keyboard.isExpanded}")


        cs_lv.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }
        /*  cs_lv.setOnItemClickListener { adapterView, view, i, l ->
              Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show()
          }*/


        btn_fbt.setOnClickListener {
            //startActivity(Intent(this,fourbythree::class.java))
            //insertcutsizemaster()
            getlastid()
            insertcutsizecft()
        }
        /* save.setOnClickListener {
             insertcutsizemaster()
             insertcutsizecft()
         }*/
        btn_cs_cft.setOnClickListener {
            if (w.text.isEmpty()) {
                w.error = "Enter Width"
                w.requestFocus()
            } else if (h.text.isEmpty()) {
                h.error = "Enter Height"
                h.requestFocus()
            } else if (l.text.isEmpty()) {
                l.error = "Enter Length"
                l.requestFocus()
            } else if (q.text.isEmpty()) {
                q.error = "Enter Qty"
                q.requestFocus()
            } else {
                getCutSizeCFT(
                    w.text.toString().toDouble(),
                    h.text.toString().toDouble(),
                    l.text.toString().toDouble(),
                    q.text.toString().toInt()
                )
                getCutsizeTotalCFT()
                if (d.text.toString().isEmpty()) {
                    l.setText("")
                    q.setText("")
                    l.requestFocus()
                } else {
                    l.setText("${l.text.toString().toDouble() + d.text.toString().toDouble()}")
                    q.setText("")
                    q.requestFocus()
                }

                //  et_cs_length.requestFocus()

            }
        }

//        et_cs_qty.setOnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_NONE) {
//                btn_cs_cft.performClick()
//                et_cs_qty.requestFocus()
//                val imm =
//                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.showSoftInput(et_cs_qty, InputMethodManager.SHOW_IMPLICIT)
//                showKeyboard(this, et_cs_qty)
//                true
//            } else {
//            }
//            false
//        }

    }

    fun getCutsizeTotalCFT() {
        var cft = 0.0
        var cmt = 0.0
        for (i in 0 until lstcs.size) {
            cft = (cft + lstcs[i].cft!!)
            cmt = (cmt + lstcs[i].cmt!!)
        }
        val f = DecimalFormat("##.##")
        val f1 = DecimalFormat("##.####")
        val fc = f.format(cft)
        val fm = f1.format(cmt)
        tv_cs_cft.text = fc
        tv_cs_cmt.text = fm


    }

    fun getCutSizeCFT(w: Double, h: Double, l: Double, q: Int) {
        val cft = w * h * l * q / 144
        val cmt = cft / 35.30
        val f = DecimalFormat("###.##")
        val fm = DecimalFormat("###.####")
        val fcft = f.format(cft).toDouble()
        val fcmt = fm.format(cmt).toDouble()
        val c = CutSize(w, h, l, q, fcft, fcmt)
        if (q == 0) {
            val a = AdapterForCutSize(this, lstcs, cscft, tv_cs_cft, tv_cs_cmt, cs_lv, keyboard)
            cs_lv.adapter = a

            /*  cs_lv.setOnItemClickListener{ adapterView, view, i, l ->
                  val inflater=LayoutInflater.from(this)
                  val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                  var view:View=inflater.inflate(R.layout.cutsizeupdatelayout,null)
                  var l=view.findViewById<EditText>(R.id.et_u_length)
                  var q=view.findViewById<EditText>(R.id.et_u_qty)
                  l.setText("${lstcs[i].length}")
                  q.setText("${lstcs[i].qty}")
                  builder.setView(view)
                      .setTitle("Update")
                      .setIcon(R.drawable.ic_edit_black_24dp)
                      .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                          if (l.text.toString().isEmpty() )
                          {
                              l.requestFocus()
                          }
                          else if(q.text.toString().isEmpty())
                          {
                              q.requestFocus()
                          }
                          else{

                              var cft=getCutSizeCFT(lstcs[i].width,lstcs[i].height,l.text.toString().toDouble(),q.text.toString().toInt())
                              lv_cs.deferNotifyDataSetChanged()
                              lstcs[i].length=l.text.toString().toDouble()
                              lstcs[i].qty=q.text.toString().toInt()
                              lstcs[i].cft=cft.toString().toDouble()
                              getCutsizeTotalCFT()
                          }
                      })
                      .setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->  })
                      .show()
              }*/

        } else {
            lstcs.add(c)
            val a = AdapterForCutSize(this, lstcs, cscft, tv_cs_cft, tv_cs_cmt, cs_lv, keyboard)
            cs_lv.adapter = a
            cs_lv.isScrollContainer = false
            cs_lv.isClickable = false

            /*cs_lv.setOnItemClickListener{ adapterView, view, i, l ->
                val inflater=LayoutInflater.from(this)
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                var view:View=inflater.inflate(R.layout.cutsizeupdatelayout,null)
                var l=view.findViewById<EditText>(R.id.et_u_length)
                var q=view.findViewById<EditText>(R.id.et_u_qty)
                l.setText("${lstcs[i].length}")
                q.setText("${lstcs[i].qty}")
                builder.setView(view)
                    .setTitle("Update")
                    .setIcon(R.drawable.ic_edit_black_24dp)
                    .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                        if (l.text.toString().isEmpty() )
                        {
                            l.requestFocus()
                        }
                        else if(q.text.toString().isEmpty())
                        {
                            q.requestFocus()
                        }
                        else{

                            var cft=getCutSizeCFT(lstcs[i].width,lstcs[i].height,l.text.toString().toDouble(),q.text.toString().toInt())
                            cs_lv.deferNotifyDataSetChanged()
                            lstcs[i].length=l.text.toString().toDouble()
                            lstcs[i].qty=q.text.toString().toInt()
                            lstcs[i].cft=cft.toString().toDouble()
                            getCutsizeTotalCFT()
                        }
                    })
                    .setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->  })
                    .show()
            }*/
        }
    }

    fun getinsertCutSizeCFT(w: Double, h: Double, l: Double, q: Int): Double {
        val cft = w * h * l * q / 144
        val cmt = cft / 35.30
        val f = DecimalFormat("###.##")
        val fm = DecimalFormat("###.####")
        val fcft = f.format(cft).toDouble()
        val fcmt = fm.format(cmt).toDouble()
        /* val c=CutSize(w,h,l,q,fcft,fcmt)
         lstcs.add(c)
         val a=AdapterForCutSize(this,lstcs,cscft,tv_cs_cft,tv_cs_cmt)
         cs_lv.adapter=a*/
        return fcft
    }

    fun insertcutsizemaster(name: String) {
        val context = this
        val db = DBHandler(context)
        val cutSize = CutSize(name)
        db.insertcutsizemaster(cutSize)
    }

    fun getlastid(): Int {
        val context = this
        val db = DBHandler(context)
        val i = db.getlastid()
        Toast.makeText(context, "$i", Toast.LENGTH_SHORT).show()
        return i
    }

    fun insertcutsizecft() {
        val context = this
        val db = DBHandler(context)
        val lastid = getlastid()
        for (i in 0..lstcs.size - 1) {
            val cs = CutSize(
                lstcs[i].width,
                lstcs[i].height,
                lstcs[i].length,
                lstcs[i].qty,
                lstcs[i].cft,
                lastid
            )
            db.insertcutsize(cs)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater
        menuInflater.inflate(maitrik.smarttimber.R.menu.cutsize, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.cssave -> {
                savedialog()
                //  Toast.makeText(this,"Save",Toast.LENGTH_SHORT).show()
            }
            R.id.csclr -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setIcon(R.drawable.ic_clear_red_24dp)
                    .setTitle("Clear all the entries ?")
                    .setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            lstcs.clear()
                            w.setText("")
                            h.setText("")
                            d.setText("")
                            l.setText("")
                            q.setText("")
                            w.requestFocus()
                            val a = AdapterForCutSize(
                                this,
                                lstcs,
                                cscft,
                                tv_cs_cft,
                                tv_cs_cmt,
                                cs_lv,
                                keyboard
                            )
                            cs_lv.adapter = a
                            getCutsizeTotalCFT()
                        })
                    .setNegativeButton(
                        "No",
                        DialogInterface.OnClickListener { dialogInterface, i -> })
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showKeyboard(activity: Activity?, e: EditText) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(e, InputMethodManager.SHOW_IMPLICIT)


        //  activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun show(activity: Activity?) {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun savedialog() {
        if (tvcft.text == "") {
            Toast.makeText(this, "Please Calculate CFT!!!", Toast.LENGTH_SHORT).show()
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val inflater: LayoutInflater = layoutInflater
            val view: View = inflater.inflate(R.layout.cspartynnamelayout, null)
            var name = view.et_cs_name
            builder.setView(view)
                .setTitle("Save Record")
                .setIcon(R.drawable.ic_save_white_24dp)
                .setPositiveButton("SAVE") { _, _ ->
                    if (name.text.toString().isEmpty()) {
                        name.error = "Enter Name"
                        name.requestFocus()
                        Toast.makeText(this, "Please Enter Name!!", Toast.LENGTH_SHORT).show()
                        show(this@CutSizeCFT)
                    } else {

                        insertcutsizemaster(name.text.toString())
                        insertcutsizecft()
                        var intent = Intent(this, CutSizeList::class.java)
                        startActivity(intent)
                    }
                }
                .setNegativeButton(
                    "NO"
                ) { _, _ ->

                }
            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun onBackPressed() {
        if (keyboard.isExpanded) {
            keyboard.translateLayout()
        } else {
            super.onBackPressed()
        }
    }

    fun forbidSoftInputMethod(mEdit: EditText) {
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        val currentVersion = Build.VERSION.SDK_INT
        var methodName: String? = null
        if (currentVersion >= 16) { // 4.2
            methodName = "setShowSoftInputOnFocus"
        } else if (currentVersion >= 14) { // 4.0
            methodName = "setSoftInputShownOnFocus"
        }
        if (methodName == null) {
            mEdit.inputType = InputType.TYPE_NULL
        } else {
            val cls = EditText::class.java
            val setShowSoftInputOnFocus: Method
            try {
                setShowSoftInputOnFocus = cls.getMethod(
                    methodName,
                    Boolean::class.javaPrimitiveType
                )
                setShowSoftInputOnFocus.isAccessible = true
                setShowSoftInputOnFocus.invoke(mEdit, false)
            } catch (e: NoSuchMethodException) {
                mEdit.inputType = InputType.TYPE_NULL
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }
    }


}


