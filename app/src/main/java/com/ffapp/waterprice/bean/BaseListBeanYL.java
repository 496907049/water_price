package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.ffapp.waterprice.basis.Constants;

public abstract class BaseListBeanYL extends BasisBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int INIT_PAGE = -1;
	public static final int PAGE_SIZE = Constants.PAGE_SIZE;
	public static final String PAGE_SIZE_NAME = "pageSize";
	public static final String PAGE_NAME = "pageNo";


	/**
	 * @return the nextPage
	 */
	public int getNextPage() {
		return getCurrent_page()+1;
	}


	public void refresh(){
		setCurrent_page(INIT_PAGE);
	}
	
	public boolean hasNextPage() {
//		return getCurrentPage() < getLast_page();
//		return true;
//		LogUtil.i("hasNextPage-->"+getCurrent_page()*PAGE_SIZE+"----一共>"+getRecordsTotal());
		return (getCurrent_page()+1)*PAGE_SIZE < getRecordsTotal();
//		return page_info.hasNextPage();
	}

//	public int getCurrentPage(){
//		return current_page;
//	}

	public void setListBeanData(BaseListBeanYL listbean){
		if(listbean == null)return;
		setRecordsTotal(listbean.getRecordsTotal());
//		setPer_page(listbean.getPer_page());
//		setCurrent_page(listbean.getCurrent_page());
		current_page++;
//		setLast_page(listbean.getLast_page());
	}



	private int per_page;
	private int last_page;


	@JSONField(name="count")
	private int recordsTotal;
	@JSONField(name="page")
	private int current_page;

	@JSONField(name="count")
	public int getRecordsTotal() {
		return recordsTotal;
	}

	@JSONField(name="count")
	public void setRecordsTotal(int total) {
		this.recordsTotal = total;
	}

	public int getPer_page() {
		return per_page;
	}

	public void setPer_page(int per_page) {
		this.per_page = per_page;
	}

	public int getLast_page() {
		return last_page;
	}

	public void setLast_page(int last_page) {
		this.last_page = last_page;
	}

	@JSONField(name="page")
	public int getCurrent_page() {
		return current_page;
	}
	public int getCurrentPage() {
		return current_page;
	}

	@JSONField(name="page")
	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

}
