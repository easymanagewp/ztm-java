package cn.wym.support.container;

public class OrderContainer {
	public static final String ORDER_TYPE_DESC = "desc";
	public static final String ORDER_TYPE_ASC = "asc";
	
	public OrderContainer(String field) {
		this(field,ORDER_TYPE_ASC);
	}
	
	public OrderContainer(String field,String orderType) {
		this.setField(field);
		this.setOrderType(orderType);
	}
	
	private String field;
	
	private String orderType;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
