package com.dlsmartercity.myapplication;

/**
 * @author ZhangXiWei on 2018/3/7.
 */

public interface AsyncTask<V> {
    V call() throws Exception;
}
