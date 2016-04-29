package com.base.util;


public class Page {

	private int totalItems;			//һ��������
	private int totalPages;			//һ������ҳ
	private int itemsPerPage;		//ÿһҳ������
	private int currentPage;		//��ǰҳ
	private int beginItem;			//��ǰҳ����ʼ��
	private Boolean hasNextPage;	//�Ƿ�����һҳ
	private Boolean hasPrePage;		//�Ƿ���ǰһҳ

	public Page(int totalItems, int totalPages, int itemsPerPage,
			int currentPage, int beginItem, Boolean hasNextPage,
			Boolean hasPrePage) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.itemsPerPage = itemsPerPage;
		this.currentPage = currentPage;
		this.beginItem = beginItem;
		this.hasNextPage = hasNextPage;
		this.hasPrePage = hasPrePage;
	}
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getBeginItem() {
		return beginItem;
	}
	public void setBeginItem(int beginItem) {
		this.beginItem = beginItem;
	}
	public Boolean getHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public Boolean getHasPrePage() {
		return hasPrePage;
	}
	public void setHasPrePage(Boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public int getTotalPages() {
		return totalPages == 0 ? 1 : totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	


}
