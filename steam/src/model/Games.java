package model;
//ÓÎÏ·Ä£ÐÍ²ã
public class Games {
	private int id ;
	private String name;
	private int categoryid;
	private int price;
	private String regtime;
	private int state;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public int getPrice() {
		return price;
	}
	public String getRegtime() {
		return regtime;
	}
	public int getState() {
		return state;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	public void setState(int state) {
		this.state = state;
	}
}
