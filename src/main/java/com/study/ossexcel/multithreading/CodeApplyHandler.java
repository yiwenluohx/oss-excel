package com.study.ossexcel.multithreading;

import com.study.ossexcel.dto.CodeFileDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * ClassName: CodeApplyHandler
 * Description:
 * @Author: luohx
 * Date: 2021/12/12 下午8:35
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Slf4j
public class CodeApplyHandler extends CallableTemplate<Integer> {

    private BlockingQueue<List<CodeFileDto>> queue;

    public CodeApplyHandler(BlockingQueue<List<CodeFileDto>> queue) {
        this.queue = queue;
    }

    @Override
    public Integer process() throws Exception {
        List<CodeFileDto> codeList = queue.take();
        System.out.println("消费读取的数据codeList，数据总量：" + codeList.size() + ", codeList=" + codeList);
        return 1;
    }
}