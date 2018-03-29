/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pal.collecttool;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.FileUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CameraActivity extends AppCompatActivity {
    @InjectView(R.id.tv_warnning)
    TextView tvWarnning;
    @InjectView(R.id.ll_warnning)
    LinearLayout llWarnning;
    @InjectView(R.id.jcameraview)
    JCameraView jcameraview;

    private int collect_type = 0;
    private String[] taskTask = {"语文" ," 数学" ,"英语" ,"政治","历史","物理","化学","生物"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_camera);
        ButterKnife.inject(this);

        collect_type = getIntent().getIntExtra("TASK_TYPE",0);

        initView();
    }

    private void initView() {
        tvWarnning.setText(taskTask[collect_type]);
        jcameraview.setFeatures(JCameraView.BUTTON_STATE_ONLY_CAPTURE);     //只拍照
        jcameraview.setTip("点击拍照");

        jcameraview.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.i("CJT", "camera error");
                Intent intent = new Intent();
                setResult(103, intent);     //没有权限
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(CameraActivity.this, "需要录音权限", Toast.LENGTH_SHORT).show();
            }
        });

        //JCameraView监听
        jcameraview.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
                if(FileUtil.isExternalStorageWritable()){
                    String path = FileUtil.saveBitmap("JCamera", bitmap);
                    Log.i("JCameraView", "path = " + path);
                    //获取到了bitmap 和图片路径
                    //下一步保存到服务器
                }else{
                    Toast.makeText(CameraActivity.this,"请先允许读写文件权限",Toast.LENGTH_LONG).show();
                }


//                Intent intent = new Intent();
//                intent.putExtra("path", path);
//                setResult(101, intent);
//
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {

            }
        });

        jcameraview.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                //左上角回退按键
                CameraActivity.this.finish();
            }
        });

        jcameraview.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                //点击了图片
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jcameraview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jcameraview.onPause();
    }
}
