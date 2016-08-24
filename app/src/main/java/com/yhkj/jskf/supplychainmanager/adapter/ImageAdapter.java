package com.yhkj.jskf.supplychainmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yhkj.jskf.supplychainmanager.R;
import com.yhkj.jskf.supplychainmanager.utils.ActivityUtils;

import java.util.List;

/**
 * Created by wangxiaofei on 2016/5/9.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mLists;

    private int cellWidth;

    public ImageAdapter(Context mContext, List<String> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
        if (mContext instanceof Activity){
            cellWidth =  ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth() / 4;
        }else{
            cellWidth = 100;
        }

    }

    @Override
    public int getCount() {
        return null != mLists ? mLists.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mLists ? mLists.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = null;
        if (null == convertView){
            iv = new ImageView(mContext);
            iv.setLayoutParams(new AbsListView.LayoutParams(cellWidth,cellWidth));
            iv.setPadding(10,10,10,10);
            convertView = iv;
        }else{
            iv = (ImageView)convertView;
        }
        if (position == 0) {
            iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.app_panel_add_icon));
//            iv.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View v) {
//                    uploadImage(v);
//                }
//            });
        }else{
            String imagePath = mLists.get(position);
            if (!TextUtils.isEmpty(imagePath)) {
                if(-1 != imagePath.indexOf("/upload")){
                    imagePath = "http://" + ActivityUtils.init(mContext).getServerUrl() +":" + ActivityUtils.init(mContext).getServerPort() + imagePath;
//                    ImageUtils.displayImg(mContext,iv,imagePath);
                }else{
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imagePath,options);
                    float widthScale = (float)(options.outWidth * 1.0 / cellWidth);
                    float heightScale = (float)(options.outHeight * 1.0 / cellWidth);
                    float scale = widthScale > heightScale ? widthScale:heightScale;
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = (int)scale;
                    Bitmap bmp = BitmapFactory.decodeFile(imagePath,options);
                    iv.setImageBitmap(bmp);
                }
            }



//            iv.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
////                        uploadImage(v);
//                }
//            });
        }
        return convertView;
    }

//    private void uploadImage() {
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            time = System.currentTimeMillis();
//            file = new File(Environment.getExternalStorageDirectory() + "/supplychainmanager/images/" + time + ".jpg");
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//            startActivityForResult(it, 200);
//        } else {
//            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
//        }
//    }

}
