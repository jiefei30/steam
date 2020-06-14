package view.start;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import javax.swing.*;

import controller.Login_ctl;
import model.Model;
import model.sql.Sql;
import tools.Constant;
public class Login extends JFrame implements ActionListener{
	/**
	 * 登录页面
	 */
	private static final long serialVersionUID = 2967935310757063606L;
	private Image Login_bg=(new ImageIcon(Constant.IMAGEPATH+"login.jpg")).getImage();	   //登录背景
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JButton btn_reg =new JButton("注册");												
	private JButton btn_log =new JButton("登录");
	private JButton btn_ext =new JButton("退出");
	private JButton btn_abt =new JButton("？");	
	private JButton btn_pwd =new JButton("忘记密码");	
	private JButton[] btns = {btn_log,btn_reg,btn_ext,btn_abt,btn_pwd};
	private JTextArea about = new JTextArea("三月-三组-王明灿",1,1);		//关于
	private JTextField text_act =new JTextField(10);
	private JPasswordField text_pwd =new JPasswordField(10);	
	private JRadioButton btn_adm = new JRadioButton("管理员");
	private JRadioButton btn_ply = new JRadioButton("玩家",true);	
	private JComponent[] objects = {btn_reg,btn_log,btn_ext,btn_abt,btn_pwd,text_act,text_pwd,btn_adm,btn_ply};
	private InetAddress ip = null;
	private Model model=new Model();
	private Login_ctl login_ctl ;
	private Sql sql = new Sql(model);
	public Login() {
		this.setTitle("Steam登录");															//标题
		this.setIconImage(icon);															//图标
		this.setVisible(true);																//窗口可见
		this.setSize(600,383);							//大小
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);								//关闭窗口
		this.setLayout(null);																//取消布局	
		this.setResizable(false);
		login_ctl = new Login_ctl();

		for(int i=0;i<btns.length;i++) {
			btns[i].setSize(70, 30);
			btns[i].setLocation(300+i*100, 300);
			btns[i].addActionListener(this);
			this.add(btns[i]);
		}
		btn_abt.setBounds(535, 5, 50, 30);
		btn_pwd.setBounds(5, 5, 100, 30);

		text_act.setBounds(100,260,160,25);
		text_pwd.setBounds(100,302,160,25);
		this.add(text_act);
		this.add(text_pwd);

		ButtonGroup identity = new ButtonGroup();
		btn_adm.setBounds(400,260,70,25);
		btn_ply.setBounds(500,260,70,25);
		identity.add(btn_adm);
		identity.add(btn_ply);
		this.add(btn_adm);
		this.add(btn_ply);
	} 

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// 如果点击登录按钮 
		b:if (source==btn_log) 														
		{	
			//身份更改
			if(btn_adm.isSelected()) {
				model.getUsers().setIdentity(0); 
			}else model.getUsers().setIdentity(1); 

			model.getUsers().setAccount(text_act.getText());
			model.getUsers().setPassword(text_pwd.getText());
			switch(login_ctl.startTestInput(sql,model.getUsers())) {
			case 0 :break;
			case 1:
				JOptionPane.showConfirmDialog(this, "请填写完整","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2:
				JOptionPane.showConfirmDialog(this, "账号或密码格式有误","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			}

			try {
				ip=InetAddress.getLocalHost();														//把网络地址更改为本主机
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}

			String logip=ip.getHostAddress();														//把本主机ip赋值给logip

			switch(login_ctl.startLogin(sql,model.getUsers(),new Timestamp(System.currentTimeMillis()),logip)) {									//开始注入，并判断
			case 0 :
				JOptionPane.showConfirmDialog(this, "账号不存在","警告",JOptionPane.DEFAULT_OPTION);
				break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "登录成功","提示",JOptionPane.DEFAULT_OPTION);
				this.dispose();
				new Wait2(model,sql);
				break;
			case 2 :
				JOptionPane.showConfirmDialog(this, "密码错误","警告",JOptionPane.DEFAULT_OPTION);
				break;
			case 3 :
				JOptionPane.showConfirmDialog(this, "身份错误","警告",JOptionPane.DEFAULT_OPTION);
				break;
			case 4 :
				JOptionPane.showConfirmDialog(this, "该账号已被冻结","警告",JOptionPane.DEFAULT_OPTION);
				break;
			case 5 :
				JOptionPane.showConfirmDialog(this, "该账号已登录","警告",JOptionPane.DEFAULT_OPTION);
				break;
			}
		}	
		//注册
		if (source==btn_reg) 														
		{		


		}	
		//退出
		if (source==btn_ext) 														
		{		
			int n=JOptionPane.showConfirmDialog(this,"你确认要退出Steam吗","退出Steam",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) this.dispose();
		}
		//关于
		if (source==btn_abt) 														
		{		
			JOptionPane.showConfirmDialog(this, about,"关于",JOptionPane.DEFAULT_OPTION);
		}	
		//忘记密码
		if (source==btn_pwd) 														
		{		
			this.setEnabled(false);
			new FindPassword(this,sql);
		}	
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", 1, 18));
		g.drawImage(Login_bg, 0, 10, this);
		g.drawString("QQ号 : ",20,308);
		g.drawString("密码 : ",20,350);
		g.drawString("登陆方式 : ",300,308);
		for(int i=0;i<objects.length;i++) {
			objects[i].repaint();
		}

	}
}

