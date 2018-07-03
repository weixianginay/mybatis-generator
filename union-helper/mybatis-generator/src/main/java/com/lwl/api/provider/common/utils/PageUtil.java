//package com.lwl.api.provider.common.utils;
//
///**
// * 分页辅助类
// * @author DYJ
// *
// */
//public class PageUtil {
//	//起始页
//	private int startPage;
//	//每页的大小
//	private int pageSize;
//	//起始行
//	private int start;
//	//总记录数
//	private int totalRecords;
//	//结束行
//	private int end;
//	//总页数
//	private int totalPages;
//
//
//	public PageUtil(int startPage, int pageSize, int totalRecords) {
//		super();
//		this.pageSize = pageSize;
//		this.startPage = startPage;
//		this.totalRecords = totalRecords;
//		//开始计算总页数
//		this.totalPages = totalRecords / pageSize;
//		if(totalRecords % pageSize > 0) {
//			this.totalPages++;
//		}
//		//计算起始行
//		if(this.startPage > 0) {
//			this.start = (this.startPage - 1) * pageSize;
//		} else {
//			this.start = 0;
//		}
//		//计算最后一行
//		this.end = this.start + pageSize;
//		if(this.end > totalRecords) {
//			this.end = totalRecords;
//		}
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public int getStart() {
//		return start;
//	}
//
//	public int getTotalRecords() {
//		return totalRecords;
//	}
//
//	public int getEnd() {
//		return end;
//	}
//
//	public int getTotalPages() {
//		return totalPages;
//	}
//
//	public int getStartPage() {
//		return startPage;
//	}
//
//
//}
