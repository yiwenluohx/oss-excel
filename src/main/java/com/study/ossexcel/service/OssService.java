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
}
