package maitrik.smarttimber.TimberCalculator.components.keyboard.controllers

import android.view.inputmethod.InputConnection
import android.widget.EditText

/**
 * Created by Don.Brody on 7/20/18.
 */
class NumberDecimalKeyboardController(inputConnection: InputConnection,et : EditText):
    DefaultKeyboardController(inputConnection,et) {

    override fun handleKeyStroke(c: Char) {
        if (c == '.') {
            // decimal numbers can only have one decimal point
            if (!inputText().contains('.')) {
                addCharacter(c)
            }
        } else {
            addCharacter(c)
        }
    }


//    override fun handleKeyStroke(key: SpecialKey) {
//        if (key == SpecialKey.HALF){
//            if (!inputText().contains(".25")){
//                addDecimalMultipleDigit(".25")
//            }
//        }else {
//            addDecimalMultipleDigit(".25")
//        }
//    }
}