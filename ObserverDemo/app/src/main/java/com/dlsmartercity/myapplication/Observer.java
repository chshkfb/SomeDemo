package com.dlsmartercity.myapplication;

/**
 * @author ZhangXiWei on 2018/3/6.
 */

public interface Observer<T> {
    void onCompleted();

    void onError(Throwable t);

    void onNext(T var1);
}