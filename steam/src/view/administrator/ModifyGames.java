package view.administrator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import controller.ModifyGames_ctl;
import model.Model;
import model.sql.Sql;
import tools.Constant;
public class ModifyGames extends JFrame implements ActionListener{
	/**
	 * 修改信息
	 */
	private static final long serialVersionUID = 4148628129312677952L;
	private Image register_bg=(new ImageIcon(Constant.IMAGEPATH+"register.jpg")).getImage();	  
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JButton btn_cfm =new JButton("修改");
	private JButton btn_del =new JButton("删除");
	private JButton btn_ext =new JButton("返回");
	private JButton[] btns = {btn_cfm,btn_del,btn_ext};
	private JRadioButton btn_yes = new JRadioButton("可售");
	private JRadioButton btn_no = new JRadioButton("下架");
	private JTextField text_price =new JTextField(10);
	private JTextField text_nam =new JTextField(10);
	private JTextField text_time =new JTextField(10);
	private JComponent[] objects= {btn_cfm,btn_del,btn_ext,btn_yes,btn_no,text_nam,text_time,text_price};
	private JLabel jlb;
	private String price;
	private int state;
	private JFrame steamJFrame;
	private Model model;
	private ManageGames managegames;
	private ModifyGames_ctl modifygames_ctl;
	public ModifyGames(JFrame jf,Model model,Sql sql,ManageGames managegames) {
		this.setTitle("修改信息");															//标题
		this.setIconImage(icon);															//图标
		this.setVisible(true);																//窗口可见
		this.setSize(600,350);																//大小
		this.setLocationRelativeTo(null); 
		this.setLayout(null);																//取消布局	
		this.setResizable(false);
		this.setDefaultCloseOperation(0);
		this.managegames=managegames;
		this.model=model;
		modifygames_ctl=new ModifyGames_ctl(model, sql);
		steamJFrame=jf;

		text_nam.setText(sql.getGames_sql().getName(model.getGames().getId()));
		
			text_time.setText(sql.getGames_sql().getRegtime(model.getGames().getId()));
		
		text_price.setText(Integer.toString(sql.getGames_sql().getPrice(model.getGames().getId())));	
		text_nam.setEnabled(false);
		text_time.setEnabled(false);
		for(int i=5;i<objects.length;i++) {
			objects[i].setBounds(220, 35+(i-5)*40, 180, 25);
		}
		if(sql.getGames_sql().getState(model.getGames().getId())==1) btn_yes.setSelected(true);
		else btn_no.setSelected(true);

		ButtonGroup btn_state = new ButtonGroup();
		btn_yes.setBounds(230, 195, 70, 25);
		btn_no.setBounds(310, 195, 70, 25);
		btn_state.add(btn_yes);
		btn_state.add(btn_no);
		
		for(int i=0;i<btns.length;i++) {
			btns[i].setBounds(150+i*100, 250, 70, 30);
			btns[i].addActionListener(this);
		}

		jlb = new JLabel(sql.getCategory_sql().getCategory(sql.getGames_sql().getCategoryId(model.getGames().getId())));
		jlb.setBackground(Color.gray);
		jlb.setBounds(220,150,40,30);
		this.add(jlb);
		for(int i=0;i<objects.length;i++) {
			this.add(objects[i]);
		}
	}
	public void paint(Graphics g) {
		String[] drawString= {"名称：","时间：","价钱：","类型：","状态："};
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", 1, 18));
		g.drawImage(register_bg, 0, 0, this);
		for(int i=0;i<drawString.length;i++) {
			g.drawString(drawString[i], 150, 80+i*40);
		}
		for(int i=0;i<objects.length;i++) {
			objects[i].repaint();
		}
		jlb.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//确认按钮
		b:if (source==btn_cfm) 														
		{		
			if(btn_yes.isSelected()) {
				state=1;
			}else state=0;
			price=text_price.getText();
			switch(modifygames_ctl.startTestInput(price, state)) {
			case 0:break;
			case 1:
				JOptionPane.showConfirmDialog(this, "请输入正确的价格","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2:
				JOptionPane.showConfirmDialog(this, "请输入价格","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 3:
				JOptionPane.showConfirmDialog(this, "请做出修改","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 4:
				JOptionPane.showConfirmDialog(this, "该游戏处于特卖状态，不可下架","提示",JOptionPane.DEFAULT_OPTION);
				break b;
			}	
			model.getGames().setPrice(Integer.valueOf(price).intValue());
			model.getGames().setState(state);
			modifygames_ctl.startModify();
			JOptionPane.showConfirmDialog(this, "修改成功","提示",JOptionPane.DEFAULT_OPTION);
			steamJFrame.setEnabled(true);
			this.dispose();
		}	

	if (source==btn_ext) 														
	{		
		steamJFrame.setEnabled(true);
		this.dispose();
	}	
	a:if (source==btn_del) 														
	{		
		int n=JOptionPane.showConfirmDialog(this,"你确定要删除游戏吗?","删除",JOptionPane.YES_NO_OPTION);	
		if(n==JOptionPane.YES_OPTION) {
			switch(modifygames_ctl.startDelet()) {
			case 0:break;
			case 1:
				JOptionPane.showMessageDialog(this,"该游戏已被玩家购买，不能删除","提示",JOptionPane.CLOSED_OPTION);	
				break a;
			case 2:
				JOptionPane.showMessageDialog(this, "该游戏处于特卖状态，不能删除","提示",JOptionPane.CLOSED_OPTION);	
				break a;
			}	
			JOptionPane.showMessageDialog(this,"删除成功","提示",JOptionPane.CLOSED_OPTION);	
			steamJFrame.setEnabled(true);
			managegames.updata();
			managegames.pageChange();
			managegames.updataJLabel();
			this.dispose();
		}
	}	
	managegames.updata();
	managegames.getText_modify().setText("");
	}
}
