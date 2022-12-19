package com.digital.v3.schema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ErrorMsg {

	@ApiModelProperty(required = true, position = 1, notes = "에러 코드", example = "0", dataType = "long")
	private long errorCode;
	
	@ApiModelProperty(required = true, position = 2, notes = "에러 메시지", example = "에러 메시지", dataType = "string")
	private String errorMsg;

	public long getErrorCode() {
		long errorCode = this.errorCode;
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		String errorMsg = this.errorMsg;
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
