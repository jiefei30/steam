package model.sql;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import model.Users;
import tools.Constant;
import tools.SendEmail;
//和用户有关的sql语句
public class Users_sql { 
	private Sql sql;
	private SendEmail sendemail;
	private Object[][] rowData;
	private Users users;
	public Users_sql(Sql sql,Users users) {
		this.sql=sql;
		sendemail = new SendEmail(sql);
		this.users=users;
	}
	//获取ID
	public int getId(String act) {
		return (int)sql.returnRes(true, "select id from users where account = ?", act);
	}
	//获取账号信息
	public String getAccount(int id) {
		return (String)sql.returnRes(true, "select account from users where id = ?", id);
	}
	//获取密码
	public String getPassword(String act) {
		return (String)sql.returnRes(true, "select password from users where account = ?", act);
	}
	//获取昵称
	public String getName(String act) {
		return (String)sql.returnRes(true, "select username from users where account = ?", act);
	}
	//获取余额
	public int getMoney(int id) {
		return (int)sql.returnRes(true,"select money from users where id = ?", id);
	}
	//获取密保类型
	public int getSecurityId(String act) {
		return (int)sql.returnRes(true,"select securityid from users where account = ?", act);
	}
	//获取密保答案
	public String getSecurityAnswer(String act) {
		return (String)sql.returnRes(true, "select securityanswer from users where account = ?", act);
	}
	//获取身份
	public int getIdentity(String act) {	
		boolean player=(boolean)sql.returnRes(true,  "select identity from users where account = ?", act);
		if(player) {
			return 1;
		}else return 0;
	}
	//获取性别
	public int getSex(String act) {
		boolean boy=(boolean)sql.returnRes(true,"select sex from users where account = ?", act);
		if(boy) {
			return 1;
		}else return 0;
	}
	//获取状态
	public int getState(int id) {
		boolean enable=(boolean)sql.returnRes(true,"select state from users where id = ?", id);
		if(enable) {
			return 1;
		}else return 0;
	}
	//获取登录状态
	public int getLogging(int id) {
		boolean logging=(boolean)sql.returnRes(true, "select logging from users where id = ?", id);
		if(logging) {
			return 1;
		}else return 0;
	}
	//设置用户的登录状态
	public void setLogging(int id,int state) {
		sql.write("UPDATE users SET logging=? where id = ?", state,id);
	}
	//充值
	public void recharge(Users users,int money) {
		sql.write("UPDATE users set money = ? where id = ?", (users.getMoney()+money),users.getId());
		//将当前model中的user的余额修改
		users.setMoney(getMoney(users.getId()));
	}

	//查询是否有该玩家账号
	public boolean testAccountExist(String act) {
		boolean res=false;
		try {
			ResultSet result =(ResultSet)sql.returnRes(false,"select account from users" );
			//账号存在的话res是true
			while(result.next()) {		
				if(act.equals( result.getString(1))) {
					res = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	//MD5加密方法
	public String md5(String pwd) {
		return (String)sql.returnRes(true, "select md5(?)",pwd);
	}
	//注册成功后开始向数据库写入数据
	public void register(String act,String pwd,String nam,int sex,int securityId,String securityAnswer,Timestamp curTime) {
		sql.write("insert into users(account,password,username,money,securityid,securityanswer,sex,regtime) values(?,?,?,?,?,?,?,?)",act,
				md5(pwd),nam,500,securityId,securityAnswer,sex,sql.dateToInt(curTime));
	}
	//注册时检测注册输入的内容
	public int testInput(String act,String nam,String pwd,String pwd2,String asw) {
		int res = 0;
		if(act.length()==0 || nam.length()==0 || pwd.length()==0 || pwd2.length()==0 || asw.length() == 0){
			res=1;
		}else {
			if(nam.length()>10 || pwd.length()>12 || pwd2.length()>12|| asw.length() >10 ){
				res = 2;
			}else {
				if(nam.contains(" ") || asw.contains(" ") || nam.contains("%") || asw.contains("%")) {
					res = 6;
				}else {
					if(sql.getUsers_sql().testRepatition(act)) {               //是否重复
						res = 5;
					}else {
						if(!act.matches(Constant.REGEX_NUM)) {
							res=3;
						}else {
							if(!pwd.matches(Constant.REGEX_NUM)) {
								res=4;
							}else {
								if(!pwd.equals(pwd2)) {
									res=7;
								}
							}
						}
					}
				}
			}
		}
		if(res==0) {
			sendemail.makeSecurityCode();
			try {
				sendemail.sendSecurityCode(act+"@qq.com");
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
		return res ;
	}
	//(注册)注册时检测检测输入的验证码
	public int testSecurityCode(String code) {
		int res = 0;
		if(code.length()==0) {
			res = 1;
		}else {
			if(code.length()>6) {
				res = 2;
			}else {
				if(!code.equals(sendemail.getSecurityCode())) {  //判断验证码是否正确
					res=3;
				}
			}
		}
		return res;
	}
	//检测账号是否重复
	public boolean testRepatition(String act) {
		boolean res = false;
		try {
			ResultSet result = (ResultSet)sql.returnRes(false, "select account from users");
			while(result.next()) {
				if(result.getString("account").equals(act)) {
					res =  true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	//修改信息
	public void modifyInfo(Users users) {
		sql.write("UPDATE users SET username=?,password=?,sex=?,securityid=?,securityanswer=? WHERE id=?",  users.getUsername(),md5(users.getPassword()),
				users.getSex(),users.getSecurityId(),users.getSecurityAnswer(),users.getId());
	}
	//重置密码
	public void replacePassword(String account,String pwd) {
		sql.write("UPDATE users SET password=? WHERE account =?", md5(pwd), account);
	}
	//获取全部用户人员数量
	public int getAllUsersNumber(String username) {
		ResultSet res;
		int number=0;
		try {
			res=(ResultSet)sql.returnRes(false,"select count(*) from users where identity = '1' and username like '%"+username+"%'");
			res.next();
			number = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return number ;
	}
	//获取全部用户内容
	public Object[][] getAllUsers(int page,String username){
		ResultSet res;
		rowData= new Object[getAllUsersNumber(username)][9];
		int number=(page-1)*20+1,usersnumber = getAllUsersNumber(username),row=0;
		if(page*20>usersnumber) {
			res=(ResultSet)sql.returnRes(false,"select * from users where identity = '1' and username like '%"+username+"%' limit ?,?",(page-1)*20, (usersnumber-(page-1)*20));
		}else res=(ResultSet)sql.returnRes(false,"select * from users where identity = '1' and username like '%"+username+"%' limit ?,?", (page-1)*20,20);
		try {
			while(res.next()) {	
				rowData[row][0] = number ;
				rowData[row][1] = res.getInt(1);
				rowData[row][2] = res.getLong(3);		
				rowData[row][3] =res.getString(5);
				rowData[row][4] = res.getInt(6);
				if(res.getInt(7)==1) {
					rowData[row][5] = "男";
				}else rowData[row][5] = "女";
				rowData[row][6] = sql.getSecurity_sql().getSecurity(res.getInt(8)); 
				rowData[row][7] = sql.intToDate( res.getString(10));
				if(res.getInt(11)==1) {
					rowData[row][8] = "可登录";
				}else {
					rowData[row][8] = "已被冻结";
				}
				row++;
				number++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rowData;
	}
	//管理员冻结用户
	public void freezeUser(int id,int state) {
		sql.write("UPDATE users SET state = ? where id = ?", state,id);
	}
	//(找回密码)核对密保
	public int Find(String act,int securityId,String securityAnswer) {
		int res = 0;
		b:if(testAccountExist(act)) {							//先判断账号是否存在
			if(securityId != getSecurityId(act)) {				//再判断该账号是否选择了该密保
				res=1;
				break b;
			}
			if(securityAnswer.equals(getSecurityAnswer(act))) {	//匹配密保答案
				res =2 ;
			}else res = 3;
		}
		return res;
	}
	//(找回密码)检测输入的重置密码格式
	public int testInput(String replace,String replace2) {
		int res = 0; 
		if(replace.length()==0 || replace2.length()==0) {
			res = 1;
		}else {
			if(!replace.matches(Constant.REGEX_NUM)  || !replace2.matches(Constant.REGEX_NUM)) {
				res = 2;
			}else {
				if(!replace.equals(replace2)) {
					res = 3;
				}
			}
		}
		return res;
	}
	//(找回密码)检测输入的密保答案格式
	public int testAnswer(String asw) {
		int res = 0; 
		if(asw.length()==0) {
			res = 1;
		}else {
			if(asw.length()>10) {
				res = 2;
			}
		}
		return res;
	}
	//(找回密码)忘记密保前是否输入了账号，并开始发送邮件
	public int testInputAct(String act) {
		int res = 0;
		if(act.length()==0) {
			res = 1;
		}else {
			if(!act.matches(Constant.REGEX_NUM)) {
				res = 2;
			}else {
				if(!testAccountExist(act)) {
					res = 3;
				}
			}
		}
		if(res==0) {											
			sendemail.makeSecurityCode();								//先让sendemail生成一个验证码
			try {
				sendemail.sendSecurityCode(act+"@qq.com");				//传qq邮箱
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	//(修改信息)检测输入名称和性别
	public int testInput(String nam,int sex) {
		int res = 0; 
		//先判断是否做出了修改
		if(nam.equals(users.getUsername())&&sex==users.getSex()) {
			res = 1;
		}else {
			if(nam.length()==0 ) {
				res = 2;
			}else {
				if(nam.contains(" ") ) {					//是否含有空格
					res = 4;
				}else {
					if(nam.length()>10) {
						res = 3;
					}
				}
			}
		}
		return res;
	}
	//(修改信息)发送验证码
	public void send() {
		sendemail.makeSecurityCode();
		try {
			sendemail.sendSecurityCode(users.getAccount()+"@qq.com");
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	//（修改信息）检测输入的旧密码和新密码
	public int testReplacePwd(String oldPwd,String newPwd) {
		int res=0;
		if(oldPwd.length()==0 || newPwd.length()==0 ) {
			res = 1;
		}else {
			if(!oldPwd.matches(Constant.REGEX_NUM) || !newPwd.matches(Constant.REGEX_NUM)) {
				res = 2;
			}else {
				if(!oldPwd.equals(users.getPassword())) {
					res=3;
				}
			}
		}
		return res;
	}
	//(修改信息)检测输入的验证码
	public int testCode(String yzm) {
		int res = 0;
		if(yzm.length()==0) {
			res = 1;
		}else {
			if(!yzm.equals(sendemail.getSecurityCode())) {
				res = 2;
			}
		}
		return res;
	}
	//（修改信息）开始检测输入的密保
	public int testASW(String asw) {
		int res = 0;
		if(asw.length()==0) {
			res = 1;
		}else {
			if(asw.length()>10) {
				res = 2;
			}else {
				if(asw.contains(" ")) {
					res = 3;
				}
			}
		}
		return res;
	}
}
