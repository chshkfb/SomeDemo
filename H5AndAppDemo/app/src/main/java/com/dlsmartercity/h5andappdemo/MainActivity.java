package com.dlsmartercity.h5andappdemo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    Button mBtn;

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.webview);
        mBtn = findViewById(R.id.mBtn);
        //设置编码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置背景颜色 透明
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        //设置本地调用对象及其接口
        mWebView.addJavascriptInterface(new JavaScriptObject(this), "myObj");
        //载入js
        mWebView.loadUrl("file:///android_asset/test.html");

        //点击调用js中方法
        mBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:funFromjs()");
                Toast.makeText(MainActivity.this, "调用javascript:funFromjs()", Toast.LENGTH_LONG).show();
            }
        });

    }

}
