package com.yhkj.jskf.supplychainmanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * 网络连接工具类
 *
 * @author liupeng
 */
public class NetUtils {

    //private final static String url = "http://www.xnsn.cc:86/api/"; // 正式版
    //private final static String url = "http://125.64.43.37/api/"; //远程测试
    public final static String url = "http://192.168.1.45:8080/"; //本地测试  MySSM/
    // private final static String url = "http://221.237.157.106:86/api/";// 新正式版
    private static Context cx;
    private static NetUtils nUtils;

    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    /**
     * webApi调用方法
     *
     * @param context 上下文
     * @param context
     * @return
     */
    public static NetUtils init(Context context) {
        if (nUtils == null) {
            nUtils = new NetUtils(context);
        }
        return nUtils;
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public NetUtils(Context context) {
        // TODO Auto-generated constrctor stub
        cx = context;
    }


    /**
     * 判断网络情况
     *
     * @param context 上下文
     * @return false 表示没有网络 true 表示有网络
     */
    public boolean isNetworkAvalible(Context context) {
        boolean isOk = false;

        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            isOk = false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isOk = true;
                    }
                }
            }
        }
        if (!isOk) {
            Toast.makeText(context, "当前没有可以使用的网络，请设置网络！",
                    Toast.LENGTH_SHORT).show();
        }
        return isOk;
    }

    /**
     * @param ctx
     * @return
     */
    public Boolean isNetwork(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    public Boolean isWifi(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return info.getType() == ConnectivityManager.TYPE_WIFI;
        } else {
            return false;
        }
    }


    /**
     * @param context
     * @return
     */
    public String getNetWorkSubTypeName(Context context) {
        String newworkSubtype = null;
        // 获得网络状态管理器
        connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            newworkSubtype = null;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        newworkSubtype = net_info[i].getSubtypeName();
                    }
                }
            }
        }
        return newworkSubtype;
    }


    /**
     * 接口
     */
    public static final class API {
        /**
         * 用户管理
         */
        public static final String LOGIN = "loginOn";
        public static final String getAllClient = "getAllClient";//获取所有的发货去向

        ///////////////////////////////////////////////收集//////////////////////////////////////////////////
        /**
         * 购买管理
         */

        // public static final String BUY_GOODS = "BUY_GOODS";
        public static final String addPurchaseGoods = "addPurchaseGoods";//添加采购
        public static final String updateOnePurchaseGoods = "updateOnePurchaseGoods";//更新采购
        public static final String findAllPurchaseGoodsByPage = "findAllPurchaseGoodsByPage";//分页查询采购管理
        public static final String findAllByPagedAndTime = "findAllByPagedAndTime";//分页查询采购管理

        /**
         * 发货管理
         */
        public static final String addDeliveryGood = "addDeliveryGood";//添加发货
        public static final String findAllDeliveryGoods = "findAllDeliveryGoods";//获取所有的发货信息
        public static final String findAllDeliveryGoodsByPage = "findAllDeliveryGoodsByPage";//获取所有的发货信息
        public static final String updateOneDeliveryGoods = "updateOneDeliveryGoods";//更新所有的发货信息

        /**
         * 收货管理
         */
        public static final String addReceiveGoods = "addReceiveGoods";//交货
        public static final String findAllReceiveGoods = "findAllReceiveGoods";//分页收货管理查询
        public static final String findAllReceiveGoodsByPage = "findAllReceiveGoodsByPage";//分页收货管理查询
        public static final String getNoReceiveDeliveryGoodsListByUserId = "getNoReceiveDeliveryGoodsListByUserId";//分页收货管理查询

        //////////////////////////////////////////////报表///////////////////////////////////////////////////
        public static final String findAllReceivingGoodsStatistics = "findAllReceivingGoodsStatistics";//客户收货查询


    }

}
