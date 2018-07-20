package com.dlsmartercity.mytestapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends Activity {

    private WebView webView;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.addJavascriptInterface(new WebViewUtils.BrowseWebImageJavascriptInterface(this), WebViewUtils.ON_IMAGE_CLICK_LISTENER);
        WebViewUtils.wrapper(webView, new WebViewUtils.JsOpenImageWebViewClient());
        webView.loadUrl("http://dev-yanan.dlsmartercity.com/annualaccount-api/annual/yeardata/2017?uid=Dc2ALBtewW8");


    }
}
