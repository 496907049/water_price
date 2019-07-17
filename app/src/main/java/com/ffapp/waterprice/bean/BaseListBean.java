package com.ffapp.waterprice.bean;


import com.ffapp.waterprice.basis.Constants;

public abstract class BaseListBean extends BasisBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int INIT_PAGE = 0;
	public static final int PAGE_SIZE = Constants.PAGE_SIZE;
	public static final String PAGE_SIZE_NAME = "pageSize";
	public static final String PAGE_NAME = "pageNo";

	public BasePageInfoBean getPage_info() {
		return page_info;
	}

	public void setPage_info(BasePageInfoBean page_info) {
		this.page_info = page_info;
	}

	private BasePageInfoBean page_info = new BasePageInfoBean();

	/**
	 * @return the nextPage
	 */
	public int getNextPage() {
		return page_info.getNextPage();
	}


	public void refresh(){
		page_info.setPage(INIT_PAGE);
		page_info.setTotal_page(0);
		page_info.setCount(0);
	}
	
	public boolean hasNextPage() {
//		return getCurrentPage()+1 < getPages();
//		return true;
//		return getCurrentPage()*pageSize < totalCounts;
		return page_info.hasNextPage();
	}

	public int getCurrentPage(){
		return page_info.getPage();
	}

	public void setListBeanData(BaseListBean listbean){
		if(listbean == null)return;
		page_info = listbean.getPage_info();
	}
}
