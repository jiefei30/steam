package model.sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//和数据库连接
public class GetConn_sql {
	private static String driver="com.mysql.cj.jdbc.Driver";
	private static String url ="jdbc:mysql://localhost:3306/steam2?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true";
	private static String user="root";
	private static String pwd="12345";
	private Connection conn ;     
	//建立连接（连接对象内部其实包含了Socket对象，是一个远程连接。比较耗时！这是Connection对象管理的一个要点 ）
	// 真正的开发中，为了提高效率，都会用连接池来管理连接对象
	//连接数据库
	public GetConn_sql() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			if(conn!=null) {
				System.out.println("数据库连接成功");
			}
			conn.setAutoCommit(false);										//取消自动提交
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();											//发生错误时回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}  
	
	public Connection getConn() {
		return conn;
	}
}
