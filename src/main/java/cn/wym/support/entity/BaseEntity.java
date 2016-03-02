package cn.wym.support.entity;

public class BaseEntity {
	
	private String id;
	
	private boolean isDel = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}
	
	
	
	
	
}
