package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.sql.Sql;
import model.table.Tables_sql;
import tools.Pageinfo;
//登陆记录TableController
public class ManageLogin_ctl extends Tables_ctl{
	//表头
	private Object[] columnNames2 = {"序号","登录ID", "用户ID", "用户账号", "登录时间", "登录IP"};
	//构造
	public ManageLogin_ctl(Sql sql,Tables_sql tables_sql) {
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getUserlogin_sql().getAllLoginNumber(0);
		colNumber=6;
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}		
	//取得行数据
	public Object[][] getRowData(Pageinfo pageinfo,int id) {
			return  tables_sql.manageLoginGetRowData(pageinfo, id);
	}
	//更新数据
	public void startUpdata(JTable table,Pageinfo pageinfo,String id) {
		tables_sql.manageLoginUpdata(table, pageinfo, id, tableInf, tableModel, columnNames2, rowData);
	}
}
