package maitrik.smarttimber.TimberCalculator.components.keyboard.controllers

import android.text.InputType
import android.view.inputmethod.InputConnection
import android.widget.EditText
import maitrik.smarttimber.Cut_Size.ActCutSizeCFT
import maitrik.smarttimber.Cut_Size.App
import maitrik.smarttimber.Cut_Size.CutSizeCFT


/**
 * Created by Maitrik on 28/06/2020.
 */
open class DefaultKeyboardController(inputConnection: InputConnection ,editText: EditText ) :
        KeyboardController(inputConnection) {

    var et = editText

    companion object {
        // Default controller character lengths should be set as an attribute of their EditText
        private const val MAX_CHARACTERS: Int = Int.MAX_VALUE
        var isClick : Boolean = true

            var onClickSpecialButton : OnClickSpecialButton? = null

            fun onCheck(listener: OnClickSpecialButton){
                onClickSpecialButton = listener
            }

    }




    override fun handleKeyStroke(c: Char) {
        addCharacter(c)
    }

    override fun handleKeyStroke(key: SpecialKey) {
        when (key) {
            SpecialKey.DELETE -> {
                deleteNextCharacter()
            }
            SpecialKey.PONA -> {
                if (ActCutSizeCFT.click) {
                    if (!inputText().contains('.')) {
                        addDecimalMultipleDigit(".75")
                    }
                }
            }
            SpecialKey.HALF -> {
                if (ActCutSizeCFT.click) {
                    if (!inputText().contains('.')) {
                        addDecimalMultipleDigit(".5")
                    }
                }
//                if (!inputText().contains('.')) {
//                    if (key == SpecialKey.HALF) {
//                        if (!inputText().contains(".5")) {
//                            addDecimalMultipleDigit(".5")
//                        }
//                    } else {
//                        addDecimalMultipleDigit(".5")
//                    }
//                }
            }
            SpecialKey.PA -> {
                if (ActCutSizeCFT.click) {
                    if (!inputText().contains('.')) {
                        addDecimalMultipleDigit(".25")
                    }
                }
            }
            SpecialKey.BACKSPACE -> {
                deletePreviousCharacter()
            }
            SpecialKey.CLEAR -> {
                clearAll()
            }
            SpecialKey.FORWARD -> {
                moveCursorForwardAction()
            }
            SpecialKey.BACK -> {
                moveCursorBackAction()
            }
            else -> {
                // If you need access to one of the SpecialKey's not listed here, override
                // this method in a child class and implement it there.
                return
            }
        }
    }

    override fun maxCharacters(): Int {
        return MAX_CHARACTERS
    }


}

interface OnClickSpecialButton{
    fun checkIsNumber(inputText : KeyboardController.SpecialKey)
}
