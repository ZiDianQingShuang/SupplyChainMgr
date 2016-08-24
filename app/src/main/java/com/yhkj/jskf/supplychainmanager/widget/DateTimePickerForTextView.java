/**
 *
 */
package com.yhkj.jskf.supplychainmanager.widget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author liupeng 日期输入控件
 */
public class DateTimePickerForTextView extends TextView {

    private Context context;

    private DatePickerDialog dpDialog;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Date date = null;

    private Calendar calendar = null;

    /**
     * 构造方法
     *
     * @param context
     */
    public DateTimePickerForTextView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public DateTimePickerForTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public DateTimePickerForTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        date = new Date();
        calendar = Calendar.getInstance(Locale.CHINA);
        this.context = context;
        setText(sdf.format(date));
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
                setText("");
                dpDialog = new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpDialog.show();
                dpDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        TimePickerDialog timeDialog = new TimePickerDialog(context, TimePickerDialog.THEME_HOLO_LIGHT,
                                timeListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                                true);
                        timeDialog.show();
                    }
                });
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private String selectDate = "";
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            try {
                String month = (monthOfYear + 1) < 10 ? ("0" + (monthOfYear + 1)) : (monthOfYear + 1) + "";
                String day = (dayOfMonth) < 10 ? ("0" + (dayOfMonth)) : (dayOfMonth) + "";
                selectDate = year + "-" + month + "-" + day;
                date = sdf.parse(year + "-" + month + "-" + day + " 00:00");
                setText(selectDate);
                dpDialog.dismiss();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    };

    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = (hourOfDay<10?("0"+hourOfDay):hourOfDay) + ":" + (minute<10?("0"+minute):minute);
            setText(selectDate + " " + time);
            try {
                date = sdf.parse(getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dpDialog.dismiss();
        }


    };


    /**
     * 获取时间戳
     * @return
     */
    public long getTimeZone(){
        return date.getTime();
    }


    public String getTimeString(){
        return getText().toString().trim();
    }
}
