package com.pal.collecttool.utils;

import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pal.collecttool.R;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;

import butterknife.InjectView;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class CustomHelper {
    private View rootView;
    Spinner spGrade;

    public static CustomHelper of(View rootView) {
        return new CustomHelper(rootView);
    }

    private CustomHelper(View rootView) {
        this.rootView = rootView;
        spGrade = rootView.findViewById(R.id.sp_grade);
    }


    public void onClick(View view, TakePhoto takePhoto) {
        configTakePhotoOption(takePhoto);
        configCompress(takePhoto);
        int spinerPos = spGrade.getSelectedItemPosition();
        String name = "grade_"+spinerPos;

        switch (view.getId()) {
            case R.id.bt_chinese:
                name += "_chinese";
                startTakePic(name,takePhoto);
                break;
            case R.id.bt_math:
                name += "_math";
                startTakePic(name,takePhoto);
                break;
            case R.id.bt_english:
                name += "_english";
                startTakePic(name,takePhoto);
                break;
            case R.id.bt_politics:
                name += "_politics";
                startTakePic(name,takePhoto);
                break;

            case R.id.bt_history:
                name += "_history";
                startTakePic(name,takePhoto);
                break;
            case R.id.bt_physics:
                name += "_physics";
                startTakePic(name,takePhoto);
                break;
            case R.id.bt_chemistry:
                name += "_chemistry";
                startTakePic(name,takePhoto);
                break;
            case R.id.bt_geography:
                name += "_geography";
                startTakePic(name,takePhoto);
                break;
            default:
                break;
        }
    }

    private void startTakePic(String name, TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/collect/"+name+"_" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
    }


    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    private void configCompress(TakePhoto takePhoto) {
        takePhoto.onEnableCompress(null, false);
    }

    private CropOptions getCropOptions() {
        int height = 400;
        int width = 300;
        boolean withWonCrop = false;

        CropOptions.Builder builder = new CropOptions.Builder();

        // builder.setAspectX(width).setAspectY(height);        //width / height
        builder.setOutputX(width).setOutputY(height);       //width*height
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

}
