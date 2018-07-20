package com.dlsmartercity.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Promise<Test> resolve = Promise.wrap(new AsyncTask<Test>() {
            @Override
            public Test call() throws Exception {
                return new Test();
            }
        });
    }
}
