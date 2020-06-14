package controller;
import model.Users;
import model.sql.Sql;
//信息界面Controller
public class Information_ctl {
	private Sql sql;
	public Information_ctl(Sql sql) {
		this.sql=sql;
	}
	//充值控制方法
	public void Recharge(Users users,int money) {
		//将数据库中的余额修改
		sql.getUsers_sql().recharge(users,money);
	}

}
