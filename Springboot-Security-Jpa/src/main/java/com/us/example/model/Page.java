package com.us.example.model;
import java.util.ArrayList;
import java.util.List;


public class Page<T> {
    /**每页多少条记录数*/
	private int size = 10;
	/**当前页*/
	private int page = 1;
	/**总记录数*/
	private int pageSizeCount = 0;
	/**总页数*/
	private int pageCount = 0;
	/**起始下标*/
	private int start;
	/**排序，0为asc小-大  正序，1为desc大-小  倒序*/
	private int sort;
	/**返回泛型集合*/
	private List<T> pt = new ArrayList<>();
	/**接收泛型对象参数*/
	private T t;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSizeCount() {
        return pageSizeCount;
    }
    public void setPageSizeCount(int pageSizeCount) {
        this.pageSizeCount = pageSizeCount;
    }
    public int getPageCount() {
	    if(pageSizeCount % size == 0){
	        return pageSizeCount / size;
	    }else{
	        return pageSizeCount / size + 1;
	    }
	}
    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }
    public int getStart()
    {
        return size * (page - 1);
    }
    public void setStart(int start)
    {
        this.start = start;
    }
    public int getSort()
    {
        return sort;
    }
    public void setSort(int sort)
    {
        this.sort = sort;
    }
    public List<T> getPt() {
        return pt;
    }
    public void setPt(List<T> pt) {
        this.pt = pt;
    }
    public T getT()
    {
        return t;
    }
    public void setT(T t)
    {
        this.t = t;
    }
    @Override
    public String toString()
    {
        return "Page [size=" + size + ", page=" + page + ", pageSizeCount=" + pageSizeCount + ", pageCount="
                + pageCount + ", start=" + start + ", sort=" + sort + ", pt=" + pt + ", t=" + t + "]";
    }
}
