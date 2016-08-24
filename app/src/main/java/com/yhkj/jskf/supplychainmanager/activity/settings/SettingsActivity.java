package com.yhkj.jskf.supplychainmanager.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yhkj.jskf.supplychainmanager.R;
import com.yhkj.jskf.supplychainmanager.activity.LoginActivity;
import com.yhkj.jskf.supplychainmanager.activity.ParentActivity;
import com.yhkj.jskf.supplychainmanager.utils.ActivityUtils;
import com.yhkj.jskf.supplychainmanager.utils.ToastUtility;
import com.yhkj.jskf.supplychainmanager.widget.HeadTitleView;

public class SettingsActivity extends ParentActivity implements View.OnClickListener {
    private EditText et_ip, et_port;

    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
    }

    private void initView() {
        headTitleView = (HeadTitleView) findViewById(R.id.headTitleView);
        headTitleView.setRightVisible(false);
        headTitleView.setTltleText("设置远程地址和端口");
        headTitleView.setVisibility(View.VISIBLE);
        headTitleView.setLeftVisible(false);

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        et_ip = (EditText) findViewById(R.id.et_ip);
        et_port = (EditText) findViewById(R.id.et_port);
        btn_confirm.setOnClickListener(this);

        et_ip.setText( ActivityUtils.init(this).getServerUrl());
        et_port.setText( ActivityUtils.init(this).getServerPort());
    }

    @Override
    public void onClick(View v) {
        final String ip = et_ip.getText().toString().trim();
        if (TextUtils.isEmpty(ip)) {
            ToastUtility.showToast("请输入服务器地址");
            return;
        }
        final String port = et_port.getText().toString().trim();
        if (TextUtils.isEmpty(port)) {
            ToastUtility.showToast("请输入端口号");
            return;
        }

        ActivityUtils.init(this).setServerUrl(ip);
        ActivityUtils.init(this).setServerPort(port);
        ActivityUtils.init(this).putUserID(0);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
