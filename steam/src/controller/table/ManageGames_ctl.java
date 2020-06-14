package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;

//管理游戏TableController
public class ManageGames_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"序号","游戏ID", "名称", "类型", "价格", "上架日期","销售数量","状态"};			//表头
	private boolean del=false;																					//是否查看待删除的游戏
	public ManageGames_ctl(Sql sql,Tables_sql tables_sql) {
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getGames_sql().getGamesNumber(0,"",false);														//初始行数
		colNumber=8;																								//列数
		tableModel=new DefaultTableModel(0,colNumber);																//Table模型
		rowData= new  Object[vaild][colNumber];																		//行数据
		columnNames = columnNames2;																					//表头
		setTableInf();																								//设置表的基本属性
	}	
	public void setDel(boolean del) {
		this.del=del;
	}
	public Boolean getDel() {
		return del;
	}
	//获得表头信息
	public Object[][] getRowData(Pageinfo pageinfo,String name) {
		return tables_sql.manageGamesGetRowData(pageinfo, name,del);
	}	
	//更新table数据
	public void startUpdata(JTable table,Pageinfo pageinfo,String name) {
		tables_sql.manageGamesUpdata(table, pageinfo, name, tableInf, tableModel, columnNames2, rowData,del);
	}
	//恢复游戏状态为0
	public void recoverGame(int id) {
		sql.getGames_sql().recoverState(id);
	}
	//真正的删除
	public void realDelete(int id) {
		sql.getGames_sql().deletGame(id);
	}
}
