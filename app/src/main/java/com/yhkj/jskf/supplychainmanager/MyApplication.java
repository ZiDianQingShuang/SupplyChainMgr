package com.yhkj.jskf.supplychainmanager;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheEntity;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.cookie.store.PersistentCookieStore;
import com.yhkj.jskf.supplychainmanager.entity.PositionDetails;
import com.yhkj.jskf.supplychainmanager.utils.ActivityUtils;
import com.yhkj.jskf.supplychainmanager.utils.CrashHandler;
import com.yhkj.jskf.supplychainmanager.utils.NetUtils;
import com.yhkj.jskf.supplychainmanager.utils.ToastUtility;

/**
 * Created by wangxiaofei on 2016/5/5.
 */
public class MyApplication extends Application {

    private Application application;

    private LocationClient locationClient;

    private PositionDetails mPositionDetails;

    public PositionDetails getmPositionDetails() {
        return mPositionDetails;
    }

    public void setmPositionDetails(PositionDetails mPositionDetails) {
        this.mPositionDetails = mPositionDetails;
    }

    public LocationClient getLocationClient() {
        return locationClient;
    }

    public void setLocationClient(LocationClient locationClient) {
        this.locationClient = locationClient;
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        NetUtils.init(application);
        ActivityUtils.ISDEBUG = isApkDebugable();
        CrashHandler.getInstance().init(this);
        ActivityUtils.init(this);
        ToastUtility.init(this);
        //必须调用初始化
        OkHttpUtils.init(this);
        initOkHttpUtilsConfig();

        myApplication = new MyApplication();

        initLocationClient();
    }

    /**
     * 初始化百度地图的基本属性
     */
    private void initLocationClient() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//定位模式
        option.setOpenGps(true);
        option.setScanSpan(10 * 1000);
        option.setCoorType("bd09ll");
        option.SetIgnoreCacheException(true);
        option.setIgnoreKillProcess(false);
        locationClient = new LocationClient(this, option);
        MyLocationListener mMyLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(mMyLocationListener);
    }

    /**
     * 是否是调试模式然后是否打印日志
     *
     * @return
     */
    private boolean isApkDebugable() {
        try {
            ApplicationInfo info = getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
            // Log.i("BaiduLocationApiDem 纬度", location.getLatitude() + "");
            // Log.i("BaiduLocationApiDem 经度", location.getLongitude() + "");
            // Log.i("BaiduLocationApiDem 海拔", location.getAltitude() + "");
            // Log.i("BaiduLocationApiDem 时间", location.getTime() + "");

            if (location.getLocType() == 63 || location.getLocType() == 62
                    || location.getLocType() == 68 || location.getLocType() > 161) {
//                if (GPSLocation != null) {
//                    LatLng latlng = gpsToBaidu(GPSLocation.getLatitude(), GPSLocation.getLongitude());
//                    location.setAltitude(GPSLocation.getAltitude());
//                    location.setLatitude(latlng.latitude);
//                    location.setLongitude(latlng.longitude);
////					location.setLatitude(GPSLocation.getLatitude());
////					location.setLongitude(GPSLocation.getLongitude());
//
//                    if (GPSLocation.getProvider().equals(LocationManager.GPS_PROVIDER)) {
//                        location.setLocType(1000);
//                    } else if (GPSLocation.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
//                        location.setLocType(2000);
//                    } else if (GPSLocation.getProvider().equals(LocationManager.PASSIVE_PROVIDER)) {
//                        location.setLocType(3000);
//                    }
//                }
            }

//            if (TextUtils.isEmpty(location.getTime())) {
//                location.setTime(sdf.format(new Date()));
//            }

            if (location.getLatitude() != 4.9E-324
                    && location.getLongitude() != 4.9E-324
                    && !TextUtils.isEmpty(location.getTime())
                    && (location.getLocType() == 61 || location.getLocType() == 62
                    || location.getLocType() == 66 || location.getLocType() == 161
                    || location.getLocType() == 1000 || location.getLocType() == 2000 || location
                    .getLocType() == 3000)) {
                if (null != mPositionDetails) {
                    mPositionDetails.setAltitude(location.getAltitude());
                    mPositionDetails.setLatitude(location.getLatitude());
                    mPositionDetails.setLongitude(location.getLongitude());
                    mPositionDetails.setPositionTime(location.getTime());
                    mPositionDetails.setPositionType(location.getLocType());
                }
//                loca.setLATITUDE(location.getLatitude());
//                loca.setELEVATION(location.getAltitude());
//                loca.setLONGITUDE(location.getLongitude());
//                loca.setTIME(location.getTime());
//                loca.setLocationType(location.getLocType());
            }
        }
    }


    private void initOkHttpUtilsConfig() {
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkHttpUtils.getInstance()

                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("OkHttpUtils")

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认就是Default,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy0216/
                    .setCacheMode(CacheMode.DEFAULT)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //如果不想让框架管理cookie,以下不需要
//                .setCookieStore(new MemoryCookieStore())                //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore());          //cookie持久化存储，如果cookie不过期，则一直有效

            //可以设置https的证书,以下几种方案根据需要自己设置,不需要不用设置
//                    .setCertificates()                                  //方法一：信任所有证书
//                    .setCertificates(getAssets().open("srca.cer"))      //方法二：也可以自己设置https证书
//                    .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密

            //可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

//                    //这两行同上,不需要就不要传
//                    .addCommonHeaders(headers)                                         //设置全局公共头
//                    .addCommonParams(params);                                          //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
