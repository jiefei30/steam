package controller;
import model.sql.Sql;
//找回密码Controller
public class FindPassword_ctl {
	private Sql sql;	
	public FindPassword_ctl(Sql sql) {
		this.sql=sql;
	}
	//开始核对密保
	public int startFind(String act,int securityId,String securityAnswer) {	
		return sql.getUsers_sql().Find(act, securityId, securityAnswer);
	}
	//开始重置密码
	public void startReplace(String act,String pwd) {
		sql.getUsers_sql().replacePassword(act, pwd);
	}
	//开始检测输入的重置密码格式
	public int startTestInput(String replace,String replace2) {
		return sql.getUsers_sql().testInput(replace, replace2);
	}
	//开始检测输入的密保答案格式
	public int startTestAnswer(String asw) {
		return sql.getUsers_sql().testAnswer(asw);
	}
	//开始检测忘记密保前是否输入了账号
	public int startTestInputAct(String act) {
		return sql.getUsers_sql().testInputAct(act);
	}
	//开始检测输入的验证码
	public int startTestSecurityCode(String code) {
		return sql.getUsers_sql().testSecurityCode(code);
	}
}
