package model.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
//和用户订单有关的sql语句
public class Userorders_sql {
	private Object[][] rowData1;
	private Object[][] rowData2;
	private Sql sql;
	public Userorders_sql(Sql sql) {
		this.sql=sql;
	}
	//购买游戏的时间
	public String getBuyTime(int userid,int gameid) {	
		return (String)sql.returnRes(true, "select FROM_UNIXTIME(buytime,'%Y年%m月%d') from userorders where userid = ? and gameid = ?", userid,gameid);
	}
	//该游戏销售的个数
	public int sellNumber(int gameid) {
		int res = 0;
		try {
			ResultSet result = (ResultSet)sql.returnRes(false,"select count(*) from userorders where gameid = ?", gameid);
			if(result!=null) {
				result.next();
				res = result.getInt(1);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return res;
	}
	//查询玩家是否拥有该游戏
	public boolean isUserHave(int userid,int gameid) {
		boolean res =true;
		int haveGameId;
		try {
			ResultSet result = (ResultSet)sql.returnRes(false,"select gameid from userorders where userid = ?", userid);
			b:while(result.next()) {
				haveGameId=result.getInt("gameid");
				if(gameid==haveGameId) {
					res=false;
					break b;
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return res;
	}
	//购买游戏
	public void buy(int userid,int gameid,int price,int userMoney,Timestamp curTime) {
			sql.write("insert into userorders(userid,gameid,price,buytime) values(?,?,?,?)", userid,gameid,price,sql.dateToInt(curTime));
			sql.write("update users set money = ? where id = ?", (userMoney-price),userid);	
	}
	//查询我的所有游戏个数
	public int allGamesNumber(int id) {
		int number=0;
		try {	
			ResultSet result = (ResultSet)sql.returnRes(false, "SELECT count(*) from userorders where userid = ?", id);
			result.next();
			number =result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return number ;
	}
	//获取我的所有游戏（我的游戏表）
	public Object[][] getAllMyGames(Sql sql,int page,int userid){
		ResultSet result;
		int number=(page-1)*20+1,row=0,vaild=allGamesNumber(userid);
		if(vaild<20) {
			rowData1 = new Object[vaild][7];
		}else {
			rowData1 = new Object[20][7];
		}
		try {
			if(page*20>vaild) {
				result=(ResultSet)sql.returnRes(false, "select gameid from userorders where userid= ? limit ?,?", userid,(page-1)*20,(vaild-(page-1)*20));
			}else 	result=(ResultSet)sql.returnRes(false, "select gameid from userorders where userid= ? limit ?,?", userid,(page-1)*20,20);
			while(result.next()) {
				rowData1[row][0] = number;
				rowData1[row][1] = result.getInt(1);
				rowData1[row][2] = sql.getGames_sql().getName(result.getInt(1));
				rowData1[row][3] = sql.getCategory_sql().getCategory(sql.getGames_sql().getCategoryId(result.getInt(1)));
				rowData1[row][4] = sql.getGames_sql().getPrice(result.getInt(1));
				rowData1[row][5] = getBuyTime(userid,result.getInt(1));
				if(sql.getGames_sql().getState(result.getInt(1))==1) {
					rowData1[row][6] = "可玩";
				}else {
					rowData1[row][6] = "暂时下架";
				}
				row++;
				number++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return rowData1;
	}
	//获取全部订单数量
	public int getAllOrdersNumber(int id) {
		ResultSet res;
		int number=0;
		if(id==0) res=(ResultSet)sql.returnRes(false,"select count(*) from userorders ") ;
		else res=(ResultSet)sql.returnRes(false,"select count(*) from userorders where userid = ?",id) ; 
		try {
			res.next();
			number=res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return number ;
	}
	//获取全部订单内容(管理员)（订单表）
	public Object[][] getAllOrders(int page,int id){
		ResultSet res;
		rowData2 = new Object[getAllOrdersNumber(id)][7];
		int number=(page-1)*20+1,ordersnumber = getAllOrdersNumber(id),row=0;
		if(id==0) {
			if(page*20>ordersnumber) {
				res = (ResultSet)sql.returnRes(false, "select * from userorders limit ?,?", (page-1)*20,(ordersnumber-(page-1)*20));
			}else res = (ResultSet)sql.returnRes(false, "select * from userorders limit ?,?", (page-1)*20,20);
		}else {
			if(page*20>ordersnumber) {
				res = (ResultSet)sql.returnRes(false,"select * from userorders where userid = ? limit ?,? ", id,(page-1)*20,(ordersnumber-(page-1)*20));
			}else res = (ResultSet)sql.returnRes(false,"select * from userorders where userid = ? limit ?,? ", id,(page-1)*20,20);
		}
			try {
				while(res.next()) {	
					rowData2[row][0] = number ;
					rowData2[row][1] = res.getInt(1);
					rowData2[row][2] = res.getInt(2);
					rowData2[row][3] = sql.getUsers_sql().getAccount(res.getInt(2));
					rowData2[row][4] = sql.getGames_sql().getName(res.getInt(3));
					rowData2[row][5] = res.getInt(4);
					rowData2[row][6] = sql.intToDate(res.getString(5));
					row++;
					number++;
				}
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		
		return rowData2;
	}
}
