package com.yhkj.jskf.supplychainmanager.utils;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.WindowManager;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * Activity工具类
 *
 * @author liupeng
 */
public class ActivityUtils {

    private static Dialog builder;

    private static ProgressDialog m_pDialog;

    private static ActivityUtils aUtility;

    private Context ctx;

    /**
     * 是否处于调试或者开发模式
     */
    public static boolean ISDEBUG = false;

    /**
     * 初始化方法，只有一个实体类存在
     *
     * @param context
     * @return
     */
    public static ActivityUtils init(Context context) {
        if (aUtility == null) {
            aUtility = new ActivityUtils(context);
        } else {
            if (null != context) {
                aUtility.ctx = context;
            }
        }
        return aUtility;
    }

    /**
     * 构造方法
     */
    private ActivityUtils(Context context) {
        // TODO Auto-generated constructor stub
        this.ctx = context;
        ISDEBUG = isApkDebugable(context);
    }

    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     *
     * @param context
     * @return
     */
    private static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 进度条
     *
     * @param context
     * @param message
     */
    public static ProgressDialog showProgress(Context context, String message) {
        if (null != m_pDialog) {
            if (m_pDialog.isShowing()) {//销毁掉之前的显示的加载窗
                return m_pDialog;
            }
        }
        m_pDialog = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);

        // 设置进度条风格，风格为圆形，旋转的
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // 设置ProgressDialog 提示信息
        m_pDialog.setMessage(message);

        // 设置ProgressDialog 的进度条是否不明确
        m_pDialog.setIndeterminate(true);

        // 设置ProgressDialog 是否可以按退回按键取消
        m_pDialog.setCancelable(false);

        // 让ProgressDialog显示
        m_pDialog.show();

        return m_pDialog;
    }

    public static void hideProgress() {
        m_pDialog.dismiss();
    }

    /**
     * Activity跳转动画
     *
     * @param ct
     */
    public static void starAnimForActivity(Activity ct) {
        ct.overridePendingTransition(anim.slide_in_left, anim.slide_out_right);
    }

    /*
     * 保存图片并返回图片路径
     */
    public static ArrayList<String> saveAccessoryToFile(ArrayList<String> photoList,
                                                        Activity context, int requestCode, Intent intent) {
        ArrayList<String> list = new ArrayList<String>();
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String name = "" + c.get(Calendar.YEAR) + c.get(Calendar.MONTH)
                + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.HOUR_OF_DAY)
                + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + ".jpg";

        String string = null;
        if (photoList == null) {
            photoList = new ArrayList<String>();
        }

        switch (requestCode) {
            case 0:
            /*
             * Bundle bundle = intent.getExtras(); bitmap = (Bitmap)
			 * bundle.get("data");
			 */
                File fileDir = Environment.getExternalStorageDirectory();
                String fromFile = fileDir.getPath() + "/southwest/123.jpg" /*
                                                                         * +
																		 * name
																		 */;
                string = fileDir.getPath() + "/southwest/" + name;
                list.add(string);
            /*
             * try { File file = new File(fileDir.getPath() + "/forestPolice/");
			 * if (!file.exists()) { file.mkdir(); } fos = new
			 * FileOutputStream(new File(string));
			 * bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			 * 
			 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); } finally { try { fos.flush();
			 * fos.close(); bitmap.recycle(); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 * 
			 * }
			 */
                copyfile(new File(fromFile), new File(string), true);
                break;
            case 2:
            /*
             * Uri uri = intent.getData(); Cursor cursor =
			 * context.getContentResolver().query(uri, null, null, null, null);
			 * cursor.moveToFirst(); string = cursor.getString(1); // 图片文件路径
			 * cursor.close();
			 */

                list = (ArrayList<String>) intent.getSerializableExtra("photolist");
                break;

            default:
                break;
        }
        for (int i = 0; i < list.size(); i++) {
            photoList.add(list.get(i));
        }
        // Log.i("liupeng", "path   :   " + string);
        return photoList;
    }

    /*
     * 复制图片
     */
    private static void copyfile(File fromFile, File toFile, Boolean rewrite) {

        if (!fromFile.exists()) {
            return;
        }

        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            // 关闭输入、输出流
            fosfrom.close();
            fosto.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

//    public static void showDialogMenu(final Activity ct, final int menu[]) {
//        builder = new Dialog(ct, R.style.mydialogstyle);
//        ListView lvDialog = new ListView(ct);
//        lvDialog.setStackFromBottom(false);
//        lvDialog.setAdapter(new BaseAdapter() {
//
//            @Override
//            public View getView(int arg0, View arg1, ViewGroup arg2) {
//                // TODO Auto-generated method stub
//                TextView tv = new TextView(ct);
//                if (arg0 == 0) {
//                    tv.setText(menu[arg0]);
//                    tv.setWidth(arg2.getWidth());
//                    tv.setHeight(160);
//                    tv.setGravity(Gravity.CENTER_VERTICAL);
//                    tv.setId(menu[arg0]);
//                    tv.setTextColor(Color.WHITE);
//                    tv.setTextSize(16);
//                    tv.setPadding(50, 0, 0, 0);
//                    //tv.setBackgroundResource(R.drawable.signature_save_bg);
//                } else {
//                    tv.setText(menu[arg0]);
//                    tv.setWidth(arg2.getWidth());
//                    tv.setHeight(160);
//                    tv.setGravity(Gravity.CENTER_VERTICAL);
//                    tv.setId(menu[arg0]);
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(16);
//                    tv.setPadding(50, 0, 0, 0);
//                    //tv.setBackgroundResource(R.drawable.signature_save_bg);
//                    tv.setEnabled(false);
//                }
//                return tv;
//            }
//
//            @Override
//            public long getItemId(int arg0) {
//                // TODO Auto-generated method stub
//                return arg0;
//            }
//
//            @Override
//            public Object getItem(int arg0) {
//                // TODO Auto-generated method stub
//                return menu[arg0];
//            }
//
//            @Override
//            public int getCount() {
//                // TODO Auto-generated method stub
//                return menu.length;
//            }
//        });
//        lvDialog.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                Intent it = null;
//                builder.dismiss();
//                switch (arg1.getId()) {
//                    case R.string.camera: // 拍照
//                        File file = new File(Environment
//                                .getExternalStorageDirectory()
//                                + "/supplychain/123.jpg");
//                        if (!file.getParentFile().exists()) {
//                            file.getParentFile().mkdirs();
//                        }
//                        it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//                        ct.startActivityForResult(it, 0);
//                        break;
//                    case R.string.photo: // 相册
//                        it = new Intent(ct, PhotoListActivity.class);
//                        it.putExtra("class", ct.getClass());
//                        ct.startActivityForResult(it, 2);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        builder.setContentView(lvDialog);
//        builder.show();
//    }

	/*
     * public static void showCheckBox(Context ct, EditText view, int menu[]) {
	 * list = new String[menu.length]; boolList = new boolean[menu.length];
	 * result = new String(); for (int i = 0; i < menu.length; i++) { list[i] =
	 * ct.getString(menu[i]); boolList[i] = false; } new AlertDialog.Builder(ct)
	 * .setTitle("请选择接警人！") // 标题 .setMultiChoiceItems(list, boolList, new
	 * DialogInterface.OnMultiChoiceClickListener() {// 设置多选条目 public void
	 * onClick(DialogInterface dialog, int which, boolean isChecked) { // do
	 * something for (int i = 0; i < list.length; i++) { if (i == which &&
	 * isChecked) { result += list[i] + ","; }
	 * 
	 * } } }) .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int which) { // do something
	 * } }).show(); view.setText(result); }
	 */

    public static ArrayList<Integer> li;

	/*
     * 显示复选框
	 */
    /*
     * public static void showCheckBox(Context ct, final EditText view,
	 * ArrayList<String> pcAdapter, String title) { list = new
	 * String[pcAdapter.size()]; boolList = new boolean[list.length]; result =
	 * new String(); li = new ArrayList<Integer>(); for (int i = 0; i <
	 * pcAdapter.size(); i++) { list[i] = pcAdapter.get(i); boolList[i] = false;
	 * } new AlertDialog.Builder(ct) .setTitle(title) // 标题
	 * .setMultiChoiceItems(list, boolList, new
	 * DialogInterface.OnMultiChoiceClickListener() {// 设置多选条目 public void
	 * onClick(DialogInterface dialog, int which, boolean isChecked) { // do
	 * something for (int i = 0; i < list.length; i++) { if (i == which &&
	 * isChecked) { result += list[i] + ","; li.add(i); } } } })
	 * .setPositiveButton("确定", new DialogInterface.OnClickListener() { public
	 * void onClick(DialogInterface dialog, int which) { // do something
	 * view.setText(result); } }).show(); }
	 */

//	/**
//	 * 显示下拉框
//	 *
//	 * @param activity
//	 * @param con
//	 * @param sp
//	 */
//	public static void showSpinnerAdapter(Context activity, ArrayList<String> list, Spinner sp) {
//		sp.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, list));
//	}
//
//	public static void showSpinnerAdapter(Context activity, String con, Spinner sp) {
//		ArrayList<String> sptca = new ArrayList<String>();
//		sptca.add(con);
//		sp.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, sptca));
//	}
//
//	public static ArrayList<Integer> listId = null;

    /*
     * public static void showContent(Activity activity, final ArrayList<String>
     * list, GridView view) { final Activity ac = activity; listId = new
     * ArrayList<Integer>(); view.setAdapter(new BaseAdapter() {
     *
     * @Override public View getView(final int position, View convertView,
     * ViewGroup parent) { // TODO Auto-generated method stub if (convertView ==
     * null) { convertView = LayoutInflater.from(ac).inflate( R.layout.pc_item,
     * parent, false); } CheckBox cb = (CheckBox) convertView
     * .findViewById(R.id.cbPC_Content); cb.setText(list.get(position));
     * cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     *
     * @Override public void onCheckedChanged(CompoundButton buttonView, boolean
     * isChecked) { // TODO Auto-generated method stub if (isChecked) {
     * listId.add(position + 1); } } }); return convertView; }
     *
     * @Override public long getItemId(int position) { // TODO Auto-generated
     * method stub return position; }
     *
     * @Override public Object getItem(int position) { // TODO Auto-generated
     * method stub return list.get(position); }
     *
     * @Override public int getCount() { // TODO Auto-generated method stub
     * return list.size(); } }); }
     */
	/*
	 * 显示
	 * 
	 * public static void showCheckBoxAdapter(Context ct, final
	 * ArrayList<String> list, CheckBoxEditText cbet) { boolList = new
	 * boolean[list.size()]; for (int i = 0; i <list.size(); i++) { boolList[i]
	 * = false; } new AlertDialog.Builder(ct) .setTitle("请选择接警人！") // 标题
	 * .setSingleChoiceItems(list, boolList, new
	 * DialogInterface.OnMultiChoiceClickListener() {// 设置多选条目 public void
	 * onClick(DialogInterface dialog, int which, boolean isChecked) { // do
	 * something for (int i = 0; i < list.size(); i++) { if (i == which &&
	 * isChecked) { result += list.get(i) + ","; }
	 * 
	 * } } }) .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int which) { // do something
	 * } }).show(); cbet.setText(result); }
	 */
    public static boolean isGPSOpen(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps && (gps || network);
    }

    /*
     * 判断是否登陆过
     */
    public static boolean isLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        return sp.getInt("userID", 0) != 0;
    }

    public static int getUseId(Context ct) {
        SharedPreferences sp = ct.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        return sp.getInt("userID", 0);
    }

    /*
     * 提示对话框，进入登陆
     */
	/*
	 * public static void showLogin(final Context context) {
	 * 
	 * new AlertDialog.Builder(context).setMessage("以下操作需用用户登陆")
	 * .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface arg0, int arg1) { // TODO
	 * Auto-generated method stub Intent it = new Intent(context,
	 * LoginActivity.class); context.startActivity(it); }
	 * }).setNegativeButton("取消", null).create().show(); }
	 */
	/*
	 * 提示对话框，进入登陆
	 */
    public static void showGPSSetting(final Context context) {

        new AlertDialog.Builder(context).setMessage("此功能需要精确的定位服务，请您在室外在位置设置中打开GPS！")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        ActivityUtils.openGPS(context);
                    }
                }).setNegativeButton("确定", null).create().show();
    }

    // public static void getLocation(Context context) {
    //
    // Constant.Location = new Loaction();
    // final LocationManager locationManager = (LocationManager) context
    // .getSystemService(Context.LOCATION_SERVICE);
    //
    //
    // LocationListener locationListener = new LocationListener() {
    //
    // /**
    // * 位置信息变化时触发
    // */
    // public void onLocationChanged(Location location) {
    // // updateView(location);
    // // Log.i(TAG, "时间："+location.getTime());
    // Log.i("liupeng", "经度：" + location.getLongitude());
    // Log.i("liupeng", "纬度：" + location.getLatitude());
    // Log.i("liupeng", "海拔：" + location.getAltitude());
    //
    // Constant.Location.setLATITUDE(location.getLatitude()); // 纬度
    // Constant.Location.setELEVATION(location.getAltitude()); // 高度
    // Constant.Location.setLONGITUDE(location.getLongitude()); // 经度
    // }
    //
    // /**
    // * GPS状态变化时触发
    // */
    // public void onStatusChanged(String provider, int status,
    // Bundle extras) {
    // switch (status) {
    // // GPS状态为可见时
    // case LocationProvider.AVAILABLE:
    // // Log.i(TAG, "当前GPS状态为可见状态");
    // break;
    // // GPS状态为服务区外时
    // case LocationProvider.OUT_OF_SERVICE:
    // // Log.i(TAG, "当前GPS状态为服务区外状态");
    // break;
    // // GPS状态为暂停服务时
    // case LocationProvider.TEMPORARILY_UNAVAILABLE:
    // // Log.i(TAG, "当前GPS状态为暂停服务状态");
    // break;
    // }
    // }
    //
    // /**
    // * GPS开启时触发
    // */
    // public void onProviderEnabled(String provider) {
    // // Location location=lm.getLastKnownLocation(provider);
    // // updateView(location);
    // }
    //
    // /**
    // * GPS禁用时触发
    // */
    // public void onProviderDisabled(String provider) {
    // // updateView(null);
    // }
    //
    // };
    // Criteria criteria = new Criteria();
    // // 设置定位精确度 Criteria.ACCURACY_COARSE 比较粗略， Criteria.ACCURACY_FINE则比较精细
    // criteria.setAccuracy(Criteria.ACCURACY_FINE);
    // // 设置是否需要海拔信息 Altitude
    // criteria.setAltitudeRequired(true);
    // // 设置是否需要方位信息 Bearing
    // criteria.setBearingRequired(true);
    // // 设置是否允许运营商收费
    // criteria.setCostAllowed(false);
    // // 设置对电源的需求
    // criteria.setPowerRequirement(Criteria.POWER_LOW);
    // // 获取GPS信息提供者
    // String bestProvider = locationManager.getBestProvider(criteria, true);
    //
    // locationManager.requestLocationUpdates(bestProvider, 10, 0,
    // locationListener);
    // }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        // Toast.makeText(context, "IMEI   :   "+tm.getDeviceId(),
        // Toast.LENGTH_LONG).show();
        return tm.getDeviceId();
    }

    /**
     * @param context
     */
    public static void openGPS(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            ActivityUtils.starAnimForActivity((Activity) context);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
                ActivityUtils.starAnimForActivity((Activity) context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param imgPath
     * @param bitmap
     * @return
     */
    public static String imgToBase64(String imgPath, Bitmap bitmap) {
        if (imgPath != null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath);
        }
        if (bitmap == null) {
            // bitmap not found!!
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @param imgPath
     * @return
     */
    public static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }

    }


    public static double getDateProgress(String dateString, int field) {
        double progress = 0;
        String format = "yyyy-MM-dd";
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);
        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(dateString);
            calendar.setTime(date);

            int day = calendar.get(field);
            int dayOfMonthCount = calendar.getActualMaximum(field);

            progress = ((double) day / (double) dayOfMonthCount);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return progress * 100;
    }

    public static int getDisplayWidth(Context ct) {
        WindowManager wm = (WindowManager) ct.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 格式化日期　格式（yyyy-MM-dd）
     *
     * @param number
     * @return
     */
    public static String formatDate(Date number) {
        SimpleDateFormat dFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return dFormatter.format(number);
    }

//    public static void showCameraOrPhoto(Activity activity, View view) {
//        if (view.getTag() != null && view.getTag().equals(R.drawable.app_panel_add_icon)) {
//            ActivityUtils.showDialogMenu(activity, Constant.CAMERA_OR_PHOTO);
//        } else {
//            Intent it = new Intent(activity, ImageShowActivity.class);
//            it.putExtra("filepath", view.getTag().toString()); //
//            it.putExtra("index", // Integer.parseInt(arg1.getTag().toString()));it.putExtra("class", activity.getClass());
//                    activity.startActivityForResult(it, 10);
//        }
//    }

	/*
	 * 网络图片显示
	 * 
	 * public static Bitmap returnBitMap(String url) { URL myFileUrl = null;
	 * Bitmap bitmap = null; try { myFileUrl = new URL(url); } catch
	 * (MalformedURLException e) { e.printStackTrace(); } try {
	 * HttpURLConnection conn = (HttpURLConnection) myFileUrl .openConnection();
	 * conn.setDoInput(true); conn.connect(); InputStream is =
	 * conn.getInputStream(); bitmap = BitmapFactory.decodeStream(is);
	 * is.close(); } catch (IOException e) { e.printStackTrace(); } return
	 * bitmap; }
	 */

    /**
     * 当前日期
     *
     * @param date 日期字符串
     */
    public Calendar getData(String date) {
        String format = "yyyy-MM-dd";
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);
        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        Date date1;
        try {
            if (date != null) {
                date1 = dateformat.parse(date);
                calendar.setTime(date1);
            } else {
                calendar = currentCalendar;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * 当前日期
     *
     * @param date 日期字符串
     */
    public String getDataForYear(String date) {
        String format = "yyyy-MM-dd";
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);
        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        Date date1;
        try {
            if (date != null) {
                date1 = dateformat.parse(date);
                calendar.setTime(date1);
            } else {
                calendar = currentCalendar;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日,";
    }

    // 创建文件夹及文件
    public static void CreateText(String filenameTemp) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory().getPath());
        if (!file.exists()) {
            try {
                // 按照指定的路径创建文件夹
                file.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        File dir = new File(filenameTemp);
        if (!dir.exists()) {
            try {
                // 在指定的文件夹中创建文件
                dir.createNewFile();
            } catch (Exception e) {
            }
        }

    }

    // 向已创建的文件中写入数据
    public static void print(String str, String filenameTemp) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        String datetime = "";
        try {
            CreateText(filenameTemp);
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
            datetime = tempDate.format(new Date()).toString();
            fw = new FileWriter(filenameTemp, true);//
            // 创建FileWriter对象，用来写入字符流
            bw = new BufferedWriter(fw); // 将缓冲对文件的输出
            String myreadline = datetime + "[]" + str;

            bw.write(myreadline + "\n"); // 写入文件
            bw.newLine();
            bw.flush(); // 刷新该流的缓冲
            bw.close();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                bw.close();
                fw.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
            }
        }
    }

    /**
     * 判断应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        boolean hasInstalled = false;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo p : list) {
            if (packageName != null && packageName.equals(p.packageName)) {
                hasInstalled = true;
                break;
            }
        }
        return hasInstalled;
    }

    /**
     * 格式化日期字符串 （精确称秒或毫秒）
     *
     * @param datestring 日期字符串
     * @return
     */
    public static String getCalendarFromISO(String datestring) {
        String format;
        if (datestring.length() > 20) {
            format = "yyyy-MM-dd'T'HH:mm:ss.SS";
        } else {
            format = "yyyy-MM-dd'T'HH:mm:ss";
        }
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);

        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(datestring);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(calendar.getTime());

    }

    /**
     * 格式化日期字符串 （获取小时）
     *
     * @param datestring 日期字符串
     * @return
     */
    public static int getHousFromISO(String datestring) {
        String format;
        if (datestring.length() > 20) {
            format = "yyyy-MM-dd'T'HH:mm:ss.SS";
        } else {
            format = "yyyy-MM-dd'T'HH:mm:ss";
        }
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);

        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(datestring);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime().getHours();

    }

    /**
     * 格式化日期字符串 （精确称秒或毫秒）
     *
     * @param datestring 日期字符串
     * @return
     */
    public static String getDateFromISO(String datestring) {
        String format;
        if (datestring.length() > 20) {
            format = "yyyy-MM-dd'T'HH:mm:ss.SS";
        } else {
            format = "yyyy-MM-dd'T'HH:mm:ss";
        }
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);

        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(datestring);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(calendar.getTime());

    }

    // 向已创建的文件中写入数据
    public static void print(String str, String logType, String filenameTemp) {
        OutputStreamWriter out = null;
        BufferedWriter bw = null;
        String datetime = "";
        File dirPath = new File(Environment.getExternalStorageDirectory().getPath()
                + "/xnsn/management/" + logType);
        File strFile = null;
        try {
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            strFile = new File(dirPath.getPath() + "/" + filenameTemp);
            if (!strFile.exists()) {
                strFile.createNewFile();
            }
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            datetime = tempDate.format(new Date()).toString();
            out = new OutputStreamWriter(new FileOutputStream(strFile, true), "UTF-8");
            // 创建FileWriter对象，用来写入字符流
            bw = new BufferedWriter(out); // 将缓冲对文件的输出
            String myreadline = datetime + "[]" + str;
            // myreadline = new String(myreadline.getBytes(), "GBK");
            bw.write(myreadline + "\n"); // 写入文件
            bw.newLine();
            bw.flush(); // 刷新该流的缓冲
            bw.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                bw.close();
                out.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
            }
        }
    }

    private SharedPreferences sp;

    /**
     * 根据指定的name获取SharedPreferences
     *
     * @param filename 指定的文件名称
     * @return
     */
    public ActivityUtils SP(String filename) {
        sp = ctx.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return this;
    }

    /**
     * put data to SharedPreferences
     *
     * @param key   键
     * @param value 值
     */
    @SuppressWarnings("unchecked")
    public void putSP(String key, Object value) {
        if (null == sp) {
            return;
        }
        Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Set<?>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                editor.putStringSet(key, (Set<String>) value);
            } else {
                System.out.println(Build.VERSION.SDK_INT + "is to low,put data failed");
            }
        }
        editor.commit();
    }

    /**
     * get data from SharedPreferences
     *
     * @param key   键
     * @param clazz 指定需要获取的数据的类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getSP(String key, Class<T> clazz) {
        if (null == sp) {
            return null;
        }
        if ((String.class).equals(clazz)) {
            return (T) sp.getString(key, null);
        } else if ((Integer.class).equals(clazz)) {
            return (T) Integer.valueOf(sp.getInt(key, 0));
        } else if ((Float.class).equals(clazz)) {
            return (T) Float.valueOf(sp.getFloat(key, 0));
        } else if ((Long.class).equals(clazz)) {
            return (T) Long.valueOf(sp.getLong(key, 0));
        } else if ((Boolean.class).equals(clazz)) {
            return (T) Boolean.valueOf(sp.getBoolean(key, false));
        } else if ((Set.class).equals(clazz)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return (T) sp.getStringSet(key, null);
            } else {
                System.out.println(Build.VERSION.SDK_INT + "is to low,get data failed");
            }
        }
        return null;
    }

    private static final String DEVICE = "device";
    private static final String DEVICEID = "deviceId";

    /**
     * 统一将当前设备编号写入至SharedPreferences
     */
    public String putDeviceID() {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        SP(DEVICE).putSP(DEVICEID, deviceId);
        return deviceId;
    }

    /**
     * 统一从SharedPreferences获取当前设备编号
     *
     * @return
     */
    public String getDeviceID() {
        String deviceId = SP(DEVICE).getSP(DEVICEID, String.class);
        if (TextUtils.isEmpty(deviceId)) {//检查一遍是否启动没有put进去
            deviceId = putDeviceID();
        }
        return deviceId;
    }

    private static final String USER = "USER";
    private static final String USERID = "USERID";

    private static final String ROLEID = "ROLEID";

    public int getUserID() {
        Integer userID = SP(USER).getSP(USERID, Integer.class);
        return userID;
    }

    public void putUserID(int userId) {
        SP(USER).putSP(USERID, userId);
    }

    public int getUserRoleId() {
        Integer userID = SP(USER).getSP(ROLEID, Integer.class);
        return userID;
    }

    public void putRoleID(int roleId) {
        SP(USER).putSP(ROLEID, roleId);
    }

    private static final String SERVER = "SERVER";
    private static final String SERVER_URL = "SERVER_URL";//服务器地址
    private static final String SERVER_PORT = "SERVER_PORT";//服务器端口

    public String getServerUrl() {
        return SP(SERVER).getSP(SERVER_URL, String.class);
    }

    public void setServerUrl(String serverUrl) {
        SP(SERVER).putSP(SERVER_URL, serverUrl);
    }

    public String getServerPort() {
        return SP(SERVER).getSP(SERVER_PORT, String.class);
    }

    public void setServerPort(String serverPort) {
        SP(SERVER).putSP(SERVER_PORT, serverPort);
    }


    private static final String CLIENTS = "CLIENTS";

    /**
     * 根据客户的编号查客户名称
     *
     * @param clientId
     * @return
     */
    public String getClentByClientId(int clientId) {
        sp = ctx.getSharedPreferences(CLIENTS, Context.MODE_PRIVATE);
        Map<String, String> map = (Map<String, String>) sp.getAll();
        if (null == map || map.isEmpty()) {
            return null;
        }
        return SP(CLIENTS).getSP(String.valueOf(clientId), String.class);
    }

    /**
     * 在BEBUG为true模式下<br>
     * 1.控制台简单打印<br>2.保存临时数据方便本地查看 覆盖之前存在的所有数据
     *
     * @param dataString String类型
     */
    public static void saveTempDataToSD(final String dataString) {
        if (!ISDEBUG) {
            return;
        }
//		System.out.println("temp_data>>>" + dataString);
        // TODO Auto-generated method stub
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    File file = new File(Environment.getExternalStorageDirectory(), "temp_data.txt");
                    fileDataOperation(dataString, file);
                }
            }
        }).start();
    }

    /**
     * 写入字符串到指定的文件
     *
     * @param dataString
     * @param file
     */
    private static void fileDataOperation(String dataString, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                if (null != fw) {
//					fw.write("deviceId:" + getDeviceID()+"\n");
                    fw.write(dataString);
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileWriter fw = new FileWriter(file);
                if (null != fw) {
//					fw.write("deviceId:" + getDeviceID()+"\n");
                    fw.write(dataString);
                    fw.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public static String MD5(String string) {
        if (null != string && !"".equals(string)) {
            StringBuffer sb = new StringBuffer();
            try {
                MessageDigest md5 = MessageDigest.getInstance("Md5");
                byte[] bytes = md5.digest(string.getBytes("UTF-8"));
                for (int i = 0; i < bytes.length; i++) {
                    int value = bytes[i] & 0xff;
                    if (value < 16) {
                        sb.append("0");
                    }
                    sb.append(Integer.toHexString(value));
                }
                string = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return string;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                             int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param
     * @return
     */
    private static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static String encodeBase64File(String path) throws IOException {
        String type = path.substring(path.lastIndexOf(".") + 1);
        if (type.equals("jpg") || type.equals("png")) {
            Bitmap bm = getSmallBitmap(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (null == bm) {
                return null;
            }
            bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.NO_PADDING);
        } else {
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.NO_WRAP);
        }
    }

}
