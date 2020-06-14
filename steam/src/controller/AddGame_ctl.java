package controller;
import model.Model;
import model.sql.Sql;
//添加游戏View层所对应的controller
public class AddGame_ctl {
	private Model model;
	private Sql sql;
	//构造
	public AddGame_ctl(Model model,Sql sql) {
		this.model=model;
		this.sql=sql;
	}
	//开始检测输入的游戏
	public int startTestGame(String name,String price) {
		return sql.getGames_sql().testGame(name, price);
	}
	//调用sql.getGames_sql()中的添加方法，参数为game模型
	public void startAdd() {
		sql.getGames_sql().addGame(model.getGames());
	}
}
