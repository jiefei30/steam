package model.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
//和类型表有关的sql语句
public class Category_sql {
	private String[] allCategories;
	private Sql sql;
	public Category_sql(Sql sql) {
		this.sql=sql;
	}
	//获得类型的个数
	public int getCategoryNumber() {
		ResultSet res;
		int a =0;
		try {
			res = (ResultSet)sql.returnRes(false,"select count(*) from category");
			res.next();
			a=res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a ;
	}
	//获得某个类型内容
	public String getCategory(int id) {
		return (String)sql.returnRes(true, "select categories from category where id = ?",id);
	}
	//获取所有类型组成字符串数组
	public String[] getAllCategories() {
		allCategories=new String[getCategoryNumber()];
		int t=0;
		try {
			ResultSet res=(ResultSet)sql.returnRes(false,"select categories from category");
			while(res.next()) {
				allCategories[t]=res.getString("categories");
				t++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return allCategories;
	}
}
