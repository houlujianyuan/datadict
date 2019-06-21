package com.qk365.datadict.po;

import cn.afterturn.easypoi.excel.annotation.Excel;


public class TableInfo {
    /**
     * 序号
     */
    @Excel(name = "序号")
    private Integer idx;

    /**
     * 列名
     */
    @Excel(name = "列名")
    private String columnName;

    /**
     * 列说明
     */
    @Excel(name = "列说明")
    private String explain;

    /**
     * 数据类型
     */
    @Excel(name = "数据类型")
    private String dataType;

    /**
     * 长度
     */
    @Excel(name = "长度")
    private String length;

    /**
     * 小数位数
     */
    @Excel(name = "小数位数")
    private Integer decimalDigits;

    /**
     * 标识
     */
    @Excel(name = "标识")
    private String identification;

    /**
     * 主键
     */
    @Excel(name = "主键")
    private String pk;

    /**
     * 默认值
     */
    @Excel(name = "默认值")
    private String defaultValue;

    /**
     * 允许空
     */
    @Excel(name = "允许空")
    private String empty;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }


}
