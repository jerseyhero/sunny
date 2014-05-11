package com.sunny.core.base.web;

import java.util.List;

public class Page<T> {
	private int pageSize;//每页大小

	private int pageNumber;//第几页
	
	
	public boolean isAutoCount() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setTotalCount(long totalCount) {
		// TODO Auto-generated method stub
		
	}

	public void setResult(List result) {
		// TODO Auto-generated method stub
		
	}

	public int getFirst() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPageSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
