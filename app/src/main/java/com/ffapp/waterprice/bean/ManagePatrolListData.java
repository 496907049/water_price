package com.ffapp.waterprice.bean;


import my.TimeUtils;

public class ManagePatrolListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id : 9359579b-f2a8-19c0-f781-2ed207b430f0
	 * patrol_name : test-hal
	 * duty_person_id : 3b3694e9-1402-9840-6340-2ed73ee9f4d9
	 * duty_route : ces-巡检
	 * begin_time : 1573723926
	 * end_time : 1574352000
	 * admin_id : 1
	 * create_time : 2019-11-14 17:32:11
	 * update_time : 2019-11-14 17:32:11
	 * is_delete : 0
	 * recycle_time : 0
	 * duty_person_name : 廖浩
	 */

	private String id;
	private String patrol_name;
	private String duty_person_id;
	private String duty_route;
	private long begin_time;
	private long end_time;
	private int admin_id;
	private String create_time;
	private String update_time;
	private int is_delete;
	private long recycle_time;
	private String duty_person_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatrol_name() {
		return patrol_name;
	}

	public void setPatrol_name(String patrol_name) {
		this.patrol_name = patrol_name;
	}

	public String getDuty_person_id() {
		return duty_person_id;
	}

	public void setDuty_person_id(String duty_person_id) {
		this.duty_person_id = duty_person_id;
	}

	public String getDuty_route() {
		return duty_route;
	}

	public void setDuty_route(String duty_route) {
		this.duty_route = duty_route;
	}

	public long getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(long begin_time) {
		this.begin_time = begin_time;
	}

	public long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(int end_time) {
		this.end_time = end_time;
	}

	public int getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public long getRecycle_time() {
		return recycle_time;
	}

	public void setRecycle_time(long recycle_time) {
		this.recycle_time = recycle_time;
	}

	public String getDuty_person_name() {
		return duty_person_name;
	}

	public void setDuty_person_name(String duty_person_name) {
		this.duty_person_name = duty_person_name;
	}

	public BaseListDataListBean getListInfo(){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData(patrol_name,"任务名称"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(begin_time*1000,"yyyy-MM-dd HH:mm:ss"),"开始时间"));
		dataListBean.getList().add(new BaseListData(duty_person_name,"执行人"));
		dataListBean.getList().add(new BaseListData("等待执行","执行状态"));
		return  dataListBean;
	}

	public BaseListDataListBean getListInfoTODO ( boolean isExcute,boolean isPost){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData(patrol_name,"任务名称"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(begin_time*1000,"yyyy-MM-dd HH:mm:ss"),"开始时间"));
		dataListBean.getList().add(new BaseListData(duty_person_name,"执行人"));
		if(!isExcute){
			dataListBean.getList().add(new BaseListData("等待执行","执行状态"));
		}else  if(!isPost){
			dataListBean.getList().add(new BaseListData("正在执行","执行状态"));
		}else {
			dataListBean.getList().add(new BaseListData("已执行","执行中"));
		}
		return  dataListBean;
	}
	public BaseListDataListBean getListInfoPatrol_Detail(){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData(patrol_name,"巡检名称"));
		dataListBean.getList().add(new BaseListData(duty_route,"巡检任务内容"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(begin_time*1000,"yyyy-MM-dd HH:mm:ss"),"巡检预计开始时间"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(end_time*1000,"yyyy-MM-dd HH:mm:ss"),"巡检预计结束时间"));
		dataListBean.getList().add(new BaseListData(duty_person_name,"巡检人"));
		dataListBean.getList().add(new BaseListData(TimeUtils.getTimeLongToStrByFormat(begin_time*1000,"yyyy-MM-dd HH:mm:ss"),"巡检起始时间"));
		dataListBean.getList().add(new BaseListData("1 ","巡检记录"));
		dataListBean.getList().add(new BaseListData("2019-07-19 18:53","巡检时间"));
		dataListBean.getList().add(new BaseListData("巡检地址123","巡检地址"));
		dataListBean.getList().add(new BaseListData("A站点，A渠系","巡检情况"));
		return  dataListBean;
	}
}
