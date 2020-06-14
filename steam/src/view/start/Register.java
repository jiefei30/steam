package view.start;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import javax.swing.*;

import controller.Register_ctl;
import model.sql.Sql;
import tools.Constant;
public class Register extends JFrame implements ActionListener{
	/**
	 * 注册窗口
	 */
	private static final long serialVersionUID = 4148628129312677952L;
	private Image register_bg=(new ImageIcon(Constant.IMAGEPATH+"register.jpg")).getImage();	  
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JButton btn_cfm =new JButton("发送验证码");												
	private JButton btn_ext =new JButton("退出");
	private JButton btn_test =new JButton("验证");
	private JButton[] btns = {btn_cfm,btn_ext,btn_test};
	private JRadioButton btn_boy = new JRadioButton("男",true);
	private JRadioButton btn_girl = new JRadioButton("女");
	private JRadioButton btn_food = new JRadioButton("水果",true);
	private JRadioButton btn_animal = new JRadioButton("动物");
	private JRadioButton btn_people = new JRadioButton("明星");	
	private JRadioButton[] rbtns = {btn_boy,btn_girl,btn_food,btn_animal,btn_people};
	private JTextField text_act =new JTextField(10);
	private JTextField text_nam =new JTextField(10);
	private JTextField text_asw =new JTextField(10);
	private JTextField text_test =new JTextField(10);
	private JTextField[] texts = {text_nam,text_act,text_asw,text_test};
	private JPasswordField text_pwd =new JPasswordField(10);
	private JPasswordField text_pwd2 =new JPasswordField(10);
	private JPasswordField[] pfs= {text_pwd,text_pwd2};
	private JComponent[][] objects = {btns,rbtns,texts,pfs};
	private JTextArea about = new JTextArea("昵称和密保答案在10字符以内"
			+"\n"+"昵称和密保答案不要含空格或%"											
			+"\n"+ "账号是QQ号，密码为6~12个数字。"
			+"\n"+ "填完基本信息后发送验证码验证"
			+"\n"+"谢谢配合！",5,15);
	private JFrame log_frame;
	private Register_ctl register_ctl;
	private String act,nam,pwd,pwd2,asw,code;
	private int securityId=1,sex=1;
	private String[] words= {"昵称：","账号：","密码：","再次输入密码：","性别：","您最喜欢的：","答案：","输入验证码" };
	/*
	 * 
	 * 构造
	 */
	public Register(JFrame jf,Sql sql) {
		this.setTitle("Steam注册");															//标题
		this.setIconImage(icon);															//图标
		this.setVisible(true);																//窗口可见
		this.setSize(600,380);																//大小
		this.setLocationRelativeTo(null); 
		this.setLayout(null);																//取消布局	
		this.setResizable(false);
		this.setDefaultCloseOperation(0);	
		log_frame=jf;
		register_ctl = new Register_ctl(sql);
		
		for(int i=0;i<texts.length;i++) {
			texts[i].setSize(180, 25);
			texts[i].setLocation(220, 10+i*35);
			this.add(texts[i]);
		}
		text_asw.setLocation(220, 230);
		text_test.setLocation(220, 269);
		text_test.setEnabled(false);
		for(int i=0;i<pfs.length;i++) {
			pfs[i].setSize(180, 25);
			pfs[i].setLocation(220, 10+2*37+i*37);
			this.add(pfs[i]);
		}
		ButtonGroup sex = new ButtonGroup();
		for(int i=0;i<2;i++) {
			rbtns[i].setSize(70, 25);
			rbtns[i].setLocation(230+i*80, 155);
			sex.add(rbtns[i]);
			this.add(rbtns[i]);
		}

		ButtonGroup security = new ButtonGroup();
		for(int i=2;i<5;i++) {
			rbtns[i].setSize(70, 25);
			rbtns[i].setLocation(230+(i-2)*80, 195);
			security.add(rbtns[i]);
			this.add(rbtns[i]);
		}

		for(int i=0;i<3;i++) {
			btns[i].setSize(100, 30);
			btns[i].setLocation(150*i, 300);
			this.add(btns[i]);
			btns[i].addActionListener(this);
		}
		btn_cfm.setBounds(420, 265, 100, 30);
		JOptionPane.showConfirmDialog(this, about,"注册规则",JOptionPane.DEFAULT_OPTION);

	}
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", 1, 18));
		g.drawImage(register_bg, 0, 0, this);
		for(int i =0;i<words.length;i++) {
			g.drawString(words[i], 90, 60+i*37);
		}
		g.drawString("（QQ号）", 420, 95);
		g.drawString("6~12个数字", 420, 135);
		
		for(int i = 0 ; i<objects.length;i++) {
			for(int j=0;j<objects[i].length;j++) {
				objects[i][j].repaint();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//确认按钮
		b:if (source==btn_cfm) 														
		{		
			if(btn_girl.isSelected()) {
				sex=0;
			}else sex = 1;

			if(btn_animal.isSelected()) {
				securityId=2;
			}else {
				if(btn_people.isSelected()) 
					securityId=3;
				else securityId=1;
			}

			act=text_act.getText();
			nam=text_nam.getText();
			pwd=text_pwd.getText();
			pwd2=text_pwd2.getText();
			asw=text_asw.getText();
			switch(register_ctl.startTestInput(act, nam, pwd,pwd2, asw)){
			case 0:
				JOptionPane.showConfirmDialog(this, "已向你QQ邮箱里发送验证码"+"\n"+"请完成验证","提示",JOptionPane.DEFAULT_OPTION);
				break;
			case 1:
				JOptionPane.showConfirmDialog(this, "请填写完整","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2:
				JOptionPane.showConfirmDialog(this, "请控制在10字符以内","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 3:
				JOptionPane.showConfirmDialog(this, "QQ号格式不正确","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 4:
				JOptionPane.showConfirmDialog(this, "密码格式不正确","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 5:
				JOptionPane.showConfirmDialog(this, "QQ号重复，请更换 ","提示",JOptionPane.DEFAULT_OPTION);
				break b;
			case 6:
				JOptionPane.showConfirmDialog(this, "不要含有空格或%","提示",JOptionPane.DEFAULT_OPTION);
				break b;
			case 7:
				JOptionPane.showConfirmDialog(this, "两次密码不一致 ","提示",JOptionPane.DEFAULT_OPTION);
				break b;
			}	
			text_pwd.setEnabled(false);
			text_pwd2.setEnabled(false);
			btn_cfm.setEnabled(false);
			for(int i=0;i<texts.length;i++) {
				texts[i].setEnabled(false);
			}
			for(int i=0;i<rbtns.length;i++) {
				rbtns[i].setEnabled(false);
			}
			text_test.setEnabled(true);
			btn_test.setEnabled(true);
		}	
		//验证按钮
		if(source==btn_test) {
			code = text_test.getText();
			switch (register_ctl.startTestSecurityCode(code)) {
			case 0 :
				register_ctl.startRegister(act, pwd, nam, sex,securityId,asw,new Timestamp(System.currentTimeMillis()));
				JOptionPane.showConfirmDialog(this, "注册成功，赶快登录吧 ^_^ ","提示",JOptionPane.DEFAULT_OPTION);
				log_frame.setEnabled(true);
				this.dispose();	
				break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "请输入验证码 ","提示",JOptionPane.DEFAULT_OPTION);
				break;
			case 2 :
				JOptionPane.showConfirmDialog(this, "请输入正确的验证码 ","提示",JOptionPane.DEFAULT_OPTION);
				break;
			case 3 :
				JOptionPane.showConfirmDialog(this, "验证码错误","提示",JOptionPane.DEFAULT_OPTION);
				break;
			}
		}
		//退出
		if (source==btn_ext) 														
		{		
			int n=JOptionPane.showConfirmDialog(this,"你确认要退出注册吗","退出注册",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) 
			{
				log_frame.setEnabled(true);
				this.dispose();
			}
		}	

	}
}
