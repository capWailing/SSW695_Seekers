package com.seekers.seekerback.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class PagedList implements Serializable {

	
	
	/**
	* @Fields serialVersionUID : TODO()
	*/ 
	private static final long serialVersionUID = 4919005678725317855L;

	private int total;
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	/*
	 * 当前页记录列表.
	 */
	private List<Map<String, Object>> rows;

	/**
	 * 当前页码, 0表示第一页.
	 */
	private int pageIndex;

	/**
	 * 每页记录数.
	 */
	private int pageSize;

	/**
	 * 总记录数.
	 */
	private int totalItemCount;

	/**
	 * 当前页的总记录数.
	 */
	private int thisPageTotal;

	/**
	 * 总页数.
	 */
	private int pageTotal;

	/**
	 * 当前页的上一页.
	 */
	private int prevPage;

	/**
	 * 当前页的下一页.
	 */
	private int nextPage;

	/**
	 * 导航的步进. (即导航条显示多少页)
	 */
	private int step;

	/**
	 * 导航的起始页.
	 */
	private int startPage;

	/**
	 * 导航的结束页.
	 */
	private int endPage;


	/**
	 * 
	 * @param pageItems
	 * @param totalItemCount
	 * @since anychem @ Jul 6, 2010
	 */
	public PagedList(List<Map<String, Object>> pageItems, int totalItemCount) {
		this(pageItems, 0, 20, totalItemCount);
	}

	/**
	 * 构造分页结果集. 其中将{@link #step}设置为10.
	 * 
	 * @see #PagedList(int, int, int, List, int)
	 */
	public PagedList(List<Map<String, Object>> pageItems, int pageIndex, int pageSize,
			int totalItemCount) {
		this(pageIndex, pageSize, totalItemCount, pageItems, 10);
	}

	/**
	 * 构造分页结果集.
	 * 
	 * @param pageItems
	 *            the .
	 */
	public PagedList(List<Map<String, Object>> pageItems) {
		this.pageIndex = 0;
		this.pageSize = pageItems.size();
		this.totalItemCount = pageItems.size();
		this.rows = pageItems;
		this.thisPageTotal = pageItems.size();
	}

	/**
	 * 构造分页结果集.
	 * 
	 * @param pageIndex
	 *            the {@link #pageIndex}.
	 * @param pageSize
	 *            the {@link #pageSize}.
	 * @param totalItemCount
	 *            the {@link #totalItemCount}.
	 * @param pageItems
	 *            the .
	 * @param step
	 *            the {@link #step}.
	 */
	public PagedList(int pageIndex, int pageSize, int totalItemCount,
			List<Map<String, Object>> pageItems, int step) {
		this.pageIndex = (pageIndex < 0) ? 0 : pageIndex;
		this.pageSize = (pageSize <= 0) ? 5 : pageSize;
		this.totalItemCount = totalItemCount;
		this.rows = pageItems;
		this.thisPageTotal = (pageItems == null) ? 0 : pageItems.size();

		computePageIndex(step);
	}

	/**
	 * 计算页码导航的各个值.
	 * 
	 * @param stepValue
	 *            页码导航显示多少页.
	 */
	private void computePageIndex(int stepValue) {
		if (totalItemCount <= 0) {
			pageTotal = 0;
		} else {
			pageTotal = (totalItemCount / pageSize)
					+ ((totalItemCount % pageSize == 0) ? 0 : 1);
		}
		prevPage = (pageIndex == 0) ? 0 : pageIndex - 1;
		nextPage = (pageIndex >= pageTotal - 1) ? pageTotal - 1 : pageIndex + 1;
		step = stepValue;
		startPage = (pageIndex / step) * step;
		endPage = (startPage + step >= pageTotal) ? pageTotal - 1 : startPage
				+ step;
	}

	/**
	 * 返回当前页的第index条记录.
	 */
	public Map<String, Object> get(int index) {
		return rows.get(index);
	}



	/**
	 * @return total count of items
	 */
	public int getTotalItemCount() {
		return totalItemCount;
	}

	/**
	 * @return total count of pages
	 */
	public int getTotalPageCount() {
		return getPageTotal();
	}

	/**
	 * @return Returns the pageTotal.
	 */
	public int getPageTotal() {
		return pageTotal;
	}

	/**
	 * 返回第一页(首页)的页码.
	 */
	public int getFirstPageNo() {
		int firstPageNo = 0;
		return firstPageNo;
	}

	/**
	 * 返回最后一页(末页)的页码.
	 */
	public int getLastPageNo() {
		return pageTotal - 1;
	}

	/**
	 * @return true if this is the first page
	 */
	public boolean isFirstPage() {
		return isFirstPage(getPageIndex());
	}

	/**
	 * @return true if this is the last page
	 */
	public boolean isLastPage() {
		return isLastPage(getPageIndex());
	}

	/**
	 * @param page
	 * @return true if the page is the first page
	 */
	public boolean isFirstPage(int page) {
		return page <= 0;
	}

	/**
	 * @param page
	 * @return true if the page is the last page
	 */
	public boolean isLastPage(int page) {
		return page >= getTotalPageCount() - 1;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the thisPageTotal.
	 */
	public int getThisPageTotal() {
		return thisPageTotal;
	}

	/**
	 * @return step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * @return startPage
	 */
	public int getStartPage() {
		return startPage;
	}

	/**
	 * @return endPage
	 */
	public int getEndPage() {
		return endPage;
	}

	/**
	 * @return prevPage
	 */
	public int getPrevPage() {
		return prevPage;
	}

	/**
	 * @return nextPage
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PagedList [pageIndex=").append(pageIndex);
		builder.append(", total=").append(totalItemCount);
		builder.append(", thisPageTotal=").append(thisPageTotal);
		if (rows != null) {
			builder.append("; pageItems=").append(rows);
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * 获取当前结果集的记录数
	 * 
	 * @return
	 */
	public int size() {
		return (rows == null) ? 0 : rows.size();
	}

	/**
	 * 获取当前结果集的开始记录数
	 * 
	 */
	protected int getStartIndex() {
		return ((pageIndex > 1 ? pageIndex : 1) - 1) * this.pageSize + 1;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	

	
}
