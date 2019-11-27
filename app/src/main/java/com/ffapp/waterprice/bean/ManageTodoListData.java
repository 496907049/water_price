package com.ffapp.waterprice.bean;


import my.TimeUtils;

public class ManageTodoListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final int TYPE_PATROL =   1,TYPE_MAINTAIN = 2;

	public static final int STATUS_WAIT = 3,STATUS_DOING = 4,STATUS_DONE = 5;

	/**
	 * createTime : 1574146203761
	 * id : 22502602c6a447b0a81fb1df22086c5c
	 * startTime : 1574100188000
	 * taskId : 1ab57e5442b642c3890b56167753eba4
	 * taskName : 超人
	 * taskUser : root
	 * type : 2
	 * updateTime : 1574146203761
	 */

	private long createTime;
	private String id;
	private long startTime;
	private String taskId;
	private String taskName;
	private String taskUser;
	private int type;
	private int taskState;
	private long updateTime;

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskUser() {
		return taskUser;
	}

	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}

	public int getType() {
		return type;
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

	public BaseListDataListBean getListInfo ( ){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData(taskName,"任务名称"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(startTime,"yyyy-MM-dd HH:mm:ss"),"开始时间"));
		dataListBean.getList().add(new BaseListData(taskUser,"执行人"));
			dataListBean.getList().add(new BaseListData(getTaskState_STR(),"执行状态"));
		return  dataListBean;
	}

	public int getTaskState() {
		return taskState;
	}

	public void setTaskState(int taskState) {
		this.taskState = taskState;
	}

	public static String getTaskState_STR(int status){
		switch (status){
			case STATUS_WAIT:
				return "待执行";
			case STATUS_DOING:
				return "执行中";
			case STATUS_DONE:
				return "已完成";
		}
		return "";
	}

	public String getTaskState_STR(){
		switch (taskState){
			case STATUS_WAIT:
				return "待执行";
			case STATUS_DOING:
				return "执行中";
			case STATUS_DONE:
				return "已完成";
		}
		return "";
	}
}
