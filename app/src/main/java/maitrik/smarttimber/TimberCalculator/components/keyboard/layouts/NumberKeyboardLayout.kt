package maitrik.smarttimber.TimberCalculator.components.keyboard.layouts

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.controllers.KeyboardController
import maitrik.smarttimber.TimberCalculator.components.keyboard.layouts.KeyboardLayout

/**
 * Created by Don.Brody on 7/18/18.
 */
class NumberKeyboardLayout(context: Context, controller: KeyboardController?) :
        KeyboardLayout(context, controller) {
    var ll = findViewById<LinearLayout>(R.id.customKeyboard_llMain)
    constructor(context: Context): this(context, null)

    override fun createRows(): List<LinearLayout> {
        val columnWidth = 0.20f
        textSize = 22.0f

        val rowOne = ArrayList<View>()
        rowOne.add(createButton("1", columnWidth, '1'))
        rowOne.add(createButton("2", columnWidth, '2'))
        rowOne.add(createButton("3", columnWidth, '3'))

        val rowTwo = ArrayList<View>()
        rowTwo.add(createButton("4", columnWidth, '4'))
        rowTwo.add(createButton("5", columnWidth, '5'))
        rowTwo.add(createButton("6", columnWidth, '6'))

        val rowThree = ArrayList<View>()
        rowThree.add(createButton("7", columnWidth, '7'))
        rowThree.add(createButton("8", columnWidth, '8'))
        rowThree.add(createButton("9", columnWidth, '9'))

        val rowFour = ArrayList<View>()
        rowFour.add(createButton("⌫", columnWidth, KeyboardController.SpecialKey.BACKSPACE))
        rowFour.add(createButton("0", columnWidth, '0'))
        if (hasNextFocus) {
            rowFour.add(createButton("Next", columnWidth, KeyboardController.SpecialKey.NEXT))
        } else {
            rowFour.add(createButton("Done", columnWidth, KeyboardController.SpecialKey.DONE))
        }

        val rows = ArrayList<LinearLayout>()
        rows.add(createRow(rowOne))
        rows.add(createRow(rowTwo))
        rows.add(createRow(rowThree))
        rows.add(createRow(rowFour))

        return rows
    }
}
