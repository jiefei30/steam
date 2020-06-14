package model.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Model;
import model.sql.Sql;
import tools.Constant;
import tools.Pageinfo;
public class Tables_sql {
	protected Model model;									//声明模型	
	protected Sql sql;										//声明sql语句
	public Tables_sql(Model model,Sql sql) {
		this.model=model;
		this.sql=sql;
	}
	//设置当前页的行数并赋值
	public void updataRow(JTable table,Pageinfo pageinfo,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		if(pageinfo.getPage()*20>tableInf[0]) {											//如果是最后一页
			tableInf[1]=(tableInf[0]-(pageinfo.getPage()-1)*20);									
			tableModel.setRowCount(tableInf[1]);										//给表格模型设行
			table.setModel(tableModel);												//把表格模型赋给对应视图层的table
		}else {																		//如果是中间的页数
			tableInf[1]=20;
			tableModel.setRowCount(tableInf[1]);
			table.setModel(tableModel);
		}
		//开始给表赋行数据
		for(int row=0;row<tableInf[1];row++) {
			for(int col=0;col<tableInf[2];col++) {
				table.setValueAt(rowData[row][col], row, col);
			}
		}
	}
	//检测输入的ID是否正确
	public int testInputId(String id) {
		int res = 0 ; 
		if(id.length()==0) {
			res = 1;
		}else {
			if(!id.matches(Constant.REGEX_ID) || Integer.valueOf(id).intValue()<1 ) {					//ID一定是大于1的，再正则匹配
				res = 2;
			}
		}
		return res;
	}
	//检测页面的输入与判断
	public int testPage(String id,int page,int vaild) {
		int done = 3;
		if(testInputId(id)==0) {																		//先借用testInputId
			if(Integer.valueOf(id).intValue()>(int)(vaild/20+1)) {									//如果页数超过总页数
				done = 4;
			}else {
				if(Integer.valueOf(id).intValue()==page) {												//如果在当前页
					done = 5;
				}		
			}
			return done;
		}else {
			return testInputId(id);
		}
	}
	//判断输入的文字
	public int testSearch(String search) {
		int res = 0;
		if(search.length()==0) {
			res=1;
		}else {
			if(search.length()>15 || search.contains("%") || search.contains(" ")) {
				res=2;
			}
		}
		return res;
	}
	//查询ID是否存在
		public boolean isUserIdExist(int id) {
			boolean res=false;
			try {
				ResultSet result =(ResultSet)sql.returnRes(false,"select id from users" );
				//账号存在的话res是true
				while(result.next()) {		
					if(id== result.getInt(1)) {
						res = true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}

	//输入的游戏ID是否"不"存在(管理员)
	public boolean gameIdExist(String id) {
		boolean res=false;
		if(!sql.getGames_sql().gameIdExist(Integer.valueOf(id).intValue())) {
			res=true;
		}
		return res;
	}
	public void publicUpdata(JTable table,Pageinfo pageinfo,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableModel.setColumnIdentifiers(columnNames);
		updataRow(table, pageinfo, tableInf, tableModel, columnNames, rowData);
		table.updateUI();
	}
	/**
	 * 管理游戏
	 */
	public Object[][] manageGamesGetRowData(Pageinfo pageinfo,String name,boolean del) {	
		return sql.getGames_sql().getAllGamesData(pageinfo.getPage(),pageinfo.getCategoryid(),name,del);		
	}	
	public void manageGamesUpdata(JTable table,Pageinfo pageinfo,String name,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData,boolean del) {
		tableInf[0]=sql.getGames_sql().getGamesNumber(pageinfo.getCategoryid(),name,del);						//总行数												
		rowData=manageGamesGetRowData(pageinfo,name,del);	//获得行数据
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * 管理登录
	 */
	public Object[][] manageLoginGetRowData(Pageinfo pageinfo,int id) {
		return  sql.getUserlogin_sql().getAllLogin(pageinfo.getPage(),id); 
	}
	public void manageLoginUpdata(JTable table,Pageinfo pageinfo,String id,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableInf[0]=sql.getUserlogin_sql().getAllLoginNumber(Integer.valueOf(id).intValue());
		rowData=manageLoginGetRowData(pageinfo,Integer.valueOf(id).intValue());
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * 管理订单
	 */
	public Object[][] manageOrdersGetRowData(Pageinfo pageinfo,int id) {
		return  sql.getUserorders_sql().getAllOrders(pageinfo.getPage(),id); 
	}
	public void manageOrdersUpdata(JTable table,Pageinfo pageinfo,String id,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableInf[0]=sql.getUserorders_sql().getAllOrdersNumber(Integer.valueOf(id).intValue());
		rowData=manageOrdersGetRowData(pageinfo,Integer.valueOf(id).intValue());
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * 管理用户
	 */
	public Object[][] manageUsersGetRowData(Pageinfo pageinfo,String username) {
		return  sql.getUsers_sql().getAllUsers(pageinfo.getPage(),username); 
	}
	//冻结用户
	public void Freeze(int id) {
		int state = sql.getUsers_sql().getState(id);			//获得用户当前的状态
		if(state==1) state=0;
		else state=1;
		sql.getUsers_sql().freezeUser(id, state);
	}
	public void manageUsersUpdata(JTable table,Pageinfo pageinfo,String username,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {	
		tableInf[0]=sql.getUsers_sql().getAllUsersNumber(username);
		rowData=manageUsersGetRowData(pageinfo,username);
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * 我的游戏
	 */
	public Object[][] MyGamesGetRowData(Pageinfo pageinfo) {
		return sql.getUserorders_sql().getAllMyGames(sql,pageinfo.getPage(), model.getUsers().getId());
	}

	public void MyGamesUpdata(JTable table,Pageinfo pageinfo,String o,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableInf[0]=sql.getUserorders_sql().allGamesNumber(model.getUsers().getId());		//获得该页的行数
		rowData=MyGamesGetRowData(pageinfo);										//获得行数据
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * 商店
	 */
	public Object[][] storeGetRowData(Pageinfo pageinfo,String name) {
		return sql.getGames_sql().getAllGamesData(pageinfo.getPage(),pageinfo.getCategoryid(),name,false);
	}
	//更新数据
	public void storeUpdata(JTable table,Pageinfo pageinfo,String name,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableInf[0]=sql.getGames_sql().getGamesNumber(pageinfo.getCategoryid(),name,false);
		rowData=storeGetRowData(pageinfo,name);																		//获得行数据
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	//购买游戏
	public int buy(int id) {
		int res=0;
		if(sql.getGames_sql().testGame(id)) {													//检测该游戏是否下架或删除
			if(sql.getUserorders_sql().isUserHave(model.getUsers().getId(), id)) {				//是否已拥有
				if(model.getUsers().getMoney()>sql.getGames_sql().getPrice(id)) {				//余额是否充足
					//调用购买方法
					sql.getUserorders_sql().buy(model.getUsers().getId(), id, sql.getGames_sql().getPrice(id),model.getUsers().getMoney(), new Timestamp(System.currentTimeMillis()));
					model.getUsers().setMoney(model.getUsers().getMoney()-sql.getGames_sql().getPrice(id));
					res=2;
				}else res = 1;
			}
		}else res=3;
		return res;
	}
	//输入的游戏ID是否"不"存在
	public boolean storeGameIdExist(String id) {
		boolean res=false;
		if(!sql.getGames_sql().gameIdExist(Integer.valueOf(id).intValue())) {
			res=true;
		}
		return res;
	}
}
