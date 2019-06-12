package com.qk365.datadict.dto;

import java.io.Serializable;

/**
 * 返回值对象
 */
public class ResultVO<T> implements Serializable {

	private static final long serialVersionUID = -9128600495587366380L;

	/** 返回状态 - 成功 **/
	public static final int RESULT_SUCCESS = 0;
	/** 返回状态 - 失败 **/
	public static final int RESULT_FAIL = 1;

	protected int result; // 返回状态
	protected String message = ""; // 返回信息
	protected T  data; // 返回数据
    
	public ResultVO(){
		
	}
	public ResultVO(int result,String message,T data) {
		this.result = result;
		this.message =message;
		this.data=data;
	}


	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
