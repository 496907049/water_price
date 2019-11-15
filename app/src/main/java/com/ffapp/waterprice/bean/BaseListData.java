package com.ffapp.waterprice.bean;

public class BaseListData extends BasisBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String tenant;
	private String accessKey;

	private String value;

	private String account;

	private boolean isClick = false;

	private boolean isCheck = false;
	private int resid = -1;
	private int resBg = -1;

	public BaseListData() {
	}
	public BaseListData(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public BaseListData( String name, String value,int resid) {
		this.name = name;
		this.value = value;
		this.resid = resid;
	}

	public BaseListData(String id, String name, int resid, int resBg) {
		this.id = id;
		this.name = name;
		this.resid = resid;
		this.resBg = resBg;
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

	public int getResid() {
		return resid;
	}

	public void setResid(int resid) {
		this.resid = resid;
	}

    public int getResBg() {
        return resBg;
    }

    public void setResBg(int resBg) {
        this.resBg = resBg;
    }


	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean check) {
		isCheck = check;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean click) {
		isClick = click;
	}

	public BaseListDataListBean getListInfoTodo(){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData("1号路线巡检任务","任务名称"));
		dataListBean.getList().add(new BaseListData("2019-9-12","开始时间"));
		dataListBean.getList().add(new BaseListData("张三","执行人"));
		dataListBean.getList().add(new BaseListData("等待执行","执行状态"));
		return  dataListBean;
	}
	public BaseListDataListBean getListInfoTodo_Detail(){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData("1号路线巡检任务","任务名称"));
		dataListBean.getList().add(new BaseListData(" 4号机井站、12号机井站、13号机井站巡检","任务内容"));
		dataListBean.getList().add(new BaseListData("张珊珊","执行人员"));
		dataListBean.getList().add(new BaseListData(" 巡检人员","职务"));
		dataListBean.getList().add(new BaseListData("苟主任","审核"));
		dataListBean.getList().add(new BaseListData("  班长","职务"));
		dataListBean.getList().add(new BaseListData("2019-9-6  17:00","任务下发时间"));
		dataListBean.getList().add(new BaseListData("2019-9-12","预计执行时间"));
		dataListBean.getList().add(new BaseListData("2019-10-12","预计结束时间"));
		return  dataListBean;
	}

	public BaseListDataListBean getListInfoPatrol_Detail(){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData("123","巡检名称"));
		dataListBean.getList().add(new BaseListData(" 4号机井站、12号机井站、13号机井站巡检","巡检任务内容"));
		dataListBean.getList().add(new BaseListData("2019-07-19 ","巡检预计开始时间"));
		dataListBean.getList().add(new BaseListData(" 2019-09-19 ","巡检预计结束时间"));
		dataListBean.getList().add(new BaseListData("苟主任","巡检人"));
		dataListBean.getList().add(new BaseListData("  2019-07-19 15:53","巡检起始时间"));
		dataListBean.getList().add(new BaseListData("1 ","巡检记录"));
		dataListBean.getList().add(new BaseListData("2019-07-19 18:53","巡检时间"));
		dataListBean.getList().add(new BaseListData("巡检地址123","巡检地址"));
		dataListBean.getList().add(new BaseListData("A站点，A渠系","巡检情况"));
		return  dataListBean;
	}

	public BaseListDataListBean getListInfoMaintain(){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData("1号路线巡检任务","维护任务"));
		dataListBean.getList().add(new BaseListData("2019-9-12","开始时间"));
		dataListBean.getList().add(new BaseListData("张三","执行人"));
		dataListBean.getList().add(new BaseListData("软件维护","维护类型"));
		dataListBean.getList().add(new BaseListData("等待执行","执行状态"));
		return  dataListBean;
	}

	public BaseListDataListBean getListInfoMaintain_Detail(){
		BaseListDataListBean dataListBean = new BaseListDataListBean();
		dataListBean.getList().add(new BaseListData("123","维护名称"));
		dataListBean.getList().add(new BaseListData("软件运维","维护类型"));
		dataListBean.getList().add(new BaseListData(" 4号机井站、12号机井站、13号机井站巡检","维护任务内容"));
		dataListBean.getList().add(new BaseListData("2019-07-19 ~09-19 ","维护时间周期"));
		dataListBean.getList().add(new BaseListData(" 是多少 ","维护人"));
		dataListBean.getList().add(new BaseListData("2","维护次数"));
		dataListBean.getList().add(new BaseListData("  2019-07-19 15:53","维护时间"));
		dataListBean.getList().add(new BaseListData("维护内容12123","维护内容"));
		dataListBean.getList().add(new BaseListData("2019-07-19 15:53","结束时间"));
		dataListBean.getList().add(new BaseListData("2h","维护时长"));
		return  dataListBean;
	}
}
