package com.yhkj.jskf.supplychainmanager.utils;

import java.text.DecimalFormat;

/**
 * Created by wangxiaofei on 2016/5/17.
 */
public class DecimalUtils {
    private static DecimalFormat decimalFormat;


    public static String decimalKeepTwoPoint(double number) {
        decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(number);
    }
}
