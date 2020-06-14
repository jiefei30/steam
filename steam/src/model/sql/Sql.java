package model.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import model.Model;
//承载所有sql语句类的媒介
public class Sql {
	private GetConn_sql getConn_sql= new GetConn_sql();
	private Category_sql category_sql;
	private Games_sql games_sql;
	private Security_sql security_sql;
	private Userlogin_sql userlogin_sql;
	private Userorders_sql userorders_sql;
	private Users_sql users_sql; 

	public Sql(Model model) {
		games_sql = new Games_sql(this,model.getGames());
		category_sql = new Category_sql(this);
		security_sql = new Security_sql(this);
		userlogin_sql = new Userlogin_sql(this);
		userorders_sql = new Userorders_sql(this);
		users_sql = new Users_sql(this,model.getUsers());
	}
	public GetConn_sql getGetConn_sql() {
		return getConn_sql;
	}
	public Category_sql getCategory_sql() {
		return category_sql;
	}
	public Games_sql getGames_sql() {
		return games_sql;
	}
	public Security_sql getSecurity_sql() {
		return security_sql;
	}
	public Userlogin_sql getUserlogin_sql() {
		return userlogin_sql;
	}
	public Userorders_sql getUserorders_sql() {
		return userorders_sql;
	}
	public Users_sql getUsers_sql() {
		return users_sql;
	}
	//返回多占位的结果的封装
	public Object returnRes(boolean next,String sql, Object... params) {
		PreparedStatement stmt=null;
		try {
			stmt = getConn_sql.getConn().prepareStatement(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet res=null;
		Object o=null;
		for(int i=0;i<params.length;i++) {
			try {
				stmt.setObject(i+1, params[i]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			res=stmt.executeQuery();
			if(next) {
				res.next();
				o=res.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(next) {
			return o;	
		}else return res;
	}

	//删除，更新，插入操作的封装
	public void write(String sql, Object... params) {
		PreparedStatement stmt=null;
		try {
			stmt = getConn_sql.getConn().prepareStatement(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		for(int i=0;i<params.length;i++) {
			try {
				stmt.setObject(i+1, params[i]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			stmt.execute();
			getConn_sql.getConn().commit();    //提交
			stmt.close();
		} catch (SQLException e) {
			try {
				getConn_sql.getConn().rollback();   //出现错误回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	//日期转换为时间戳
	public int dateToInt(String date){
		ResultSet res=null;
		int time=0;
		res=(ResultSet)returnRes(false,"SELECT UNIX_TIMESTAMP(?)",date);
		try {
			res.next();
			time=res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}
	//日期转换为时间戳
	public int dateToInt(Timestamp curTime){
		ResultSet res=null;
		int time=0;
		res=(ResultSet)returnRes(false,"SELECT UNIX_TIMESTAMP(?)",curTime);
		try {
			res.next();
			time=res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}
	//时间戳转为日期
	public String intToDate(String date){
		return (String)returnRes(true,"SELECT FROM_UNIXTIME(?,'%Y年%m月%d')",date);
	}
	//时间戳转为日期
	public String intToDate(long date){
		return (String)returnRes(true,"SELECT FROM_UNIXTIME(?,'%Y年%m月%d')",date);
	}
}
