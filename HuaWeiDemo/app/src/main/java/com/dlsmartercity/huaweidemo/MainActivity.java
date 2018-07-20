package com.dlsmartercity.huaweidemo;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.main);

        constraintLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                //比较Activity根布局与当前布局的大小
                int heightDiff = constraintLayout.getRootView().getHeight() - constraintLayout.getHeight();
                if (heightDiff > 100) {
                    //大小超过100时，一般为显示虚拟键盘事件
                    Toast.makeText(MainActivity.this, "onGlobalLayout,显示了", Toast.LENGTH_SHORT).show();
                } else {
                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
                    Toast.makeText(MainActivity.this, "onGlobalLayout,隐藏了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
