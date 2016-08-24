package com.yhkj.jskf.supplychainmanager.utils;

/**
 * Created by wangxiaofei on 2016/5/6.
 */
public class API {
    //private final static String url = "http://www.xnsn.cc:86/api/"; // 正式版
    //private final static String url = "http://125.64.43.37/api/"; //远程测试
    private final static String url = "http://192.168.1.45:8080/"; //本地测试  MySSM/
    // private final static String url = "http://221.237.157.106:86/api/";// 新正式版


    /**
     * 接口
     */
    public static final class method {
        public static final String LOGIN = "loginOn";
        public static final String BUY = "BUY_GOODS";
        public static final String SEND = "addDeliveryGood";
        public static final String GET = "addReceiveGoods";
        public static final String REPORT = "REPORT_FORMS";
    }
}
