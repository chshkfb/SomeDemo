package com.dlsmartercity.myapplication;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangXiWei on 2018/3/7.
 */

public abstract class Promise<V> {
    /**
     * 线程池服务
     */
    protected static ExecutorService executorService;

    /**
     * 获取执行结果
     *
     * @return
     */
    public abstract V get();

    /**
     * 带超时时间的获取执行结果
     *
     * @param timeout
     * @param unit
     * @return
     */
    public abstract V get(long timeout, TimeUnit unit);

    /**
     * 获取异常信息
     *
     * @return
     */
    public abstract Throwable getException();

    /**
     * 将普通逻辑包装成Promise
     *
     * @param task
     * @param <V>
     * @return
     */
    public static <V> Promise<V> wrap(final AsyncTask<V> task) {
        if (executorService == null) {
            setDefaultExecutorService();
        }
        return new DefaultPromise<V>(executorService.submit(new Callable<V>() {
            @Override
            public V call() throws Exception {
                return task.call();
            }
        }));
    }

    public static void setExecutorService(ExecutorService executorService) {
        Promise.executorService = executorService;
    }

    private static void setDefaultExecutorService() {
        Promise.executorService = Executors.newCachedThreadPool();
    }
}
