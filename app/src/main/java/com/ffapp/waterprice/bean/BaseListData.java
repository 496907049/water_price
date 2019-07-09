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
}
