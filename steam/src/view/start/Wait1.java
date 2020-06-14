package view.start;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import tools.Constant;
public class Wait1 extends JFrame{
	/**
	 * 第一个载入动画
	 */
	private static final long serialVersionUID = -6252620521894905030L;
	private Image wait1_bg=(new ImageIcon(Constant.IMAGEPATH+"wait1.jpg")).getImage();	   //登录背景
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();			
	public Wait1() {
		this.setTitle("连接中...");															//标题
		this.setIconImage(icon);															//图标
		this.setVisible(true);																//窗口可见
		this.setSize(441,320);							//大小
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);								//关闭窗口
		this.setLayout(null);																//取消布局	
		this.setResizable(false);
	}
	public static void main(String[] args) {
		new Wait1();
	}
	public void drawDelay (Graphics g) {
		g.setColor(Color.WHITE);
		g.drawImage(wait1_bg, 0, 20, this);
		g.drawRect(15, 288, 405,14);
		for(int i=20;i<=410;) {
			g.draw3DRect(i, 290, 10, 10,true);
			i+=20;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.dispose();
	}
	public void paint(Graphics g) {
		drawDelay(g);
		new Login();
	}
}
