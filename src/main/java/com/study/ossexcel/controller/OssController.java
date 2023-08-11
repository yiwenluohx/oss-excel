package com.study.ossexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.study.ossexcel.dropdown.CustomSheetWriteHandler;
import com.study.ossexcel.dropdown.UnitExcelDownVoOne;
import com.study.ossexcel.service.OssService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * ClassName: OssController
 * Description: oss下载解析excel
 *
 * @Author: luohx
 * Date: 2021/12/12 下午7:56
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           oss下载解析excel
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @Resource
    public HttpServletResponse response;

    @GetMapping("/upload/excel")
    public Integer uploadExcel(@RequestParam("url") String url) throws Exception{
        //https://pic591.oss-cn-beijing.aliyuncs.com/codefile/code-20211212.xlsx
        ossService.downLoadFromUrl(url);
//        读取excel多sheet并合并单元格的数据
//        ossService.readExcelMerge(url);

        //导出excel，带下拉选择
//        String fileName = URLEncoder.encode("XXX模板导出", "UTF-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), UnitExcelDownVoOne.class).sheet("中职院校").registerWriteHandler(new CustomSheetWriteHandler()).doWrite(Lists.newArrayList());

        return 1234;
    }

}
