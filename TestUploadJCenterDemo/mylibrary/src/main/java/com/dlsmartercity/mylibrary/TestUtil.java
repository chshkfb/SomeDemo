package com.dlsmartercity.mylibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ZhangXiWei on 2018/4/18.
 */
public class TestUtil {
    public static void SayHello(Context context) {
        Toast.makeText(context, "Hello!", Toast.LENGTH_SHORT).show();
    }
}
