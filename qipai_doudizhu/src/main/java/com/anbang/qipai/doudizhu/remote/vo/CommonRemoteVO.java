package com.anbang.qipai.doudizhu.remote.vo;

/**
 * 一般的view obj
 * 
 * @author neo
 *
 */
public class CommonRemoteVO {

	private boolean success;

	private String msg;

	private Object data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
