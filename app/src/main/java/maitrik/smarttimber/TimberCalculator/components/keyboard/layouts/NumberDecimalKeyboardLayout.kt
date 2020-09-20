package maitrik.smarttimber.TimberCalculator.components.keyboard.layouts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import maitrik.smarttimber.R
import maitrik.smarttimber.TimberCalculator.components.keyboard.controllers.KeyboardController

/**
 * Created by Don.Brody on 7/20/18.
 */
class NumberDecimalKeyboardLayout(context: Context, controller: KeyboardController?) :
        KeyboardLayout(context, controller) {
    constructor(context: Context): this(context, null)



    override fun createRows(): List<LinearLayout> {
        val  view = LayoutInflater.from(context).inflate(R.layout.keyboard,this,true)
        var ll = view.findViewById<LinearLayout>(R.id.customKeyboard_llMain)
        val btn1 = view.findViewById<Button>(R.id.btn1)
        val btn2 = view.findViewById<Button>(R.id.btn2)
        val btn3 = view.findViewById<Button>(R.id.btn3)
        val btn4 = view.findViewById<Button>(R.id.btn4)
        val btn5 = view.findViewById<Button>(R.id.btn5)
        val btn6 = view.findViewById<Button>(R.id.btn6)
        val btn7 = view.findViewById<Button>(R.id.btn7)
        val btn8 = view.findViewById<Button>(R.id.btn8)
        val btn9 = view.findViewById<Button>(R.id.btn9)
        val btn0 = view.findViewById<Button>(R.id.btn0)
        val btnEqual = view.findViewById<Button>(R.id.btnEqual)
        val btnPoint = view.findViewById<Button>(R.id.btnPoint)
        val btnPA = view.findViewById<Button>(R.id.btnPA)
        val btnHalf = view.findViewById<Button>(R.id.btnHalf)
        val btnPona = view.findViewById<Button>(R.id.btnPona)
        val btnBackSpace = view.findViewById<Button>(R.id.btnBackSpace)
        val btnNext = view.findViewById<Button>(R.id.btnNext)

        clickButton(btn1,'1')
        clickButton(btn2,'2')
        clickButton(btn3,'3')
        clickButton(btn4,'4')
        clickButton(btn5,'5')
        clickButton(btn6,'6')
        clickButton(btn7,'7')
        clickButton(btn8,'8')
        clickButton(btn9,'9')
        clickButton(btn0,'0')
        clickButton(btnPoint,'.')
        clickButton(btnEqual,'=')
        clickButton(btnPA,KeyboardController.SpecialKey.PA)
        clickButton(btnHalf,KeyboardController.SpecialKey.HALF)
        clickButton(btnPona,KeyboardController.SpecialKey.PONA)
        clickButton(btnBackSpace,KeyboardController.SpecialKey.BACKSPACE)
        clickButton(btnNext,KeyboardController.SpecialKey.NEXT)

//        var  view = LayoutInflater.from(context).inflate(R.layout.keyboard,this,true)
//        var ll = view.findViewById<LinearLayout>(R.id.customKeyboard_llMain)
//        var btn1 = view.findViewById<Button>(R.id.ck_One)
////        btn1.setOnClickListener {
////            Toast.makeText(context,"dfasd",Toast.LENGTH_SHORT).show()
////            clickButton(btn1)
////        }
//        clickButton(btn1)

//        var ll = view.findViewById<LinearLayout>(R.id.llf)
        val columnWidth = 0.20f
        textSize = 22.0f
        val rowOne = ArrayList<View>()

        rowOne.add(createButton("1", columnWidth, '1'))
        rowOne.add(createButton("2", columnWidth, '2'))
        rowOne.add(createButton("3", columnWidth, '3'))
        rowOne.add(createButton("|||", columnWidth,KeyboardController.SpecialKey.PONA))
        rowOne.add(createButton("⌫", columnWidth, KeyboardController.SpecialKey.BACKSPACE))

        val rowTwo = ArrayList<View>()
        rowTwo.add(createButton("4", columnWidth, '4'))
        rowTwo.add(createButton("5", columnWidth, '5'))
        rowTwo.add(createButton("6", columnWidth, '6'))
        rowTwo.add(createButton("||", columnWidth,KeyboardController.SpecialKey.HALF))
        if (hasNextFocus) {
            rowTwo.add(createButton("Next", columnWidth, KeyboardController.SpecialKey.NEXT))
        } else {
            rowTwo.add(createButton("Done", columnWidth, KeyboardController.SpecialKey.DONE))
        }

        val rowThree = ArrayList<View>()
        rowThree.add(createButton("7", columnWidth, '7'))
        rowThree.add(createButton("8", columnWidth, '8'))
        rowThree.add(createButton("9", columnWidth, '9'))
        rowThree
                .add(createButton("|", columnWidth,KeyboardController.SpecialKey.PA))
        rowThree.add(createButton(".", columnWidth, '.'))

        val rowFour = ArrayList<View>()
        rowFour.add(createButton("⇦", columnWidth, KeyboardController.SpecialKey.BACK))
        rowFour.add(createButton("0", columnWidth, '0'))
        rowFour.add(createButton("⇨", columnWidth, KeyboardController.SpecialKey.FORWARD))
        rowFour.add(createButton("Clear", columnWidth, KeyboardController.SpecialKey.CLEAR))

        val rows = ArrayList<LinearLayout>()
        rows.add(createRow(rowOne))
        rows.add(createRow(rowTwo))
        rows.add(createRow(rowThree))
        rows.add(createRow(rowFour))

//        rows.add(ll)

        return rows
    }
}