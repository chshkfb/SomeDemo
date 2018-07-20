package com.dlsmartercity.h5andappdemo;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * @author ZhangXiWei on 2018/1/19.
 */

public class JavaScriptObject {
    Context mContxt;

    public JavaScriptObject(Context mContxt) {
        this.mContxt = mContxt;
    }

    @JavascriptInterface//sdk17版本以上加上注解
    public void fun1FromAndroid(String name) {
        Toast.makeText(mContxt, name, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface//sdk17版本以上加上注解
    public void fun2(String name) {
        Toast.makeText(mContxt, "调用fun2:" + name, Toast.LENGTH_SHORT).show();
    }
}
