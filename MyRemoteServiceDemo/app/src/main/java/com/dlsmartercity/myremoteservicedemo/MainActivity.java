package com.dlsmartercity.myremoteservicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface iMyAidlInterface;// 定义接口变量
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindRemoteService();
    }

    private void bindRemoteService() {
        Intent intentService = new Intent();
        intentService.setAction("TestTheRemoteService");

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                // 从连接中获取Stub对象
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
                // 调用Remote Service提供的方法
                try {
                    Log.d("MainActivity",
                            "获取到消息：" + iMyAidlInterface.getMessage());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                // 断开连接
                iMyAidlInterface = null;
            }
        };

        bindService(intentService, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }
}
