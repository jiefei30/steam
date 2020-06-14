package tools;
//每个选项卡的信息，哪个页面和类型id
public class Pageinfo {
	private int page=1,categoryid=0;
	public int getPage() {
		return page;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

}
