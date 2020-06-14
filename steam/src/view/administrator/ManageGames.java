package view.administrator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.ModifyGames_ctl;
import controller.table.ManageGames_ctl;
import model.Model;
import model.sql.Sql;
import tools.Pageinfo;
import view.Viewer;

public class ManageGames extends Viewer implements ActionListener{
	/**
	 * 商店
	 */
	private static final long serialVersionUID = 2836941430985440294L;
	private Model model;
	private ManageGames_ctl managegames_ctl;
	private JFrame steamJFrame;
	private JCheckBox del=new JCheckBox("待删除");
	private JButton btn_add=new JButton("添加游戏"),btn_modify=new JButton("修改"),btn_search=new JButton("搜索");
	private JLabel jlb2,jlb3,jlb4;
	private JComboBox jcb; 
	private JTextField text_modify =new JTextField(10);
	private String str_id;
	private String[] jcb_content,categoryNumber;
	Object[] options = {"恢复","删除","取消"};
	public ManageGames(Model model,Sql sql,JFrame jf,ManageGames_ctl managegames_ctl) {
		super(sql,managegames_ctl);
		this.model=model;
		pageinfo = new Pageinfo();
		this.managegames_ctl=managegames_ctl;
		steamJFrame=jf;

		text_modify.setBounds(950,50,40,40);
		text_search.setBounds(340, 50, 150, 40);

		jcb_content=new String[sql.getCategory_sql().getCategoryNumber()+1];
		jcb_content[0]="全部";
		categoryNumber = sql.getCategory_sql().getAllCategories();
		for(int i=1;i<jcb_content.length;i++) {
			jcb_content[i]=categoryNumber[i-1];
		}
		jcb = new JComboBox(jcb_content);  
		jcb.setBounds(700, 50, 80, 40);
		jcb.addActionListener(this);

		btn_modify.setBounds(1000,50,80,40);
		btn_search.setBounds(500,50,80,40);
		btn_add.setBounds(1200,50,100,40);
		del.setBounds(20, 50, 100, 40);
		btn_modify.addActionListener(this);	
		btn_search.addActionListener(this);
		btn_add.addActionListener(this);
		del.addActionListener(this);
		if((int)(managegames_ctl.getVaild()/20)==0) {
			btn_down.setEnabled(false);
			btn_last.setEnabled(false);
		}

		jlb2= new JLabel("根据类型排序");
		jlb3= new JLabel("请输入想要修改的ID");
		jlb4= new JLabel("请输入想要搜索的游戏名");
		jlb2.setBounds(600, 50, 100, 40);
		jlb3.setBounds(800, 50, 150, 40);
		jlb4.setBounds(190, 50, 150, 40);
		JComponent[] objects= {btn_modify,btn_search,btn_add,del,text_modify,text_search,jcb,jlb2,jlb3,jlb4};
		for(int i=0;i<objects.length;i++) {
			option.add(objects[i]);
		}

		table = new JTable(managegames_ctl.getRowData(pageinfo,str_search), managegames_ctl.getColumnNames()){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setTable(table);
		table.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				text_modify.setText(table.getValueAt(table.getSelectedRow(),1).toString());
				str_id=text_modify.getText();
				if(managegames_ctl.getDel()) {
					if(e.getClickCount()==2) {		
						setDeleted();
					}
				}else {	
					if(e.getClickCount()==2) {
						createdModify();
					}
				}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		}); 
	}	

	public void actionPerformed(ActionEvent e) {
		source = e.getSource();
		str_search=text_search.getText();
		//查看待删除游戏
		if(source==del) {
			if(del.isSelected()) {
				pageinfo.setPage(1);
				managegames_ctl.setDel(true);
			}else managegames_ctl.setDel(false);
			updata();
		}
		//搜索游戏
		a:if(source==btn_search) {
			if(managegames_ctl.testSearch(str_search)==2)
			{
				JOptionPane.showConfirmDialog(this, "请输入正确的游戏名","警告",JOptionPane.DEFAULT_OPTION);
				break a;
			}
			table.setModel(managegames_ctl.getTableModel());
			pageinfo.setPage(1);
			managegames_ctl.startUpdata(table, pageinfo,str_search);
		}
		//修改游戏
		b:if(source==btn_modify) {

			switch(managegames_ctl.testInputId(str_id)) {
			case 0 :break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "请输入要修改的id","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2 :
				JOptionPane.showConfirmDialog(this, "请输入正确的id","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			if(managegames_ctl.gameIdExist(str_id)) {
				JOptionPane.showConfirmDialog(this, "不存在该ID","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			if(managegames_ctl.getDel()) {
				setDeleted();
			}else createdModify();
		}
		//根据类型查找
		if(source==jcb) {
			pageinfo.setPage(1);
			pageinfo.setCategoryid(jcb.getSelectedIndex());
			updata();
			updataJLabel();
		}
		//添加游戏
		if(source==btn_add) {
			steamJFrame.setEnabled(false);
			new AddGame(model,sql,steamJFrame,this);
		}
		pageJump();
		pageChange();
	}
	public ManageGames_ctl getManagegames_ctl() {
		return managegames_ctl;
	}
	public JTextField getText_modify() {
		return text_modify;
	}
	public void setDeleted() {
		int response=JOptionPane.showOptionDialog (table, "恢复该游戏，或者永久删除","对待删除游戏的操作",JOptionPane.YES_OPTION ,JOptionPane.PLAIN_MESSAGE,
				null, options, options[0] ) ;
		if (response == 0) {
			managegames_ctl.recoverGame(Integer.valueOf(str_id).intValue());
			JOptionPane.showMessageDialog(table,"已成功恢复");
		}
		else if(response == 1) {
			managegames_ctl.realDelete(Integer.valueOf(str_id).intValue());
			JOptionPane.showMessageDialog(table,"已永久删除");
		}
		else if(response == 2) {}
		updata();
	}
	public void createdModify() {
		updata();
		steamJFrame.setEnabled(false);
		model.getGames().setId(Integer.valueOf(str_id).intValue());
		new ModifyGames(steamJFrame,model,sql,this);
	}
}
