package maitrik.smarttimber.TimberCalculator.components.keyboard.controllers

import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputConnection
import maitrik.smarttimber.TimberCalculator.components.keyboard.KeyboardListener

/**
 * Created by Maitrik on 28/06/2020.
 */
abstract class KeyboardController(private val inputConnection: InputConnection) {

    private val listeners = ArrayList<KeyboardListener>()
    var cursorPosition: Int = 0
    private var inputText = ""

    companion object {
        private fun deleteCharacter(text: String, index: Int): String {
            Log.d("From Keyboard "," Delete : $text && $index")
            return StringBuilder(text).deleteCharAt(index).toString()
        }

        private fun addCharacter(text: String, addition: Char, index: Int): String {
            return text.substring(0, index) + addition + text.substring(index)
        }
        // Me Maitrik
        private fun addDecimalDigit(text: String,addition: String,index: Int):String{
            Log.d("From Key Board","Add Decimal Digits Fun: ${text.substring(0, index) + addition + text.substring(index)}")
            return text.substring(0, index) + addition + text.substring(index)
        }

        private fun replaceCharacter(text: String, replacement: Char, index: Int): String {
            return StringBuilder(text).setCharAt(index, replacement).toString()
        }

    }

    @Synchronized fun onKeyStroke(c: Char) {
        updateMembers()
        handleKeyStroke(c)
        for (listener in listeners) {
            listener.characterClicked(c)
        }
    }

    @Synchronized fun onKeyStroke(key: SpecialKey) {
        Log.d("From Key Board","On Key Stroke")
        updateMembers()
        handleKeyStroke(key)
        Log.e("From Key Board"," Key : $key")
        for (listener in listeners) {
            listener.specialKeyClicked(key)
        }
    }

    fun registerListener(listener: KeyboardListener) {
        listeners.add(listener)
    }

    private fun updateMembers() {
        val beforeText = beforeCursor()
        val afterText = afterCursor()
        cursorPosition = beforeText.length
        inputText = beforeText + afterText
        Log.d("From Key Board","update members $inputText")
    }

    private fun beforeCursor(): String {
        return inputConnection.getTextBeforeCursor(100, 0).toString()
    }

    private fun afterCursor(): String {
        return inputConnection.getTextAfterCursor(100, 0).toString()
    }

     fun cursorPosition(): Int {
        return cursorPosition
    }

    internal fun inputText(): String {
        return inputText
    }

    internal fun deleteNextCharacter() {
        if (cursorPosition >= inputText.length) {
            return
        }
        inputConnection.deleteSurroundingText(0, 1)
        inputText = deleteCharacter(inputText, cursorPosition)
    }

    internal fun deletePreviousCharacter() {
        if (cursorPosition == 0) {
            return
        }
        inputConnection.deleteSurroundingText(1, 0)
        inputText = deleteCharacter(inputText, --cursorPosition)
    }

    internal fun addCharacter(c: Char) {
        if (cursorPosition >= maxCharacters()) {
            return
        }
        inputConnection.commitText(c.toString(), 1)
        inputText = if (cursorPosition++ >= inputText.length) { inputText + c } else {
            addCharacter(inputText, c, (cursorPosition - 1))
        }
    }

    //Add By Maitrik
    fun addDecimalMultipleDigit(s : String){
        if (cursorPosition >= maxCharacters()) {
            return
        }
        inputConnection.commitText(s.toString(), 1)
        Log.d("From Key Board","Add Decimal Digits S: $s")
        inputText = if (cursorPosition++ >= inputText.length) {
            inputText + s
        } else {
            Log.d("From Key Board","Add Decimal Digits Condition$s")
            addDecimalDigit(inputText, s, (cursorPosition - 1))
        }
        Log.d("From Key Board","Input Text : S $s")
    }


    internal fun replaceNextCharacter(c: Char) {
        deleteNextCharacter()
        addCharacter(c)
    }

    // in the case a synchronous action is required, use this
    internal fun synchronousMoveCursorForward() {
        replaceNextCharacter(inputText()[cursorPosition()])
    }

    // cursor actions are asynchronous events
    internal fun moveCursorForwardAction() {
        if (cursorPosition >= inputText.length) {
            return
        }
        inputConnection.sendKeyEvent(
                KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT)
        )
        inputConnection.sendKeyEvent(
                KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_RIGHT)
        )
    }

    internal fun moveCursorBackAction() {
        if (cursorPosition == 0) {
            return
        }
        inputConnection.sendKeyEvent(
                KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT)
        )
        inputConnection.sendKeyEvent(
                KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_LEFT)
        )
    }

    internal fun clearAll() {
        while (cursorPosition < inputText.length) {
            deleteNextCharacter()
        }
        while (cursorPosition > 0) {
            deletePreviousCharacter()
        }
    }

    internal abstract fun handleKeyStroke(c: Char)

    internal abstract fun handleKeyStroke(key: SpecialKey)

    internal abstract fun maxCharacters(): Int

    enum class SpecialKey {
        DELETE,
        BACKSPACE,
        CLEAR,
        FORWARD,
        BACK,
        NEXT,
        CAPS,
        SYMBOL,
        ALPHA,
        DONE,
        PONA,
        HALF,
        PA
    }
}
