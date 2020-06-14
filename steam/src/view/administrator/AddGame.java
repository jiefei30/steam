package view.administrator;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.AddGame_ctl;
import model.Model;
import model.sql.Sql;
import tools.Constant;

public class AddGame extends JFrame implements ActionListener{  
	/**
	 * 添加游戏视图层
	 */
	private static final long serialVersionUID = -4567273164475073558L;
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JButton btn_cfm =new JButton("添加");
	private JButton btn_ext =new JButton("返回");
	private JTextField text_price =new JTextField(10);
	private JTextField text_nam =new JTextField(10);
	private JLabel[] jlbs = new JLabel[3];
	private String[] jlbsText = {"名称：","价钱：","类型："};
	private JComboBox jcb;
	private String name,price;
	private String[] jcb_content;
	private Model model;
	private JFrame steamJframe;
	private ManageGames managegames;
	private AddGame_ctl addgame_ctl ;
	public AddGame(Model model,Sql sql,JFrame jf,ManageGames mg) {
		this.setTitle("添加游戏信息");														//标题
		this.setIconImage(icon);															//图标
		this.setVisible(true);																//窗口可见
		this.setSize(600,350);																//大小
		this.setLocationRelativeTo(null); 
		this.setLayout(null);																//取消布局	
		this.setResizable(false);
		this.setDefaultCloseOperation(0);
		this.model=model;
		steamJframe=jf;
		managegames=mg;
		addgame_ctl = new AddGame_ctl(model, sql);
		
		jcb_content = sql.getCategory_sql().getAllCategories();
		jcb = new JComboBox(jcb_content);  
        jcb.setBounds(220, 115, 180, 25);
        jcb.addActionListener(this);
        this.add(jcb);
        
        for(int i=0;i<jlbs.length;i++) {
        	jlbs[i] = new JLabel(jlbsText[i]);
        	jlbs[i].setBounds(150, 35+40*i, 50, 25);
        	this.add(jlbs[i]);
        }
        
        text_nam.setBounds(220, 35, 180, 25);
		text_price.setBounds(220, 75, 180, 25);
		this.add(text_nam);
		this.add(text_price);
				
		btn_cfm.setBounds(150, 250, 70, 30);	
		btn_ext.setBounds(350, 250, 70, 30);	
		this.add(btn_cfm);
		this.add(btn_ext);		
		btn_cfm.addActionListener(this);
		btn_ext.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		name=text_nam.getText();
		price=text_price.getText();	
		//确认
		b:if(source==btn_cfm) {
			switch(addgame_ctl.startTestGame(name, price)) {
			case 0 :break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "请填写完整","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2 :
				JOptionPane.showConfirmDialog(this, "请填写正确的价格","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 3 :
				JOptionPane.showConfirmDialog(this, "游戏名重复","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 4 :
				JOptionPane.showConfirmDialog(this, "不要含有空格和%","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			model.getGames().setCategoryid(jcb.getSelectedIndex()+1);
			model.getGames().setName(name);
			model.getGames().setPrice(Integer.valueOf(price).intValue());
			model.getGames().setRegtime(new Timestamp(System.currentTimeMillis()).toString());
			model.getGames().setState(1);
			addgame_ctl.startAdd();
			JOptionPane.showConfirmDialog(this, "添加成功","提示",JOptionPane.DEFAULT_OPTION);
			steamJframe.setEnabled(true);
			managegames.updata();
			managegames.pageChange();
			managegames.updataJLabel();
			this.dispose();
			
		}
		//退出
		if (source==btn_ext) 														
		{		
				steamJframe.setEnabled(true);
				this.dispose();
		}	
		
	}
}
