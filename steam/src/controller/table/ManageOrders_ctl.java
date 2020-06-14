package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;
//管理订单TableController
public class ManageOrders_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"序号","订单ID", "用户ID","用户账号", "游戏名称", "订单价格", "购买时间"};
	public  ManageOrders_ctl(Sql sql,Tables_sql tables_sql){
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getUserorders_sql().getAllOrdersNumber(0);
		colNumber=7;
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}		
	//获得表头内容
	public Object[][] getRowData(Pageinfo pageinfo,int id) {
			return  tables_sql.manageOrdersGetRowData(pageinfo, id); 
	}
	//更新数据
	public void startUpdata(JTable table,Pageinfo pageinfo,String id) {
		tables_sql.manageOrdersUpdata(table, pageinfo, id, tableInf, tableModel, columnNames2, rowData);
	}
	
}
