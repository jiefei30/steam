package view.start;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Model;
import model.Users;
import model.sql.Sql;
import tools.Constant;
import view.SteamJTP;
public class Wait2 extends Wait1{
	/**
	 * 第一个载入动画
	 */
	private static final long serialVersionUID = -6252620521894905030L;
	private Sql sql;
	private Model model;
	public Wait2(Model model,Sql sql) {
		this.sql=sql;
		this.model=model;
	}
	public void paint(Graphics g) {
		drawDelay(g);
		new SteamJTP(model,sql);
		if(model.getUsers().getIdentity()==1) new Sell();
	}
}
class Sell extends JFrame {
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();		
	private ImageIcon bg = new ImageIcon(Constant.IMAGEPATH+"advertisement.jpg");
	private JLabel jb = new JLabel(bg);
	public Sell() {
		this.setTitle("秋季特卖");
		this.setIconImage(icon);															//图标
		this.setVisible(true);																//窗口可见
		this.setSize(600,698);							//大小
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(1);								//关闭窗口
		this.setLayout(null);																//取消布局	
		this.setResizable(false);
		jb.setBounds(0, 0, 600, 698);
		this.add(jb);
	}
}
