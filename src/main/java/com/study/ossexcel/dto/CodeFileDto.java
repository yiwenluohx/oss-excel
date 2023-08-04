package com.study.ossexcel.dto;

import lombok.Data;

/**
 * ClassName: CodeFileDto
 * Description:
 * @Author: luohx
 * Date: 2021/12/12 下午8:23
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Data
public class CodeFileDto {
    /**
     * 序号
     */
    private String no;

    /**
     * 企业编码
     */
    private String enterpriseNo;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 产品编码
     */
    private String goodsCode;

    /**
     * 产品名称
     */
    private String goodsName;

    /**
     * 统一标识码
     */
    private String traceCode;
}
