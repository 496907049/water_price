package com.ffapp.waterprice.bean;

public class AlarmMsgListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * relateId : 38
	 * type : 1
	 */

	private String id;
	private int type;
	private String title;
	private String content;
	private String time;
	private boolean isRead;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

}
