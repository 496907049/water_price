package com.ffapp.waterprice.bean;


import my.TimeUtils;

public class ManagePatrolListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * auditTime : 1574298164023
	 * content : 计划233
	 * createTime : 1574298124210
	 * createUser : root
	 * dutyId : 62ca0145429a400585a67be514afc582
	 * endTime : 1574524800000
	 * id : 009ccdacb43043f39fd194319c6d2bf7
	 * name : 计划233
	 * patrolUser : asdasds
	 * patrolUserName : 值班1
	 * startTime : 1574438400000
	 * state : 3
	 * updateTime : 1574298164046
	 */

	private long auditTime;
	private String content;
	private long createTime;
	private String createUser;
	private String dutyId;
	private long endTime;
	private String id;
	private String name;
	private String patrolUser;
	private String patrolUserName;
	private long startTime;
	private int state;
	private long updateTime;


	public long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(long auditTime) {
		this.auditTime = auditTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDutyId() {
		return dutyId;
	}

	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
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

	public String getPatrolUserName() {
		return patrolUserName;
	}

	public void setPatrolUserName(String patrolUserName) {
		this.patrolUserName = patrolUserName;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}


	public BaseListDataListBean getListInfo( ){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData(name,"任务名称"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(startTime,"yyyy-MM-dd HH:mm:ss"),"开始时间"));
		dataListBean.getList().add(new BaseListData(patrolUserName,"执行人"));
		dataListBean.getList().add(new BaseListData(ManageTodoListData.getTaskState_STR(state),"执行状态"));
		return  dataListBean;
	}


}
