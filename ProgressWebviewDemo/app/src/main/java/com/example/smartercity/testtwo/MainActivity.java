package com.example.smartercity.testtwo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {


    WebView webView;
    Handler handler = new Handler();
    boolean blockLoadingNetworkImage = false;
    ProgressDialog loadingProgressDialog = null;
    final static int PROGRESS_DIALOG_CONNECTING = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setBlockNetworkImage(true);
        blockLoadingNetworkImage = true;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
//                Activities and WebViews measure progress with different
//                scales.
//                        The progress meter will automatically disappear when we reach
//                100 %
//                        Log.i(TAG, "progress:" + progress);
                if (loadingProgressDialog != null && loadingProgressDialog.isShowing())
                    loadingProgressDialog.setProgress(progress);
                if (progress >= 100) {
//                    if(t==0)
//                        t=System.currentTimeMillis()-t1;
//                    else
//                        t=(t+System.currentTimeMillis()-t1)>>1;
//                    t1=System.currentTimeMillis()-t1;
//                    Log.i(TAG, "t:" + t/1000+" t1:"+t1/1000);
                    if (blockLoadingNetworkImage) {
                        webView.getSettings().setBlockNetworkImage(false);
                        blockLoadingNetworkImage = false;
                    }
                    if (loadingProgressDialog != null && loadingProgressDialog.isShowing())
                        dismissDialog(PROGRESS_DIALOG_CONNECTING);
                }
            }
        });
        Runnable r = new Runnable() {
            public void run() {
                webView.loadUrl("http://mp.weixin.qq.com/s/MuNSO0Jldp_Y4owQr-PfHg");
//                t1=System.currentTimeMillis();
                Log.i("TAG", "url:" + "http://mp.weixin.qq.com/s/MuNSO0Jldp_Y4owQr-PfHg");
                showDialog(PROGRESS_DIALOG_CONNECTING);
            }
        };
        handler.postDelayed(r, 200);


//        webView.loadUrl("http://mp.weixin.qq.com/s/MuNSO0Jldp_Y4owQr-PfHg");

    }

    protected void onResume() {
        super.onResume();
        if (webView.getProgress() < 100)
            showDialog(PROGRESS_DIALOG_CONNECTING);
    }

    protected void onDestroy() {
        webView.stopLoading();
        webView.destroy();
        super.onDestroy();
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PROGRESS_DIALOG_CONNECTING: {
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage(getResources()
                        .getString(R.string.app_name));
                loadingProgressDialog = progressDialog;
                return progressDialog;
            }
            default:
                break;
        }
        return null;
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case PROGRESS_DIALOG_CONNECTING: {
                loadingProgressDialog.setMax(100);
                dialog.show();
            }
            break;
            default:
                break;
        }
    }
}
