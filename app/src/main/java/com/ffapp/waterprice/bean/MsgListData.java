package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.JSONObject;

import my.LogUtil;

public class MsgListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * article_id : 37
	 * title : 2019年3月14日17:31:20
	 * create_time : 2019-03-14 17:31:23
	 * is_read : 0
	 * content : 未为群二无群二无群
	 */

	private int article_id;
	private String title;
	private String create_time;
	private boolean is_read;
	private String content;

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public boolean getIs_read() {
		return is_read;
	}

	public void setIs_read(boolean is_read) {
		this.is_read = is_read;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getJsonReadInfo(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(getArticle_id()+"","1");

		LogUtil.i(""+jsonObject.toJSONString());
		return jsonObject.toJSONString();
	}
}
