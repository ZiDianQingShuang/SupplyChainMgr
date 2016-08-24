package com.yhkj.jskf.supplychainmanager.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 *
 * @author liupeng
 */
public class ToastUtility {

    private static ToastUtility tUtility;
    private static Context ctx;

    public static ToastUtility init(Context context) {
        ctx = context;
        tUtility = new ToastUtility(context);
        return tUtility;
    }

    /**
     * 构造函数
     */
    private  ToastUtility(Context context) {
        // TODO Auto-generated constructor stub
        ctx = context;
    }


    public static void showToast(String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }


    public void showToast(String message, int gravity) {
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }


    public void showToast(String message, float horizontalMargin, float verticalMargin) {
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
        toast.setMargin(horizontalMargin, verticalMargin);
        toast.show();
    }

}
