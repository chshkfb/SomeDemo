package com.dlsmartercity.mytestapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

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
        });
    }

    public static class JsOpenImageWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            addImageClickListener(view);
        }

        private void addImageClickListener(WebView webView) {
            webView.loadUrl(""
                    + "javascript:(function() {"
                    + "    var imgs = document.getElementsByTagName(\"img\");"
                    + "    var size = imgs.length;"
                    + "    for(var i = 0; i < size; i++) {"
                    + "        imgs[i].onclick = function() {"
                    + "            window." + ON_IMAGE_CLICK_LISTENER + ".browseImage(this.src);"
                    + "        }"
                    + "    }"
                    + "})()");
        }
    }

    public static final String ON_IMAGE_CLICK_LISTENER = "onImageClickListener";

    public static class BrowseWebImageJavascriptInterface {
        private Context mContext;

        public BrowseWebImageJavascriptInterface(Context context) {
            this.mContext = context;
        }


    }

    // region [设置下载 上下文菜单]
    public void registerForDownloadImageContextMenu(@NonNull Activity activity, @NonNull WebView webView) {
        activity.registerForContextMenu(webView);
    }

    private final static int ITEM_ID_DOWNLOAD_IMAGE = 0;
    private final static String MIME_TYPE_IMAGE = "image/*";


    // endregion

    private static class RetrieveContentTypeTask extends AsyncTask<String, Void, Void> {
        private static final String HTTP_CONTENT_TYPE = "Content-Type";

        private Callback mCallback;
        private Handler mHandler = new Handler(Looper.getMainLooper());

        private RetrieveContentTypeTask(@NonNull Callback callback) {
            this.mCallback = callback;
        }

        @Override
        protected Void doInBackground(String... imgUrls) {
            URLConnection u;
            try {
                u = new URL(imgUrls[0]).openConnection();
            } catch (final IOException e) {
                e.printStackTrace();
                postFailCallback("网络连接失败, 请尝试重新下载.");
                return null;
            }

            final String contentType = u.getHeaderField(HTTP_CONTENT_TYPE);
            if (TextUtils.isEmpty(contentType)) {
                postFailCallback("网络连接失败, 请尝试重新下载.");
                return null;
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetContentTypeSuccess(contentType);
                }
            });

            return null;
        }

        private void postFailCallback(final String reason) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetContentTypeFail(reason);
                }
            });
        }

        private interface Callback {
            @UiThread
            void onGetContentTypeSuccess(String contentType);

            @UiThread
            void onGetContentTypeFail(String reason);
        }
    }

    // region [下载图片逻辑]



    // endregion [下载图片逻辑]
}
