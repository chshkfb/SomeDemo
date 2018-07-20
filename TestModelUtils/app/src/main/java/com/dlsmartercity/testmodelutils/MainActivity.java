package com.dlsmartercity.testmodelutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dlsmartercity.modelutil.TestUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestUtil testUtil = new TestUtil();
        testUtil.test();
    }
}
