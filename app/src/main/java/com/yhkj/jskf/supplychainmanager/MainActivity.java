package com.yhkj.jskf.supplychainmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_helloworld)
    TextView tv_helloworld;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);



//        if (TextUtils.isEmpty(ActivityUtils.init(getApplicationContext()).getUserID() + "")) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }

//        ToastUtility.showToast(tv_helloworld.toString());


//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);



    }
}
