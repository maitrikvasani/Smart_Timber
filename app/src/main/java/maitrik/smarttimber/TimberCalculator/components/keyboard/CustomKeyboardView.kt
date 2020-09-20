package maitrik.smarttimber.TimberCalculator.components.keyboard

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import maitrik.smarttimber.Cut_Size.ActCutSizeCFT
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.expandableView.ExpandableState
import maitrik.smarttimber.TimberCalculator.components.expandableView.ExpandableStateListener
import maitrik.smarttimber.TimberCalculator.components.expandableView.ExpandableView
import maitrik.smarttimber.TimberCalculator.components.keyboard.controllers.DefaultKeyboardController
import maitrik.smarttimber.TimberCalculator.components.keyboard.controllers.KeyboardController
import maitrik.smarttimber.TimberCalculator.components.keyboard.controllers.NumberDecimalKeyboardController
import maitrik.smarttimber.TimberCalculator.components.keyboard.layouts.KeyboardLayout
import maitrik.smarttimber.TimberCalculator.components.keyboard.layouts.NumberDecimalKeyboardLayout
import maitrik.smarttimber.TimberCalculator.components.keyboard.layouts.NumberKeyboardLayout
import maitrik.smarttimber.TimberCalculator.components.keyboard.layouts.QwertyKeyboardLayout
import maitrik.smarttimber.TimberCalculator.components.textFields.CustomTextField
import maitrik.smarttimber.TimberCalculator.components.utilities.ComponentUtils
import java.util.*

/**
 * Created by Maitrik on 28/06/2020
 */
class CustomKeyboardView(context: Context, attr: AttributeSet) : ExpandableView(context, attr) {
    private var fieldInFocus: EditText? = null
    private val keyboards = HashMap<EditText, KeyboardLayout?>()
    private val keyboardListener: KeyboardListener

    companion object {

        var onNextCLick: OnNextClickListener? = null
        var onFocus: OnFocusChanged? = null

        fun onNextClickFun(listener: OnNextClickListener) {
            onNextCLick = listener
        }

        fun onEditTextFocus(listener: OnFocusChanged){
            onFocus = listener
        }
    }


    init {
        setBackgroundColor(Color.TRANSPARENT)


        keyboardListener = object : KeyboardListener {
            override fun characterClicked(c: Char) {
                // don't need to do anything here
            }

            override fun specialKeyClicked(key: KeyboardController.SpecialKey) {
                if (key === KeyboardController.SpecialKey.DONE) {
                    translateLayout()
                } else if (key === KeyboardController.SpecialKey.NEXT) {
                    onNextCLick!!.onClick(fieldInFocus!!)
                }
            }
        }


        // register listener with parent (listen for state changes)
        registerListener(object : ExpandableStateListener {
            override fun onStateChange(state: ExpandableState) {
                if (state === ExpandableState.EXPANDED) {
                    checkLocationOnScreen()
                }
            }
        })

        // empty onClickListener prevents user from
        // accidentally clicking views under the keyboard
        setOnClickListener({})
        isSoundEffectsEnabled = false
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun registerEditText(type: KeyboardType, field: EditText) {
        if (!field.isEnabled) {
            return  // disabled fields do not have input connections
        }

        field.setRawInputType(InputType.TYPE_CLASS_TEXT)
        field.setTextIsSelectable(true)
        field.showSoftInputOnFocus = false
        field.isSoundEffectsEnabled = false
        field.isLongClickable = false

        val inputConnection = field.onCreateInputConnection(EditorInfo())
        keyboards[field] = createKeyboardLayout(type, inputConnection, field)
        keyboards[field]?.registerListener(keyboardListener)

        field.onFocusChangeListener = OnFocusChangeListener { et: View, hasFocus: Boolean ->
            if (hasFocus) {

//                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                ComponentUtils.hideSystemKeyboard(context, field)

                // if we can find a view below this field, we want to replace the
                // done button with the next button in the attached keyboard
                field.focusSearch(View.FOCUS_DOWN)?.run {
                    if (this is EditText) keyboards[field]?.hasNextFocus = true
                }
                fieldInFocus = field
                renderKeyboard()
                if (!isExpanded) {
                    translateLayout()
                }
                if (et == et.findViewById<EditText>(R.id.cutSize_etQty) || et == et.findViewById(R.id.row_cutSize_etUQty)){
//                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                    ActCutSizeCFT.click = false
                }else{
                    ActCutSizeCFT.click = true
                }
//                onFocus!!.onFocus(et)
            } else if (!hasFocus && isExpanded) {
                if (et == et.findViewById<EditText>(R.id.cutSize_etQty) || et == et.findViewById(R.id.row_cutSize_etUQty)){
//                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                    ActCutSizeCFT.click = false
                }else{
                    ActCutSizeCFT.click = true
                }
//                onFocus!!.onFocus(et)
//                Toast.makeText(context, "Hello 2 ", Toast.LENGTH_SHORT).show()
                for (editText in keyboards.keys) {
                    if (editText.hasFocus()) {
                        editText.setSelection(editText.text.length)
                        return@OnFocusChangeListener
                    }
                }
                translateLayout()
            }
        }

        field.setOnClickListener{
            if (!isExpanded) {
                translateLayout()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun autoRegisterEditTexts(rootView: ViewGroup) {
        registerEditTextsRecursive(rootView)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerEditTextsRecursive(view: View) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                registerEditTextsRecursive(view.getChildAt(i))
            }
        } else {
            if (view is CustomTextField) {
                registerEditText(view.keyboardType, view)
            } else if (view is EditText) {
                when (view.inputType) {
                    InputType.TYPE_CLASS_NUMBER -> {
                        registerEditText(KeyboardType.NUMBER, view)
                    }
                    InputType.TYPE_NUMBER_FLAG_DECIMAL -> {
                        registerEditText(KeyboardType.NUMBER_DECIMAL, view)
                    }
                    else -> {
                        registerEditText(KeyboardType.QWERTY, view)
                    }
                }
            }
        }
    }

    fun unregisterEditText(field: EditText?) {
        keyboards.remove(field)
    }

    fun clearEditTextCache() {
        keyboards.clear()
    }

    private fun renderKeyboard() {
        removeAllViews()
        val keyboard: KeyboardLayout? = keyboards[fieldInFocus]
        keyboard?.let {
            it.orientation = LinearLayout.VERTICAL
            it.createKeyboard(measuredWidth.toFloat())
            addView(keyboard)
        }
    }

    private fun createKeyboardLayout(
        type: KeyboardType,
        ic: InputConnection,
        et: EditText
    ): KeyboardLayout? {
        when (type) {
            KeyboardType.NUMBER -> {
                return NumberKeyboardLayout(context, createKeyboardController(type, ic, et))
            }
            KeyboardType.NUMBER_DECIMAL -> {
                return NumberDecimalKeyboardLayout(context, createKeyboardController(type, ic, et))
            }
            KeyboardType.QWERTY -> {
                return QwertyKeyboardLayout(context, createKeyboardController(type, ic, et))
            }
            else -> return@createKeyboardLayout null // this should never happen
        }
    }

    private fun createKeyboardController(
        type: KeyboardType,
        ic: InputConnection,
        et: EditText
    ): KeyboardController? {
        return when (type) {
            KeyboardType.NUMBER_DECIMAL -> {
                NumberDecimalKeyboardController(ic, et)
            }
            else -> {
                // not all keyboards require a custom controller
                DefaultKeyboardController(ic, et)
            }
        }
    }

    override fun configureSelf() {
        renderKeyboard()
        checkLocationOnScreen()
    }

    /**
     * Check if fieldInFocus has a parent that is a ScrollView.
     * Ensure that ScrollView is enabled.
     * Check if the fieldInFocus is below the KeyboardLayout (measured on the screen).
     * If it is, find the deltaY between the top of the KeyboardLayout and the top of the
     * fieldInFocus, add 20dp (for padding), and scroll to the deltaY.
     * This will ensure the keyboard doesn't cover the field (if conditions above are met).
     */
    private fun checkLocationOnScreen() {
        fieldInFocus?.run {
            var fieldParent = this.parent
            while (fieldParent !== null) {
                if (fieldParent is ScrollView) {
                    if (!fieldParent.isSmoothScrollingEnabled) {
                        break
                    }

                    val fieldLocation = IntArray(2)
                    this.getLocationOnScreen(fieldLocation)

                    val keyboardLocation = IntArray(2)
                    this@CustomKeyboardView.getLocationOnScreen(keyboardLocation)

                    val fieldY = fieldLocation[1]
                    val keyboardY = keyboardLocation[1]

                    if (fieldY > keyboardY) {
                        val deltaY = (fieldY - keyboardY)
                        val scrollTo =
                            (fieldParent.scrollY + deltaY + this.measuredHeight + 10.toDp)
                        fieldParent.smoothScrollTo(0, scrollTo)
                    }
                    break
                }
                fieldParent = fieldParent.parent
            }
        }
    }

    enum class KeyboardType {
        NUMBER,
        NUMBER_DECIMAL,
        QWERTY
    }


}

interface OnNextClickListener {
    fun onClick(et: EditText) {
    }


}

interface OnFocusChanged{
    fun onFocus(view: View) {
    }
}
