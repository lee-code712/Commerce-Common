package com.digital.v3.schema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SuccessMsg {

	@ApiModelProperty(required = true, position = 1, notes = "성공 코드", example = "0", dataType = "long")
	private long successCode;
	
	@ApiModelProperty(required = true, position = 2, notes = "성공 메시지", example = "성공 메시지", dataType = "string")
	private String successMsg;

	public long getSuccessCode() {
		long successCode = this.successCode;
		return successCode;
	}

	public void setSuccessCode(long successCode) {
		this.successCode = successCode;
	}

	public String getSuccessMsg() {
		String successMsg = this.successMsg;
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

}
