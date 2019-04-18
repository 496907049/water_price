package com.ffapp.baseapp.bean;

import android.app.Activity;

import com.ffapp.baseapp.util.MyUtils;

public class BannerListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * title : 测试打开URL
	 * open_type : browser
	 * open_content : http://baidu.com
	 * image_url : http://api.ukl.suncco.com/uploads/file/20180502/e4082e8a547ec7c2b5ea7da6c80faac8.jpg
	 */

	public static final  String TYPE_BROWSER = "browser",TYPE_PROJECTDETAIL = "project_detail",TYPE_NONE = "no_operation"
			,TYPE_INVITE = "invitation",TYPE_NEWSDETAIL = "article",TYPE_CANDY = "drop",TYPE_ACTIVITY_APP = "activity_app",TYPE_ACTIVITY_H5 = "activity_h5",TYPE_ACTIVITY_LIST = "activity_list";

	private String title;
	private String open_type;
	private String open_content;
	private String image_url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOpen_type() {
		return open_type;
	}

	public void setOpen_type(String open_type) {
		this.open_type = open_type;
	}

	public String getOpen_content() {
		return open_content;
	}

	public void setOpen_content(String open_content) {
		this.open_content = open_content;
	}

	public String getImage_url() {
		return image_url;
	}
	public String getImage_url75x40() {
		return MyUtils.getImageUrlBySize(image_url,750,400);
	}
	public String getImage_url75x30() {
		return MyUtils.getImageUrlBySize(image_url,750,300);
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public  void open(Activity mContext){
		switch (open_type){
			case TYPE_BROWSER:
//				WebViewX5Activity.toWebView(mContext,getOpen_content(),getTitle());
				break;
//			case TYPE_PROJECTDETAIL:
//				if(!LoginActivity.toLoginIfNotLogin(mContext,1))return;
//				ProjectInfoActivity.toDetail(mContext,getOpen_content());
//				break;
//			case TYPE_NONE:
//
//				break;
//			case TYPE_INVITE:
//				if(!LoginActivity.toLoginIfNotLogin(mContext,1))return;
//				ActivityTool.skipActivity(mContext, InviteNowActivity.class);
//				break;
//			case TYPE_NEWSDETAIL:
//				NewsDetailActivity.toDetail(mContext,getOpen_content());
//				break;
//			case TYPE_CANDY:
//				CandyDetailActivity.toDetail(mContext,getOpen_content());
//				break;
//			case TYPE_ACTIVITY_H5:
//				WebViewX5Activity.toWebView(mContext,getOpen_content(),getTitle());
//				break;
//			case TYPE_ACTIVITY_APP:
//				if(!LoginActivity.toLoginIfNotLogin(mContext,1))return;
//				CandyActivityActivity.toDetail(mContext,getOpen_content());
//				break;
//			case TYPE_ACTIVITY_LIST:
//				ActivityTool.skipActivity(mContext, CandyActivityList.class);
//				break;
		}

	}
}
