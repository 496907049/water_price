package com.ffapp.waterprice.bean;


import android.text.TextUtils;

import my.TimeUtils;

public class ManagePatrolDetailBean extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id : 0a0e7bb06bbd4d1cbd3b747daef4af00
	 * name : 计划1
	 * patrolUser : asdasds
	 * state : 4
	 * content : 计划1
	 * createUser : root
	 * auditUser : null
	 * dutyId : aede00b33d384d4bbf7e9389494557e7
	 * auditNotes : null
	 * auditTime : null
	 * address : null
	 * situation : null
	 * conclusion : null
	 * executionTime : 1574302777360
	 * finishTime : null
	 * startTime : 2019-11-21 00:00:00
	 * endTime : 2019-11-22 00:00:00
	 * createTime : 1574301540003
	 * updateTime : 1574302777387
	 * createUserName : null
	 * auditUserName : 未知：null
	 * auditUserRole : null
	 * patrolUserName : 值班1
	 * patrolUserRole : ,
	 * dutyName : null
	 */

	private String id;
	private String name;
	private String patrolUser;
	private int state;
	private String content;
	private String createUser;
	private String auditUser;
	private String dutyId;
	private String auditNotes;
	private long auditTime;
	private String address;
	private String situation;
	private String conclusion;
	private long executionTime;
	private long finishTime;
	private String startTime;
	private String endTime;
	private long createTime;
	private long updateTime;
	private String createUserName;
	private String auditUserName;
	private String auditUserRole;
	private String patrolUserName;
	private String patrolUserRole;
	private String dutyName;


	public BaseListDataListBean getListInfoMaintain_Detail(boolean isEdit){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData(name,"巡检名称"));
		dataListBean.getList().add(new BaseListData(content,"巡检任务内容"));
		dataListBean.getList().add(new BaseListData(startTime,"巡检预计开始时间"));
		dataListBean.getList().add(new BaseListData(endTime,"巡检预计结束时间"));
		dataListBean.getList().add(new BaseListData(patrolUserName,"巡检人"));

		if(executionTime != 0 && (finishTime !=0)){
			dataListBean.getList().add(new BaseListData(TimeUtils.getDistanceTimeFutureHour(executionTime,finishTime),"维护时长"));
		}
		if(!isEdit){
			if(executionTime != 0){
				dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(executionTime,"yyyy-MM-dd HH:mm:ss"),"巡检时间"));
			}
			if(!TextUtils.isEmpty(situation)){
				dataListBean.getList().add(new BaseListData(address,"巡检地址"));
				dataListBean.getList().add(new BaseListData(situation,"巡检情况"));
				dataListBean.getList().add(new BaseListData(conclusion,"巡检结论"));
			}

		}

//		dataListBean.getList().add(new BaseListData("2h","维护时长"));
		return  dataListBean;
	}

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

	public String getPatrolUser() {
		return patrolUser;
	}

	public void setPatrolUser(String patrolUser) {
		this.patrolUser = patrolUser;
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

	public String getDutyId() {
		return dutyId;
	}

	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

	public String getAuditNotes() {
		return auditNotes;
	}

	public void setAuditNotes(String auditNotes) {
		this.auditNotes = auditNotes;
	}

	public long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(long auditTime) {
		this.auditTime = auditTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
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

	public String getPatrolUserName() {
		return patrolUserName;
	}

	public void setPatrolUserName(String patrolUserName) {
		this.patrolUserName = patrolUserName;
	}

	public String getPatrolUserRole() {
		return patrolUserRole;
	}

	public void setPatrolUserRole(String patrolUserRole) {
		this.patrolUserRole = patrolUserRole;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
}
