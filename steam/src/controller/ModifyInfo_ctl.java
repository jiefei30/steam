package controller;

import model.Model;
import model.sql.Sql;
//修改信息Controller
public class ModifyInfo_ctl {
	private Model model;
	private Sql sql;
	public ModifyInfo_ctl(Model model, Sql sql) {
		this.model=model;
		this.sql=sql;
	}
	//开始改信息
	public void startModify() {
		sql.getUsers_sql().modifyInfo(model.getUsers());
	}
	//开始检测输入名称和性别
	public int testInput(String nam,int sex) {
		return sql.getUsers_sql().testInput(nam, sex);
	}
	//开始发送验证码
	public void startSend() {
		sql.getUsers_sql().send();
	}
	//开始检测输入的旧密码和新密码
	public int startTestReplacePwd(String oldPwd,String newPwd) {
		return sql.getUsers_sql().testReplacePwd(oldPwd, newPwd);
	}
	//开始检测输入的验证码
	public int startTestCode(String yzm) {
		return sql.getUsers_sql().testCode(yzm);
	}
	//开始检测输入的密保
	public int startTestASW(String asw) {
		return sql.getUsers_sql().testASW(asw);
	}
	
}
