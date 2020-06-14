package controller;
import java.sql.Timestamp;
import model.Users;
import model.sql.Sql;
//登录Controller
public class Login_ctl {                                                                 										 //结果
	//开始检测输入的账号密码
	public int startTestInput(Sql sql,Users users) {
		return sql.getUserlogin_sql().testInput(users);
	}
	//开始登录
	public int startLogin(Sql sql,Users users,Timestamp curTime,String logip) {
		return sql.getUserlogin_sql().login(users, curTime, logip);
	}
}

