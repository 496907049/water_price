package my.http;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;

public class MyBaseBean {

	/** 数据 */
	private String resultData;
	
//	/** 数据 */
	@JSONField(name="result")
	private String otherResultData;

	/** 借口反馈信息 */
	private String statusInfo;
	/** 状态 200正常 */
	private int statusCode = 0;
	
	private int errCode = -1;

	@JSONField(name="count")
	private int recordsTotal;

//	private String code;

	public static final int CODE_OK = 200;


	private String api_time;

	public String getApi_time() {
		return api_time;
	}

	public void setApi_time(String api_time) {
		this.api_time = api_time;
	}
	
	@JSONField(name="data")
	public String getResultData() {

		if(!TextUtils.isEmpty(otherResultData)){
			return otherResultData;
		}
		return resultData;
	}

	@JSONField(name="data")
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

	@JSONField(name="message")
	public String getStatusInfo() {
		return statusInfo;
	}

	@JSONField(name="message")
	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	@JSONField(name="code")
	public int getStatusCode() {
		return statusCode;
	}

	@JSONField(name="code")
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public  boolean isCodeOk(){
		return statusCode == CODE_OK;
	}
//	
	@JSONField(name="result")
	public String getOtherResultData() {
		return otherResultData;
	}

	@JSONField(name="result")
	public void setOtherResultData(String otherResultData) {
		this.otherResultData = otherResultData;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
}
