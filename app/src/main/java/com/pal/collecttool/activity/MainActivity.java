package com.pal.collecttool.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pal.collecttool.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final int GET_PERMISSION_REQUEST = 100; //权限申请自定义码

    @InjectView(R.id.sample_text)
    TextView sampleText;
    @InjectView(R.id.bt_chinese)
    Button btChinese;
    @InjectView(R.id.bt_math)
    Button btMath;
    @InjectView(R.id.bt_english)
    Button btEnglish;
    @InjectView(R.id.bt_politics)
    Button btPolitics;
    @InjectView(R.id.bt_history)
    Button btHistory;
    @InjectView(R.id.bt_physics)
    Button btPhysics;
    @InjectView(R.id.bt_chemistry)
    Button btChemistry;
    @InjectView(R.id.bt_geography)
    Button btGeography;

    private int taskType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.bt_chinese, R.id.bt_math, R.id.bt_english, R.id.bt_politics, R.id.bt_history, R.id.bt_physics, R.id.bt_chemistry, R.id.bt_geography})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_chinese:
                taskType = 0;
                getPermissions(taskType);
                break;
            case R.id.bt_math:
                taskType = 1;
                getPermissions(taskType);
                break;
            case R.id.bt_english:
                taskType = 2;
                getPermissions(taskType);
                break;
            case R.id.bt_politics:
                taskType = 3;
                getPermissions(taskType);
                break;
            case R.id.bt_history:
                taskType = 4;
                getPermissions(taskType);
                break;
            case R.id.bt_physics:
                taskType = 5;
                getPermissions(taskType);
                break;
            case R.id.bt_chemistry:
                taskType = 6;
                getPermissions(taskType);
                break;
            case R.id.bt_geography:
                taskType = 7;
                getPermissions(taskType);
                break;
        }
    }

    /**
     * 获取权限
     */
    private void getPermissions(int taskType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                gotoTakePic(taskType);
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
            }
        } else {
            gotoTakePic(taskType);
        }
    }

    /**
     * 去采集图片
     *
     * @param taskType
     *  0 语文
     *  1 数学
     *  2 英语
     *  3 政治
     *  4 历史
     *  5 物理
     *  6 化学
     *  7 生物
     * */
    private void gotoTakePic(int taskType) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,CameraActivity.class);
        intent.putExtra("TASK_TYPE",taskType);
        startActivityForResult(intent,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            Log.i("CJT", "picture");
            String path = data.getStringExtra("path");
        }
        if (resultCode == 102) {
            Log.i("CJT", "video");
            String path = data.getStringExtra("path");
        }
        if (resultCode == 103) {
            Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[1];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    startActivityForResult(new Intent(MainActivity.this, CameraActivity.class), 100);
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
