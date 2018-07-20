package com.dlsmartercity.testuploadjcenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dlsmartercity.mylibrary.TestUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestUtil.SayHello(this);

    }
}
