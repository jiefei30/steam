package model;
//用户模型层
public class Users {
	private int id;
	private String account;
	private String username;
	private String password;
	private int identity;
	private int money ;
	private int sex;
	private int securityId;
	private String securityAnswer;
	private int state;
	private int logging;
	public int getLogging() {
		return logging;
	}
	public void setLogging(int logging) {
		this.logging = logging;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public String getAccount() {
		return account;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getIdentity() {
		return identity;
	}
	public int getMoney() {
		return money;
	}
	public int getSex() {
		return sex;
	}
	public int getSecurityId() {
		return securityId;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public void setSecurityId(int securityId) {
		this.securityId = securityId;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	
}