package controller;
import java.sql.Timestamp;
import model.sql.Sql;
//注册Controller
public class Register_ctl {
	Sql sql;
	//构造
	public Register_ctl(Sql sql) {
		this.sql=sql;
	}
	//开始注册
	public void startRegister(String act,String pwd,String nam,int sex,int securityId,String securityAnswer,Timestamp curTime) {
		sql.getUsers_sql().register(act, pwd, nam, sex, securityId, securityAnswer, curTime);
	}
	//开始检验输入的内容
	public int startTestInput(String act,String nam,String pwd,String pwd2,String asw) {
		return sql.getUsers_sql().testInput(act, nam, pwd, pwd2, asw);
	}
	//开始检测输入的验证码
	public int startTestSecurityCode(String code) {
		return sql.getUsers_sql().testSecurityCode(code);
	}
}
