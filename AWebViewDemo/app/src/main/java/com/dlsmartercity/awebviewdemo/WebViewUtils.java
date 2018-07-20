package com.dlsmartercity.awebviewdemo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewUtils {
    public static void wrapper(@NonNull WebView webView) {
        wrapper(webView, null);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void wrapper(@NonNull WebView webView, @Nullable WebViewClient webViewClient) {
        // Cache
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setSavePassword(true);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setDatabaseEnabled(true);
//        webView.getSettings().setDatabasePath(webView.getContext().getCacheDir().toString());
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.getSettings().setAppCachePath(webView.getContext().getCacheDir().toString());
//        webView.getSettings().setAppCacheMaxSize(50 * 1024 * 1024);

        // JS
        webView.getSettings().setJavaScriptEnabled(true);

        // 自适应 / 禁止缩放
        webView.setInitialScale(100);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        // 不显示滚动条
//         webView.setHorizontalScrollBarEnabled(false);
//         webView.setHorizontalScrollbarOverlay(false);
//         webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        // 允许 Android L 的 http/https 混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

//        webView.getSettings().setDefaultTextEncodingName("utf-8");

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }

                            break;
                        default:
                            break;
                    }
                }

                return false;
            }
        });

        webView.setWebViewClient(webViewClient != null
                ? webViewClient
                : new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 接受证书
                handler.proceed();
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d(WebViewUtils.class.getSimpleName(), cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId());
                return true;
            }
        });
    }

}
