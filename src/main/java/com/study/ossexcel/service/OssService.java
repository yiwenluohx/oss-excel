package com.study.ossexcel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.study.ossexcel.dto.CodeFileDto;
import com.study.ossexcel.dto.DemoExtraDataDto;
import com.study.ossexcel.listener.CodeFileListener;
import com.study.ossexcel.listener.MergeExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * ClassName: OssService
 * Description: oss下载解析excel
 *
 * @Author: luohx
 * Date: 2021/12/12 下午8:00
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           oss下载解析excel
 */
@Service
@Slf4j
public class OssService {

    /**
     * 初始化线程池
     */
    private final static ExecutorService executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 2,
            Runtime.getRuntime().availableProcessors() * 2,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(Integer.MAX_VALUE),
            new ThreadPoolExecutor.DiscardPolicy());

    public void downLoadFromUrl(String excelUrl) {
        try {
            URL url = new URL(excelUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            EasyExcel.read(inputStream, CodeFileDto.class, new CodeFileListener()).sheet().headRowNumber(2).doRead();
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("从网络Url中下载文件downLoadFromUrl, 发生异常，ex=" + e);
        }
    }

    public void readExcelMerge(String excelUrl) {
        try {
            URL url = new URL(excelUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            EasyExcel.read(inputStream, DemoExtraDataDto.class, new MergeExcelListener()).extraRead(CellExtraTypeEnum.MERGE).doReadAll();
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("从网络Url中下载文件downLoadFromUrl, 发生异常，ex=" + e);
        }
    }

    public void mulThreadTask() {
        //1、execute方法提交
        //1.1、匿名内部类创建执行线程
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //todo：执行业务代码
            }
        });

        //1.2、自定义创建线程类
        executor.execute(new TaskThread(12L));


        //2、invokeAll方法批量提交
        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(() -> {
                System.out.println(Thread.currentThread().getName());
                return null;
            });
        }
        try {
            List<Future<Object>> futureList = executor.invokeAll(tasks);
            // 获取全部并发任务的运行结果
            for (Future f : futureList) {
                // 获取任务的返回值，并输出到控制台
                System.out.println("result：" + f.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executor.shutdown();

        //3、使用submit方法提交
        //3.1 使用匿名内部类
        List<Future<Callable>> futureList = new ArrayList<>(10);

        for (int i = 0; i < 5; i++) {
            final int index = i;
            Future future = executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " " + index);
                return index;
            });
            futureList.add(future);
        }
        try {
            // 获取全部并发任务的运行结果
            for (Future f : futureList) {
                // 获取任务的返回值，并输出到控制台
                System.out.println("result：" + f.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executor.shutdown();

        //3.2 使用自定义线程
        List<Future<Callable>> futureList0 = new ArrayList<>(10);

        for (int i = 0; i < 5; i++) {
            final int index = i;
            Future future = executor.submit(new CallableTask1(index));
            futureList0.add(future);
        }
        try {
            // 获取全部并发任务的运行结果
            for (Future f : futureList0) {
                // 获取任务的返回值，并输出到控制台
                System.out.println("result：" + f.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executor.shutdown();

    }

    public class TaskThread implements Runnable {

        private Long applyId;

        public TaskThread(Long applyId) {
            this.applyId = applyId;
        }

        @Override
        public void run() {
            //todo：业务代码
        }
    }

    public class CallableTask1 implements Callable<Integer> {
        Integer i;

        public CallableTask1(Integer i) {
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " " + i);
            return i;
        }
    }

}
