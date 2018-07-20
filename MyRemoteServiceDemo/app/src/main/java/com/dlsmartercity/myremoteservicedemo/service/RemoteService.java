package com.dlsmartercity.myremoteservicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.dlsmartercity.myremoteservicedemo.IMyAidlInterface;

/**
 * @author ZhangXiWei on 2018/3/15.
 */

public class RemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 在客户端连接服务端时，Stub通过ServiceConnection传递到客户端
        return stub;
    }

    // 实现接口中暴露给客户端的Stub--Stub继承自Binder，它实现了IBinder接口
    private IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {

        // 实现了AIDL文件中定义的方法
        @Override
        public String getMessage() throws RemoteException {
            // 在这里我们只是用来模拟调用效果,因此随便反馈值给客户端
            return "Remote Service方法调用成功";
        }
    };

}
