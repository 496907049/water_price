package com.ffapp.waterprice.bean;


import com.ffapp.waterprice.basis.Constants;

public abstract class BaseListBeanBc extends BasisBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int INIT_PAGE = 0;
	public static final int PAGE_SIZE = Constants.PAGE_SIZE;
	public static final String PAGE_SIZE_NAME = "pageSize";
	public static final String PAGE_NAME = "pageNo";


	/**
	 * @return the nextPage
	 */
	public int getNextPage() {
		return getCurrentPage()+1;
	}


	public void refresh(){
		setCurrent_page(INIT_PAGE);
	}
	
	public boolean hasNextPage() {
		return getCurrentPage() < getLast_page();
//		return true;
//		return getCurrentPage()*pageSize < totalCounts;
//		return page_info.hasNextPage();
	}

	public int getCurrentPage(){
		return current_page;
	}

	public void setListBeanData(BaseListBeanBc listbean){
		if(listbean == null)return;
		setTotal(listbean.getTotal());
		setPer_page(listbean.getPer_page());
		setCurrent_page(listbean.getCurrent_page());
		setLast_page(listbean.getLast_page());
	}



	private int total;
	private int per_page;
	private int last_page;
	private int current_page;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

}
