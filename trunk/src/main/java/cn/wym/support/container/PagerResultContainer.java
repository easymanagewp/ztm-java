package cn.wym.support.container;

import java.util.ArrayList;
import java.util.List;

import cn.wym.support.entity.BaseEntity;

public class PagerResultContainer {
	
	private final List<BaseEntity> result = new ArrayList<BaseEntity>();
	
	private long totalRows;
	private int totalPage;
	public long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<BaseEntity> getResult() {
		return result;
	}
	public void setResult(List<? extends BaseEntity> result2){
		this.result.addAll(result2);
	}
	
	
	
}
