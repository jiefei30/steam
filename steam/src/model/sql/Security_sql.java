package model.sql;
//和密保表有关的sql语句
public class Security_sql {
	private Sql sql;
	public Security_sql(Sql sql) {
		this.sql=sql;
	}
	//获取某个密保内容
	public String getSecurity(int id) {	
		return (String)sql.returnRes(true, "select securityquestion from security where id = ?",id);
	}
}
