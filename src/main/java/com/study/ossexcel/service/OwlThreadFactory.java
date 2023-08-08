package com.study.ossexcel.service;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工厂
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/8 下午3:02
 * @menu 线程池工厂
 */
public class OwlThreadFactory implements ThreadFactory {

    private String namePrefix;
    private final AtomicInteger nextId = new AtomicInteger(1);

    public OwlThreadFactory(String whatFeatureOfGroup) {
        // 线程池名字 + 线程名字前缀
        namePrefix = "From OwlThreadFactory's " + whatFeatureOfGroup + "-Worker-";
    }

    @Override
    public Thread newThread(Runnable task) {
        // 线程id
        String name = namePrefix + nextId.getAndIncrement();
        Thread thread = new Thread(null, task, name);
        return thread;
    }
}
