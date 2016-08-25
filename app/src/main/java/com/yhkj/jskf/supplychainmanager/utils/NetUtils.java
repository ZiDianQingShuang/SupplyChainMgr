package com.yhkj.jskf.supplychainmanager.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;


/**
 * 网络连接工具类
 *
 * @author liupeng
 */
public class NetUtils {

    //private final static String url = "http://www.xnsn.cc:86/api/"; // 正式版
    //private final static String url = "http://125.64.43.37/api/"; //远程测试
    private final static String url = "http://192.168.1.81:8080/"; //本地测试  MySSM/
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
     * 判断网络状态
     *
     * @param ctx
     * @return
     */
    public Boolean isNetwork(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 判断是否是wifi网络环境
     *
     * @param ctx
     * @return
     */
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
     * 获取网络连接
     *
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
        public static final String LOGIN = "user/login";
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


    /**
     * 通用带有ProgressDialog的请求，要调用超类的OnReponse方法，或者在回调中手动关闭ProgressDialog
     *
     * @param method
     * @param params
     * @param pDialog
     * @return
     */
    public static boolean getString(String method, Map<String, String> params, final ProgressDialog pDialog, final ProgressDialogStringCallback stringCallback) {
        if (!NetUtils.init(cx).isNetwork(cx)) {
            ToastUtility.showToast("网络连接中断，请求失败!");
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            cancelRequest();
            return false;
        }
        stringCallback.setpDialog(pDialog);
        OkHttpUtils.post(url + method)
                .tag(cx)
                .params(params)
                .execute(stringCallback);
        return true;
    }


    /**
     * 通用带有ProgressDialog的请求，要调用超类的OnReponse方法，或者在回调中手动关闭ProgressDialog
     *
     * @param method
     * @param params
     * @param progressDialogTitle
     * @return
     */
    public static boolean getString(String method, Map<String, String> params, final String progressDialogTitle, final ProgressDialogStringCallback stringCallback) {
        if (!NetUtils.init(cx).isNetwork(cx)) {
            ToastUtility.showToast("网络连接中断，请求失败!");
            cancelRequest();
            return false;
        }
        final ProgressDialog pDialog = ActivityUtils.showProgress(cx, progressDialogTitle);
        stringCallback.setpDialog(pDialog);
        OkHttpUtils.post(url + method)
                .tag(cx)
                .params(params)
                .execute(stringCallback);
        return true;
    }

    /**
     * 通用没有ProgressDialog的请求
     *
     * @param method
     * @param params
     * @return
     */
    public static boolean getString(String method, Map<String, String> params, final StringCallback stringCallback) {
        if (!NetUtils.init(cx).isNetwork(cx)) {
            ToastUtility.showToast("网络连接中断，请求失败!");
            cancelRequest();
            return false;
        }
        OkHttpUtils.post(url + method)
                .tag(cx)
                .params(params)
                .execute(stringCallback);
        return true;
    }


    /**
     * 带有 ProgressDialog 的  Json String请求
     *
     * @param method
     * @param params
     * @param pDialog
     * @param progressDialogCallback
     * @return
     */
    public static <T> boolean getString(String method, Map<String, String> params, final ProgressDialog pDialog, final Class<T> clazz, final ProgressDialogCallback progressDialogCallback) {
        if (!NetUtils.init(cx).isNetwork(cx)) {
            ToastUtility.showToast("网络连接中断，请求失败!");
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            cancelRequest();
            return false;
        }
        OkHttpUtils.post(url + method)
                .tag(cx)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            T t = GsonUtils.fromJson(s, clazz);
                            progressDialogCallback.onSuccess(t);
                        } else {
                            progressDialogCallback.onFailed();
                        }
                    }
                });
        return true;
    }

    /**
     * 带有 ProgressDialog 的   Json String请求
     *
     * @param method
     * @param params
     * @param progressDialogTitle
     * @param progressDialogCallback
     * @return
     */
    public static <T> boolean getString(String method, Map<String, String> params, final String progressDialogTitle, final Class<T> clazz, final ProgressDialogCallback progressDialogCallback) {
        if (!NetUtils.init(cx).isNetwork(cx)) {
            ToastUtility.showToast("网络连接中断，请求失败!");
            cancelRequest();
            return false;
        }
        final ProgressDialog pDialog = ActivityUtils.showProgress(cx, progressDialogTitle);
        OkHttpUtils.post(url + method)
                .tag(cx)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            T t = GsonUtils.fromJson(s, clazz);
                            progressDialogCallback.onSuccess(t);
                        } else {
                            progressDialogCallback.onFailed();
                        }
                    }
                });
        return true;
    }

    /**
     * @param method
     * @param params
     * @param progressDialogTitle
     * @param clazz
     * @param entityCallback
     * @param <T>
     * @return
     */
    public static <T> boolean getString(String method, Map<String, String> params, final String progressDialogTitle, final Class<T> clazz, final EntityCallback entityCallback) {
        if (!NetUtils.init(cx).isNetwork(cx)) {
            ToastUtility.showToast("网络连接中断，请求失败!");
            cancelRequest();
            return false;
        }
        final ProgressDialog pDialog = ActivityUtils.showProgress(cx, progressDialogTitle);
        OkHttpUtils.post(url + method)
                .tag(cx)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            T t = GsonUtils.fromJson(s, clazz);
                            entityCallback.onSuccess(t);
                        } else {
                            entityCallback.onFailed();
                        }
                    }
                });
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static boolean getJsonString(Context cx,String method, String jsonString, String progressDialogTitle, JsonCallback jsonCallback) {
        if (!NetUtils.init(cx).isNetwork(cx)) {
            ToastUtility.showToast("网络连接中断，请求失败!");
            cancelRequest();
            return false;
        }
        final ProgressDialog pDialog = ActivityUtils.showProgress(cx, progressDialogTitle);
        jsonCallback.setpDialog(pDialog);
        OkHttpUtils.post(url + method)
                .postJson(jsonString)
                .execute(jsonCallback);

        return true;
    }

    public static boolean getJsonString(Context cx,String method, String jsonString, JsonCallback jsonCallback) {
        return getJsonString(cx,method, jsonString, "请求中...", jsonCallback);
    }


    /**
     * 取消请求
     */
    public static void cancelRequest() {
        //根据 Tag 取消请求
        OkHttpUtils.getInstance().cancelTag(cx);
    }

    public class ProgressDialogStringCallback extends  StringCallback{
        private ProgressDialog pDialog;

        public void setpDialog(ProgressDialog pDialog) {
            this.pDialog = pDialog;
        }

        @Override
        public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }


    public interface IProgressDialogCallback<T> {
        void onSuccess(T t);

        void onFailed();
    }

    public abstract class ProgressDialogCallback implements IProgressDialogCallback {
        private ProgressDialog pDialog;

        public void setpDialog(ProgressDialog pDialog) {
            this.pDialog = pDialog;
        }


        @Override
        public void onFailed() {
            ToastUtility.showToast("系统发生错误，请求失败!");
        }
    }

    //////////////////////////////////////////////////

    public interface IEntityCallback<T> {
        void onSuccess(T t);

        void onFailed();
    }

    public abstract class EntityCallback implements IEntityCallback {
        @Override
        public void onFailed() {
            ToastUtility.showToast("系统发生错误，请求失败!");
        }
    }


    public static abstract class JsonCallback<T> extends StringCallback {
        private Class<T> clazz;

        private ProgressDialog pDialog;

        public void setpDialog(ProgressDialog pDialog) {
            this.pDialog = pDialog;
        }

        public JsonCallback(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (response.isSuccessful()) {
                T t = GsonUtils.fromJson(s, clazz);
                onSuccess(t);
            } else {
                onFailed();
            }

        }

        public void onFailed() {
            ToastUtility.showToast("系统发生错误，请求失败!");
        }

        public abstract void onSuccess(T t);
    }


}
