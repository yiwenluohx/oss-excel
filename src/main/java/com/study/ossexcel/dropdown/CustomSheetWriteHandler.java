package com.study.ossexcel.dropdown;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/12 下午5:45
 * @menu
 */
public class CustomSheetWriteHandler implements SheetWriteHandler {



    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        //定义一个map key是需要添加下拉框的列的index value是下拉框数据
        Map<Integer, String[]> mapDropDown = new HashMap<>(3);
        //设置单位身份 值写死
        String[] unitIdentity = {"职教集团成员单位","拟合作单位","合作单位"};

        //地区
        String[] area = {"国内","国外"};

        //等级下拉选
        String[] level = {"国家级示范校","国家级骨干校","省级示范校","省级骨干校","其他"};

        //年份下拉选
        String[] joinYear = {"1990年","1991年","1992年","1993年","1994年","1995年","1996年","1997年","1998年",
                "1999年","2000年","2001年","2002年","2003年","2004年","2005年","2006年","2007年","2008年","2009年","2010年","2011年","2012年",
                "2013年","2014年","2015年","2016年","2017年","2018年","2019年","2020年","2021年","2022年"};

        //下拉选在Excel中对应的列
        mapDropDown.put(0,unitIdentity);
        mapDropDown.put(1,joinYear);
        mapDropDown.put(4,area);
        mapDropDown.put(10,level);

        //获取工作簿
        Sheet sheet = writeSheetHolder.getSheet();
        ///开始设置下拉框
        DataValidationHelper helper = sheet.getDataValidationHelper();
        //设置下拉框
        for (Map.Entry<Integer, String[]> entry : mapDropDown.entrySet()) {
            /*起始行、终止行、起始列、终止列  起始行为1即表示表头不设置**/
            CellRangeAddressList addressList = new CellRangeAddressList(1, 65535, entry.getKey(), entry.getKey());
            /*设置下拉框数据**/
            DataValidationConstraint constraint = helper.createExplicitListConstraint(entry.getValue());
            DataValidation dataValidation = helper.createValidation(constraint, addressList);
            sheet.addValidationData(dataValidation);
        }
    }
}
