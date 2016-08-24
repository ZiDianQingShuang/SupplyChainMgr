package com.yhkj.jskf.supplychainmanager.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.jskf.supplychainmanager.adapter.ImageAdapter;
import com.yhkj.jskf.supplychainmanager.utils.ActivityUtils;
import com.yhkj.jskf.supplychainmanager.utils.ToastUtility;
import com.yhkj.jskf.supplychainmanager.widget.GridViewForScrollView;
import com.yhkj.jskf.supplychainmanager.widget.HeadTitleView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaofei on 2016/5/9.
 */
public class ParentActivity extends Activity {


    public static final String SHOWIMAGE = "showimage";

    /**
     *
     */
    protected HeadTitleView headTitleView;

    /***
     * 照片后返回的文件对象
     */
    protected File file;

    /**
     * 简单封装的findViewById
     * @param id
     * @param <T>
     * @return
     */
    protected <T> T findViewFromId(int id) {
        return (T) findViewById(id);
    }

    /**
     * 简单封装的getText
     * @param view
     * @return
     */
    protected String getText(TextView view) {
        return view.getText().toString().trim();
    }

    protected Context mContext;

    /**
     * 分页个数
     */
    protected  static final int PAGESIZE = 5;

    protected static final String PAGEINDEX_MARK = "pageIndex";
    protected static final String PAGESIZE_MARK = "pageSize";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
    }

    /**
     * 初始化imageList、ImageAdapter、GridView
     * @param imageList
     * @param imageAdapter
     * @param gv_images
     */
    protected  void initGridView(List<String> imageList, ImageAdapter imageAdapter, GridViewForScrollView gv_images){
        imageList = new ArrayList<String>();
        imageList.add("");
        imageAdapter = new ImageAdapter(this, imageList);
        gv_images.setAdapter(imageAdapter);
    }

    /**
     * 添加照片
     *
     * @param requestCode 请求码
     */
    private void uploadImage(int requestCode) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory() + "/supplychainmanager/images/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(it, requestCode);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示一张图片
     *
     * @param imgUrl
     */
    private void showImageView(String imgUrl) {
        Intent intent = new Intent(this, ShowImageActivity.class);
        intent.putExtra(SHOWIMAGE, imgUrl);
        startActivity(intent);
    }

    /**
     * 保存图片上传
     *
     * @param imageList
     * @param imageAdapter
     * @param gv_images
     */
    protected String saveImageToUpload(List<String> imageList, ImageAdapter imageAdapter, GridViewForScrollView gv_images) {
        if (null != file) {
            if (null == imageList) {
                imageList = new ArrayList<String>();
                imageList.add("");
                imageAdapter = new ImageAdapter(this, imageList);
                gv_images.setAdapter(imageAdapter);
            }
            int imageSize = imageList.size();
            if (1 == imageList.size()) {
                imageList.add(1, file.getPath());//将已经存在的显示
                imageAdapter.notifyDataSetChanged();
            } else if (2 == imageSize) {
                imageList.remove(1);//删除掉第二个
                imageList.add(1, file.getPath());//添加新的图片
                imageAdapter.notifyDataSetChanged();
            }
            StringBuilder sb = new StringBuilder();
            for (String image : imageList) {
                try {
                    if (!TextUtils.isEmpty(image) && -1 == image.indexOf("http")) {
                        if (null != ActivityUtils.encodeBase64File(image)) {
                            sb.append(ActivityUtils.encodeBase64File(image));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        } else {
            ToastUtility.showToast("获取照片失败，请再试一次");
        }
        return null;
    }

    /**
     * 更新图片上传
     *
     * @param imageList
     * @param imageAdapter
     * @param gv_images
     */
    protected String updateImageToUpload(List<String> imageList, ImageAdapter imageAdapter, GridViewForScrollView gv_images) {
        if (null == imageList) {
            imageList = new ArrayList<String>();
            imageList.add("");
            imageAdapter = new ImageAdapter(this, imageList);
            gv_images.setAdapter(imageAdapter);
            if (null != file) {
                imageList.add(1, file.getPath());//将已经存在的显示
                imageAdapter.notifyDataSetChanged();
            } else {
                ToastUtility.showToast("获取照片失败，请再试一次");
            }
            StringBuilder sb = new StringBuilder();
            for (String image : imageList) {
                try {
                    if (!TextUtils.isEmpty(image) && -1 == image.indexOf("/upload")) {
                        if (null != ActivityUtils.encodeBase64File(image)) {
                            sb.append(ActivityUtils.encodeBase64File(image));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        } else {
            if (null != file) {
                imageList.remove(1);//删除掉第二个
                imageList.add(1, file.getPath());///添加新的图片
                imageAdapter.notifyDataSetChanged();
                StringBuilder sb = new StringBuilder();
                for (String image : imageList) {
                    try {
                        if (!TextUtils.isEmpty(image) && -1 == image.indexOf("/upload")) {
                            if (null != ActivityUtils.encodeBase64File(image)) {
                                sb.append(ActivityUtils.encodeBase64File(image));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return sb.toString();
            } else {
                ToastUtility.showToast("获取照片失败，请再试一次");
            }
        }
        return null;
    }


    /**
     * {@link com.yhkj.jskf.supplychainmanager.widget.GridViewForScrollView}的照相的服务类，目前只能照一张图片上传
     */
    protected class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        private int requestCode;
        private List<String> stringLists;
        private boolean isSave = true;
        private boolean isOnlySee = false;

        private int oldSize = 0;

        public MyOnItemClickListener(List<String> stringLists, int requestCode) {
            this.requestCode = requestCode;
            this.stringLists = stringLists;
            this.oldSize = null == stringLists ? 0 : stringLists.size();
        }

        public MyOnItemClickListener(List<String> stringLists, int requestCode, boolean isSave) {
            this.requestCode = requestCode;
            this.stringLists = stringLists;
            this.isSave = isSave;
            this.oldSize = null == stringLists ? 0 : stringLists.size();
        }

        public MyOnItemClickListener(List<String> stringLists, int requestCode, boolean isSave,boolean isOnlySee) {
            this.requestCode = requestCode;
            this.stringLists = stringLists;
            this.isSave = isSave;
            this.isOnlySee = isOnlySee;
            this.oldSize = null == stringLists ? 0 : stringLists.size();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (0 == position) {
                if (isSave) {
                    if (stringLists.size() >= 2) {
                        new AlertDialog.Builder(ParentActivity.this).setMessage("目前允许上传一张图片,继续拍照会替换之前的照片，是否继续?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        uploadImage(requestCode);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    } else {
                        uploadImage(requestCode);
                    }
                } else {
                    if (isOnlySee){
                        return;
                    }
                    new AlertDialog.Builder(ParentActivity.this).setMessage("目前允许上传一张图片,继续拍照会替换之前的照片，是否继续?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    uploadImage(requestCode);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                }
            } else {
                if(TextUtils.isEmpty(stringLists.get(position))){
                    ToastUtility.showToast("图片路径错误，无法查看图片");
                    return;
                }
                showImageView(stringLists.get(position));
            }
        }
    }


}
