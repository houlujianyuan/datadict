package com.qk365.datadict.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;


public class Paging<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer pageNumber;

    private Integer pageSize;

    @JSONField(name="totalPage")
    private long pageCount;

    @JSONField(name="totalRow")
    private long totalCount;

    private List<T> itemList;
    
    public Paging(){
    	
    }
    

    public Paging(int pageNumber, int pageSize, long totalCount, List<T> itemList) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.itemList = itemList;
        if (pageSize != 0) {
            if (totalCount % pageSize == 0) {
                pageCount = totalCount / pageSize;
            } else {
                pageCount = totalCount / pageSize + 1;
            }
        }
    }
    
    

   	  
    

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getPageCount() {
        return pageCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public boolean hasPrevPage() {
        return pageNumber > 1 && pageNumber <= pageCount;
    }

    public boolean hasNextPage() {
        return pageNumber < pageCount;
    }

    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    public boolean isLastPage() {
        return pageNumber == pageCount;
    }

    public List<T> getItemList() {
        return itemList;
    }
}


