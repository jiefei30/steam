package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controller.table.ManageGames_ctl;
import controller.table.ManageLogin_ctl;
import controller.table.ManageOrders_ctl;
import controller.table.ManageUsers_ctl;
import controller.table.MyGames_ctl;
import controller.table.Store_ctl;
import model.Model;
import model.sql.Sql;
import model.table.Tables_sql;
import tools.Constant;
import view.administrator.ManageGames;
import view.administrator.ManageLogin;
import view.administrator.ManageOrders;
import view.administrator.ManageUsers;
public class SteamJTP extends JFrame{
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();	
	private JTabbedPane jtab = new JTabbedPane(JTabbedPane.TOP);
	private Tables_sql tables_sql;
	//声明各各选项卡的view层
	private Information information;
	private Store store;
	private MyGames mygames;
	private ManageGames managegames;
	private ManageUsers manageusers;
	private ManageOrders manageorders;
	private ManageLogin managelogin;
	//声明各view层对应的controller层
	private Store_ctl store_ctl;
	private MyGames_ctl mygames_ctl;
	private ManageGames_ctl managegames_ctl;
	private ManageUsers_ctl manageusers_ctl;
	private ManageOrders_ctl manageorders_ctl;
	private ManageLogin_ctl  managelogin_ctl;
	
	public SteamJTP(Model model ,Sql sql) {
		this.setTitle("Steam");
		this.setIconImage(icon);
		this.setLocation(80,10); 
		this.setSize(1400, 800);
		this.setResizable(false);
		this.setDefaultCloseOperation(0);
		this.setVisible(true);
		tables_sql=new Tables_sql(model, sql);
		//实例化各controller层
		store_ctl = new Store_ctl(model, sql,tables_sql);
		mygames_ctl = new MyGames_ctl(model, sql,tables_sql);
		managegames_ctl = new ManageGames_ctl(sql,tables_sql);
		manageorders_ctl = new ManageOrders_ctl(sql,tables_sql);
		managelogin_ctl = new ManageLogin_ctl(sql,tables_sql);
		manageusers_ctl = new ManageUsers_ctl(sql,tables_sql);
		
		jtab.add("Welcome",new Welcome());
		information = new Information(this,model,sql);
		jtab.add("我的信息", information);
		
		if(model.getUsers().getIdentity()==1) {						//如果是玩家
		mygames=new MyGames(model,sql,mygames_ctl);
		store = new Store(model,sql,information,mygames,store_ctl);
		jtab.add("3A商店", store);
		jtab.add("我的游戏",mygames);
		jtab.add("特卖",new Sell());
		}
		else {
			managegames=new ManageGames(model,sql,this,managegames_ctl);
			manageusers=new ManageUsers(model, sql,manageusers_ctl);
			manageorders = new ManageOrders(sql,manageorders_ctl);
			managelogin = new ManageLogin(sql, managelogin_ctl,this);
			jtab.add("管理游戏",managegames);
			jtab.add("管理用户",manageusers);
			jtab.add("查看订单",manageorders);
			jtab.add("查看登录",managelogin);
		}
		this.add(jtab);
	}

}
//第一个选项卡页面是欢迎来到Steam
class Welcome extends JPanel {
	private Image bg = new ImageIcon(Constant.IMAGEPATH+"welcome.jpg").getImage();
	public Welcome() {	
	}
	public void paint(Graphics g) {
		g.setFont(new Font(null, Font.BOLD, 30));      // 字体样式
		g.setColor(Color.WHITE);
		g.drawImage(bg, 0, 0, this);
		g.drawString("Hi ~ ", 20, 40);
	}
}
//特卖
class Sell extends JPanel {
	private Image bg = new ImageIcon(Constant.IMAGEPATH+"sell.jpg").getImage();
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, this);
	}
}
