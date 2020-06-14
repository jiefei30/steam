package controller;

import model.Model;
import model.sql.Sql;
//修改游戏Controller
public class ModifyGames_ctl {
	private Sql sql;
	private Model model;
	public ModifyGames_ctl(Model model, Sql sql) {
		this.sql=sql;
		this.model=model;
	} 
	//开始改游戏
	public void startModify() {
		sql.getGames_sql().modifyGame(model.getGames());
	}
	//开始删游戏
	public int startDelet() {
		return sql.getGames_sql().fakeDelet();
	}
	//开始检测输入的价格
	public int startTestInput(String price,int state){
		return sql.getGames_sql().testInput(price, state);
	}
}
