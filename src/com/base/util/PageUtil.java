package com.base.util;

public class PageUtil {
	
	public static Page createPage(int totalItems, int itemsPerPage, int currentPage) {
		currentPage = getCurrentPage(currentPage);
		int totalPages = getTotalPages(totalItems, itemsPerPage);
		int beginItem = getBeginItem(itemsPerPage, currentPage);
		Boolean hasNextPage = hasNextPage(totalPages, currentPage);
		Boolean hasPrePage = hasPrePage(totalPages, currentPage);
		Page page = new Page(totalItems, totalPages, itemsPerPage, 
				currentPage, beginItem, hasNextPage, hasPrePage);
		return page;
	}
	
	public static int getCurrentPage(int currentPage) {
		return currentPage == 0 ? 1 : currentPage;
	}
	
	public static int getTotalPages(int totalItems, int itemsPerPage) {
		if(totalItems % itemsPerPage == 0) return (int) (totalItems / itemsPerPage);
		else return (int) (totalItems / itemsPerPage + 1);
	}
	
	public static int getBeginItem(int itemsPerPage, int currentPage) {
		return itemsPerPage * (currentPage-1);			//当前页是从第一页算起的
	}
	
	public static Boolean hasNextPage(int totalPages, int currentPage) {
		return currentPage == totalPages || totalPages == 0 ? false : true;
	}
	
	public static Boolean hasPrePage(int totalPages, int currentPage) {
		return currentPage == 1 || totalPages == 0 ? false : true;
	}


}
