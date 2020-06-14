package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Model;
import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;
//商店TableController
public class Store_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"序号","游戏ID", "名称", "类型", "价格", "上架日期","销售数量"};
	public Store_ctl(Model model,Sql sql,Tables_sql tables_sql) {
		this.model=model;
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getGames_sql().getGamesNumber(0,"",false);	
		colNumber=7;
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}		
	public Object[][] getRowData(Pageinfo pageinfo,String name) {
		return tables_sql.storeGetRowData(pageinfo, name);
	}
	//更新数据
	public void startUpdata(JTable table,Pageinfo pageinfo,String name) {
		tables_sql.storeUpdata(table, pageinfo, name, tableInf, tableModel, columnNames2, rowData);
	}
	//购买游戏
	public int buy(int id) {
		return tables_sql.buy(id);
	}
	//输入的游戏ID是否"不"存在
	public boolean gameIdExist(String id) {
		return tables_sql.storeGameIdExist(id);
	}
}
