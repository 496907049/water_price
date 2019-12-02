package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.annotation.JSONField;

import my.TimeUtils;

public class ManageMaintainListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * content : 踩到底
	 * createTime : 1574146203754
	 * createUser : root
	 * declaration : 巡检人员
	 * endTime : 1574276405000
	 * executionTime : 1574241391
	 * faultId : 1
	 * finishTime : 1574241174
	 * id : 1ab57e5442b642c3890b56167753eba4
	 * maintenanceUser : root
	 * maintenanceUserName : 超人
	 * name : 等等
	 * situation : OK了啦
	 * startTime : 1574100188000
	 * state : 4
	 * type : 1
	 * updateTime : 1574241391783
	 */

	private String content;
	private long createTime;
	private String createUser;
	private String declaration;
	private long endTime;
	private long executionTime;
	private String faultId;
	private long finishTime;
	private String id;
	private String maintenanceUser;
	private String maintenanceUserName;
	private String name;
	private String situation;
	private long startTime;
	private int state;
	private int type;
	private long updateTime;

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

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public String getFaultId() {
		return faultId;
	}

	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaintenanceUser() {
		return maintenanceUser;
	}

	public void setMaintenanceUser(String maintenanceUser) {
		this.maintenanceUser = maintenanceUser;
	}

	public String getMaintenanceUserName() {
		return maintenanceUserName;
	}

	public void setMaintenanceUserName(String maintenanceUserName) {
		this.maintenanceUserName = maintenanceUserName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
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

	public int getType() {
		return type;
	}

	public String getTYPE_STR(){
		switch (type){
			case ManageMaintainDetailBean.TYPE_SOFT:
				return "软件";
			case ManageMaintainDetailBean.TYPE_MACHINE:
				return "硬件";
		}
		return "";
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@JSONField(serialize = false)
	public BaseListDataListBean getListInfoMaintain(){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData(name,"维护名称"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(startTime,"yyyy-MM-dd HH:mm:ss"),"开始时间"));

		dataListBean.getList().add(new BaseListData(declaration,"执行人"));
		dataListBean.getList().add(new BaseListData(getTYPE_STR(),"维护类型"));
		dataListBean.getList().add(new BaseListData(ManageTodoListData.getTaskState_STR(state),"执行状态"));
		return  dataListBean;
	}
}
