package controller.table;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Model;
import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;
//我的游戏TableController
public class MyGames_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"序号","游戏ID", "名称", "类型", "价格", "购买时间","状态"};		//表头
	//构造
	public MyGames_ctl(Model model,Sql sql,Tables_sql tables_sql) {
		this.model=model;
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getUserorders_sql().allGamesNumber(model.getUsers().getId());				//根据订单查询购买的游戏数量
		colNumber=7;																			//列数
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}
	//更新查到的行数
	public void updateVaild() {
		vaild = sql.getUserorders_sql().allGamesNumber(model.getUsers().getId());		
	}
	//返回行数据
	public Object[][] getRowData(Pageinfo pageinfo) {
		return tables_sql.MyGamesGetRowData(pageinfo);
	}
	//更新数据
	public void startUpdata(JTable table,Pageinfo pageinfo,String o) {
		tables_sql.MyGamesUpdata(table, pageinfo, o, tableInf, tableModel, columnNames2, rowData);									//刷新该表格
	}
}
