package com.study.ossexcel.dropdown;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/12 下午5:46
 * @menu
 */
@Data
@ContentRowHeight(25)
@HeadRowHeight(25)
@ColumnWidth(50)
public class UnitExcelDownVoOne {

    @ExcelIgnore
    private Integer unitType;

    @ExcelProperty(value = "单位身份", index = 0)
    private String unitIdentity;

    @ExcelProperty(value = "加入年份", index = 1)
    private String joinYears;
    @ExcelIgnore
    private Integer joinYear;

    @ExcelProperty(value = "您想加入，成为集团成员吗？", index = 2)
    private String joinIdea;

    @ExcelProperty(value = "单位名称", index = 3)
    private String unitName;

    @ExcelProperty(value = "地区", index = 4)
    private String area;

    @ExcelProperty(value = "地区-省", index = 5)
    private String province;

    @ExcelProperty(value = "地区-市", index = 6)
    private String city;

    @ExcelProperty(value = "地区-县区", index = 7)
    private String county;

    @ExcelProperty(value = "统一社会信用代码", index = 8)
    private String code;

    @ExcelProperty(value = "学校标识码", index = 9)
    private String schoolCode;

    @ExcelProperty(value = "级别", index = 10)
    private String level;

    @ExcelProperty(value = "单位代表人姓名", index = 11)
    private String contact;

    @ExcelProperty(value = "单位代表人联系电话", index = 12)
    private String phone;

    @ExcelProperty(value = "单位代表人职务", index = 13)
    private String representPost;

    @ExcelProperty(value = "单位联系人姓名", index = 14)
    private String companyContactName;

    @ExcelProperty(value = "单位联系人电话", index = 15)
    private String companyContactPhone;

    @ExcelProperty(value = "单位联系人职务", index = 16)
    private String companyContactPost;

    @ExcelProperty(value = "单位联系人部门", index = 17)
    private String workUnit;
}
