package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Model;
import model.sql.Sql;
import model.table.Tables_sql;
import tools.Pageinfo;
/*
 * 该类是所有有table界面视图层的controller的父类，为抽象类，其中包含了有table界面视图层的controller大部分成员变量与方法
 * 
 */
public abstract class Tables_ctl {
	protected Model model;									//声明模型	
	protected Sql sql;										//声明sql语句
	protected int vaild,rowNumber,colNumber;				//该页的查询到的有效行数，行数（媒介），列数
	protected Object[] columnNames;							//表头
	protected Object[][] rowData;							//表格数据
	protected DefaultTableModel tableModel;					//表格模型
	protected Tables_sql tables_sql ;
	protected int[] tableInf=new int[3];
	//设置表格基本属性
	public void setTableInf() {
		tableInf[0]=vaild;
		tableInf[1]=rowNumber;
		tableInf[2]=colNumber;
	}
	//返回表头
	public Object[] getColumnNames() {						
		return columnNames;
	}
	//返回该页查询到的有效行数
	public int getVaild() {
		return tableInf[0];
	}
	
	//返回改table的表格模型
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	//设置当前页的行数并赋值
	public void updataRow(JTable table,Pageinfo pageinfo) {
		tables_sql.updataRow(table, pageinfo,tableInf, tableModel, columnNames, rowData);
	}
	//检测输入的ID是否正确
	public int testInputId(String id) {
		return tables_sql.testInputId(id);
	}
	//检测页面的输入与判断
	public int testPage(String id,int page) {
		return tables_sql.testPage(id, page,getVaild());
	}
	//判断输入的文字
	public int testSearch(String search) {
		return tables_sql.testSearch(search);
	}
	//输入的用户ID是否存在(管理员)
	public boolean userIdExist(String id) {
		return tables_sql.isUserIdExist(Integer.valueOf(id).intValue());
	}
	//输入的游戏ID是否"不"存在(管理员)
	public boolean gameIdExist(String id) {
		return tables_sql.gameIdExist(id);
	}
	//用于每个控制层的updata方法体不一样，视图层父类Viewer又要用到该Controller中的这个方法，所以将该方法声明为抽象
	public abstract void startUpdata(JTable table,Pageinfo pageinfo,String str_search) ;

}


