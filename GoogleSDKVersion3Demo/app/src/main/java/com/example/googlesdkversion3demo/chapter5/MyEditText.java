package com.example.googlesdkversion3demo.chapter5;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

public class MyEditText extends EditText {
    private int startPoint=0;
    private int endPoint=0;
    public MyEditText(Context context, AttributeSet attrs){
        super(context,attrs,android.R.attr.editTextStyle);
        setText(getText(), BufferType.EDITABLE);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Editable editable=getEditableText();
        int action=event.getAction();
        Layout layout=getLayout();

        //由于编辑框内的内容是可以滚动的，所以需要加上scrollY,即向上滚动了y值+当前触摸的y值
        //event.getY是现在触摸在TextView的位置，srcollY是已经滑动的y
        int pointY=layout.getLineForVertical(
                getScrollY()+(int)event.getY()
        );

        switch(action){
            case MotionEvent.ACTION_DOWN:
                //得到水平偏移量
                startPoint=layout.getOffsetForHorizontal(pointY,event.getX());
                Selection.setSelection(editable,startPoint);
                break;
            case MotionEvent.ACTION_MOVE:
                //得到水平偏移量
                endPoint=layout.getOffsetForHorizontal(
                        pointY,event.getX()
                );
                Selection.setSelection(editable,startPoint,endPoint);
                break;
        }
        return true;
    }

    public String getSelectedText(){
        return getText().toString().substring(startPoint,endPoint);
    }
}
