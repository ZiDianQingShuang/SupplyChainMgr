/**
 *
 */
package com.yhkj.jskf.supplychainmanager.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.TextView;

import com.yhkj.jskf.supplychainmanager.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author liupeng 日期输入控件
 */
public class DatePickerForTextView extends TextView {

    private Context context;

    private DatePickerDialog dpDialog;

    private Calendar calendar = null;

    /**
     * 构造方法
     *
     * @param context
     */
    public DatePickerForTextView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public DatePickerForTextView(Context context, AttributeSet attrs) {
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
    public DatePickerForTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        calendar = Calendar.getInstance(Locale.CHINA);
        this.context = context;
        setText(DateTimeUtils.formatTime(calendar.getTimeInMillis(), "yyyy-MM-dd"));
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
                oldTimeString = getText().toString().trim();
                if (dpDialog == null) {
                    dpDialog = new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                            dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dpDialog.show();
                } else if (dpDialog != null && !dpDialog.isShowing()) {
                    dpDialog.show();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated catch block
            String month = (monthOfYear + 1) < 10 ? ("0" + (monthOfYear + 1)) : (monthOfYear + 1) + "";
            String day = (dayOfMonth) < 10 ? ("0" + (dayOfMonth)) : (dayOfMonth) + "";
            String date = year + "-" + month + "-" + day;
            dpDialog.dismiss();
            setText(date);
        }

    };

    /**
     * 获取时间戳
     *
     * @return
     */
    public long getTimeZone() {
        return DateTimeUtils.DateStringToLong(getText().toString().trim(), "yyyy-MM-dd");
    }


    public String getTimeString() {
        return getText().toString().trim();
    }

    private String oldTimeString;

    public String getOldTimeString() {
        return oldTimeString;
    }

}
