package com.yhkj.jskf.supplychainmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.yhkj.jskf.supplychainmanager.R;
import com.yhkj.jskf.supplychainmanager.utils.ActivityUtils;

public class ShowImageActivity extends Activity {

    private ImageView imageView;

    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (null != intent) {
            int widowWidth = getWindowManager().getDefaultDisplay().getWidth();
            String imageUrl = intent.getStringExtra(ParentActivity.SHOWIMAGE);
            if (!TextUtils.isEmpty(imageUrl)) {
                if (-1 != imageUrl.indexOf("/upload")) {
                    imageUrl = "http://" + ActivityUtils.init(this).getServerUrl() +":" + ActivityUtils.init(this).getServerPort() + imageUrl;
                    //ImageUtils.displayImg(getApplication(),imageView,imageUrl,false);
                }else{
                    bmp = fileToBitmap(widowWidth,imageUrl);
                    imageView.setImageBitmap(bmp);
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != bmp){
            bmp.recycle();
        }
    }

    /**
     * 文件转换为Bitmap
     * @param width
     * @param imgUrl
     * @return
     */
    private Bitmap fileToBitmap(int width,String imgUrl){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgUrl,options);
        float widthScale = (float)(options.outWidth * 1.0 / width);
        float heightScale = (float)(options.outHeight * 1.0 / width);
        float scale = widthScale > heightScale ? widthScale:heightScale;
        options.inJustDecodeBounds = false;
        options.inSampleSize = (int)scale;
        Bitmap bmp = BitmapFactory.decodeFile(imgUrl,options);
        return bmp;
    }


}
