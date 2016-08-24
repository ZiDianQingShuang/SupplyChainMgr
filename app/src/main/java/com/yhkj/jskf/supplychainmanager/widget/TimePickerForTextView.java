/**
 *
 */
package com.yhkj.jskf.supplychainmanager.widget;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author liupeng 日期输入控件
 */
public class TimePickerForTextView extends TextView {

    private Context context;

//    private DatePickerDialog dpDialog;

    private TimePickerDialog dpDialog;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Date date = new Date();

    private Calendar calendar = Calendar.getInstance(Locale.CHINA);

    /**
     * 构造方法
     *
     * @param context
     */
    public TimePickerForTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public TimePickerForTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public TimePickerForTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        // TODO Auto-generated method stub
        super.setOnClickListener(l);
    }

    /**
     * 焦点改变事件
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        // TODO Auto-generated method stub
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isEnabled()) {
                    return super.onTouchEvent(event);
                }
                dpDialog = new TimePickerDialog(context, TimePickerDialog.THEME_HOLO_LIGHT,
                        dateListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        true);
                dpDialog.show();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    TimePickerDialog.OnTimeSetListener dateListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = (hourOfDay<10?("0"+hourOfDay):hourOfDay) + ":" + (minute<10?("0"+minute):minute);
            setText(time);
            dpDialog.dismiss();
        }


    };


}
