package com.ffapp.waterprice.bean;

import android.text.TextUtils;

public class UploadImgData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String path;
    public UploadImgData() {
    }
    public UploadImgData(String id, String path) {
        this.id = id;
        this.path = path;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public boolean isNeedpost(){
    	if(TextUtils.isEmpty(path))return false;
    	return !path.startsWith("http");
	}
}
