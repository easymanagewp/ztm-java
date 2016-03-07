package cn.wym.support.container;

import java.util.LinkedList;
import java.util.List;

public class QueryContainer {

	public static final String ORDER_TYPE_DESC = OrderContainer.ORDER_TYPE_DESC;
	public static final String ORDER_TYPE_ASC = OrderContainer.ORDER_TYPE_ASC;
	
	private final List<ConditionContainer> conditions = new LinkedList<ConditionContainer>();
	
	private final List<OrderContainer> orders = new LinkedList<OrderContainer>();
	
	private int pageSize = 10;		// 默认请求10条数据		
	private int pageNum = 1;		// 默认获取第一页数据

	public List<ConditionContainer> getConditions() {
		return conditions;
	}

	public List<OrderContainer> getOrders() {
		return orders;
	}
	
	public void addOrder(String field){
		this.addOrder(field,null);
	} 
	
	public void addOrder(String field,String orderType){
		this.orders.add(new OrderContainer(field,orderType));
	}
	
	public void addCondition(String condition){
		this.conditions.add(new ConditionContainer(condition));
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public boolean hasCondition(){
		return this.getConditions().size() > 0;
	}
	
	public boolean hasOrder(){
		return this.getOrders().size() > 0;
	}
	
	
	
}
