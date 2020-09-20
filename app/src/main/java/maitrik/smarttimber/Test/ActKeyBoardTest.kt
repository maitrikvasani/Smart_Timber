package maitrik.smarttimber.Test

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.act_key_board_test.*
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.CustomKeyboardView
import maitrik.smarttimber.TimberCalculator.components.keyboard.layouts.KeyboardLayout

class ActKeyBoardTest : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_key_board_test)
//        boardView.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, et1)
//        boardView.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL,et2)
//        boardView.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, et3)
//        boardView.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, et4)
//        boardView.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, et5)
//        boardView.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, et6)
    }


}
