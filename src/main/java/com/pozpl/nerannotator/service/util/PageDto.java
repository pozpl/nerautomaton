package com.pozpl.nerannotator.service.util;

import java.util.Collections;
import java.util.List;

public class PageDto<T> {

	/**
	 * Current Page
	 */
	private int curPage = 0;

	/**
	 * Total Page
	 */
	private int totalPage = 0;
	/**
	 * total size
	 */
	private int size = 0;
	/**
	 * Has Pre page
	 */
	private boolean hasPre = false;
	/**
	 * Has next page
	 */
	private boolean hasNext = false;
	/**
	 * Current page content
	 */
	private List<T> content = null;

	public PageDto(){
	}

	/**
	 * Creates a paging context useful for listing out content
	 *
	 * @param curPage Page number that starts from 1
	 * @param totalNumberOfResults total number of results
	 * @param sizePerPage number in each page
	 * @param content the content for the current page
	 */
	public PageDto(int curPage, int totalNumberOfResults, int sizePerPage, List<T> content){
		this.setCurPage(curPage);
		this.setSize(totalNumberOfResults);

		int totalPage = 0;
		if( (totalNumberOfResults % sizePerPage) > 0){
			totalPage = totalNumberOfResults / sizePerPage + 1;
		}else{
			totalPage = totalNumberOfResults / sizePerPage;
		}
		this.setTotalPage(totalPage);

		this.setHasPre(curPage > 1);
		this.setHasNext(curPage < totalPage);
		this.setContent(content);
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isHasPre() {
		return hasPre;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}


	public static PageDto empty(){
		return new PageDto(1, 0, 1, Collections.emptyList());
	}

}
