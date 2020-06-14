package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;

import controller.table.MyGames_ctl;
import model.Model;
import model.sql.Sql;

public class MyGames extends Viewer implements ActionListener{
	private MyGames_ctl mygames_ctl;
	public MyGames(Model model,Sql sql,MyGames_ctl mygames_ctl) {
		super(sql,mygames_ctl);
		this.mygames_ctl=mygames_ctl;
		// 创建一个表格，指定 所有行数据 和 表头
		table = new JTable(mygames_ctl.getRowData(pageinfo), mygames_ctl.getColumnNames()){
			   public boolean isCellEditable(int row, int column){
			       return false;
			   }
			};
		setTable(table);
	}
	public MyGames_ctl getMygames_ctl() {
		return mygames_ctl;
	}
	public void actionPerformed(ActionEvent e) {
		source = e.getSource();
		pageJump();
		pageChange();
	}
}
