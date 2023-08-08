package com.study.ossexcel.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/8 下午2:55
 * @menu 自定义线程池
 */
public class OwlThreadPoolExecutor {
    /**
     * default value
     */
    private static int corePoolSite = 5;
    private static int maxPoolSite = 10;
    private static int queueCapacity = 100;
    private static Long keepAliveTime = 1L;

    public static volatile ThreadPoolExecutor threadPoolExecutorInstance = null;

    private OwlThreadPoolExecutor() {}

    public static void initialize(int corePoolSite, int maxPoolSite, int queueCapacity, long keepAliveTime) {
        OwlThreadPoolExecutor.corePoolSite = corePoolSite;
        OwlThreadPoolExecutor.maxPoolSite = maxPoolSite;
        OwlThreadPoolExecutor.queueCapacity = queueCapacity;
        OwlThreadPoolExecutor.keepAliveTime = keepAliveTime;
    }

    public static ThreadPoolExecutor getThreadPoolExecutorInstance() {
        if (threadPoolExecutorInstance == null || threadPoolExecutorInstance.isShutdown()) {
            synchronized (OwlThreadPoolExecutor.class) {
                // double check
                if (threadPoolExecutorInstance == null || threadPoolExecutorInstance.isShutdown()) {
                    System.out.println("The thread pool instance is empty, so need to create.");
                    threadPoolExecutorInstance = new ThreadPoolExecutor(
                            corePoolSite,
                            maxPoolSite,
                            keepAliveTime,
                            TimeUnit.SECONDS,
                            new ArrayBlockingQueue<>(queueCapacity),
                            new OwlThreadFactory("ThreadPool"),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    System.out.println("The thread pool instance info: " + threadPoolExecutorInstance);
                }
            }
        }
        return threadPoolExecutorInstance;
    }
}
