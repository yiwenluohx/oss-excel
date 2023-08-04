package com.study.ossexcel.multithreading;

import java.util.concurrent.Callable;

/**
 * ClassName: CallableTemplate
 * Description:
 * Author: luohx
 * Date: 2021/12/12 下午8:42
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public abstract class CallableTemplate<V> implements Callable<V> {

    /**
     * 前置处理，子类可以Override该方法
     */
    public void beforeProcess() {
        System.out.println("before process....");
    }

    /**
     * 处理业务逻辑的方法,需要子类去Override
     *
     * @return
     * @throws Exception
     */
    public abstract V process() throws Exception;

    /**
     * 后置处理，子类可以Override该方法
     */
    public void afterProcess() {
        System.out.println("after process....");
    }

    @Override
    public V call() throws Exception {
        beforeProcess();
        V result = process();
        afterProcess();
        return result;
    }
}