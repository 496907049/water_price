package com.ffapp.waterprice.bean;

public class BasePageInfoBean extends BasisBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /**
     * page : 1
     * pagesize : 10
     * total_page : 103
     * count : 1025
     */

    private int page;
    private int pagesize;
    private int total_page;
    private int count;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean hasNextPage() {
        return page < total_page;
//		return getPage()*getPagesize() < getCount();
//		return true;
    }

    public int getNextPage() {
        return getPage() + 1;
    }

}
