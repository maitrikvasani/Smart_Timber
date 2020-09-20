package maitrik.smarttimber.Test;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import maitrik.smarttimber.R;

public class MyKeyboardCopy extends LinearLayout implements View.OnClickListener {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDel, btnNext, btnSava, btnHalf, btnPona, btnPoint;

    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;

    @Override
    public void removeAllViews() {
        super.removeAllViews();
    }


    public MyKeyboardCopy(Context context) {
        this(context, null, 0);
    }

    public MyKeyboardCopy(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboardCopy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        btn1 = (Button) findViewById(R.id.ck_One);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.ck_Two);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.ck_Three);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.ck_Four);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.ck_Five);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.ck_Six);
        btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.ck_Seven);
        btn7.setOnClickListener(this);
        btn8 = (Button) findViewById(R.id.ck_Eight);
        btn8.setOnClickListener(this);
        btn9 = (Button) findViewById(R.id.ck_Nine);
        btn9.setOnClickListener(this);
        btn0 = (Button) findViewById(R.id.ck_Zero);
        btn0.setOnClickListener(this);
        btnSava = (Button) findViewById(R.id.ck_Sava);
        btnSava.setOnClickListener(this);
        btnHalf = (Button) findViewById(R.id.ck_Half);
        btnHalf.setOnClickListener(this);
        btnPona = (Button) findViewById(R.id.ck_Pona);
        btnPona.setOnClickListener(this);
        btnPoint = (Button) findViewById(R.id.ck_Point);
        btnPoint.setOnClickListener(this);
        btnNext = (Button) findViewById(R.id.ck_Next);
        btnNext.setOnClickListener(this);
        btnDel = (Button) findViewById(R.id.ck_BackSPace);
        btnDel.setOnClickListener(this);

        keyValues.put(R.id.ck_One, "1");
        keyValues.put(R.id.ck_Two, "2");
        keyValues.put(R.id.ck_Three, "3");
        keyValues.put(R.id.ck_Four, "4");
        keyValues.put(R.id.ck_Five, "5");
        keyValues.put(R.id.ck_Six, "6");
        keyValues.put(R.id.ck_Seven, "7");
        keyValues.put(R.id.ck_Eight, "8");
        keyValues.put(R.id.ck_Nine, "9");
        keyValues.put(R.id.ck_Zero, "0");
        keyValues.put(R.id.ck_Sava, ".25");
        keyValues.put(R.id.ck_Half, ".5");
        keyValues.put(R.id.ck_Pona, ".75");
        keyValues.put(R.id.ck_Point, ".");
        keyValues.put(R.id.ck_Next, "\n");
        keyValues.put(R.id.ck_BackSPace, "1");
    }


    @Override
    public void onClick(View view) {
        if (inputConnection == null)
            return;

        if (view.getId() == R.id.ck_BackSPace) {
            CharSequence selectedText = inputConnection.getSelectedText(0);

            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(view.getId());
            if (view.getId() == R.id.ck_Half) {
                Toast.makeText(getContext(),"Half"+value,Toast.LENGTH_SHORT).show();
                if (!value.contains(".5")) {
                    inputConnection.commitText(value, 1);
                }
            }
            else if (view.getId() == R.id.ck_Pona) {
                Toast.makeText(getContext(),"Pona"+value,Toast.LENGTH_SHORT).show();
                if (!value.contains(".75")) {
                    inputConnection.commitText(value, 1);
                }
            }
//            else if (view.getId() == R.id.ck_Sava) {
//                if (!value.contains(".25")) {
//                    inputConnection.commitText(value, 1);
//                }
//            } else if (view.getId() == R.id.ck_Point) {
//                if (!value.contains(".")) {
//                    inputConnection.commitText(value, 1);
//                }
//            }
            else {
                inputConnection.commitText(value, 1);
            }


//            value.contains(".25");
//            if (value.contains(".25")){
//                Log.e("From",value);
//                return;
//            }else{
//            inputConnection.commitText(value,1);
//            }
        }
    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }
}
