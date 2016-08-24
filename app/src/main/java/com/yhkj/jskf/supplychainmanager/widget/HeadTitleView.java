package com.yhkj.jskf.supplychainmanager.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhkj.jskf.supplychainmanager.R;


/**
 * 自定义标题头
 *
 * @author liupeng
 */
public class HeadTitleView extends LinearLayout implements OnClickListener {

    private TextView tvTitleText;
    private Activity activity;
    private ImageView ivTitleIcon, iv_right;
    private RelativeLayout rl_title;

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public HeadTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
        }
        // TODO Auto-generated constructor stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.head_title, this);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        tvTitleText = (TextView) findViewById(R.id.tvTitleText);
        ivTitleIcon = (ImageView) findViewById(R.id.ivTitleIcon);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        if (null != ivTitleIcon) {
            ivTitleIcon.setOnClickListener(this);
        }
        if (null != iv_right) {
            iv_right.setOnClickListener(this);
        }
    }

    /**
     * 构造方法
     *
     * @param context
     */
    public HeadTitleView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.activity = (Activity) context;
    }

    /**
     * 设置标题文本
     *
     * @param text
     */
    public void setTltleText(String text) {
        tvTitleText.setText(text);
    }


    public TextView getTvTitleText() {
        return tvTitleText;
    }

    /**
     * 设置标题图标
     *
     */
    public void setTitleIcon(int resId) {
        ivTitleIcon.setImageResource(resId);
    }

    /**
     * 获取标题图标控件
     *
     * @return
     */
    public ImageView getView() {
        return ivTitleIcon;
    }

    /**
     * 设置右边的图标是否可视
     *
     * @param isVisible
     */
    public void setLeftVisible(boolean isVisible) {
        ivTitleIcon.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置右边的图标是否可视
     *
     * @param isVisible
     */
    public void setRightVisible(boolean isVisible) {
        iv_right.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    public ImageView getIv_right() {
        return iv_right;
    }

    public void setTitleBackground(int colorId) {
        rl_title.setBackgroundColor(getResources().getColor(colorId));
    }

    public void setTitleBackground(String color){
        rl_title.setBackgroundColor(Color.parseColor(color));
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ivTitleIcon:
                activity.finish();
                break;
            default:
                break;
        }
    }

}
