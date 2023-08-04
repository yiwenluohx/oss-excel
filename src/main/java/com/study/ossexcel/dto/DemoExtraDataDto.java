package com.study.ossexcel.dto;

/**
 * easyExcel读取excel额外信息（批注、超链接、合并单元格）
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/3/24 上午10:21
 * @menu easyExcel读取excel额外信息（批注、超链接、合并单元格）
 */
public class DemoExtraDataDto {

    private String row1;

    private String row2;

    /**
     * Gets the value of row1.
     *
     * @return the value of row1
     */
    public String getRow1() {
        return row1;
    }

    /**
     * Sets the row1. *
     * <p>You can use getRow1() to get the value of row1</p>
     * * @param row1 row1
     */
    public void setRow1(String row1) {
        this.row1 = row1;
    }

    /**
     * Gets the value of row2.
     *
     * @return the value of row2
     */
    public String getRow2() {
        return row2;
    }

    /**
     * Sets the row2. *
     * <p>You can use getRow2() to get the value of row2</p>
     * * @param row2 row2
     */
    public void setRow2(String row2) {
        this.row2 = row2;
    }
}
