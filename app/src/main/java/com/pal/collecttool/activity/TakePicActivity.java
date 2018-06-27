package com.pal.collecttool.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pal.collecttool.R;

import org.devio.takephoto.app.TakePhotoActivity;

public class TakePicActivity extends TakePhotoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic);
    }
}
