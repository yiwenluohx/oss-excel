package com.study.ossexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.study.ossexcel.dto.CodeFileDto;
import com.study.ossexcel.multithreading.CodeApplyHandler;
import com.study.ossexcel.multithreading.CodeTaskProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * ClassName: CodeFileListener
 * Description:
 * Author: luohx
 * Date: 2021/12/12 下午8:25
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class CodeFileListener implements ReadListener<CodeFileDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeFileListener.class);
    /**
     * 每隔n条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 2;
    private List<CodeFileDto> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
    private int maximumPoolSize = (int) (Runtime.getRuntime().availableProcessors() / (1 - 0.8));
    private LinkedBlockingQueue<List<CodeFileDto>> queue = new LinkedBlockingQueue<>(32);
    private long keepAliveTime = 60;
    private ExecutorService executor;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param
     */
    public CodeFileListener() {
        this.executor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<>(32), new ThreadPoolExecutor.DiscardPolicy());
    }

    /**
     * 每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(CodeFileDto data, AnalysisContext context) {
        LOGGER.info("CodeFileListener解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        executor.shutdown();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", cachedDataList.size());
        try {
            executor.submit(new CodeTaskProducer(queue, cachedDataList));
            Integer res = executor.submit(new CodeApplyHandler(queue)).get();
            if (res <= 0) {
                LOGGER.info("通过线程池将解析的数据入库，入库数据如下 cachedDataList=" + JSON.toJSONString(cachedDataList));
            }
        } catch (Exception ex) {
            LOGGER.info("码入库操作saveData发生异常！ex=" + ex);
        }
        LOGGER.info("存储数据库成功！");
    }
}