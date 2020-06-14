package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;
//管理用户TableController
public class ManageUsers_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"序号","ID", "账号", "昵称","余额","性别","密保","注册时间","状态"};
	public  ManageUsers_ctl(Sql sql,Tables_sql tables_sql) {
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getUsers_sql().getAllUsersNumber("");
		colNumber=9;
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}		
	//获得表头内容
	public Object[][] getRowData(Pageinfo pageinfo,String username) {
		return  tables_sql.manageUsersGetRowData(pageinfo, username); 
	}
	//冻结用户
	public void StartFreeze(int id) {
		tables_sql.Freeze(id);
	}
	//更新数据
	public void startUpdata(JTable table,Pageinfo pageinfo,String username) {
		tables_sql.manageUsersUpdata(table, pageinfo, username, tableInf, tableModel, columnNames2, rowData);
	}
}
