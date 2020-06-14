package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Information_ctl;
import model.Model;
import model.sql.Sql;
import tools.Constant;
import view.start.Wait1;
public class Information extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1192155896058762437L;
	private Image Mine_bg=(new ImageIcon(Constant.IMAGEPATH+"mine.jpg")).getImage();	
	private JButton btn_chag = new JButton("修改信息");	
	private JButton btn_lot = new JButton("切换账号");		
	private JButton btn_exit = new JButton("退出");	
	private JButton btn_rcg = new JButton("充值");
	private JButton[] btns= {btn_chag,btn_rcg,btn_lot,btn_exit};
	private JFrame steamJFrame;
	private Sql sql;
	private Model model;
	private Information_ctl information_ctl;
	private String[] paintText= {"昵称：","账号：","性别","身份：","Money："};
	public Information(JFrame jf,Model model,Sql sql) {
		this.setLayout(null);
		this.setVisible(true);
		steamJFrame=jf;
		this.sql=sql;
		this.model=model;
		information_ctl = new Information_ctl(sql);

		for(int i=0;i<btns.length;i++) {
			btns[i].setBounds(200+i*300, 600, 100, 50);
			this.add(btns[i]);
			btns[i].addActionListener(this);
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", 1, 25));
		g.drawImage(Mine_bg, 0, 0, this);
		for(int i=0;i<paintText.length;i++) {
			g.drawString(paintText[i], 200, 100+i*100);
		}
		int x=400,y=100;
		g.drawString(model.getUsers().getUsername(), x, y);
		g.drawString(model.getUsers().getAccount(), x, y+100);
		if(model.getUsers().getSex()==1) {
			g.drawString("男", x, y+200);
		}else g.drawString("女", x, y+200);
		if(model.getUsers().getIdentity()==1) {
			g.drawString("玩家", x, y+300);
		}else g.drawString("管理员", x, y+300);
		g.drawString(""+model.getUsers().getMoney(), 400, y+400);
		for(int i=0;i<btns.length;i++) {
			btns[i].repaint();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//切换账号
		if (source==btn_lot) {	
			int n=JOptionPane.showConfirmDialog(this,"你确认要切换账号吗","切换账号",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) { 
				try {
					sql.getUsers_sql().setLogging(model.getUsers().getId(), 0);
					sql.getGetConn_sql().getConn().close();											//断开连接
					System.out.println("数据库连接已断开");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				sql = null;
				model = null;
				steamJFrame.dispose();
				new Wait1();
			}
		}	
		//退出
		if (source==btn_exit) 														
		{		
			int n=JOptionPane.showConfirmDialog(this,"你确认要退出Steam吗","退出Steam",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) {
				try {
					sql.getUsers_sql().setLogging(model.getUsers().getId(), 0);
					sql.getGetConn_sql().getConn().close();											//断开连接
					System.out.println("数据库连接已断开");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				sql = null;
				model = null;
				System.exit(0);
			}
		}
		//充值
		if (source==btn_rcg) {
			steamJFrame.setEnabled(false);
			new AddMoney(steamJFrame, this,information_ctl,model.getUsers());
		}
		//修改信息
		if (source==btn_chag) {
			steamJFrame.setEnabled(false);
			new ModifyInfo(steamJFrame,model,sql);
		}
	}
}
