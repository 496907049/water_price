package com.ffapp.baseapp.bean;

public class BaseListData extends BasisBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String appid;
	private String authkey;

	private boolean isCheck = false;
	private int resid = -1;
	private int resBg = -1;

	public BaseListData() {
	}
	public BaseListData(String id, String name) {
		this.id = id;
		this.name = name;
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

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean check) {
		isCheck = check;
	}
}
