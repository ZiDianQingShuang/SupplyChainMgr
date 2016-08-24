package com.yhkj.jskf.supplychainmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.yhkj.jskf.supplychainmanager.MainActivity;
import com.yhkj.jskf.supplychainmanager.R;

import java.io.File;

public class WelceomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welceome);

//        CrashHandler.getInstance().init(this);
//        ActivityUtils.init(this);
//        GsonNetUtils.init(this);
//        ToastUtility.init(this);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    startActivity(new Intent(WelceomeActivity.this, MainActivity.class));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void updateApp() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            int versionCode = pi.versionCode;
//            GsonNetUtils.getInstance().getJsonReslut()

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *安装APP
     * @param apkUrl
     */
    private void initApp(String apkUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String appRootUrl = Environment.getExternalStorageDirectory().getPath() + File.separator + "supplychianmanager" + File.separator + "app" + File.separator;
        File file = new File(appRootUrl);
        if (!(file.exists())){
            file.mkdirs();
        }
        intent.setDataAndType(Uri.parse(appRootUrl + apkUrl), "application/vnd.android.package-archive");
        startActivity(intent);
    }


}