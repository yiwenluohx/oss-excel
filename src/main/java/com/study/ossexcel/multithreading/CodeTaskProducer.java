package com.study.ossexcel.multithreading;

import com.alibaba.fastjson.JSON;
import com.study.ossexcel.dto.CodeFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * ClassName: CodeTaskProducer
 * Description:
 * Author: luohx
 * Date: 2021/12/12 下午8:35
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class CodeTaskProducer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeTaskProducer.class);

    private BlockingQueue<List<CodeFileDto>> queue;
    private List<CodeFileDto> codes;

    public CodeTaskProducer(BlockingQueue<List<CodeFileDto>> queue, List<CodeFileDto> codes) {
        this.queue = queue;
        this.codes = codes;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("CodeTaskProducer码申请集合入队：" + JSON.toJSONString(codes));
            queue.put(codes);
        } catch (InterruptedException e) {
            LOGGER.error("CodeTaskProducer码申请集合入队失败：：codes=" + JSON.toJSONString(codes));
        }
    }
}