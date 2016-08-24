package com.yhkj.jskf.supplychainmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yhkj.jskf.supplychainmanager.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        if (TextUtils.isEmpty(ActivityUtils.init(getApplicationContext()).getUserID() + "")) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}
