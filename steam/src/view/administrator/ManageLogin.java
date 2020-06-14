package view.administrator;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import controller.table.ManageLogin_ctl;
import model.sql.Sql;
import view.Viewer;

public class ManageLogin extends Viewer{
	private ManageLogin_ctl manageLogin_ctl;
	private JLabel jlb;

	public ManageLogin(Sql sql,ManageLogin_ctl manageLogin_ctl,JFrame jf) {
		super(sql,manageLogin_ctl);
		this.manageLogin_ctl= manageLogin_ctl;

		text_search.setBounds(630, 50, 50, 40);		
		option.add(text_search);

		btn_search=new JButton("搜索");
		btn_search.setBounds(700,50,80,40);
		btn_search.addActionListener(this);
		option.add(btn_search);

		jlb= new JLabel("请输入某ID的所有登录");
		jlb.setBounds(490, 50, 150, 40);
		option.add(jlb);

		// 创建一个表格，指定 所有行数据 和 表头
		table = new JTable(manageLogin_ctl.getRowData(pageinfo,0), manageLogin_ctl.getColumnNames()){
			   public boolean isCellEditable(int row, int column){
			       return false;
			   }
			};
		setTable(table);
		table.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
            	text_search.setText(table.getValueAt(table.getSelectedRow(),2).toString());  
            	str_search=text_search.getText();
            	if(e.getClickCount()==2) {
            		search();
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
		if(str_search.length()==0) {
			str_search="0";
		}
		//搜索按钮
		b:if(source==btn_search) {
			pageinfo.setPage(1);
			str_search=text_search.getText();
			switch(manageLogin_ctl.testInputId(str_search)) {
			case 0 :break;
			case 1 :
				manageLogin_ctl.startUpdata(table, pageinfo,zero);
				break b;
			case 2 :
				JOptionPane.showConfirmDialog(this, "请输入正确的用户id","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			if(!manageLogin_ctl.userIdExist(str_search)) {
				JOptionPane.showConfirmDialog(this, "不存在该ID","警告",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			search();
		}
		pageJump();
		pageChange();
	}
	public void search() {
		updata();
		pageJump();
		pageChange();
	}
}
