package com.ffapp.waterprice.bean;


import android.text.TextUtils;

import java.util.Date;

import my.LogUtil;
import my.TimeUtils;

public class ManageMaintainDetailBean extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final int TYPE_SOFT = 1,TYPE_MACHINE = 2;

	/**
	 * id : 1ab57e5442b642c3890b56167753eba4
	 * name : 等等
	 * type : 1
	 * maintenanceUser : root
	 * declaration : 巡检人员
	 * declareUser : null
	 * state : 1
	 * content : 踩到底
	 * faultId : 1
	 * createUser : root
	 * auditUser : null
	 * auditNotes : null
	 * updateDevice : null
	 * situation : 
	 * auditTime : null
	 * startTime : 2019-11-18T18:03:08.000+0000
	 * endTime : 2019-11-20T19:00:05.000+0000
	 * executionTime : null
	 * finishTime : null
	 * createTime : 1574146203754
	 * updateTime : 1574146203754
	 * createUserName : null
	 * auditUserName : 未知：null
	 * auditUserRole : null
	 * maintenanceUserName : 超人
	 * maintenanceUserRole : ,
	 * faultCause : null
	 */

	private String id;
	private String name;
	private int type;
	private String maintenanceUser;
	private String declaration;
	private String declareUser;
	private int state;
	private String content;
	private String faultId;
	private String createUser;
	private String auditUser;
	private String auditNotes;
	private String updateDevice;
	private String situation;
	private String auditTime;
	private String startTime;
	private String endTime;
	private long executionTime;
	private long finishTime;
	private long createTime;
	private long updateTime;
	private String createUserName;
	private String auditUserName;
	private String auditUserRole;
	private String maintenanceUserName;
	private String maintenanceUserRole;
	private String faultCause;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public String getTYPE_STR(){
		switch (type){
			case TYPE_SOFT:
				return "软件";
			case TYPE_MACHINE:
				return "硬件";
		}
		return "";
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMaintenanceUser() {
		return maintenanceUser;
	}

	public void setMaintenanceUser(String maintenanceUser) {
		this.maintenanceUser = maintenanceUser;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public String getDeclareUser() {
		return declareUser;
	}

	public void setDeclareUser(String declareUser) {
		this.declareUser = declareUser;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFaultId() {
		return faultId;
	}

	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getAuditNotes() {
		return auditNotes;
	}

	public void setAuditNotes(String auditNotes) {
		this.auditNotes = auditNotes;
	}

	public String getUpdateDevice() {
		return updateDevice;
	}

	public void setUpdateDevice(String updateDevice) {
		this.updateDevice = updateDevice;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public String getAuditUserRole() {
		return auditUserRole;
	}

	public void setAuditUserRole(String auditUserRole) {
		this.auditUserRole = auditUserRole;
	}

	public String getMaintenanceUserName() {
		return maintenanceUserName;
	}

	public void setMaintenanceUserName(String maintenanceUserName) {
		this.maintenanceUserName = maintenanceUserName;
	}

	public String getMaintenanceUserRole() {
		return maintenanceUserRole;
	}

	public void setMaintenanceUserRole(String maintenanceUserRole) {
		this.maintenanceUserRole = maintenanceUserRole;
	}

	public String getFaultCause() {
		return faultCause;
	}

	public void setFaultCause(String faultCause) {
		this.faultCause = faultCause;
	}

	public BaseListDataListBean getListInfoMaintain_Detail(boolean isEdit){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData(name,"维护名称"));
		dataListBean.getList().add(new BaseListData(getTYPE_STR(),"维护类型"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getDistanceTimeFuture(TimeUtils.getLongTimeByFormat(startTime,"yyyy-MM-dd HH:mm:ss"),TimeUtils.getLongTimeByFormat(endTime,"yyyy-MM-dd HH:mm:ss")),"维护时间周期"));
		dataListBean.getList().add(new BaseListData(declaration,"维护人"));
//		dataListBean.getList().add(new BaseListData("1","维护次数"));
//		if(executionTime != 0){
//			dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(executionTime,"yyyy-MM-dd HH:mm:ss"),"维护时间"));
//		}
		dataListBean.getList().add(new BaseListData(content,"维护内容"));
		if(executionTime != 0 && (finishTime !=0)){
			dataListBean.getList().add(new BaseListData(TimeUtils.getDistanceTimeFutureHour(executionTime,finishTime),"维护时长"));
		}
		if(!isEdit){
			if(type == TYPE_MACHINE){
				dataListBean.getList().add(new BaseListData(updateDevice,"替换器件"));
			}
			if(executionTime != 0){
				dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(executionTime,"yyyy-MM-dd HH:mm:ss"),"维护时间"));
			}

			if(!TextUtils.isEmpty(situation)){
				dataListBean.getList().add(new BaseListData(situation,"维护情况"));
			}

			if(finishTime != 0){
				dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(finishTime,"yyyy-MM-dd HH:mm:ss"),"结束时间"));
			}
		}

//		dataListBean.getList().add(new BaseListData("2h","维护时长"));
		return  dataListBean;
	}
}
