package com.qk365.datadict.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;


public class TableInfoExp {
//	序号	字段	说明	数据类型	长度	自增	主键	允许空	默认值
    /**
     * 序号
     */
	@Excel(name = "序号")
	private Integer idx;
	
	/**
	 * 列名 
	 */
	@Excel(name = "字段",width=15)
	private String columnName;
	
	/**
	 * 列说明
	 */
	@Excel(name = "说明",width=20)
	private String explain;
	
	/**
	 * 数据类型
	 */
	@Excel(name = "数据类型")
	private String  dataType ;
	
	/**
	 * 长度
	 */
	@Excel(name = "长度")
	private Integer length;
	
	/**
	 * 标识
	 */
	@Excel(name = "自增")
	private String  identification;
	
	/**
	 * 主键
	 */
	@Excel(name = "主键")
	private String  pk;
	
	/**
	 * 允许空
	 */
	@Excel(name = "允许空")
	private String  empty;
	
	/**
	 * 默认值
	 */
	@Excel(name = "默认值")
	private String  defaultValue;
	

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

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
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
